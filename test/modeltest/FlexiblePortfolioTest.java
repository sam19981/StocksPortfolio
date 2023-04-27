package modeltest;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.FlexiblePortfolioImpl;
import model.Portfolio;
import model.PortfolioImpl;
import model.Stock;
import model.StockImpl;

import static org.junit.Assert.assertEquals;

/**
 * Class to test flexible portfolios and their functionalities.
 */
public class FlexiblePortfolioTest {
  Portfolio flexiblePortfolio;

  @Before
  public void setup() {
    flexiblePortfolio = FlexiblePortfolioImpl.getBuilder().portfolioName("School Funds")
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("IBM")
                    .quantity(1)
                    .purchaseDate(LocalDate.of(2020, 11, 2))
                    .create())
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("IBM")
                    .quantity(1)
                    .purchaseDate(LocalDate.of(2020, 10, 2))
                    .create())
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("AAPL")
                    .quantity(1)
                    .purchaseDate(LocalDate.of(2020, 5, 1))
                    .create())
            .create();
  }

  @Test
  public void testPortfolioAttributes() {
    Portfolio builder =
            FlexiblePortfolioImpl.getBuilder().portfolioName("School Funds")
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("IBM")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 11, 2))
                            .purchaseValue((float) 81.3015)
                            .create()).create();

    assertEquals("School Funds", builder.getPortfolioName());
    assertEquals("IBM", builder.getStock("IBM").getStockSymbol());
    assertEquals(10, builder.getStock("IBM").getQuantity(), 0);
    assertEquals(LocalDate.of(2020, 11, 2),
            builder.getStock("IBM").getPurchaseDate());
    assertEquals((float) 112.91, builder.getStock("IBM").getPurchaseValue(), 0);
  }

  @Test
  public void testPortfolioWithTwoStocks() {
    Portfolio builder =
            FlexiblePortfolioImpl.getBuilder().portfolioName("College Funds")
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("IBM")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 11, 2))
                            .purchaseValue((float) 81.3015)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("AAPL")
                            .quantity(14)
                            .purchaseDate(LocalDate.of(2020, 5, 1))
                            .create())
                    .create();

    assertEquals("College Funds", builder.getPortfolioName());
    assertEquals("IBM", builder.getStock("IBM").getStockSymbol());
    assertEquals(10, builder.getStock("IBM").getQuantity(), 0);
    assertEquals(LocalDate.of(2020, 11, 2),
            builder.getStock("IBM").getPurchaseDate());
    assertEquals((float) 112.91, builder.getStock("IBM").getPurchaseValue(), 0);

    assertEquals("College Funds", builder.getPortfolioName());
    assertEquals("AAPL", builder.getStock("AAPL").getStockSymbol());
    assertEquals(14, builder.getStock("AAPL").getQuantity(), 0);
    assertEquals(LocalDate.of(2020, 5, 1),
            builder.getStock("AAPL").getPurchaseDate());
    assertEquals((float) 289.07, builder.getStock("AAPL").getPurchaseValue(), 0);
  }

  @Test
  public void testPortfolioWithTwoSameStocksOnePortfolio() {
    assertEquals("School Funds", flexiblePortfolio.getPortfolioName());
    assertEquals("IBM", flexiblePortfolio.getStock("IBM").getStockSymbol());
    assertEquals((float) 120.57, flexiblePortfolio.getStock("IBM").getPurchaseValue(), 0);

    assertEquals("School Funds", flexiblePortfolio.getPortfolioName());
    assertEquals("AAPL", flexiblePortfolio.getStock("AAPL").getStockSymbol());
    assertEquals((float) 289.07, flexiblePortfolio.getStock("AAPL").getPurchaseValue(), 0);
  }

  @Test
  public void testBuyStocks() {
    assertEquals(437.83, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    List<Stock> stockList = new ArrayList<>();
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("IBM")
            .quantity(10)
            .purchaseDate(LocalDate.of(2020, 11, 2))
            .purchaseValue((float) 81.3015)
            .create());
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("AAPL")
            .quantity(14)
            .purchaseDate(LocalDate.of(2020, 5, 1))
            .create());
    flexiblePortfolio.addStock(stockList.get(0));
    assertEquals(1883.03, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.addStock(stockList.get(1));
    assertEquals(3966.089, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
  }

  @Test
  public void testBuyStocksCheckII() {
    assertEquals(437.83, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    List<Stock> stockList = new ArrayList<>();
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("IBM")
            .quantity(1)
            .purchaseDate(LocalDate.of(2020, 11, 2))
            .purchaseValue((float) 81.3015)
            .create());
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("AAPL")
            .quantity(1)
            .purchaseDate(LocalDate.of(2020, 5, 1))
            .create());
    flexiblePortfolio.addStock(stockList.get(0));
    assertEquals(582.3499, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.addStock(stockList.get(1));
    assertEquals(731.140, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
  }

  @Test
  public void testBuyAndSellStocks() {
    assertEquals(437.83, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    List<Stock> stockList = new ArrayList<>();
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("IBM")
            .quantity(1)
            .purchaseDate(LocalDate.of(2020, 11, 2))
            .purchaseValue((float) 81.3015)
            .create());
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("AAPL")
            .quantity(1)
            .purchaseDate(LocalDate.of(2020, 5, 1))
            .create());
    flexiblePortfolio.addStock(stockList.get(0));
    assertEquals(582.3499, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.addStock(stockList.get(1));
    assertEquals(731.140, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.sellStockQuantity("IBM", 2, LocalDate.now());
    assertEquals(442.100, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.sellStockQuantity("IBM", 1, LocalDate.now());
    assertEquals(297.5800, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.sellStockQuantity("AAPL", 2, LocalDate.now());
    assertEquals(0, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
  }

  @Test
  public void testBuyAndSellStocksNegative() {
    assertEquals(437.83, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    List<Stock> stockList = new ArrayList<>();
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("IBM")
            .quantity(1)
            .purchaseDate(LocalDate.of(2020, 11, 2))
            .purchaseValue((float) 81.3015)
            .create());
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("AAPL")
            .quantity(1)
            .purchaseDate(LocalDate.of(2020, 5, 1))
            .create());
    flexiblePortfolio.addStock(stockList.get(0));
    assertEquals(582.3499, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.addStock(stockList.get(1));
    assertEquals(731.140, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.sellStockQuantity("IBM", 2, LocalDate.now());
    assertEquals(442.100, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.sellStockQuantity("IBM", 2, LocalDate.now());
    assertEquals(-1.0, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.addStock(stockList.get(1));
    assertEquals(-1.0, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.addStock(stockList.get(0));
    assertEquals(446.3699, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
  }

  @Test
  public void testBuyAndSellStocksZero() {
    assertEquals(437.83, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    List<Stock> stockList = new ArrayList<>();
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("IBM")
            .quantity(1)
            .purchaseDate(LocalDate.of(2020, 11, 2))
            .purchaseValue((float) 81.3015)
            .create());
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("AAPL")
            .quantity(1)
            .purchaseDate(LocalDate.of(2020, 5, 1))
            .create());
    flexiblePortfolio.addStock(stockList.get(0));
    assertEquals(582.3499, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.addStock(stockList.get(1));
    assertEquals(731.140, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.sellStockQuantity("IBM", 2, LocalDate.now());
    assertEquals(442.100, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.sellStockQuantity("IBM", 1, LocalDate.now());
    assertEquals(297.58, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
    flexiblePortfolio.addStock(StockImpl.getBuilder().
            stockSymbol("IBM").quantity(1).
            purchaseDate(LocalDate.of(2022, 5, 11)).create());
    assertEquals(442.100, flexiblePortfolio.getPortfolioValue(LocalDate.now()), 0.1);
  }

  @Test
  public void computeCostBasisFlexible() {
    Portfolio builder =
            FlexiblePortfolioImpl.getBuilder().portfolioName("School Funds")
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 11, 2))
                            .purchaseValue((float) 81.3015).commissionFees(10)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 10, 2))
                            .purchaseValue((float) 81.3015).commissionFees(3)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("AAPL")
                            .quantity(14)
                            .purchaseDate(LocalDate.of(2020, 5, 1))
                            .commissionFees(2)
                            .create())
                    .create();
    assertEquals(4151.7001953125,
            builder.getPortfolioValue(LocalDate.of(2022, 10, 24)), 0);
    assertEquals(builder.getPortfolioValue(LocalDate.of(2022, 10, 24))
                    + 3 + 2 + 10,
            builder.getCostBasis(LocalDate.of(2022, 10, 24)), 0);
  }

  @Test
  public void computePortfolioFutureDateFlexible() {
    Portfolio builder =
            FlexiblePortfolioImpl.getBuilder().portfolioName("School Funds")
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 11, 2))
                            .purchaseValue((float) 81.3015)
                            .commissionFees(10)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 10, 2))
                            .purchaseValue((float) 81.3015)
                            .commissionFees(3)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("AAPL")
                            .quantity(14)
                            .purchaseDate(LocalDate.of(2020, 5, 1))
                            .commissionFees(5)
                            .create())
                    .create();
    assertEquals(-2,
            builder.getPortfolioValue(LocalDate.of(2023, 10, 24)), 0);
  }

  @Test
  public void computeFlexiblePortfolioSunday() {
    Portfolio builder =
            FlexiblePortfolioImpl.getBuilder().portfolioName("School Funds")
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 11, 2))
                            .purchaseValue((float) 81.3015)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 10, 2))
                            .purchaseValue((float) 81.3015)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("AAPL")
                            .quantity(14)
                            .purchaseDate(LocalDate.of(2020, 5, 1))
                            .create())
                    .create();
    assertEquals(builder.getPortfolioValue(LocalDate.of(2022, 10, 28)),
            builder.getPortfolioValue(LocalDate.of(2022, 10, 30)), 0);
  }

  @Test
  public void computeFlexiblePortfolioInvalidDate() {
    Portfolio builder =
            PortfolioImpl.getBuilder().portfolioName("School Funds")
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 11, 2))
                            .purchaseValue((float) 81.3015)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 10, 2))
                            .purchaseValue((float) 81.3015)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("AAPL")
                            .quantity(14)
                            .purchaseDate(LocalDate.of(2020, 5, 1))
                            .create())
                    .create();
    assertEquals(0,
            builder.getPortfolioValue(LocalDate.of(20, 10, 30)), 0);
  }

  @Test(expected = NullPointerException.class)
  public void computeFlexiblePortfolioNegativeQuantity() {
    Portfolio builder =
            PortfolioImpl.getBuilder().portfolioName("School Funds")
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
                            .quantity(-10)
                            .purchaseDate(LocalDate.of(2020, 11, 2))
                            .purchaseValue((float) 81.3015)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 10, 2))
                            .purchaseValue((float) 81.3015)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("AAPL")
                            .quantity(14)
                            .purchaseDate(LocalDate.of(2020, 5, 1))
                            .create())
                    .create();
    assertEquals(0,
            builder.getPortfolioValue(LocalDate.of(20, 10, 30)), 0);
  }
}
