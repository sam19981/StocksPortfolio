package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * class to store all the portfolio data associated to a user.
 */
public class PortfolioImpl extends AbstractPortfolio {

  PortfolioImpl() {
    super();
    costBasis = 0;
    isFlexible = false;
  }

  private PortfolioImpl(String name, List<Stock> stockList) {
    super(name, stockList);
    costBasis = 0;
    isFlexible = false;
  }

  @Override
  public ArrayList<Stock> getAllInFlexibleStocks() {
    return getAllStocks();
  }

  public static CustomerBuilder getBuilder() {
    return new CustomerBuilder();
  }

  /**
   * Builder to set all the fields of a portfolio to class
   * without any errors.
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
     * Creates a PortfolioImpl object after setting all its fields.
     * Returns PortfolioImpl object with all its fields set with.
     * default values or values specified by the user.
     *
     * @return - returns PortfolioImpl object.
     */
    public PortfolioImpl create() {
      if (portfolioName.equals("")) {
        return null;
      }
      return new PortfolioImpl(portfolioName, stocks);
    }
  }

  @Override
  public ArrayList<Stock> getAllStocks() {
    ArrayList<Stock> stockList = new ArrayList<>();
    for (Stock stock : stocks.values()) {
      if (stock.getQuantity() > 0) {
        stockList.add(stock);
      }
    }
    return stockList;
  }

  @Override
  public float getPortfolioValue(LocalDate date) {
    float total = 0;
    for (Stock stock : stocks.values()) {
      total += fetchValue(stock, date);
    }
    return total;
  }

  @Override
  public void addStock(Stock newStock) {
    boolean flag = false;
    for (String s : stocks.keySet()) {
      if (s.contains(newStock.getStockSymbol())) {
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
  public float sellStock(Stock stockVal, LocalDate date) {
    throw new IllegalArgumentException("Cannot sell stocks from an inflexible portfolio");
  }

  @Override
  public float sellAllStocks() {
    throw new IllegalArgumentException("Cannot sell stocks from an inflexible portfolio");
  }

  @Override
  public float getCostBasis(LocalDate date) {
    throw new IllegalArgumentException("Cannot calculate Cost Basis for an inflexible portfolio");
  }

  @Override
  public int sellStockQuantity(String stockName, float quantity, LocalDate date) {
    throw new IllegalArgumentException("Cannot sell stocks from an inflexible portfolio");
  }
}
