package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import view.Draw;

/**
 * This interface acts as a doorway between the controller,
 * and the rest of the Stock, Portfolio, User implementation.
 */
public interface IModel {

  /**
   * Creates user with the given username, password and filename.
   * If the user opts to provide his input manually and not through a file.
   * We create a new file ourselves and associate it with him.
   *
   * @param username - Unique username of the user.
   * @param password - Password associated with the user.
   * @param fileName - File if any provided by the user.
   * @return - -1 or -2 if arguments provided are invalid. 0 if the user is successfully created.
   */
  int createUser(String username, String password, String fileName);

  /**
   * This method checks if the new user that is to be created already exists.
   *
   * @param username - Unique username of the user.
   * @return - True if the user exists, False otherwise.
   */
  boolean checkExistingUser(String username);

  /**
   * The following functions sets the user whose information is provided.
   * as the current user to be operated on.
   * Returns -1 if the details provided are invalid.
   *
   * @param username - Unique username of the user.
   * @param password - Unique password of the user.
   * @return - -1 if the user does not exist or cannot be set, 0 if the set was successful.
   */
  int setUser(String username, String password);

  /**
   * Fetches a list of all the portfolios that is associated with the user.
   *
   * @return - List of portfolios.
   */
  List<Portfolio> getUserPortFolios();

  /**
   * Computes the value of a given portfolio on a given date.
   * It internally computes the value of each of the stock under its belt.
   *
   * @param portfolioName - The name of the Portfolio whose value has to be computed.
   * @param d             - The date on which the value has to be computed.
   * @return - The value of the portfolio summed over all of its stocks.
   */
  float computePortfolioValues(String portfolioName, LocalDate d);

  boolean checkValidMonth(String month);

  boolean checkValidDay(String day);

  boolean checkValidYear(String year);

  /**
   * Checks if a date is valid or not, taking into consideration each of year, month and day.
   *
   * @param year  - Year of the date
   * @param month - Month of the date
   * @param day   - Day of the year
   * @return - LocalDate object if the date was valid else Null
   */
  LocalDate checkValidDate(String year, String month, String day);

  /**
   * Checks if the number is valid or not.
   * The number is asserted as valid only if the number is not negative, non-zero.
   * and does not have any decimal parts
   *
   * @param number - number whose value has to be checked.
   * @return - float value of the String passed as input if valid, -1 otherwise.
   */
  float checkValidNaturalNumber(String number);

  /**
   * Adds new portfolio under the user's belt with the given Portfolio name.
   *
   * @param portfolioName - Name of the new Portfolio.
   * @param portfolioType - Type of portfolio.
   * @return - -1 if Portfolio already exists, 0 if the addition of portfolio was successful.
   */
  int addPortfolios(String portfolioName, String portfolioType);

  /**
   * Create a new stock under the portfolio mentioned by the user if the provided inputs are valid.
   * Returns null if any of them is invalid.
   *
   * @param quantity - Quantity (Shares) of the new Stock.
   * @param date     - Date of purchase.
   * @param symbol - Stock symbol of the stock.
   * @param commissionFees - Commission fees associated with a stock.
   * @return - Stock objected created if all inputs are valid else null.
   */


  Stock createStock(String quantity,
                    LocalDate date, String symbol, float commissionFees);

  /**
   * Associates a given stock object with a Portfolio object.
   *
   * @param portfolio - Portfolio to be associated with.
   * @param stocks    - Stock to be added.
   */
  void addStock(Portfolio portfolio, Stock stocks);

  void addStockCache(Stock stocks);

  List<Stock> getStockCache();

  /**
   * Checks if a given file exists, is of the right format and is not a directory.
   *
   * @param filename - Filename whose validity has to be checked.
   * @return - 0 if valid, -1 if not
   */
  boolean checkValidFile(String filename);

  /**
   * Checks if a username is valid.
   * For a username to be valid the following criteria must be met:.
   * - It has to be unique.
   * - Has to be longer than or equal to 5 but lesser than or equal to 30 characters.
   * - Has to be alphanumeric but '_' is the exception.
   *
   * @param username - Username to be checked validity for.
   * @return - True if valid, False if not.
   */
  boolean checkValidUserName(String username);

  /**
   * Checks if a Stock Symbol is valid or not from the set of Stock Symbols that is valid.
   *
   * @param symbol - Symbole to be validated.
   * @return - 1 if successfully validated, 0 otherwise
   */
  int validateStocksymbol(String symbol);

  /**
   * Checks if the password provided by the user conforms to all the below rules:.
   * - Is of length greater than or equal to length 5.
   * - Does not contain blank spaces in it.
   * - Has digits in it.
   *
   * @param password - Password provided by the new user.
   * @return - True if the password is valid, false otherwise.
   */
  boolean checkValidPassword(String password);

  /**
   * Checks whether the given stock exists withon the given portfolio.
   *
   * @param stock - the stock name which needs to be checked.
   * @return - returns true if the given stock exists within the
   *           given portfolio.
   */
  boolean checkValidStockName(String stock);

  /**
   * Fetches the portfolio of the given name.
   *
   * @param portfolioName - Name of the portfolio which need sto be fetched.
   * @return - return the portfolio object of the given name.
   */
  Portfolio getExistingPortfolio(String portfolioName);

  /**
   * Gets the list of all the flexible portfolios.
   *
   * @return - List containing all the flexible portfolios
   *           attached to the given user.
   */
  List<Portfolio> getFlexiblePortfolios();

  /**
   * Checks of the given portfolioType is valid or not.
   *
   * @param portfolioType - determine if the given portfolio type is valid or not.
   * @return - returns true if it is a valid portfolio. (valid portfolios -
   *           flexible and inflexible portfolios)
   */
  boolean checkValidPortfolioType(String portfolioType);

  /**
   * Sells the stocks from the given portfolio.
   *
   * @param pName    - portfolio name from which the stocks need to be sold.
   * @param sName    - name of the stock to be sold.
   * @param quantity - nuber of share of the given stock to be sold.
   * @param date     - date on which it need to be sold.
   * @return - returns positive if sell was successful
   *           else negative if operation fails.
   */
  int sellStockFromPortfolio(String pName, String sName, float quantity, LocalDate date);

  /**
   * Persists all the transaction done so far to the user's file.
   */
  void save();

  /**
   * returns the total cost basis value for the given portfolio on a given date.
   *
   * @param pName - name of the portfolio
   * @param date  - date on which the cost basis needs to determined for the given
   *              portfolio.
   * @return - The total cost basis value for the given portfolio.
   */
  float getCostBasis(String pName, LocalDate date);

  /**
   * Function to return a Draw object with all the necessary object to
   * show the performance of the given portfolio.
   *
   * @param sDate   - Start date from which performance needs to be determined.
   * @param eDate   - End date till which performance needs to be determined.
   * @param porName - portfolio for which the graph need to be drawn.
   * @return - A draw object to show the performance.
   */
  Draw drawBarGraph(LocalDate sDate, LocalDate eDate, String porName);

  void setCommissionFees(String p, String stock, float commissionFees);

  Float checkCommissionFees(String commissionFees);

  float validatePercentage(String percentage);

  /**
   * Checks if the stock is supported for the given date.
   *
   * @param symbol - Symbol to be checked for.
   * @param date   - Date to be checked for.
   * @return - true if the date is supported, false otherwise.
   */
  boolean checkForDate(String symbol, LocalDate date);

  void addPlan(LocalDate date, LocalDate date2, String pName, HashMap<String, Float> map,
               float amount, float commissionFees, long frequency,
               boolean isOngoing);

  float updateMap(String symbol, float percent);

  float fetchMapTotalValue();

  HashMap<String, Float> getHashMap();

  void removeStockFromCache(int i);

  void removeStockFromMap(String symbol);

  boolean validateInput(String text, String type);

  void clearMap();
}
