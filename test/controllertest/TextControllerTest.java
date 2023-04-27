package controllertest;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import controller.IController;
import controller.TextController;
import model.IModel;
import model.Portfolio;
import model.PortfolioImpl;
import model.Stock;
import model.StockImpl;
import model.User;
import model.UserImpl;
import parsers.UserXmlReaderImpl;
import parsers.UserXmlWriterImpl;
import view.Draw;
import view.IView;

import static org.junit.Assert.assertEquals;

/**
 * The following class makes use of a Mock
 * Model to test the correct working of a controller.
 * It also tests the functionality of the program with end-end scenarios.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TextControllerTest {
  String userInput;
  StringBuilder log;
  IModel mockModel;
  IView mockView;
  ByteArrayInputStream bais;
  ByteArrayOutputStream baos;
  IView view;
  IController controller;

  @Before
  public void setup() {
    log = new StringBuilder();
    mockModel = new MockModel(log);
    view = new MockView(log);
    System.setIn(bais);
    baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);
    System.setOut(printStream);
  }

  @Test
  public void testAGoForInitialQuit() {
    userInput = "Q\n";
    bais = new ByteArrayInputStream(userInput.getBytes());
    controller = new TextController(mockModel, bais, view);
    //log for mock model
    controller.connect();
    assertEquals("showPreLoginOptions called by the Controller", log.toString());
  }

  @Test
  public void testBGoForLoginLogoutQuit() {
    userInput = "E\nkarthik12\nkarthik123\nL\nQ";
    bais = new ByteArrayInputStream(userInput.getBytes());
    controller = new TextController(mockModel, bais, view);
    //log for mock model
    controller.connect();
    assertEquals("showPreLoginOptions called by the Controller" +
            "requestUsername called by the ControllerpleaseEnterString" +
            " called by the Controller with:passwordPassword provided " +
            "to checkValidPassword:karthik123Username provided to setUser:karthik12\n" +
            "Password provided to setUser:karthik123\n" +
            "showGreeting called by the Controller with: karthik12showUser" +
            "Operations called by the ControllershowPreLoginOptions called " +
            "by the Controller", log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testCGoForDisplayPortfolios() {
    userInput = "E\nkarthik12\nkarthik123\nD\nInflexible2\nL\nQ";
    bais = new ByteArrayInputStream(userInput.getBytes());
    controller = new TextController(mockModel, bais, view);
    //log for mock model
    controller.connect();
    assertEquals("showPreLoginOptions called by the Controllerrequest" +
            "Username called by the ControllerpleaseEnterString called by the" +
            " Controller with:passwordPassword provided to checkValidPassword:" +
            "karthik123Username provided to setUser:karthik12\n" +
            "Password provided to setUser:karthik123\n" +
            "showGreeting called by the Controller with: karthik12showUserOperations" +
            " called by the ControllerCalled getUserPortfoliosdisplayAllPortfolioNames" +
            " called by the Controller with:Inflexible2\n" +
            "Inflexible\n" +
            "Flexible\n" +
            "Flexible2\n" +
            "fetchPortfolioForDisplay called by the ControllergetExistingPortfolio " +
            "called by the controller with: Inflexible2\n" +
            "PortfolioName provided to computePortfolioValues:Inflexible2\n" +
            "LocalDate provided to computePortfolioValues:2022-11-17\n" +
            "getExistingPortfolio called by the controller with: Inflexible2\n" +
            "printStocksForPortfolio called by the Controller with: Inflexible2" +
            "showUserOperations called by the ControllershowPreLoginOptions called by" +
            " the Controller", log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testCGoForExistingCompute() {
    String userInput = "E\nkarthik12\nkarthik123\nC\nInflexible\n20-03-2020\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(mockModel, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("showPreLoginOptions called by the ControllerrequestUsername" +
            " called by the ControllerpleaseEnterString called by the Controller with:" +
            "passwordPassword provided to checkValidPassword:karthik123Username provided " +
            "to setUser:karthik12\n" +
            "Password provided to setUser:karthik123\n" +
            "showGreeting called by the Controller with: karthik12showUserOperations " +
            "called by the ControllerCalled getUserPortfoliosdisplayAllPortfolioNames " +
            "called by the Controller with:Inflexible2\n" +
            "Inflexible\n" +
            "Flexible\n" +
            "Flexible2\n" +
            "fetchPortfolioForComputation called by the Controller getExistingPortfolio" +
            " called by the controller with: Inflexible\n" +
            "enterDateFor called by the Controller with: valid date in format " +
            "dd-mm-yyyycompute total valuePortfolioName provided to " +
            "computePortfolioValues:Inflexible\n" +
            "LocalDate provided to computePortfolioValues:2020-03-20\n" +
            "displayPortfolioResults called by the Controller with 4584.8003" +
            "showUserOperations called by the ControllershowPreLoginOptions " +
            "called by the Controller", log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testDGoForExistingAddPortfolio() {
    String userInput = "E\nkarthik12\nkarthik123\nA" +
            "\n1\nVacation Funds\n1\n1\nGOOG\n100\n20-03-2020\nGOOG\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(mockModel, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("showPreLoginOptions called by the Controllerrequest" +
            "Username called by the ControllerpleaseEnterString called by the " +
            "Controller with:passwordPassword provided to checkValidPassword:" +
            "karthik123Username provided to setUser:karthik12\n" +
            "Password provided to setUser:karthik123\n" +
            "showGreeting called by the Controller with: karthik12" +
            "showUserOperations called by the ControllergetNoPortfolios" +
            " called by the ControllerNumber provided to checkValidNumber:1\n" +
            "getPortfolioNames called by the ControllergetPortfolioNumber " +
            "called by the Controller1askPortfolioType called by the Controller" +
            "Call made to checkValidPortfolioType from controller with: 1" +
            "PortfolioName provided to addPortfolios:Vacation Funds\n" +
            "Called getUserPortfoliosgetStockforPortfolio called by the Controller" +
            " with:Vacation FundsNumber provided to checkValidNumber:1\n" +
            "pleaseEnterString called by the Controller with:stock symbol for " +
            "stock: 1Symbol provided to validateStocksymbol: GOOGpleaseEnterString" +
            " called by the Controller with:quantity for GOOGNumber provided to " +
            "checkValidNumber:100\n" +
            "enterDateFor called by the Controller with: valid date in format " +
            "dd-mm-yyyyadd stockQuantity provided to createStock: 100\n" +
            "Symbol provided to createStock: GOOG\n" +
            "Quantity provided to createStock: 100\n" +
            "Symbol provided to createStock: GOOG\n" +
            "Portfolio provided to addStock: Vacation Funds\n" +
            "Quantity provided to addStock: GOOG\n" +
            "showUserOperations called by the ControllershowOptionError " +
            "called by the ControllershowUserOperations called by the Controller" +
            "showPreLoginOptions called by the Controller", log.toString());
    //inputs reached the model correctly
  }

  @Test
  public void testEGoForAddFlexiblePortfolio() {
    String userInput = "E\nkarthik12\nkarthik123\nN" +
            "\nFlexible\nAAPL\n100\n20-03-2020\nGOOG\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(mockModel, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("showPreLoginOptions called by the Controller" +
                    "requestUsername called by the ControllerpleaseEnterString called by the " +
                    "Controller with:passwordPassword provided to checkValid" +
                    "Password:karthik123Username provided to setUser:karthik12\n" +
                    "Password provided to setUser:karthik123\n" +
                    "showGreeting called by the Controller with: karthik12" +
                    "showUserOperations called by the ControllergetNoPortfolios called " +
                    "by the Controller" +
                    "Number provided to checkValidNumber:1\n" +
                    "getPortfolioNames called by the ControllergetPortfolioNumber called " +
                    "by the Controller" +
                    "1askPortfolioType called by the ControllerCall made to " +
                    "checkValidPortfolioType from" +
                    " controller with: 1PortfolioName provided to addPortfolios:Vacation Funds\n" +
                    "Called getUserPortfoliosgetStockforPortfolio called by the Controller with:" +
                    "Vacation FundsNumber provided to checkValidNumber:1\n" +
                    "pleaseEnterString called by the Controller with:stock symbol for stock:" +
                    " 1Symbol provided to validateStocksymbol: GOOGpleaseEnterString called by" +
                    " the Controller with:quantity for GOOGNumber provided to " +
                    "checkValidNumber:100\n" +
                    "enterDateFor called by the Controller with: valid date in " +
                    "format dd-mm-yyyyadd" +
                    " stockQuantity provided to createStock: 100\n" +
                    "Symbol provided to createStock: GOOG\n" +
                    "Quantity provided to createStock: 100\n" +
                    "Symbol provided to createStock: GOOG\n" +
                    "Portfolio provided to addStock: Vacation Funds\n" +
                    "Quantity provided to addStock: GOOG\n" +
                    "showUserOperations called by the ControllershowOptionError called by " +
                    "the ControllershowUserOperations called by the " +
                    "ControllershowPreLoginOptions called by the Controller",
            log.toString()); //inputs reached the model correctly
  }

  @Test
  public void testNewUser() {
    String userInput = "N\nSank123\nSank123\nI\n1\nsam\n1\nsam\n1\n11-01-2020\nGOOG\nL\nQ\n";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(bais);
    controller = new TextController(mockModel, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("Username provided to checkValidUserName: Sank123" +
            "Password provided to checkValidPassword:Sank123" +
            "Username provided to createUser:Sank123\n" +
            "Password provided to createUse:Sank123\n" +
            "Filename provided to createUse:\n" +
            "Number provided to checkValidNumber:1\n" +
            "PortfolioName provided to addPortfolios:sam\n" +
            "Called getUserPortfoliosNumber provided to checkValidNumber:1\n" +
            "Number provided to checkValidNumber:1\n" +
            "Number provided to checkValidNumber:11\n" +
            "Number provided to checkValidNumber:1\n" +
            "Number provided to checkValidNumber:2020\n" +
            "Year provided to createValidDate:2020\n" +
            "Month provided to createValidDate:1\n" +
            "Date provided to createValidDate:11\n" +
            "Symbol provided to validateStocksymbol: GOOG" +
            "Stock Name provided to createStock: sam\n" +
            "Quantity provided to createStock: 1\n" +
            "Date provided to createStock: 11\n" +
            "Month provided to createStock: 1\n" +
            "Year provided to createStock: 2020\n" +
            "Symbol provided to createStock: GOOG\n" +
            "Portfolio provided to addStock: sam\n" +
            "Quantity provided to addStock: sam\n", log.toString());
  }

  @Test
  public void testNewUserWithFileInput() {
    String userInput = "N\nSanka123\nSanka123\nF\nSam19981.txt\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(bais);
    IController controller = new TextController(mockModel, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("showPreLoginOptions called by the Controllerrequest" +
                    "Username called by the ControllerUsername provided to checkValidUserName:" +
                    " Sanka123pleaseEnterString called by the Controller with:passwordPassword" +
                    " provided to checkValidPassword:Sanka123showGreeting called by the " +
                    "Controller" +
                    " with: Sanka123showDataEntryOptions called by the " +
                    "ControllerfileInstructions" +
                    " called by the ControllerpleaseEnterString called by " +
                    "the Controller with:" +
                    "file from which you want to input informationFile provided to " +
                    "checkValidFile:" +
                    " Sam19981.txtUsername provided to createUser:Sanka123\n" +
                    "Password provided to createUse:Sanka123\n" +
                    "Filename provided to createUse:Sam19981.txt\n" +
                    "Username provided to setUser:Sanka123\n" +
                    "Password provided to setUser:Sanka123\n" +
                    "showGreeting called by the Controller with: Sanka123showUserOperations" +
                    " called by the ControllershowPreLoginOptions called by the Controller",
            log.toString());
  }


  static class MockView implements IView {

    private final StringBuilder log;

    MockView(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void showPreLoginOptions() {
      log.append("showPreLoginOptions called by the Controller");
    }

    @Override
    public void showDataEntryOptions() {
      log.append("showDataEntryOptions called by the Controller");
    }

    @Override
    public void requestUsername() {
      log.append("requestUsername called by the Controller");
    }

    @Override
    public void showOptionError() {
      log.append("showOptionError called by the Controller");
    }

    @Override
    public void getNoPortfolios() {
      log.append("getNoPortfolios called by the Controller");
    }

    @Override
    public void showUserOperations() {
      log.append("showUserOperations called by the Controller");
    }

    @Override
    public void displayPortfolios(List<Portfolio> p) {
      log.append("displayPortfolios called by the Controller with:");
      for (Portfolio portfolio : p) {
        log.append(portfolio.getPortfolioName()).append("\n");
      }
    }

    @Override
    public void displayIndividualPortfolio(List<Portfolio> portfolio, String portfolioName) {
      log.append("displayIndividualPortfolio called by the Controller with: " + portfolioName);
      for (Portfolio p : portfolio) {
        log.append(p.getPortfolioName()).append("\n");
      }
    }

    @Override
    public void printStocksForPortfolio(Portfolio p) {
      log.append("printStocksForPortfolio called by the Controller with: ")
              .append(p.getPortfolioName());
    }

    @Override
    public void displayPortfolioResults(float result) {
      log.append("displayPortfolioResults called by the Controller with ").append(result);
    }

    @Override
    public void displayAllPortfolioNames(List<Portfolio> portfolios) {
      log.append("displayAllPortfolioNames called by the Controller with:");
      for (Portfolio p : portfolios) {
        log.append(p.getPortfolioName()).append("\n");
      }
    }

    @Override
    public void fetchPortfolioForComputation() {
      log.append("fetchPortfolioForComputation called by the Controller ");
    }

    @Override
    public void fetchPortfolioForDisplay() {
      log.append("fetchPortfolioForDisplay called by the Controller");
    }

    @Override
    public void fetchDate() {
      log.append("fetchDate called by the Controller");
    }

    @Override
    public void showperformaceGraph(Draw draw) {
      log.append("showperformaceGraph called by the Controller with Draw Object");
    }

    @Override
    public void pleaseEnterAValidPassword() {
      log.append("pleaseEnterAValidPassword called by the Controller");
    }

    @Override
    public void fetchYear() {
      log.append("fetchYear called by the Controller");
    }

    @Override
    public void fetchMonth() {
      log.append("fetchMonth called by the Controller");
    }

    @Override
    public void pleaseInputCorrectDetails(String s) {
      log.append("pleaseInputCorrectDetails called by the Controller with: ").append(s);
    }

    @Override
    public void pleaseUseAValidUserName() {
      log.append("pleaseUseAValidUserName called by the Controller");
    }

    @Override
    public void getPortfolioNames() {
      log.append("getPortfolioNames called by the Controller");
    }

    @Override
    public void getPortfolioNumber(String n) {
      log.append("getPortfolioNumber called by the Controller").append(n);
    }

    @Override
    public void getStockforPortfolio(String pName) {
      log.append("getStockforPortfolio called by the Controller with:").append(pName);
    }

    @Override
    public void pleaseEnterString(String s) {
      log.append("pleaseEnterString called by the Controller with:").append(s);
    }

    @Override
    public void pleaseUseADifferentPortfolioName() {
      log.append("pleaseUseADifferentPortfolioName called by the Controller");
    }

    @Override
    public void fileInstructions() {
      log.append("fileInstructions called by the Controller");
    }

    @Override
    public void showGreeting(String username) {
      log.append("showGreeting called by the Controller with: ").append(username);
    }

    @Override
    public void askPortfolioType() {
      log.append("askPortfolioType called by the Controller");
    }

    @Override
    public void noFlexiblePortfolios() {
      log.append("noFlexiblePortfolios called by the Controller");
    }

    @Override
    public void enterPortfoliotoDeleteStock() {
      log.append("enterPortfoliotoDeleteStock called by the Controller");
    }

    @Override
    public void performanceStartDate() {
      log.append("performanceStartDate called by the Controller");
    }

    @Override
    public void performanceEndDate() {
      log.append("performanceEndDate called by the Controller");
    }

    @Override
    public void enterStockToDelete() {
      log.append("enterStockToDelete called by the Controller");
    }

    @Override
    public void enterDateFor(String s, String cause) {
      log.append("enterDateFor called by the Controller with: ").append(s).append(cause);
    }

    @Override
    public void incorrectDateEntered() {
      log.append("incorrectDateEntered called by the Controller");
    }

    @Override
    public void enterDateToGetCostBasis(String s) {
      log.append("enterDateToGetCostBasis called by the Controller with: ").append(s);
    }

    @Override
    public void cannotDeleteStocksFromNonExistentStock() {
      log.append("cannotDeleteStocksFromNonExistentStock called by the Controller");
    }

    @Override
    public void yourTransactionsAreNotValid() {
      log.append("yourTransactionsAreNotValid called by the Controller");
    }

    @Override
    public void pleaseCheckFromTheAboveTransactionsAndRectify() {
      log.append("pleaseCheckFromTheAboveTransactionsAndRectify called by the Controller");
    }

    @Override
    public void enterPortfolioForCostBasis() {
      log.append("enterPortfolioForCostBasis called by the Controller");
    }

    @Override
    public void displayCostBasis(float value) {
      log.append("displayCostBasis called by the Controller with: ").append(value);
    }

    @Override
    public void incorrectNumberInput() {
      log.append("incorrectNumberInput called by the Controller");
    }

    @Override
    public void cannotExamineThePortfolioInvalidState() {
      log.append("cannotExamineThePortfolioInvalidState called by the Controller");
    }

    @Override
    public void cannotViewThePortfolioPerformanceInvalidState() {
      log.append("cannotViewThePortfolioPerformanceInvalidState called by the Controller");
    }

    @Override
    public void printErrorMessage(String message) {
      log.append("printErrorMessage called by the Controller with: ").append(message);
    }

    @Override
    public void pleaseEnterAValidPortfolioName() {
      log.append("pleaseEnterAValidPortfolioName called by the Controller");
    }

    @Override
    public void pleaseAddValidTransactions() {
      log.append("pleaseAddValidTransactions called by the Controller");
    }

    @Override
    public void pressQtoQuit() {
      log.append("pressQtoQuit called by the Controller");
    }

    @Override
    public void couldNotCreateStockDueToUnsupportedPurchaseDate() {
      log.append("couldNotCreateStockDueToUnsupportedPurchaseDate called by the Controller");
    }

    @Override
    public void getCommissionFees() {
      log.append("getCommissionFees called by the Controller");
    }

    @Override
    public void pleaseEnterAValidCommissionFees() {
      log.append("pleaseEnterAValidCommissionFees called by the Controller");
    }

    @Override
    public void fetchPortfolioForDCAPlan() {
      log.append("fetchPortfolioForDCAPlan called by the Controller");
    }
  }

  /**
   * The following is the mock implementation of the Model that the Controller interacts with.
   * By having logging statements for each of the methods that the Controller calls.
   * We can fetch the inputs provided to each of the methods in the Model.
   * and verify that there are no changes.
   */
  static class MockModel implements IModel {
    private final StringBuilder log;
    private final HashSet<String> userNames;
    private final UserXmlWriterImpl write;
    private String fileLink;
    private User currentUser;

    /**
     * The following constructor instantiates the logger and also creates a model object.
     *
     * @param log -  It logs all the input passed by the controller to check
     *            if the values passed by the controlled are being passed to
     *            model without any modification.
     */
    public MockModel(StringBuilder log) {
      write = new UserXmlWriterImpl();
      this.log = log;
      userNames = new HashSet<>();
    }


    @Override
    public boolean checkExistingUser(String username) {
      log.append("Username provided to checkExistingUser:").append(username).append("\n");
      return true;
    }

    @Override
    public int createUser(String username, String password, String fileName) {
      log.append("Username provided to createUser:").append(username).append("\n");
      log.append("Password provided to createUse:").append(password).append("\n");
      log.append("Filename provided to createUse:").append(fileName).append("\n");
      User user = UserImpl.createBuilder().setUserName(username).setPassword(password).create();
      userNames.add(username);
      File myObj = new File("users/" + username + "File.txt");
      try {
        if (myObj.createNewFile()) {
          fileName = username + "File.txt";
        }
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
      append(username, password, "users/" + fileName);
      currentUser = user;
      return 0;
    }

    private void append(String username, String password, String userFile) {
      try {
        FileWriter fw = new FileWriter("UserDetails.csv", true);
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
    public int setUser(String username, String password) {
      log.append("Username provided to setUser:").append(username).append("\n");
      log.append("Password provided to setUser:").append(password).append("\n");
      UserXmlReaderImpl r = new UserXmlReaderImpl();
      fileLink = "users/karthik12File.txt";
      currentUser = r.readData(fileLink, password);
      return 0;
    }

    @Override
    public List<Portfolio> getUserPortFolios() {
      log.append("Called getUserPortfolios");
      return currentUser.getAllPortfolios();
    }

    @Override
    public float computePortfolioValues(String portfolioName, LocalDate d) {
      log.append("PortfolioName provided to computePortfolioValues:")
              .append(portfolioName).append("\n");
      log.append("LocalDate provided to computePortfolioValues:").append(d).append("\n");
      return currentUser.computePortfolioValue(portfolioName, d);
    }

    @Override
    public boolean checkValidMonth(String month) {
      return true;
    }

    @Override
    public boolean checkValidDay(String day) {
      return true;
    }

    @Override
    public boolean checkValidYear(String year) {
      return true;
    }

    @Override
    public LocalDate checkValidDate(String year, String month, String date) {
      log.append("Year provided to createValidDate:").append(year).append("\n");
      log.append("Month provided to createValidDate:").append(month).append("\n");
      log.append("Date provided to createValidDate:").append(date).append("\n");
      return LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date));
    }

    @Override
    public float checkValidNaturalNumber(String number) {
      log.append("Number provided to checkValidNumber:").append(number).append("\n");
      return Float.parseFloat(number);
    }

    @Override
    public int addPortfolios(String portfolioName, String portfolioType) {
      log.append("PortfolioName provided to addPortfolios:").append(portfolioName).append("\n");
      Portfolio p = PortfolioImpl.getBuilder().portfolioName(portfolioName).create();
      return currentUser.addPortfolio(p);
    }

    @Override
    public Stock createStock(String quantity, LocalDate date, String symbol, float commissionFees) {
      log.append("Quantity provided to createStock: ").append(quantity).append("\n");
      log.append("Symbol provided to createStock: ").append(symbol).append("\n");
      return StockImpl.getBuilder()
              .quantity(Float.parseFloat(quantity))
              .purchaseDate(date)
              .stockSymbol(symbol).create();
    }

    @Override
    public void addStock(Portfolio portfolio, Stock stock) {
      log.append("Portfolio provided to addStock: ")
              .append(portfolio.getPortfolioName()).append("\n");
      log.append("Quantity provided to addStock: ").append(stock.getStockSymbol()).append("\n");
      portfolio.addStock(stock);
      fileLink = "users/Sam19981.txt";
      try {
        write.writeData(fileLink, currentUser);
      } catch (ParserConfigurationException e) {
        System.out.println(e.getMessage());
      }
    }

    @Override
    public void addStockCache(Stock stocks) {
      System.out.println("Input " + stocks + " from controller");
    }

    @Override
    public List<Stock> getStockCache() {
      return null;
    }

    @Override
    public boolean checkValidFile(String filename) {
      log.append("File provided to checkValidFile: ").append(filename);
      return true;
    }

    @Override
    public boolean checkValidUserName(String username) {
      log.append("Username provided to checkValidUserName: ").append(username);
      return true;
    }

    @Override
    public int validateStocksymbol(String symbol) {
      log.append("Symbol provided to validateStocksymbol: ").append(symbol);
      return -1;
    }

    @Override
    public boolean checkValidPassword(String password) {
      log.append("Password provided to checkValidPassword:").append(password);
      return true;
    }

    @Override
    public boolean checkValidStockName(String stock) {
      return true;
    }

    @Override
    public Portfolio getExistingPortfolio(String portfolioName) {
      log.append("getExistingPortfolio called by the controller with: ")
              .append(portfolioName).append("\n");
      return currentUser.getPortfolio(portfolioName);
    }

    @Override
    public List<Portfolio> getFlexiblePortfolios() {
      return null;
    }

    @Override
    public boolean checkValidPortfolioType(String portfolioType) {
      log.append("Call made to checkValidPortfolioType from controller with: ")
              .append(portfolioType);
      return true;
    }

    @Override
    public int sellStockFromPortfolio(String pName, String sName, float quantity, LocalDate date) {
      return 0;
    }

    @Override
    public void save() {
      // Used to save data to the file tested in parser.
    }

    @Override
    public float getCostBasis(String pName, LocalDate date) {
      return 0;
    }

    @Override
    public Draw drawBarGraph(LocalDate sDate, LocalDate eDate, String porName) {
      return null;
    }

    @Override
    public void setCommissionFees(String p, String stock, float commissionFees) {
      //Function in MockModel to set Commission Fees of a portfolio.
    }

    @Override
    public Float checkCommissionFees(String commissionFees) {
      return Float.parseFloat(commissionFees);
    }

    @Override
    public float validatePercentage(String percentage) {
      return 0;
    }


    @Override
    public boolean checkForDate(String symbol, LocalDate date) {
      return false;
    }

    @Override
    public void addPlan(LocalDate date, LocalDate date2, String pName,
                        HashMap<String, Float> map, float amount,
                        float commissionFees, long frequency, boolean isOngoing) {
      System.out.println("Adding new plan for " + pName);
    }

    @Override
    public float updateMap(String symbol, float percent) {
      return 0;
    }

    @Override
    public float fetchMapTotalValue() {
      return 0;
    }

    @Override
    public HashMap<String, Float> getHashMap() {
      return null;
    }

    @Override
    public void removeStockFromCache(int i) {
      System.out.println("Input from " + i);
    }

    @Override
    public void removeStockFromMap(String symbol) {
      System.out.println("Input from " + symbol);
    }

    @Override
    public boolean validateInput(String text, String type) {
      return true;
    }

    @Override
    public void clearMap() {
      System.out.println("Clears map from controller");
    }
  }
}