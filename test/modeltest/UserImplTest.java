package modeltest;


import org.junit.Test;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import model.Portfolio;
import model.PortfolioImpl;
import model.StockImpl;
import model.User;
import model.UserImpl;

import static org.junit.Assert.assertEquals;

/**
 * Class to check if the userImpl objects are set properly using the builder
 * and functioning as expected.
 */
public class UserImplTest {

  @Test
  public void userBuilderTestwithOneStock() {
    List<Portfolio> newPortfolio = new ArrayList<Portfolio>();
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("School Funds")
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("Goog")
                    .quantity(10)
                    .purchaseDate(LocalDate.of(2020, 11, 2))
                    .purchaseValue((float) 81.3015)
                    .create()).create());

    User newUser =
            UserImpl.createBuilder().setUserName("Sam19981").setPassword("sam123")
                    .addAllPortfolioList(newPortfolio).create();
    assertEquals(newPortfolio, newUser.getAllPortfolios());


  }


  @Test
  public void userBuilderTestwithTwoStock() {
    List<Portfolio> newPortfolio = new ArrayList<>();
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("School Funds")
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
                    .create()).create());

    User newUser =
            UserImpl.createBuilder().setUserName("Sam19981")
                    .addAllPortfolioList(newPortfolio).create();
    assertEquals(newPortfolio, newUser.getAllPortfolios());
    assertEquals("Sam19981", newUser.getUserName());

  }

  @Test
  public void userBuilderTestwithTwoSt() {
    List<Portfolio> newPortfolio = new ArrayList<>();
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("School Funds")
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
                    .create()).create());

    User newUser =
            UserImpl.createBuilder().setUserName("Sam19981")
                    .addAllPortfolioList(newPortfolio).create();
    assertEquals(newPortfolio, newUser.getAllPortfolios());
    assertEquals(newPortfolio.get(0).getPortfolioName(),
            newUser.getPortfolio("School Funds").getPortfolioName());
    assertEquals(newPortfolio.get(0).getStock("Google"),
            newUser.getPortfolio("School Funds").getStock("Google"));
    assertEquals(newPortfolio.get(0).getStock("Apple"),
            newUser.getPortfolio("School Funds").getStock("Apple"));
  }

  @Test
  public void userBuildertComputeAllPortfolio() {
    List<Portfolio> newPortfolio = new ArrayList<>();
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("School Funds")
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
                    .create()).create());

    User newUser =
            UserImpl.createBuilder().setUserName("Sam19981")
                    .addAllPortfolioList(newPortfolio).create();
    assertEquals(2335.794921875, newUser.computeAllPortFolios(
            LocalDate.of(2020, 11, 2)), 0);
  }

  @Test
  public void userBuildertComputeOnePortfolio() {
    List<Portfolio> newPortfolio = new ArrayList<>();
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("School Funds")
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
                    .create()).create());
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("College Funds")
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("Goog")
                    .quantity(10)
                    .purchaseDate(LocalDate.of(2020, 11, 2))
                    .purchaseValue((float) 81.3015)
                    .create()).create());

    User newUser =
            UserImpl.createBuilder().setUserName("Sam19981")
                    .addAllPortfolioList(newPortfolio).create();
    assertEquals(2335.794921875, newUser.computePortfolioValue(
            "School Funds", LocalDate.of(2020, 11
                    , 2)), 0);
    assertEquals(813.0150146484375, newUser.computePortfolioValue(
            "College Funds", LocalDate.of(2020, 11
                    , 2)), 0);
    assertEquals(3148.81005859375, newUser.computeAllPortFolios(
            LocalDate.of(2020, 11, 2)), 0);
  }

  @Test(expected = DateTimeException.class)
  public void userBuildertComputeWithInvalidDates() {
    List<Portfolio> newPortfolio = new ArrayList<>();
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("School Funds")
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
                    .create()).create());
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("College Funds")
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("Goog")
                    .quantity(10)
                    .purchaseDate(LocalDate.of(2020, 11, 2))
                    .purchaseValue((float) 81.3015)
                    .create()).create());

    User newUser =
            UserImpl.createBuilder().setUserName("Sam19981")
                    .addAllPortfolioList(newPortfolio).create();

    assertEquals(3148.81005859375,
            newUser.computeAllPortFolios(LocalDate.of(100, 0
                    , 0)), 0);
  }

  @Test(expected = DateTimeException.class)
  public void userBuildertComputePortfolioWithInvalidDatesNeg() {
    List<Portfolio> newPortfolio = new ArrayList<>();
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("School Funds")
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
                    .create()).create());
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("College Funds")
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("Goog")
                    .quantity(10)
                    .purchaseDate(LocalDate.of(2020, 11, 2))
                    .purchaseValue((float) 81.3015)
                    .create()).create());

    User newUser =
            UserImpl.createBuilder().setUserName("Sam19981")
                    .addAllPortfolioList(newPortfolio).create();

    assertEquals(3148.81005859375,
            newUser.computeAllPortFolios(LocalDate.of(0, -1
                    , 0)), 0);
  }

  @Test(expected = DateTimeException.class)
  public void userBuildertComputePortfolioWithInvalidDatesforYear() {
    List<Portfolio> newPortfolio = new ArrayList<>();
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("School Funds")
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
                    .create()).create());
    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("College Funds")
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("Goog")
                    .quantity(10)
                    .purchaseDate(LocalDate.of(2020, 11, 2))
                    .purchaseValue((float) 81.3015)
                    .create()).create());

    User newUser =
            UserImpl.createBuilder().setUserName("Sam19981")
                    .addAllPortfolioList(newPortfolio).create();

    assertEquals(3148.81005859375,
            newUser.computeAllPortFolios(LocalDate.of(100, -1
                    , 0)), 0);
  }
}