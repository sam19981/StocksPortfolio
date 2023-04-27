package model;

import java.time.LocalDate;

/**
 * This class represents the stocks which
 * the user has brought and has in his portfolios
 * and the operations which he can perform on it .
 */
public interface Stock {

  /**
   * Returns the number of stocks which
   * the user bought for this stock.
   *
   * @return - The number of this stock the user bought.
   */
  float getQuantity();

  /**
   * Returns the purchase date when the stock
   * was bought.
   *
   * @return - returns the date when the stock was bought.
   */
  LocalDate getPurchaseDate();

  /**
   * Returns the value of the stock when it was purchased.
   *
   * @return - The value of the stock when it was purchased.
   */

  float getPurchaseValue();

  /**
   * Returns the value of the stock  at the given date.
   *
   * @param d - The date on which we are
   *          trying to determine the stock value.
   * @return - the value of the stock at that date.
   */

  float getValue(LocalDate d);

  /**
   * Returns the String name for the given stock.
   *
   * @return - Returns the sticker for the stock symbol.
   */
  String getStockSymbol();

  /**
   * Increase the existing stocks number of shares.
   *
   * @param i - represents the quantity by which we need
   *          to increase the quantity value.
   * @return - returns the stock with increased quantity value.
   */
  Stock increaseQuantity(float i);

  /**
   * Adds the commissionFee for the given stock.
   *
   * @param commissionFees - the commission spent for buying the stock.
   */
  void setCommissionFees(float commissionFees);

  /**
   * Function to get the commission which was spent for buying the stock.
   *
   * @return - the commission amount spent to buy the stock.
   */
  float getCommissionFees();
}
