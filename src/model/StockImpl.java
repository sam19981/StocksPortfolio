package model;

import java.time.LocalDate;

import connection.AlphaVantageImpl;
import connection.Connection;
import utilities.Utility;

/**
 * DESIGN CHANGES
 * ---------------------------------------------
 * Added another attribute associated with a stock object called commission fees
 * whose value is taken from the user.
 * We no longer have an attribute named Stock Name as the names provided by users could be
 * ambiguous.
 * We reference a Stock using its stock symbol.
 * ---------------------------------------------
 * Class to store all the details of stocks
 * present in a user portfolio.
 */
public class StockImpl implements Stock {
  private final String stockSymbol;
  private final LocalDate purchaseDate;
  private final float purchaseValue;
  private final float quantity;
  private float commissionFees;


  /**
   * initializes the stock with its details name,stocksymbol,purchase date and value on purchase
   * date.
   *
   * @param date   -  date of purchase of the stock.
   * @param value  - number of shares of the given stock bought
   * @param symbol - ticker symbol of the stock
   */
  private StockImpl(LocalDate date, float value,
                    String symbol, float purchaseVal, float fees) {
    float purchaseValue1;
    purchaseDate = date;
    if (value == 0) {
      throw new IllegalArgumentException("Cannot create a stock with 0 quantity");
    } else {
      quantity = value;
    }

    stockSymbol = symbol.toUpperCase();
    if (purchaseVal > 0) {
      purchaseValue1 = purchaseVal;
    } else {
      try {
        purchaseValue1 = getValue(date);
      } catch (Exception e) {
        purchaseValue1 = 0;
      }
    }
    purchaseValue = purchaseValue1;
    commissionFees = fees;
  }

  /**
   * creates the stock object after setting all its fields.
   *
   * @return - returns a new stock object with its field set with default or user specified values.
   */
  public static CustomerBuilder getBuilder() {
    return new CustomerBuilder();
  }

  /**
   * builder to set all the values of the stock with default values or user
   * specified values without any errors.
   */
  public static class CustomerBuilder {
    private LocalDate purchaseDate;
    private float purchaseValue;
    private float quantity;
    private String stockSymbol;
    private float commissionFees;

    private CustomerBuilder() {
      purchaseDate = LocalDate.now().minusDays(1);
      quantity = 0;
      stockSymbol = "";
      purchaseValue = 0;
      commissionFees = 0;
    }

    public CustomerBuilder purchaseDate(LocalDate d) {
      this.purchaseDate = d;
      return this;
    }

    public CustomerBuilder purchaseValue(float v) {
      this.purchaseValue = v;
      return this;
    }

    public CustomerBuilder quantity(float q) {
      this.quantity = q;
      return this;
    }

    public CustomerBuilder commissionFees(float fee) {
      this.commissionFees = fee;
      return this;
    }

    public CustomerBuilder stockSymbol(String symbol) {
      this.stockSymbol = symbol;
      return this;
    }

    /**
     * creates a StockImpl object after setting all its field using a builder.
     *
     * @return - a new stockimpl object with its fields set
     *            with default values or user specified values.
     */
    public StockImpl create() {
      if (stockSymbol.equals("")) {
        return null;
      }
      StockImpl stock = new StockImpl(purchaseDate, quantity, stockSymbol,
              purchaseValue, commissionFees);
      if (stock.getPurchaseValue() == 0) {
        return null;
      }
      return stock;
    }
  }


  @Override
  public float getQuantity() {
    return quantity;
  }

  @Override
  public LocalDate getPurchaseDate() {
    return purchaseDate;
  }

  @Override
  public float getPurchaseValue() {
    try {
      if (!stockSymbol.isEmpty()) {
        return getValue(purchaseDate);
      } else {
        return purchaseValue;
      }
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public String getStockSymbol() {
    return stockSymbol;
  }

  @Override
  public StockImpl increaseQuantity(float i) {
    return StockImpl.getBuilder().quantity(quantity + i)
            .purchaseDate(purchaseDate).stockSymbol(stockSymbol).create();
  }

  @Override
  public float getValue(LocalDate date) {
    float value;
    Connection connection = new AlphaVantageImpl();
    try {
      value = Utility.readOnDate(connection.fetch(stockSymbol, date), date, false);
    } catch (Exception e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    return value;
  }

  @Override
  public void setCommissionFees(float commissionFees) {
    this.commissionFees = commissionFees;
  }

  @Override
  public float getCommissionFees() {
    return commissionFees;
  }

}
