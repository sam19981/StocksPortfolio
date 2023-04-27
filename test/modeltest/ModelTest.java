package modeltest;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import view.Draw;
import model.IModel;
import model.Model;
import model.Portfolio;
import model.PortfolioImpl;
import model.Stock;
import model.StockImpl;
import model.User;
import model.UserImpl;
import view.Bargraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test to check all the functions and functionalities in the
 * model by mocking the model class.
 */

public class ModelTest {
  IModel model;

  @Before
  public void setup() {
    model = new Model();
  }

  @Test
  public void TestCheckInvalidUserNameEmptyString() {
    String username = "";
    assertFalse(model.checkValidUserName(username));
  }

  @Test
  public void TestCheckInvalidUserNameSmallLength() {
    String username = "kjb";
    assertFalse(model.checkValidUserName(username));
  }

  @Test
  public void TestCheckInvalidUserNameLargeLength() {
    String username = "kjbfgdrfeg4tger45tgerger4gedsrgtedrgtedrgdrgbrdfgbgrfgdxrfgdrxf";
    assertFalse(model.checkValidUserName(username));
  }

  @Test
  public void TestCheckValidUsername() {
    String username = "kjb_10";
    assertTrue(model.checkValidUserName(username));
  }

  @Test
  public void TestCheckInvalidPasswordEmptyString() {
    String password = "";
    assertFalse(model.checkValidPassword(password));
  }

  @Test
  public void TestCheckInvalidPasswordSmallLength() {
    String password = "kjb";
    assertFalse(model.checkValidPassword(password));
  }

  @Test
  public void TestCheckInvalidPasswordLargeLength() {
    String password = "kjbfgdrfeg4tger45tgerger4gedsrgtedrgtedrgdrgbrdfgbgrfgdxrfgdrxf";
    assertFalse(model.checkValidPassword(password));
  }

  @Test
  public void TestCheckValidPassword() {
    String password = "kjb10232";
    assertTrue(model.checkValidPassword(password));
  }

  @Test
  public void TestCreateUserInvalidUser() {
    String username = "";
    String password = "kjb10232";
    assertEquals(-2, model.createUser(username, password, ""));
  }

  @Test
  public void TestCreateUserForExisingUser() {
    String username = "Karthik";
    String password = "kjb10232";
    assertEquals(-1, model.createUser(username, password, ""));
  }

  @Test
  public void TestCreateExistingUser() {
    String username = "Karthik109";
    String password = "kjb10232";
    assertEquals(-1, model.createUser(username, password, ""));
  }

  @Test
  public void TestSetUserAfterCreationInvalidFile() {
    String username = "Karthik123";
    String password = "kjb102367";
    model.createUser(username, password, "");
    assertEquals(-2, model.setUser(username, password));
  }

  @Test
  public void TestSetUserValidUser() {
    String username = "Sam19981";
    String password = "sam123";
    assertEquals(0, model.setUser(username, password));
  }

  @Test
  public void TestCheckExistingUser() {
    String username = "Sam19981";
    assertFalse(model.checkExistingUser(username));
  }

  @Test
  public void TestCheckNonExistingUser() {
    String username = "Sam563434";
    assertTrue(model.checkExistingUser(username));
  }

  @Test
  public void TestCheckInvalidValidMonth() {
    String year = "2020";
    String month = "3.9";
    String day = "30";
    assertNull(model.checkValidDate(year, month, day));
  }

  @Test
  public void TestCheckInvalidValidDay() {
    String year = "2020";
    String month = "3.9";
    String day = "-3";
    assertNull(model.checkValidDate(year, month, day));
  }

  @Test
  public void TestCheckInvalidValidNaturalNumberEmpty() {
    String number = "";
    assertEquals(-1, model.checkValidNaturalNumber(number), 0);
  }

  @Test
  public void TestCheckInvalidValidNaturalNumberNegative() {
    String number = "-1";
    assertEquals(-1, model.checkValidNaturalNumber(number), 0);
  }

  @Test
  public void TestCheckInvalidValidNaturalNumberZero() {
    String number = "0";
    assertEquals(-1, model.checkValidNaturalNumber(number), 0);
  }

  @Test
  public void TestCheckInvalidValidNaturalNumberDecimalPart() {
    String number = "9.6";
    assertEquals(-1, model.checkValidNaturalNumber(number), 0);
  }

  @Test
  public void TestCheckInvalidValidNaturalNumber() {
    String number = "9";
    assertEquals(9, model.checkValidNaturalNumber(number), 0);
  }

  @Test
  public void TestCreateStock() {
    String quantity = "10";
    String day = "2";
    String month = "3";
    String year = "2020";
    LocalDate d = LocalDate.of(2020, 3, 2);
    String symbol = "GOOG";
    Stock stock = model.createStock(quantity, d, symbol, 0);
    assertEquals(symbol, stock.getStockSymbol());
    assertEquals(Float.parseFloat(quantity), stock.getQuantity(), 0);
    assertEquals(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),
            Integer.parseInt(day)), stock.getPurchaseDate());
    assertEquals("GOOG", stock.getStockSymbol());
  }

  @Test
  public void TestCreateStockInvalidInputs() {
    String quantity = "10.9";
    String symbol = "GOO";
    LocalDate d = LocalDate.of(2020, 3, 2);
    assertNull(model.createStock(quantity, d, symbol, 0));
  }

  @Test
  public void TestAddStock() {
    String quantity = "10";
    String symbol = "GOOG";
    LocalDate d = LocalDate.of(2020, 3, 2);
    Stock stock = model.createStock(quantity, d, symbol, 0);
    Portfolio p = PortfolioImpl.getBuilder().portfolioName("Emergency").create();
    int old = p.getAllStocks().size();
    model.setUser("Sam19981", "sam123");
    model.addStock(p, stock);
    assertEquals(old + 1, p.getAllStocks().size());
  }

  @Test
  public void TestCheckInvalidFile() {
    String file = "Karthik6778";
    assertFalse(model.checkValidFile(file));
  }

  @Test
  public void TestCheckInvalidFileFormat() {
    String file = "Karthik6778.csv";
    assertFalse(model.checkValidFile(file));
  }

  @Test
  public void TestCheckValidFile() {
    String file = "Sam19981.txt";
    assertTrue(model.checkValidFile(file));
  }

  @Test
  public void testGetportfolio() {
    class Mockmodel extends Model {

      public Mockmodel(User userval) {
        super(userval);
      }

    }

    List<Portfolio> newPortfolio = new ArrayList<>();

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
    Mockmodel mocktest = new Mockmodel(newUser);
    assertEquals(mocktest.getUserPortFolios(), newPortfolio);

  }

  @Test
  public void testComputePortfolio() {
    class Mockmodel extends Model {

      public Mockmodel(User userval) {
        super(userval);
      }

    }

    List<Portfolio> newPortfolio = new ArrayList<>();

    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("School Funds")
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("GOOG")
                    .quantity(10)
                    .purchaseDate(LocalDate.of(2020, 11, 2))
                    .create()).create());
    User newUser =
            UserImpl.createBuilder().setUserName("Sam19981").setPassword("sam123")
                    .addAllPortfolioList(newPortfolio).create();
    Mockmodel mocktest = new Mockmodel(newUser);
    assertEquals(16260.30078125, mocktest
            .computePortfolioValues("School Funds",
                    LocalDate.of(2020, 11, 2)), 0);

  }

  @Test
  public void testAddPortfolio() {
    class Mockmodel extends Model {

      public Mockmodel(User userval) {
        super(userval);
      }

    }

    List<Portfolio> newPortfolio = new ArrayList<>();

    newPortfolio.add(PortfolioImpl.getBuilder().portfolioName("School Funds")
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("Goog")
                    .quantity(10)
                    .purchaseDate(LocalDate.of(2020, 11, 2))
                    .create()).create());
    User newUser =
            UserImpl.createBuilder().setUserName("Sam19981").setPassword("sam123")
                    .addAllPortfolioList(newPortfolio).create();
    Mockmodel mocktest = new Mockmodel(newUser);
    assertEquals(0, mocktest.addPortfolios("New Funds", "1"));

  }

  @Test
  public void testDrawGraph() {
    model.setUser("Sam19981", "sam123");
    model.getFlexiblePortfolios();
    Draw tests = model.drawBarGraph(
            LocalDate.of(2018, 05, 04),
            LocalDate.of(2021, 01, 01), "Dream Funds");
    Map<String, Float> graphVal = new HashMap<>();
    graphVal.put("MAY 2018", 0f);
    graphVal.put("JUL 2018", 71818.34f);
    graphVal.put("SEP 2018", 70414.73f);
    graphVal.put("NOV 2018", 64571.375f);
    graphVal.put("JAN 2019", 65865.83f);
    graphVal.put("MAR 2019", 69225.3f);
    graphVal.put("MAY 2019", 65114.17f);
    graphVal.put("JUL 2019", 71784.125f);
    graphVal.put("SEP 2019", 71921.0f);
    graphVal.put("NOV 2019", 76992.64f);
    graphVal.put("JAN 2020", 84619.57f);
    graphVal.put("MAR 2020", 68605.8f);
    graphVal.put("MAY 2020", 84306.28f);
    graphVal.put("JUL 2020", 87494.64f);
    graphVal.put("SEP 2020", 86706.4f);
    graphVal.put("NOV 2020", 103883.66f);
    graphVal.put("JAN 2021", 103360.92f);

    Bargraph expeectedObject = new Bargraph(graphVal, 103883.66f / 50,
            "Dream Funds", LocalDate.of(2021
            , 01, 01).toString(),
            LocalDate.of(2018, 05, 04).toString());
    assertEquals(0, expeectedObject.compareTo((Bargraph) tests));
  }


}
