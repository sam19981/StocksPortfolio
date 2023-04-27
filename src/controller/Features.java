package controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import model.Stock;

/**
 * Features class describes the various functionalities supported by
 * the program and options available for a user to perform on a
 * portfolio.
 */

public interface Features {

  /**
   * function creates a new user.
   */

  void login();

  void createUser(String user, String password, String filepath);

  /**
   * create a new portfolio for the given user.
   *
   * @return -  the newly created portfolio.
   */
  int addPortfolio(String pName);

  int addStockToNewPortfolio(String pName, String symbol, String fees, String day,
                             String month, String year, String quantity);

  /**
   * Adds the stocks desired by the user to the given
   * portfolio.
   *
   * @param portfolio - portfolio to which the user wishes to add stocks.
   */
  void addStocksToPortfolio(String portfolio, String stock, String quantity, String day,
                            String month, String year, String commissionFees);

  /**
   * returs the total stock basis for the given portfolio.
   *
   * @param portfolio - the portfolio for which we need to calculate the
   *                  stockbasis.
   * @return - The total cost basis for the given portfolio.
   */

  float getCostBasis(String portfolio, String day, String month, String year);


  /**
   * Compute the total cost of the given portfolio on the given date.
   *
   * @param portfolio - portfolio whose total costs need to be determined.
   * @param day       - day on which the cost need to computed for the portfolio.
   * @return - The toatl cost of the portfolio on the given date.
   */

  float computePortfolio(String portfolio, String day, String month, String year);

  /**
   * Function to graphically show the performance of the portfolio
   * in the given timespan.
   */

  void showPerformace();

  /**
   * Transitions to the examine portfolio page from the current page.
   */
  void examinePortfolio();

  /**
   * Display the composition of the given portfolio. (Stocks present in it.)
   *
   * @param portName - the name of the portfolio which needs to be examined.
   */
  void displayPortfolio(String portName);

  /**
   * The list of operations which can be done by the application.
   *
   * @return - all the operations which can be done by the application.
   */
  List<String> operationsList();

  /**
   * Function to transition to the appropriate Screen based on User Interaction.
   * Ex - from Examine portfolio to Compute portfolio etc.
   *
   * @param option - list of options the user can see the gui for the
   *               when loggedIn.
   */
  void options(String option);

  /**
   * gets all the flexible portfolios associated to the current user.
   *
   * @return - the list of flexible portfolio.
   */
  List<String> getPortfolios();

  /**
   * Function to sell stocks from the given portfolio.
   *
   * @param portfolioName - name of the portfolio.
   * @param symbol        - the stock symbol which you want to sell.
   * @param quantity      - the amount of stalk to be sold.
   * @param day           - Day in numbers(1-31).
   * @param month         - Month in numbers (1-12).
   * @param year          - year (1995-2022)
   * @param fees          - commission fee for the transaction.
   */
  void sellStockFromPortfolio(String portfolioName, String symbol, String quantity,
                              String day, String month, String year, String fees);

  /**
   * Function to update the Map which has Stocks and their percentage
   * of the investment to be made on each stocks.
   *
   * @param symbol  - the stock symbol
   * @param percent - investment percentage.
   * @return - updated map with the symbols and values.
   */
  float updatePercent(String symbol, String percent);

  /**
   * Function to see the performance of the given portfolio.
   *
   * @param stateDate     - start date from which you want to see
   *                      the portfolio performance.
   * @param endDate       - end date of the performance
   * @param portfolioName - portfolio whose performance you wish to see.
   */
  void performance(LocalDate stateDate, LocalDate endDate, String portfolioName);

  /**
   * Represents the invest plan to be applied for thr given portfolio.
   *
   * @param sDay      - start day of the plan in numbers.
   * @param sMonth    -  start month for the plan in numbers.
   * @param sYear     - start year for the plan in years.
   * @param eDay      - end day for the plan in numbers.
   * @param eMonth    - end month for the plan in numbers.
   * @param eYear     - end year for the plan in numbers.
   * @param pName     - portfolio name for which the plan needs to be applied on.
   * @param amount    - the amount of investment to be made in the plan.
   * @param fees      - The total commission fee for the all the stocks in the plan.
   * @param frequency - the frequency of with which the plan needs to be applied on.
   */
  void addPlan(String sDay, String sMonth, String sYear, String eDay, String eMonth, String eYear,
               String pName, String amount, String fees, String frequency);

  /**
   * Function to save the user transaction and plans.
   *
   * @param message - message to be showed while saving.
   */
  void save(String message);

  /**
   * The stock cache keeps track of the stocks which you want to add to
   * your plan but have not saved it yet to allow the users to give the
   * flexibility of editing it before saving it to the plan.
   *
   * @return - the cache list of the stocks.
   */
  List<Stock> getStockCache();

  /**
   * Returns the list of the stocks present in a plan.
   *
   * @return Map of all the stocks present in the plan.
   */
  HashMap<String, Float> getHashMap();

  /**
   * Function to remove the stock from the cache used for adding the plan for a
   * portfolio.
   *
   * @param indexOfStock - the index of the stock to be removed from the
   *                     cache for the stocks present in the plan to
   *                     applied to the given portfolio.
   */
  void removeStockFromCache(int indexOfStock);

  /**
   * Function to remove the stock from the cache use for showing it in add new portfolio.
   *
   * @param stock - stocks to be removed from the cache use for adding to the portfolio.
   */
  void removeStockFromMap(String stock);

  /**
   * Function to validate the portfolio and its type.
   *
   * @param portfolioName - name of the portfolio.
   * @param portfolioType - type of the portfolio.
   * @return - return true if the portfolio and its type exists else false
   */
  boolean validate(String portfolioName, String portfolioType);
}
