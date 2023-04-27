package controller;

import java.io.Console;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import model.IModel;
import model.Portfolio;
import utilities.Utility;
import view.IView;

/**
 * DESIGN CHANGES
 * ------------------------------------------------------
 * Added new menus to incorporate the requirements of the assignments.
 * Added option for the user to
 * - Buy 1 or more stocks to an existing portfolio.
 * - Sell 1 or more stocks from an existing portfolio
 * - Compute the Cost Basis of the specific portfolio.
 * - See the performance chart for the range of dates provided by the user.
 * Modified the type of date input taken by the user as day, month year separately
 * to dd-mm-yyyy.
 * ------------------------------------------------------
 * Controller class controls the flow of execution of the entire program and
 * is responsible for taking inputs form the user and passing it to the appropriate
 * models and its functions.
 */
public class TextController implements IController {
  private final Scanner in;
  private final IView view;
  private final IModel model;

  /**
   * Facilitates instantiation of the controller object.
   *
   * @param model - data models required for
   *              the controller to represent
   *              user and perform operations on them.
   * @param in    - InputStream object to initialise scanner class for
   *              taking inputs for the user.
   * @param view  - view for displaying
   *              the prompts and user result of user interactions.
   */
  public TextController(IModel model, InputStream in, IView view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);
  }

  /**
   * Program execution flow begins here.
   * Takes input from the user and passes it to
   * the appropriate model for operations and
   * returns the result to user through view.
   */
  @Override
  public void connect() {
    boolean mainQuit = false;
    while (!mainQuit) {
      view.showPreLoginOptions();
      String option = in.nextLine().toUpperCase();
      boolean quit = false;
      switch (option) {
        case "N":
          while (!quit) {
            view.requestUsername();
            String username = in.nextLine();
            while (!model.checkValidUserName(username)) {
              view.pleaseUseAValidUserName();
              view.requestUsername();
              username = in.nextLine();
            }
            view.pleaseEnterString("password");
            String pass = fetchValidPassword();
            while (!model.checkValidPassword(pass)) {
              view.pleaseEnterAValidPassword();
              view.pleaseEnterString("password");
              pass = fetchValidPassword();
            }
            quit = true;
            view.showGreeting(username);
            view.showDataEntryOptions();
            getInputStyle(username, pass);
          }
          break;

        case "E":
          while (!quit) {
            view.requestUsername();
            String userName = in.nextLine();
            view.pleaseEnterString("password");
            String pass = fetchValidPassword();
            while (!model.checkValidPassword(pass)) {
              view.pleaseEnterAValidPassword();
              view.pleaseEnterString("password");
              pass = fetchValidPassword();
            }
            quit = setUser(userName, pass);
          }
          break;

        case "Q":
          mainQuit = true;
          break;
        default:
          view.showOptionError();
      }
    }
  }

  private boolean setUser(String userName, String pass) {
    int status = model.setUser(userName, pass);
    if (status >= 0) {
      view.showGreeting(userName);
      performUserOperation();
      return true;
    } else if (status == -1) {
      view.pleaseInputCorrectDetails("Name/Password");
    } else if (status == -2) {
      view.pleaseInputCorrectDetails("File");
    }
    return false;
  }

  private String fetchValidPassword() {
    String pass;
    Console console = System.console();
    if (console != null) {
      pass = new String(console.readPassword("Enter password: "));
    } else {
      pass = in.nextLine();
    }
    return pass;
  }

  private void getInputStyle(String username, String pass) {
    String style = in.nextLine().toUpperCase();
    boolean mainQuit = false;
    while (!mainQuit) {
      switch (style) {
        case "F":
          parseFromFile(username, pass);
          mainQuit = true;
          break;
        case "I":
          model.createUser(username, pass, "");
          for (String p : getPortfolioInformation()) {
            getStockInformation(p);
          }
          performUserOperation();
          mainQuit = true;
          break;
        case "P":
          model.createUser(username, pass, "");
          for (String p : getPortfolioInformation()) {
            getStockInformationDCA(p);
          }
          performUserOperation();
          mainQuit = true;
          break;
        default:
          view.showOptionError();
          view.showDataEntryOptions();
          style = in.nextLine();
      }
    }
  }

  /**
   * This function uses the details the user has provided so far (including the file input).
   * It calls the Parser's read Class to read the details from the XML.
   * and store it in the user object.
   *
   * @param username - Name the user provides.
   * @param pass     - Pass provided by the user.
   */
  private void parseFromFile(String username, String pass) {
    boolean quit = false;
    String fileName;
    while (!quit) {
      view.fileInstructions();
      view.pleaseEnterString("file from which you want to input information");
      fileName = in.nextLine();
      if (model.checkValidFile(fileName)) {
        quit = true;
      } else {
        view.pleaseInputCorrectDetails("file from which you want to input information");
      }
      if (model.createUser(username, pass, fileName) >= 0) {
        quit &= setUser(username, pass);
      } else {
        quit = false;
      }
    }
  }

  /**
   * This function will fetch all the information about a portfolio that-.
   * - the user wants to add to his details.
   * The details include the number of portfolios, Portfolio name.
   */
  private List<String> getPortfolioInformation() {
    boolean quit = false;
    String pName;
    String portfolioType = "";
    String number;
    List<String> portfolioNames = new ArrayList<>();
    while (!quit) {
      view.getNoPortfolios();
      number = in.nextLine();
      float n = model.checkValidNaturalNumber(number);
      if ((n) != -1) {
        quit = true;
        view.getPortfolioNames();
        for (int i = 1; i <= n; i++) {
          view.getPortfolioNumber(String.valueOf(i));
          pName = in.nextLine();
          boolean typeQuit = false;
          view.askPortfolioType();
          while (!typeQuit) {
            portfolioType = in.nextLine();
            if (model.checkValidPortfolioType(portfolioType)) {
              typeQuit = true;
            } else {
              view.pleaseInputCorrectDetails("Portfolio Type");
            }
          }
          while (model.addPortfolios(pName, portfolioType) == -1) {
            view.pleaseUseADifferentPortfolioName();
            view.getPortfolioNumber(String.valueOf(i));
            pName = in.nextLine();
          }
          portfolioNames.add(pName);
        }
      } else {
        view.pleaseInputCorrectDetails("Portfolio Number");
      }
    }
    return portfolioNames;
  }

  /**
   * This function fetches all the information that is needed before a Stock could be created.
   * then directs the Model to create the Stock.
   */
  private void getStockInformation(String pName) {
    boolean mainQuit = false;
    LocalDate date;
    String quantity = "";
    while (!mainQuit) {
      List<Portfolio> portfolios = model.getUserPortFolios();
      for (Portfolio p : portfolios) {
        if (p.getPortfolioName().equals(pName)) {
          view.getStockforPortfolio(p.getPortfolioName());
          String number = in.nextLine();
          while (model.checkValidNaturalNumber(number) <= 0) {
            view.incorrectNumberInput();
            view.getStockforPortfolio(p.getPortfolioName());
            number = in.nextLine();
          }
          for (int i = 0; i < Integer.parseInt(number); i++) {
            boolean quit = false;
            int j = i + 1;
            view.pleaseEnterString("stock symbol for stock: " + j);
            String symbol = in.nextLine().toUpperCase();
            while (model.validateStocksymbol(symbol) == 0) {
              view.pleaseEnterString("valid Symbol");
              symbol = in.nextLine().toUpperCase();
            }

            while (!quit) {
              view.pleaseEnterString("quantity for " + symbol);
              quantity = in.nextLine();
              while (model.checkValidNaturalNumber(quantity) <= 0) {
                view.pleaseInputCorrectDetails("Quantity");
                quantity = in.nextLine();
              }
              quit = true;
            }
            date = getValidDate("add stock", false);
            if (model.createStock(quantity, date, symbol, 0) == null) {
              view.couldNotCreateStockDueToUnsupportedPurchaseDate();
              break;
            }
            if (p.isFlexible()) {
              float commissionFees = getCommissionFeesFromInput();
              model.addStock(p, model.createStock(quantity,
                      date, symbol, commissionFees));
            } else {
              model.addStock(p, model.createStock(quantity,
                      date, symbol, 0));
            }
            model.save();
            mainQuit = true;
          }
        }
      }
    }
  }


  /**
   * This function fetches all the information that is needed before a Stock could be created.
   * then directs the Model to create the Stock.
   */
  private void getStockInformationDCA(String pName) {
    boolean mainQuit = false;
    LocalDate date;
    LocalDate date2;
    String amount;
    String f;
    long frequency = 0;
    float a = 0;
    boolean isOngoing = false;
    while (!mainQuit) {
      List<Portfolio> portfolios = model.getUserPortFolios();
      date = getValidDate("start Dollar Cost Averaging plan", false);
      date2 = getValidDate("end Dollar Cost Averaging plan", true);
      if (date2 == null) {
        isOngoing = true;
      }
      boolean quit = false;
      while (!quit) {
        view.pleaseEnterString("total amount to invest");
        amount = in.nextLine();
        while (model.checkValidNaturalNumber(amount) <= 0) {
          view.pleaseInputCorrectDetails("total amount to invest");
          amount = in.nextLine();
        }
        a = model.checkValidNaturalNumber(amount);
        quit = true;
      }

      quit = false;
      while (!quit) {
        view.pleaseEnterString("frequency to execute plan in days");
        f = in.nextLine();
        while (model.checkValidNaturalNumber(f) <= 0) {
          view.pleaseInputCorrectDetails("frequency to execute plan in days");
          f = in.nextLine();
        }
        frequency = Long.parseLong(f);
        quit = true;
      }
      float commissionFees = getCommissionFeesFromInput();

      for (Portfolio p : portfolios) {
        if (p.getPortfolioName().equals(pName)) {
          view.getStockforPortfolio(p.getPortfolioName());
          String number = in.nextLine();
          while (model.checkValidNaturalNumber(number) <= 0) {
            view.incorrectNumberInput();
            view.getStockforPortfolio(p.getPortfolioName());
            number = in.nextLine();
          }
          HashMap<String, Float> map = new HashMap<>();
          for (int i = 0; i < Integer.parseInt(number); i++) {
            int j = i + 1;
            view.pleaseEnterString("stock symbol for stock: " + j);
            String symbol = in.nextLine().toUpperCase();
            while (model.validateStocksymbol(symbol) == 0 || map.containsKey(symbol) ||
                    !model.checkForDate(symbol, date)) {
              view.pleaseEnterString("valid supported Symbol");
              symbol = in.nextLine().toUpperCase();
            }
            view.pleaseEnterString("percentage of shares for stock: " + j);
            String percentage = in.nextLine();
            while (model.validatePercentage(percentage) <= 0) {
              view.pleaseEnterString("percentage of shares for stock: " + j);
              percentage = in.nextLine();
            }
            map.put(symbol, model.validatePercentage(percentage));
            mainQuit = true;
          }
          try {
            model.addPlan(date, date2, pName, map, a, commissionFees, frequency, isOngoing);
          } catch (Exception e) {
            view.printErrorMessage(e.getMessage());
          }
        }
      }
    }
  }

  private void addStockToPortfolio(List<Portfolio> pList) {
    view.getPortfolioNames();
    String pName = in.nextLine();
    boolean quit = false;
    boolean contains = false;
    while (!quit) {
      for (Portfolio p : pList) {
        if (p.getPortfolioName().equals(pName)) {
          contains = true;
          break;
        }
      }
      if (!contains) {
        view.pleaseInputCorrectDetails("Portfolio Name");
        pName = in.nextLine();
      } else {
        Portfolio p = model.getExistingPortfolio(pName);
        if (p != null) {
          getStockInformation(p.getPortfolioName());
          quit = true;
        } else {
          view.pleaseInputCorrectDetails("Portfolio Name");
          pName = in.nextLine();
        }
      }
    }
  }

  private LocalDate getValidDate(String cause, boolean future) {
    view.enterDateFor("valid date in format dd-mm-yyyy", cause);
    String day = in.nextLine();
    LocalDate date;
    if (!future) {
      date = Utility.validateDates(day);
      while (date == null) {
        view.incorrectDateEntered();
        view.enterDateFor("valid date in format dd-mm-yyyy", cause);
        day = in.nextLine();
        date = Utility.validateDates(day);
      }
    } else {
      date = Utility.validateDatesFuture(day);
    }
    return date;
  }

  private void sellStockInPortfolio() {
    view.enterPortfoliotoDeleteStock();
    String pName = in.nextLine();
    while (model.getExistingPortfolio(pName) == null) {
      view.pleaseEnterString("correct portfolio name");
      pName = in.nextLine();
    }
    view.enterStockToDelete();
    String symbol = in.nextLine().toUpperCase();
    while (model.validateStocksymbol(symbol) == 0) {
      view.pleaseEnterString("valid Symbol");
      symbol = in.nextLine().toUpperCase();
    }
    LocalDate date = getValidDate("record sold transactions", false);
    view.pleaseEnterString("quantity");
    String quantity = in.nextLine();
    while (model.checkValidNaturalNumber(quantity) < 0) {
      view.pleaseEnterString("quantity");
      quantity = in.nextLine();
    }
    float q = model.checkValidNaturalNumber(quantity);

    float commissionFees = getCommissionFeesFromInput();

    int status = model.sellStockFromPortfolio(pName, symbol, q, date);
    if (status == -1) {
      view.cannotDeleteStocksFromNonExistentStock();
    }
    model.setCommissionFees(pName, symbol, commissionFees);
  }

  private float getCommissionFeesFromInput() {
    view.getCommissionFees();
    String commission = in.nextLine();
    while (model.checkCommissionFees(commission) == null) {
      view.pleaseEnterAValidCommissionFees();
      view.getCommissionFees();
      commission = in.nextLine();
    }
    return Float.parseFloat(commission);
  }

  private void validateStockTransaction(Portfolio p) {
    view.yourTransactionsAreNotValid();
    view.displayAllPortfolioNames(model.getUserPortFolios());
    view.pleaseCheckFromTheAboveTransactionsAndRectify();
    getStockInformation(p.getPortfolioName());
  }

  private void getCostBasis() {
    view.enterPortfolioForCostBasis();
    String pName = in.nextLine();
    while (model.getExistingPortfolio(pName) == null) {
      view.pleaseEnterString("correct portfolio name");
      pName = in.nextLine();
    }
    LocalDate date = getValidDate("compute cost basis", true);
    try {
      view.displayCostBasis(model.getCostBasis(pName, date));
    } catch (Exception e) {
      view.cannotExamineThePortfolioInvalidState();
      view.printErrorMessage(e.getMessage());
    }
  }

  private boolean computeValue(String portfolioName, LocalDate date) {
    try {
      float res = model.computePortfolioValues(portfolioName, date);
      if (res >= 0) {
        view.displayPortfolioResults(res);
        return true;
      } else if (res == -1) {
        view.pleaseInputCorrectDetails("Details");
      } else if (res == -2) {
        validateStockTransaction(model.getExistingPortfolio(portfolioName));
      }
    } catch (Exception e) {
      view.cannotExamineThePortfolioInvalidState();
      view.printErrorMessage(e.getMessage());
      return true;
    }
    return false;
  }

  private void performUserOperation() {
    boolean mainQuit = false;
    List<Portfolio> pList;
    while (!mainQuit) {
      view.showUserOperations();
      String operation = in.nextLine().toUpperCase();
      switch (operation) {
        case "D":
          view.displayAllPortfolioNames(model.getUserPortFolios());
          view.fetchPortfolioForDisplay();
          String portfolioName = in.nextLine();
          while (model.getExistingPortfolio(portfolioName) == null) {
            portfolioName = in.nextLine();
          }
          try {
            if (model.computePortfolioValues(portfolioName, LocalDate.now()) < 0) {
              view.cannotExamineThePortfolioInvalidState();
            } else {
              view.printStocksForPortfolio(model.getExistingPortfolio(portfolioName));
            }
          } catch (Exception e) {
            view.cannotExamineThePortfolioInvalidState();
            view.printErrorMessage(e.getMessage());
          }
          break;
        case "C":
          boolean quit = false;
          while (!quit) {
            view.displayAllPortfolioNames(model.getUserPortFolios());
            view.fetchPortfolioForComputation();
            portfolioName = in.nextLine();
            while (model.getExistingPortfolio(portfolioName) == null) {
              view.pleaseUseADifferentPortfolioName();
              portfolioName = in.nextLine();
            }
            LocalDate date = getValidDate("compute total value", false);
            quit = computeValue(portfolioName, date);
          }
          break;
        case "A":
          for (String s : getPortfolioInformation()) {
            getStockInformation(s);
          }
          break;
        case "P":
          view.displayAllPortfolioNames(model.getFlexiblePortfolios());
          view.fetchPortfolioForDCAPlan();
          portfolioName = in.nextLine();
          while (model.getExistingPortfolio(portfolioName) == null) {
            view.pleaseUseADifferentPortfolioName();
            portfolioName = in.nextLine();
          }
          getStockInformationDCA(portfolioName);
          break;
        case "N":
          pList = model.getFlexiblePortfolios();
          if (pList.size() > 0) {
            view.displayAllPortfolioNames(pList);
            addStockToPortfolio(pList);
          } else {
            view.noFlexiblePortfolios();
          }
          break;
        case "S":
          pList = model.getFlexiblePortfolios();
          if (pList.size() > 0) {
            view.displayAllPortfolioNames(pList);
            sellStockInPortfolio();
          } else {
            view.noFlexiblePortfolios();
          }
          break;
        case "B":
          pList = model.getFlexiblePortfolios();
          if (pList.size() > 0) {
            view.displayAllPortfolioNames(pList);
            getCostBasis();
          } else {
            view.noFlexiblePortfolios();
          }
          break;
        case "L":
          model.save();
          mainQuit = true;
          break;
        case "G":
          pList = model.getFlexiblePortfolios();
          LocalDate date1;
          LocalDate date2;
          if (pList.size() > 0) {
            view.displayAllPortfolioNames(pList);
          }
          view.pleaseEnterString("Enter the portfolio name to see its performance:");
          String portName = in.nextLine();
          while (Utility.checkValidportFolioName(pList, portName) == 0) {
            view.pleaseEnterString("Please enter a valid portfolio name from the list");
            portName = in.nextLine();
          }

          view.performanceStartDate();
          String sdate = in.nextLine();
          view.performanceEndDate();
          String sdate1 = in.nextLine();

          date1 = Utility.validateDates(sdate);
          date2 = Utility.validateDates(sdate1);
          while (date1 == null || date1.isAfter(LocalDate.now())) {
            view.pleaseEnterString("valid start Date (dd-mm-yyyy): ");
            sdate = in.nextLine();
            date1 = Utility.validateDates(sdate);
          }
          while (date2 == null || date2.isAfter(LocalDate.now()) ||
                  date2.isBefore(Objects.requireNonNull(date1))) {
            view.pleaseEnterString("valid end Date (dd-mm-yyyy): ");
            sdate1 = in.nextLine();
            date2 = Utility.validateDates(sdate1);
          }
          try {
            view.showperformaceGraph(model.drawBarGraph(date1, date2, portName));
          } catch (Exception e) {
            view.cannotExamineThePortfolioInvalidState();
            view.printErrorMessage(e.getMessage());
          }
          break;
        default:
          view.showOptionError();
          break;
      }
    }
  }
}
