package modeltest;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Portfolio;
import model.PortfolioImpl;
import model.Stock;
import model.StockImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * class to check if the portfolio objects are getting set properly using a
 * builder and are behaving as expected.
 */
public class PortfolioTest {
  Portfolio builder;

  @Before
  public void setup() {
    builder =
            PortfolioImpl.getBuilder().portfolioName("School Funds")
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("GOOG")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 11, 2))
                            .purchaseValue((float) 81.3015)
                            .create())
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("GOOG")
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
  }

  @Test
  public void testPortfolioAttributes() {
    Portfolio builder =
            PortfolioImpl.getBuilder().portfolioName("School Funds")
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
                            .quantity(10)
                            .purchaseDate(LocalDate.of(2020, 11, 2))
                            .purchaseValue((float) 81.3015)
                            .create()).create();

    assertEquals("School Funds", builder.getPortfolioName());
    assertEquals("GOOG", builder.getStock("GOOG").getStockSymbol());
    assertEquals(10, builder.getStock("GOOG").getQuantity(), 0);
    assertEquals(LocalDate.of(2020, 11, 2),
            builder.getStock("GOOG").getPurchaseDate());
    assertEquals((float) 1626.03, builder.getStock("GOOG").getPurchaseValue(), 0);
  }

  @Test
  public void testPortfolioWithTwoStocks() {
    Portfolio builder =
            PortfolioImpl.getBuilder().portfolioName("College Funds")
                    .addStocks(StockImpl.getBuilder()
                            .stockSymbol("Goog")
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
    assertEquals("GOOG", builder.getStock("Goog").getStockSymbol());
    assertEquals(10, builder.getStock("Goog").getQuantity(), 0);
    assertEquals(LocalDate.of(2020, 11, 2),
            builder.getStock("Goog").getPurchaseDate());
    assertEquals((float) 1626.03, builder.getStock("Goog").getPurchaseValue(), 0);

    assertEquals("College Funds", builder.getPortfolioName());
    assertEquals("AAPL", builder.getStock("AAPL").getStockSymbol());
    assertEquals(14, builder.getStock("AAPL").getQuantity(), 0);
    assertEquals(LocalDate.of(2020, 5, 1),
            builder.getStock("AAPL").getPurchaseDate());
    assertEquals((float) 289.07, builder.getStock("AAPL").getPurchaseValue(), 0);

  }

  @Test
  public void testPortfolioWithTwoSameStocksOnePortfolio() {
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

    assertEquals("School Funds", builder.getPortfolioName());
    assertEquals("GOOG", builder.getStock("GOOG").getStockSymbol());
    assertEquals(20, builder.getStock("GOOG").getQuantity(), 0);
    assertEquals(LocalDate.of(2020, 11, 2),
            builder.getStock("GOOG").getPurchaseDate());
    assertEquals((float) 1626.03, builder.getStock("GOOG").getPurchaseValue(), 0);

    assertEquals("School Funds", builder.getPortfolioName());
    assertEquals("AAPL", builder.getStock("AAPL").getStockSymbol());
    assertEquals(14, builder.getStock("AAPL").getQuantity(), 0);
    assertEquals(LocalDate.of(2020, 5, 1),
            builder.getStock("AAPL").getPurchaseDate());
    assertEquals((float) 289.07, builder.getStock("AAPL").getPurchaseValue(), 0);

  }

  @Test
  public void testAddStocks() {
    List<Stock> stockList = new ArrayList<>();
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("Goog")
            .quantity(10)
            .purchaseDate(LocalDate.of(2020, 11, 2))
            .purchaseValue((float) 81.3015)
            .create());
    stockList.add(StockImpl.getBuilder()
            .stockSymbol("AAPL")
            .quantity(14)
            .purchaseDate(LocalDate.of(2020, 5, 1))
            .create());

    Portfolio builder =
            PortfolioImpl.getBuilder().portfolioName("School Funds").stocks(stockList).create();

    assertEquals(stockList.get(0), builder.getStock("GOOG"));
    assertEquals(stockList.get(1), builder.getStock("AAPL"));
  }

  @Test
  public void computePortfolio() {
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
    assertEquals(4151.7001953125,
            builder.getPortfolioValue(LocalDate.of(2022, 10, 24)), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void computeCostBasis() {
    Portfolio builder =
            PortfolioImpl.getBuilder().portfolioName("School Funds")
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
  public void computePortfolioFutureDate() {
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
            builder.getPortfolioValue(LocalDate.of(2023, 10, 24)), 0);
  }


  @Test
  public void computePortfolioSunday() {
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
    assertEquals(builder.getPortfolioValue(LocalDate.of(2022, 10, 28)),
            builder.getPortfolioValue(LocalDate.of(2022, 10, 30)), 0);
  }

  @Test
  public void computePortfolioInvalidDate() {
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


  @Test(expected = IllegalArgumentException.class)
  public void sellStocksFromPortfolioValidDate() {
    builder.sellStockQuantity("GOOG", 100, LocalDate.of(2022, 10, 10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void sellCompleteStockFromPortfolioValidDate() {
    builder.sellStock(StockImpl.getBuilder().create(), LocalDate.now());
  }

  @Test
  public void checkFlexible() {
    assertFalse(builder.isFlexible());
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkCostBasis() {
    builder.getCostBasis(LocalDate.now());
  }


}
