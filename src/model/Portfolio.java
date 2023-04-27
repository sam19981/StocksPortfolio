package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Defines all the operations which can be done on a
 * users portfolio.
 */
public interface Portfolio {

  /**
   * returns the name of the current portfolio.
   *
   * @return - returns the portfolio name
   */
  String getPortfolioName();

  /**
   * returns the list of all the stocks present in a portfolio.
   *
   * @return - returns the list of all the stocks present in a given portfolio.
   */
  ArrayList<Stock> getAllStocks();

  /**
   * Return the total worth of a portfolio at the given date
   * by computing and add all the stock values.
   *
   * @param date - date on which the portfolio value needs to be computed.
   * @return - the total value of the portfolio.
   */
  float getPortfolioValue(LocalDate date);

  /**
   * Adds the given stock to the current portfolio.
   *
   * @param stock - stocks to be added to the given portfolio.
   */
  void addStock(Stock stock);

  /**
   * Sell the given stock in the portfolio and compute the returns.
   *
   * @param stock - stock to be sold in the portfolio.
   * @param date - date on which stock is sold.
   * @return - the profit or loss made by selling the given stock.
   */
  float sellStock(Stock stock, LocalDate date);

  /**
   * sell all the stocks present in a portfolio.
   *
   * @return - total profit or loss made by selling the all the stocks present in a portfolio.
   */
  float sellAllStocks();

  /**
   * return the stock details which user wants to examine.
   *
   * @param stockSymbol - name of the stock which user wants to examine.
   * @return - return the stock object which user wants to examine.
   */

  Stock getStock(String stockSymbol);

  /**
   * Gives the CostBasis for a portfolio on the given date.
   *
   * @param date - the date on which the cost basis needs to
   *             be calculated for the given portfolio.
   * @return - the value of cost basis for the given portfolio on a given date.
   */
  float getCostBasis(LocalDate date);

  /**
   * Function to sell the given the stock and its given number of shares.
   *
   * @param stockSymbol - The stock to be sold.
   * @param quantity    - The quantity of the stock or
   *                    the number of shares of the stock to be sold.
   * @param date        - The date on which the stock needs to be sold.
   * @return - returns zero if its sold and negative if it could not be sold.
   */

  int sellStockQuantity(String stockSymbol, float quantity, LocalDate date);

  /**
   * Determines if this portfolio is flexible or not.
   *
   * @return - true if this portfolio is flexible and false if its not.
   */
  boolean isFlexible();

  /**
   * Gives a list of all the stocks to this flexible portfolio.
   *
   * @return - list of all stocks of the this flexible portfolio.
   */
  ArrayList<Stock> getAllInFlexibleStocks();
}
