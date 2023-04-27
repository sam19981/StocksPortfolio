package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface is used to represent the user who is using the program
 * and has the methods necessary to which he would like to perform on the
 * stocks and his portfolios.
 */

public interface User {

  /**
   * Get the portfolio object with name Parameter.
   *
   * @param name - name of the portfolio to be retrieved.
   * @return - The portfolio Object with the given name.
   */
  Portfolio getPortfolio(String name);

  /**
   * Returns the name of the user who is currently loaded in memory.
   *
   * @return - name of the current user.
   */

  String getUserName();

  /**
   * This method computes the value for all the portfolio the current user in the memory has.
   *
   * @param d - The date on which the user wants to compute the portfolio values.
   * @return - Returns the total sum of the values of all the portfolios the user has.
   */

  float computeAllPortFolios(LocalDate d);

  /**
   * This method computes the total value of the given portfolio name.
   *
   * @param portfolioNam - The name of the portfolio which we want to compute the value of.
   * @param date         -  The date on which we want to compute the given portfolio value.
   * @return - Returns the sum of the value all the stock prices in the given portfolio.
   */

  float computePortfolioValue(String portfolioNam, LocalDate date);

  /**
   * This method removes the given stock from the given portfolio.
   * (Implementation not finished)
   *
   * @param n - portfolio Object from which we want to remove the give stock.
   * @param a - stock object to be removed from the given portfolio.
   * @param date - date to remove the stock.
   * @return - returns the stock object which was removed from the portfolio.
   */

  Stock removeStock(Portfolio n, Stock a, LocalDate date);

  /**
   * This method is responsible for adding a new portfolio to the current user
   * in memory.
   *
   * @param n - The portfolio object to be added to the user in memory.
   * @return - Returns an integer value to indicate if the operation was
   *            successful or not. If negative its shows failure and positive
   *            value indicates success.
   */

  int addPortfolio(Portfolio n);

  /**
   * Adds the given stock object to the given portfolio object.
   * (Implementation not finished)
   *
   * @param port  - The portfolio objet to which the stocks are to be added.
   * @param stock - The stock which s to be added to the given portfolio.
   */
  void addStock(Portfolio port, Stock stock);

  /**
   * This method is called when the user wants to sell
   * his portfolio or removing the portfolio.
   *
   * @param o The portfolio object which needs to
   *          be sold or removed.
   * @return - The worth of the portfolio object to be sold the date of sell.
   */

  float sellPortfolio(Portfolio o);

  /**
   * Returns the List of portfolios the current user has.
   *
   * @return - The list of portfolios associated to the user.
   */

  List<Portfolio> getAllPortfolios();

  /**
   * Delete the portfolio with the given name.
   *
   * @param name - name of the portfolio to be deleted.
   * @return - Returns the portfolio which was deleted.
   */

  Portfolio deletePortfolio(String name);

  /**
   * This method deletes all the portfolios related to a user.
   *
   * @return - The list of portfolios which was deleted or sold by the user.
   */

  List<Portfolio> removeAllPortfolios();

  /**
   * Sells the given stock and its share from the given portfolio.
   *
   * @param pName    - name of the portfolio from which the stocks needs to be sold.
   * @param sName    -  name of the stock to be sold.
   * @param quantity - shares of the stock to be sold.
   * @param date     - date on which the stocks to be sold.
   * @return - returns zero if the sell is successful and negative if failure.
   */
  int sellStockFromPortfolio(String pName, String sName, float quantity, LocalDate date);

  /**
   * The plan which needs to be added to the portfolio.
   *
   * @param plan - plan object to be added to the portfolio.
   */

  void addPlan(Plan plan);

  /**
   * Function to get the list of all the plans associated to a
   * portfolio.
   *
   * @return - list of all the plan associated to a portfolio.
   */
  ArrayList<Plan> getPlans();

  /**
   * Function to execute all the plans associated with the portfolios
   * associated to the user.
   */
  void executeAllPlans();
}
