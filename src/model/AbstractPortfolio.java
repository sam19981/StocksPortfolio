package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Design Changes ---------------------------------------------------------------------------
 * AbstractPortfolio class was added to house all the common functions of different portfolios
 * to incorporate union data type design for storing the two different kinds of
 * portfolios (flexible and inflexible).
 * Justification - By introducing this abstract class and using union datatype design we were
 * able to avoid major functionality change and were able to reuse the existing code for
 * inflexible portfolio and build on it rather than creating a separate set of classes and
 * functions for implementing inflexible portfolios.
 * ------------------------------------------------------------------------------------------
 * AbstractPortfolio class contains all the common functions for
 * flexible and inflexible portfolios.
 */

public abstract class AbstractPortfolio implements Portfolio {
  protected final String portfolioName;

  protected final Map<String, Stock> stocks;
  protected float costBasis;
  protected boolean isFlexible;

  public AbstractPortfolio() {
    portfolioName = "";
    stocks = new HashMap<>();
  }

  AbstractPortfolio(String name, List<Stock> stockList) {
    portfolioName = name;
    stocks = new HashMap<>();
    costBasis = 10;
    for (Stock newStock : stockList) {
      addStock(newStock);
    }
  }

  @Override
  public String getPortfolioName() {
    return portfolioName;
  }


  @Override
  public Stock getStock(String stockSymbol) {
    stockSymbol = stockSymbol.toUpperCase();
    for (Stock stock : stocks.values()) {
      if (stock.getStockSymbol().equals(stockSymbol)) {
        return stock;
      }
    }
    return null;
  }

  protected float fetchValue(Stock stock, LocalDate date) {
    float value;
    try {
      value = stock.getValue(date);
    } catch (Exception e) {
      return 0;
    }
    return value * stock.getQuantity();
  }

  @Override
  public boolean isFlexible() {
    return isFlexible;
  }

  public abstract ArrayList<Stock> getAllInFlexibleStocks();
}
