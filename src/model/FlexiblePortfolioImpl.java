package model;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Design Changes -----------------------------------------------------------------
 * We introduced new Flexible portfolio to class to provide the users with the necessary
 * functionalities for the users like to buy and sell stock which was not offered by the
 * inflexible portfolios.
 * Justification - By introducing a new class for flexible portfolio and make it extend the
 * abstract portfolio class, abstracting out the common code present in the inflexible
 * portfolio we were able to reuse the code from inflexible portfolio and make our
 * code compact.
 * --------------------------------------------------------------------------------
 * Class for representing flexible portfolios
 * and operations that can be done on them.
 */

public class FlexiblePortfolioImpl extends AbstractPortfolio {

  FlexiblePortfolioImpl() {
    super();
    isFlexible = true;
  }

  private FlexiblePortfolioImpl(String name, List<Stock> stockList) {
    super(name, stockList);
    isFlexible = true;
  }

  /**
   * Creates a flexible portfolio builder object and returns it
   * further customization.
   *
   * @return - a flexible portfolio builder object.
   */
  public static CustomerBuilder getBuilder() {
    return new CustomerBuilder();
  }

  /**
   * Builder class for Flexible portfolio to prevent instantiation errors while creating
   * a flexible portfolio.
   */

  public static class CustomerBuilder implements Builderportfolio {
    private String portfolioName;
    private final List<Stock> stocks;

    public String getportfolioName() {
      return this.portfolioName;
    }

    private CustomerBuilder() {
      portfolioName = "";
      stocks = new ArrayList<>();
    }

    public CustomerBuilder portfolioName(String name) {
      this.portfolioName = name;
      return this;
    }

    public CustomerBuilder stocks(List<Stock> s) {
      this.stocks.addAll(s);
      return this;
    }

    public CustomerBuilder addStocks(Stock s) {
      this.stocks.add(s);
      return this;
    }

    /**
     * Creates a FlexiblePortfolioImpl object after setting all its fields.
     * Returns FlexiblePortfolioImpl object with all its fields set with.
     * default values or values specified by the user.
     *
     * @return - returns FlexiblePortfolioImpl object.
     */
    public FlexiblePortfolioImpl create() {
      if (portfolioName.equals("")) {
        return null;
      }
      return new FlexiblePortfolioImpl(portfolioName, stocks);
    }
  }

  @Override
  public ArrayList<Stock> getAllStocks() {
    return new ArrayList<>(stocks.values());
  }

  @Override
  public ArrayList<Stock> getAllInFlexibleStocks() {
    Portfolio inFlexible = PortfolioImpl.getBuilder().portfolioName(getPortfolioName())
            .stocks(new ArrayList<>(getAllStocks())).create();
    return inFlexible.getAllStocks();
  }


  @Override
  public float getPortfolioValue(LocalDate date) {
    float total = 0;
    Map<String, Float> stockMap = new HashMap<>();
    PriorityQueue<Map.Entry<LocalDate, Stock>> pq = setQueue();
    while (!pq.isEmpty()) {
      LocalDate prevDate = pq.peek().getKey();
      if (prevDate.isAfter(date)) {
        break;
      }
      Map.Entry<LocalDate, Stock> popped = pq.poll();
      assert popped != null;
      Stock stock = popped.getValue();
      float fetchedValue = fetchValue(stock, date);
      if (fetchedValue == 0 & stock.getQuantity() > 0) {
        return -2;
      }
      if (stockMap.get(stock.getStockSymbol()) != null) {
        stockMap.replace(stock.getStockSymbol(),
                stockMap.get(stock.getStockSymbol()) + fetchedValue);
      } else {
        stockMap.put(stock.getStockSymbol(), fetchedValue);
      }
      if (stockMap.get(stock.getStockSymbol()) < 0) {
        throw new IllegalArgumentException("Invalid " +
                "transaction for stock: " + stock.getStockSymbol());
      }
      total += fetchedValue;
      if (total < 0) {
        return -1;
      }
    }
    return total;
  }

  /**
   * We are using a priority queue here with the Date of the Stock bought or sold and the stock's
   * combination as a key.
   * In the below function we store all of them in a priority queue so that it will
   * be automatically sold
   *
   * @return Priority Queue
   */
  private PriorityQueue<Map.Entry<LocalDate, Stock>> setQueue() {
    PriorityQueue<Map.Entry<LocalDate, Stock>> pq =
            new PriorityQueue<>(new DummyObjectComparator());
    for (Stock stock : stocks.values()) {
      pq.offer(new AbstractMap.SimpleEntry<>(stock.getPurchaseDate(), stock));
    }
    return pq;
  }

  @Override
  public float getCostBasis(LocalDate date) {
    getPortfolioValue(date);
    float total = 0;
    Map<String, Float> stockMap = new HashMap<>();
    PriorityQueue<Map.Entry<LocalDate, Stock>> pq = setQueue();
    while (!pq.isEmpty()) {
      LocalDate prevDate = pq.peek().getKey();
      if (prevDate.isAfter(date)) {
        break;
      }
      Map.Entry<LocalDate, Stock> popped = pq.poll();
      assert popped != null;
      Stock stock = popped.getValue();
      //Adding commission fees for both buy and sell transactions as commission
      // will be taken for both of them
      total += stock.getCommissionFees();
      if (stock.getQuantity() > 0) {
        float fetchedValue = fetchValue(stock, date);
        if (stockMap.get(stock.getStockSymbol()) != null) {
          stockMap.replace(stock.getStockSymbol(),
                  stockMap.get(stock.getStockSymbol()) + fetchedValue);
        } else {
          stockMap.put(stock.getStockSymbol(), fetchedValue);
        }
        if (stockMap.get(stock.getStockSymbol()) < 0) {
          throw new IllegalArgumentException("Invalid " +
                  "transaction for stock: " + stock.getStockSymbol());
        }
        total += fetchedValue;
        if (total < 0) {
          return -1;
        }

      }
    }
    return total;
  }

  static class DummyObjectComparator implements Comparator<Map.Entry<LocalDate, Stock>> {
    // Overriding compare()method of Comparator
    @Override
    public int compare(Map.Entry<LocalDate, Stock> o1, Map.Entry<LocalDate, Stock> o2) {
      int retVal = o1.getKey().compareTo(o2.getKey());
      if (retVal != 0) {
        return retVal;
      }
      return o1.getValue().getStockSymbol().compareTo(o2.getValue().getStockSymbol());
    }
  }

  @Override
  public void addStock(Stock newStock) {
    boolean flag = false;
    for (String s : stocks.keySet()) {
      if (s.equals(newStock.getStockSymbol() + ":" + newStock.getPurchaseDate())) {
        flag = true;
        stocks.replace(s, stocks.get(s).increaseQuantity(newStock.getQuantity()));
        break;
      }
    }
    if (!flag) {
      stocks.put(newStock.getStockSymbol() + ":" + newStock.getPurchaseDate(), newStock);
    }
  }

  @Override
  public float sellStock(Stock stock, LocalDate date) {
    float totalValue = 0;
    stock = stock.increaseQuantity(-2 * stock.getQuantity());
    addStock(stock);
    return totalValue;
  }

  @Override
  public float sellAllStocks() {
    float res = getPortfolioValue(LocalDate.now());
    stocks.clear();
    return res;
  }

  @Override
  public int sellStockQuantity(String stockSymbol, float quantity, LocalDate date) {
    Stock stock;
    if (!stockSymbol.isEmpty()) {
      stock = StockImpl.getBuilder().stockSymbol(stockSymbol).quantity(-1 * quantity)
              .purchaseDate(date).create();
    } else {
      throw new IllegalArgumentException("Cannot delete stock if no stock exists");
    }
    addStock(stock);
    return 0;
  }
}
