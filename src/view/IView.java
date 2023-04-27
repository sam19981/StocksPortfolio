package view;

import java.util.List;

import model.Portfolio;

/**
 * The following interface represents the View part of the project.
 * The view in short is responsible for everything that the user sees.
 * while interacting with the program.
 */
public interface IView {
  /**
   * This function shows the user options he can select when the program fires up.
   */
  void showPreLoginOptions();

  /**
   * This function provides the user options he could input data into his user information.
   * Either through a file or input it step by step.
   */
  void showDataEntryOptions();

  /**
   * The below function requests the user to provide his unique username.
   */
  void requestUsername();

  /**
   * This function is called when the user picks up an unspecified option from list of options.
   */
  void showOptionError();

  /**
   * This function requests the user for the number of portfolios.
   */
  void getNoPortfolios();

  /**
   * The following options are provided to the user in case he logs in as an existing user.
   * Or completes providing information as a new user.
   */
  void showUserOperations();

  /**
   * This function displays all the portfolio and his details.
   *
   * @param p - Portfolio object whose details are to be displayed.
   */
  void displayPortfolios(List<Portfolio> p);

  /**
   * Display individual portfolio's name for the user to pick from.
   *
   * @param portfolio     - List of portfolios of the user
   * @param portfolioName - Name of the portfolio whose details have to be fetched.
   */
  void displayIndividualPortfolio(List<Portfolio> portfolio, String portfolioName);

  void printStocksForPortfolio(Portfolio p);

  /**
   * Display the results after computation of portfolio values.
   *
   * @param result - float value to be printed on screen.
   */
  void displayPortfolioResults(float result);

  void displayAllPortfolioNames(List<Portfolio> portfolios);

  /**
   * Asks the user which portfolio's value they want to compute.
   */
  void fetchPortfolioForComputation();

  void fetchPortfolioForDisplay();

  /**
   * Asks the user for the day of the month.
   */
  void fetchDate();

  /**
   * Draws the given graph object.
   *
   * @param graph - draw object needed to show the performance.
   */
  void showperformaceGraph(Draw graph);

  /**
   * Asks the user to input a valid password through which he can log in.
   */
  void pleaseEnterAValidPassword();

  /**
   * Requests the user to input a valid year.
   */
  void fetchYear();

  /**
   * Asks the user to input a valid month.
   */
  void fetchMonth();

  /**
   * Asks the user to input correct details as they have mistyped one field.
   *
   * @param s - The field which is mistyped.
   */
  void pleaseInputCorrectDetails(String s);

  /**
   * Requests the user to input a valid username.
   */
  void pleaseUseAValidUserName();

  /**
   * Requests the user for a valid portfolio name.
   */
  void getPortfolioNames();

  /**
   * Gets the details of the portfolio the user wishes to enter.
   *
   * @param n - The portfolio whose value has to be input.
   */
  void getPortfolioNumber(String n);

  /**
   * ASks for the stocks to be entered in the portfolio.
   *
   * @param pName - Portfolio name which hosts the stocks.
   */
  void getStockforPortfolio(String pName);

  /**
   * Asks the user to enter a particular field s.
   *
   * @param s - The field to be entered.
   */
  void pleaseEnterString(String s);

  /**
   * Asks the user to enter a different portfolio name as the one input was already used.
   */
  void pleaseUseADifferentPortfolioName();

  /**
   * Outputs the file instructions for the user to follow.
   */
  void fileInstructions();

  /**
   * Greets the user after he successfully logs in.
   *
   * @param username - The uer who has just logged in.
   */
  void showGreeting(String username);

  void askPortfolioType();

  void noFlexiblePortfolios();

  void enterPortfoliotoDeleteStock();

  void performanceStartDate();

  void performanceEndDate();

  void enterStockToDelete();

  void enterDateFor(String s, String cause);

  void incorrectDateEntered();

  void enterDateToGetCostBasis(String s);

  void cannotDeleteStocksFromNonExistentStock();

  void yourTransactionsAreNotValid();

  void pleaseCheckFromTheAboveTransactionsAndRectify();

  void enterPortfolioForCostBasis();

  void displayCostBasis(float value);

  void incorrectNumberInput();

  void cannotExamineThePortfolioInvalidState();

  void cannotViewThePortfolioPerformanceInvalidState();

  void printErrorMessage(String message);

  void pleaseEnterAValidPortfolioName();

  void pleaseAddValidTransactions();

  void pressQtoQuit();

  void couldNotCreateStockDueToUnsupportedPurchaseDate();

  void getCommissionFees();

  void pleaseEnterAValidCommissionFees();

  void fetchPortfolioForDCAPlan();
}
