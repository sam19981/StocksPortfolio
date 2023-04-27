package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import connection.AlphaVantageImpl;
import connection.Connection;
import parsers.UserXmlReaderImpl;
import parsers.UserXmlWriterImpl;
import utilities.Utility;
import view.Bargraph;
import view.Draw;

import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

/**
 * Model class acts as a delegator to all other data model classes, it talks to the
 * controller and call the appropriate model based on the input passed by the
 * controller.
 */
public class Model implements IModel {
  private final UserXmlWriterImpl write;
  private final HashSet<String> userNames;
  private User currentUser;
  private String fileLink;
  private final HashSet<String> symbols = new HashSet<>();
  private final HashMap<String, Float> map = new LinkedHashMap<>();
  private final List<Stock> cache = new ArrayList<>();

  /**
   * Default constructor initializes all the data.
   * necessary for starting up the program.
   */
  public Model() {
    write = new UserXmlWriterImpl();
    userNames = new HashSet<>();

    try {
      String cwd = System.getProperty("user.dir");
      Path path = Paths.get(cwd + "/users");
      Files.createDirectories(path);
    } catch (IOException e) {
      System.err.println("Failed to create directory!" + e.getMessage());
    }

    try (BufferedReader br = new BufferedReader(new FileReader("UserDetails.csv"))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        userNames.add(Arrays.asList(values).get(0)
                .replaceAll("[^\\p{L}\\p{N}\\p{Z}\\p{Sm}" +
                        "\\p{Sc}\\p{Sk}\\p{Pi}\\p{Pf}\\p{Pc}\\p{Mc}]", ""));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try (BufferedReader br = new BufferedReader(new
            FileReader("stockDirectory/StockSymbols.csv"))) {
      String line;
      while ((line = br.readLine()) != null) {
        symbols.add(line.split(",")[0]);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Constructor to initializes a new user in memory.
   *
   * @param userVal - the new user to be initialized in the memory.
   */
  public Model(User userVal) {
    currentUser = userVal;
    write = new UserXmlWriterImpl();
    userNames = new HashSet<>();
  }

  private int loadData(String username, String password) {
    try {
      List<String> fileData = Utility.loadCsvData("UserDetails.csv");
      String line;
      for (String fileDatum : fileData) {
        line = fileDatum;
        String[] values = line.split(",");
        if (Arrays.asList(values).get(0)
                .replaceAll("[^\\p{L}\\p{N}\\p{Z}\\p{Sm}\\p{Sc}" +
                        "\\p{Sk}\\p{Pi}\\p{Pf}\\p{Pc}\\p{Mc}]", "")
                .equals(username)) {
          if (Arrays.asList(values).get(1)
                  .replaceAll("[^\\p{L}\\p{N}\\p{Z}\\p{Sm}\\p{Sc}" +
                          "\\p{Sk}\\p{Pi}\\p{Pf}\\p{Pc}\\p{Mc}]", "")
                  .equals(password)) {
            fileLink = Arrays.asList(values).get(values.length - 1);
            UserXmlReaderImpl r = new UserXmlReaderImpl();
            User u = r.readData(fileLink, password);
            if (u == null) {
              return -2;
            }
            currentUser = u;
            return 0;
          }
        }
      }
    } catch (IOException e) {
      return -1;
    }
    return -3;
  }

  private int directLoadData(String password) {
    UserXmlReaderImpl r = new UserXmlReaderImpl();
    User u = r.readData(fileLink, password);
    if (u == null) {
      return -2;
    }
    currentUser = u;
    return 0;
  }

  @Override
  public boolean checkExistingUser(String username) {
    return !userNames.contains(username);
  }

  @Override
  public int createUser(String username, String password, String fileName) {
    User user = UserImpl.createBuilder().setUserName(username).setPassword(password).create();
    if (user == null) {
      return -2;
    }
    if (userNames.contains(username)) {
      return -1;
    }
    userNames.add(username);
    int status = 0;
    if (fileName == null || fileName.isEmpty()) {
      File myObj = new File("users/" + username + "File.txt");
      try {
        if (myObj.createNewFile()) {
          fileName = username + "File.txt";
        }
      } catch (IOException e) {
        return -3;
      }
    } else {
      fileLink = "users/" + fileName;
      status = directLoadData(password);
    }
    if (status == 0) {
      fileLink = fileName;
      append("UserDetails.csv", username, password, "users/" + fileName);
      currentUser = user;
    } else if (status == -2) {
      userNames.remove(username);
    }
    return status;
  }

  @Override
  public int setUser(String username, String password) {
    if (userNames.contains(username)) {
      return loadData(username, password);
    }
    return -1;
  }

  private void append(String fileName, String username, String password, String userFile) {
    try {
      FileWriter fw = new FileWriter(fileName, true);
      PrintWriter out = new PrintWriter(fw);
      out.println();
      out.print(username + ",");
      out.print(password + ",");
      out.print(userFile);
      out.flush();
      out.close();
      fw.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public List<Portfolio> getUserPortFolios() {
    currentUser.executeAllPlans();
    return currentUser.getAllPortfolios();
  }

  @Override
  public float computePortfolioValues(String portfolioName, LocalDate d) {
    currentUser.executeAllPlans();
    return currentUser.computePortfolioValue(portfolioName, d);
  }


  @Override
  public boolean checkValidDay(String day) {
    int dateCheck;
    try {
      dateCheck = Integer.parseInt(day);
      if (dateCheck < 1 || dateCheck > 31) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  @Override
  public boolean checkValidMonth(String month) {
    int monthCheck;
    try {
      monthCheck = Integer.parseInt(month);
      if (monthCheck < 1 || monthCheck > 12) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  @Override
  public boolean checkValidYear(String year) {
    int yearCheck;
    try {
      yearCheck = Integer.parseInt(year);
      if (yearCheck < 1980 || yearCheck > LocalDate.now().getYear()) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  @Override
  public LocalDate checkValidDate(String year, String month, String date) {
    try {
      int yearCheck = Integer.parseInt(year);
      int monthCheck = Integer.parseInt(month);
      int dateCheck = Integer.parseInt(date);
      LocalDate d = LocalDate.of(yearCheck, monthCheck, dateCheck);
      if (d.isAfter(LocalDate.now())) {
        return null;
      }
      return d;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public float checkValidNaturalNumber(String number) {
    if (number.matches("-?\\d+(\\.\\d+)?")) {
      float res = Float.parseFloat(number);
      if (res <= 0) {
        return -1;
      }
      if (res % 1 != 0) {
        return -1;
      }
      return res;
    }
    return -1;
  }

  @Override
  public int addPortfolios(String portfolioName, String portfolioType) {
    if (portfolioType.equals("1")) {
      Portfolio p = PortfolioImpl.getBuilder().portfolioName(portfolioName).create();
      if (p != null) {
        return currentUser.addPortfolio(p);
      }
    } else if (portfolioType.equals("2")) {
      Portfolio p = FlexiblePortfolioImpl.getBuilder().portfolioName(portfolioName).create();
      if (p != null) {
        return currentUser.addPortfolio(p);
      }
    }
    save();
    return -1;
  }

  @Override
  public int validateStocksymbol(String symbol) {
    if (!symbols.contains(symbol)) {
      return 0;
    }
    return 1;
  }


  @Override
  public Stock createStock(String quantity,
                           LocalDate date, String symbol, float commissionFees) {
    if (checkValidNaturalNumber(quantity) == -1 || validateStocksymbol(symbol) == -1) {
      return null;
    }
    return StockImpl.getBuilder().quantity(Float.parseFloat(quantity))
            .purchaseDate(date)
            .stockSymbol(symbol)
            .commissionFees(commissionFees)
            .create();
  }

  @Override
  public void addStock(Portfolio portfolio, Stock stocks) {
    if (stocks == null) {
      if (cache.isEmpty()) {
        throw new IllegalArgumentException("No stocks to add");
      } else {
        for (Stock stock : cache) {
          portfolio.addStock(stock);
        }
      }
    } else {
      portfolio.addStock(stocks);
    }
    cache.clear();
    save();
  }

  @Override
  public void addStockCache(Stock stocks) {
    cache.add(stocks);
  }

  @Override
  public List<Stock> getStockCache() {
    return cache;
  }

  @Override
  public boolean checkValidFile(String filename) {
    File f = new File("users/" + filename);
    return f.exists() && !f.isDirectory() && (f.getName().toLowerCase().endsWith(".txt") ||
            f.getName().toLowerCase().endsWith(".xml"));
  }

  @Override
  public boolean checkValidUserName(String userName) {
    //DOC
    boolean valid = userName.length() >= 5 && userName.length() <= 30;
    if (valid) {
      for (int i = 0; i < userName.length(); i++) {
        char c = userName.charAt(i);
        valid = Character.isLetterOrDigit(c);
        if (!valid) {
          valid = c == '_';
          if (!valid) {
            break;
          }
        }
      }
    }
    if (valid) {
      valid = Character.isLetter(userName.charAt(0));
    }
    return valid & checkExistingUser(userName);
  }


  @Override
  public boolean checkValidPassword(String password) {
    if (!((password.length() >= 5)
            && (password.length() <= 15))) {
      return false;
    }

    // to check space
    if (password.contains(" ")) {
      return false;
    }

    //to check if password has numbers
    int count = 0;
    for (int i = 0; i <= 9; i++) {
      String str1 = Integer.toString(i);
      if (password.contains(str1)) {
        count = 1;
        break;
      }
    }
    return count != 0;
  }

  @Override
  public boolean checkValidStockName(String stock) {
    return !stock.isEmpty();
  }

  @Override
  public Portfolio getExistingPortfolio(String portfolioName) {
    return currentUser.getPortfolio(portfolioName);
  }

  @Override
  public List<Portfolio> getFlexiblePortfolios() {
    List<Portfolio> flexibleList = new ArrayList<>();
    for (Portfolio p : currentUser.getAllPortfolios()) {
      if (p.isFlexible()) {
        flexibleList.add(p);
      }
    }
    return flexibleList;
  }

  @Override
  public boolean checkValidPortfolioType(String portfolioType) {
    try {
      return Integer.parseInt(portfolioType) == 1 || Integer.parseInt(portfolioType) == 2;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public int sellStockFromPortfolio(String pName, String symbol, float quantity, LocalDate date) {
    currentUser.executeAllPlans();
    int status = currentUser.sellStockFromPortfolio(pName, symbol, quantity, date);
    save();
    return status;
  }

  @Override
  public void save() {
    try {
      write.writeData(fileLink, currentUser);
    } catch (ParserConfigurationException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public float getCostBasis(String pName, LocalDate date) {
    currentUser.executeAllPlans();
    Portfolio portfolio = currentUser.getPortfolio(pName);
    float current;
    if (date.isAfter(LocalDate.now())) {
      current = portfolio.getCostBasis(LocalDate.now());
    } else {
      current = portfolio.getCostBasis(date);
    }
    float fromPlans = 0;
    for (Plan plan : currentUser.getPlans()) {
      if (plan.getPortfolioName().equals(pName) && (plan.isOngoing() ||
              plan.getEndDate().isAfter(LocalDate.now()))) {
        if (plan.isOngoing()) {
          LocalDate iter = plan.getStartDate();
          while (!iter.isAfter(date) || !iter.isAfter(plan.getEndDate())) {
            fromPlans += plan.getAmount() + plan.getCommissionFees();
            iter = iter.plusDays(plan.getFrequency());
          }
        } else if (plan.getEndDate().isAfter(LocalDate.now())) {
          LocalDate iter = plan.getStartDate();
          LocalDate minDate;
          if (date.compareTo(plan.getEndDate()) <= 0) {
            minDate = date;
          } else {
            minDate = plan.getEndDate();
          }
          if (iter.equals(minDate)) {
            fromPlans += plan.getAmount();
            break;
          }
          while (!iter.isAfter(minDate)) {
            fromPlans += plan.getAmount();
            iter = iter.plusDays(plan.getFrequency());
          }
        }
      }
    }
    return current + fromPlans;
  }

  private String yaxisScale(String type, LocalDate dateComp) {
    if (type.equals("Month")) {
      return dateComp.getMonth().toString().substring(0, 3) + " " + dateComp.getYear();
    } else if (type.equals("Years")) {
      return String.valueOf(dateComp.getYear());
    } else {
      return dateComp.toString();
    }
  }

  /**
   * Computes the performance chart values taking in the start and the end date for the.
   * given portfolio.
   *
   * @param sDate    - Start date from which performance needs to be determined.
   * @param eDate    - End date till which performace needs to be determined.
   * @param portName - portfolio for which the graph need to be drawn.
   * @return - Bar graph object
   */
  public Draw drawBarGraph(LocalDate sDate, LocalDate eDate, String portName) {
    long difference_In_Days = ChronoUnit.DAYS.between(sDate, eDate);
    long difference_In_Years = ChronoUnit.YEARS.between(sDate, eDate);
    long difference_in_months = ChronoUnit.MONTHS.between(sDate, eDate);

    Map<String, Float> graphPoint = new LinkedHashMap<>();

    List<LocalDate> listOfDates = new ArrayList<>();

    listOfDates.add(sDate);

    LocalDate givenDate = LocalDate.parse(sDate.toString(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    LocalDate lastDay = sDate.with(lastDayOfYear());

    String yaxisType = "";

    if (difference_In_Days < 30) {
      for (int i = 1; i < difference_In_Days; i++) {

        if (LocalDate.now().isAfter((sDate.plusDays(i)))) {
          listOfDates.add(sDate.plusDays(i));
        }
      }
      yaxisType = "Day";
    } else if (difference_in_months < 30 && difference_in_months >= 5) {
      for (int i = 1; i < difference_in_months; i++) {
        LocalDate lastDayOfMonthDateGivenDate = givenDate.withDayOfMonth(
                givenDate.getMonth().length(givenDate.isLeapYear()));
        if (LocalDate.now().isAfter(lastDayOfMonthDateGivenDate.plusMonths(i))) {
          listOfDates.add(lastDayOfMonthDateGivenDate.plusMonths(i));
        }

      }
      yaxisType = "Month";
    } else {
      if (difference_In_Years >= 5 && difference_In_Years < 30) {
        for (int i = 1; i <= difference_In_Years; i++) {
          if (LocalDate.now().isAfter(lastDay.plusYears(i))) {
            listOfDates.add(lastDay.plusYears(i));
          }
        }
        yaxisType = "Years";
      } else {
        if (difference_In_Years > 30) {
          long unitsToadd = (long) Math.ceil(difference_In_Years / 30d);
          for (long i = unitsToadd; i < difference_In_Years; i = i + unitsToadd) {
            if (LocalDate.now().isAfter(lastDay.plusYears(i))) {
              listOfDates.add(lastDay.plusYears(i));
            }
          }
          yaxisType = "Years";
        } else if (difference_In_Days > 30 && difference_In_Days <= 150) {
          long unitsToadd = (long) Math.ceil(difference_In_Days / 30d);
          for (long i = unitsToadd; i < difference_In_Days; i = i + unitsToadd) {
            if (LocalDate.now().isAfter(sDate.plusDays(i))) {
              listOfDates.add(sDate.plusDays(i));
            }
          }
          yaxisType = "Day";
        } else {
          if (difference_in_months > 30 && difference_in_months <= 60) {
            long unitsToadd = (long) Math.ceil(difference_in_months / 30d);
            for (long i = unitsToadd; i < difference_in_months; i = i + unitsToadd) {
              LocalDate lastDayOfMonthDateGivenDate = givenDate.withDayOfMonth(
                      givenDate.getMonth().length(givenDate.isLeapYear()));
              if (LocalDate.now().isAfter(lastDayOfMonthDateGivenDate.plusMonths(i))) {
                listOfDates.add(lastDayOfMonthDateGivenDate.plusMonths(i));
              }
            }
            yaxisType = "Month";
          }
        }
      }
    }

    listOfDates.add(eDate);

    float maxPort = 0;
    for (LocalDate i : listOfDates) {
      float portVal = computePortfolioValues(portName, i);
      if (portVal > maxPort) {
        maxPort = portVal;
      }
      graphPoint.put(yaxisScale(yaxisType, i), portVal);
    }
    float scale = maxPort / 50;

    return new Bargraph(graphPoint, scale, portName, eDate.toString(), sDate.toString());

  }

  @Override
  public void setCommissionFees(String p, String s, float commissionFees) {
    if (currentUser.getPortfolio(p).isFlexible()) {
      currentUser.getPortfolio(p).getStock(s).setCommissionFees(commissionFees);
    } else {
      throw new IllegalArgumentException("Cannot add commission fees for Inflexible Portfolios");
    }
    save();
  }

  @Override
  public Float checkCommissionFees(String commissionFees) {
    float fee;
    try {
      fee = Float.parseFloat(commissionFees);
    } catch (Exception e) {
      return null;
    }
    if (fee <= 2 || fee > 40) {
      return null;
    }
    return fee;
  }

  @Override
  public float validatePercentage(String percentage) {
    float p;
    try {
      p = Float.parseFloat(percentage);
      if (p <= 0 || p > 100) {
        return -1;
      }
    } catch (Exception e) {
      return -1;
    }
    return p;
  }


  @Override
  public boolean checkForDate(String symbol, LocalDate date) {
    Connection connection = new AlphaVantageImpl();
    float val;
    try {
      val = Utility.readOnDate(connection.fetch(symbol, date), date, true);
    } catch (Exception e) {
      return false;
    }
    return val != 0;
  }

  @Override
  public void addPlan(LocalDate date, LocalDate date2, String pName,
                      HashMap<String, Float> map, float amount, float commissionFees,
                      long frequency, boolean isOngoing) {
    if (currentUser.getPortfolio(pName) == null) {
      addPortfolios(pName, "2");
    }
    Plan plan = PlanImpl.getBuilder().portfolioName(pName).frequency(frequency).amount(amount)
            .commissionFees(commissionFees).startDate(date).endDate(date2).hashMap(map)
            .isOngoing(isOngoing).create();
    if (plan == null) {
      throw new IllegalArgumentException("Cannot create a plan with illegal arguments.");
    }
    currentUser.addPlan(plan);
    plan.execute(currentUser);
    save();
  }

  @Override
  public float updateMap(String symbol, float percent) {
    if (map.containsKey(symbol)) {
      throw new IllegalArgumentException("Cannot add same stock again");
    }
    float preTotal = fetchMapTotalValue();
    if (preTotal >= 100) {
      throw new IllegalArgumentException("Cannot have stocks more than 100%");
    } else {
      float postTotal = preTotal + percent;
      if (postTotal <= 100) {
        map.put(symbol, percent);
        return postTotal;
      } else {
        throw new IllegalArgumentException("Cannot have stocks more than 100%");
      }
    }
    //return preTotal;
  }

  @Override
  public float fetchMapTotalValue() {
    float total = 0;
    for (Float f : map.values()) {
      total += f;
    }
    return total;
  }

  @Override
  public HashMap<String, Float> getHashMap() {
    return map;
  }

  @Override
  public void removeStockFromCache(int i) {
    cache.remove(i);
  }

  @Override
  public void removeStockFromMap(String symbol) {
    map.remove(symbol);
  }

  @Override
  public boolean validateInput(String text, String type) {
    switch (type) {
      case "Enter a portfolio name to apply the plan. " +
              "New portfolio will be created if the portfolio does not exist":
      case "Please add a new portfolio name":
        Portfolio p = getExistingPortfolio(text);
        if (p != null) {
          throw new IllegalArgumentException("Portfolio of the given name already exists. " +
                  "Please pick another one.");
        }
        break;
      case "Please add a commission fees between $2 and $40":
        Float cFees = checkCommissionFees(text);
        if (cFees == null) {
          throw new IllegalArgumentException("Please enter valid commission fees");
        }
        break;
      case "Please add a valid Stock symbol":
        if (validateStocksymbol(text) == 0) {
          throw new IllegalArgumentException("Please enter a valid stock symbol");
        }
        break;
      case "Please enter a quantity greater than 1":
        float q = checkValidNaturalNumber(text);
        if (q < 0) {
          throw new IllegalArgumentException("Please enter valid quantity");
        }
        break;
      case "day":
        if (!checkValidDay(text)) {
          throw new IllegalArgumentException("Invalid day");
        }
        break;
      case "month":
        if (!checkValidMonth(text)) {
          throw new IllegalArgumentException("Invalid month");
        }
        break;
      case "year":
        if (!checkValidYear(text)) {
          throw new IllegalArgumentException("Invalid year");
        }
        break;
      default:
        break;
    }
    return true;
  }

  @Override
  public void clearMap() {
    map.clear();
  }

}


