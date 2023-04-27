package parsertest;

import org.junit.Test;

import java.time.LocalDate;

import model.FlexiblePortfolioImpl;
import model.PortfolioImpl;
import model.StockImpl;
import model.User;
import model.UserImpl;
import parsers.UserXmlReaderImpl;
import parsers.XmlReader;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * class to check xmlreader class by reading the data in the file
 * and checking if the user object is loaded with the values as
 * expected.
 */
public class UserXmlReaderImplTest {

  @Test
  public void readerTest() {
    XmlReader newReader = new UserXmlReaderImpl();
    User newUser = newReader.readData("users/Sam19981.txt", "sam123");
    User checkUser =
            UserImpl.createBuilder().setUserName("Sam19981").setPassword("sam123").
                    addPortfolioList(PortfolioImpl.getBuilder().
                            portfolioName("School Funds").
                            addStocks(StockImpl.getBuilder()
                                    .quantity(17f).stockSymbol("AMZN").
                                    purchaseDate(LocalDate.of(2020, 5, 5))
                                    .create())
                            .create()).
                    create();
    assertEquals(checkUser.getUserName(), newUser.getUserName());
    assertEquals(checkUser.getPortfolio("School Funds").getPortfolioName(),
            newUser.getPortfolio("School Funds").getPortfolioName());
    assertEquals(checkUser.getPortfolio("School Funds").getStock("AMZN")
                    .getQuantity(),
            newUser.getPortfolio("School Funds").getStock("AMZN")
                    .getQuantity(), 0);
  }


  @Test
  public void readerParserTestFlexible() {
    XmlReader newReader = new UserXmlReaderImpl();
    User newUser = newReader.readData("users/Sam19981.txt", "sam123");
    User checkUser =
            UserImpl.createBuilder().setUserName("Sam19981").setPassword("sam123").
                    addPortfolioList(FlexiblePortfolioImpl.getBuilder().
                            portfolioName("Dream Funds").
                            addStocks(StockImpl.getBuilder()
                                    .quantity(59f).stockSymbol("GOOG").
                                    purchaseDate(LocalDate.of(2018, 5, 5))
                                    .create())
                            .create()).
                    create();
    assertEquals(checkUser.getUserName(), newUser.getUserName());
    assertEquals(checkUser.getPortfolio("Dream Funds").getPortfolioName(),
            newUser.getPortfolio("Dream Funds").getPortfolioName());
    assertEquals(checkUser.getPortfolio("Dream Funds").getStock("GOOG")
                    .getQuantity(),
            newUser.getPortfolio("Dream Funds").getStock("GOOG")
                    .getQuantity(), 0);
  }

  @Test
  public void readFlexibleAndInflexible() {
    XmlReader newReader = new UserXmlReaderImpl();
    User newUser = newReader.readData("users/Sam19981.txt", "sam123");
    User checkUser =
            UserImpl.createBuilder().setUserName("Sam19981").setPassword("sam123").
                    addPortfolioList(PortfolioImpl.getBuilder().
                            portfolioName("School Funds").
                            addStocks(StockImpl.getBuilder()
                                    .quantity(17).stockSymbol("AMZN").
                                    purchaseDate(LocalDate.of(2020, 5, 5))
                                    .create())
                            .create()).addPortfolioList(FlexiblePortfolioImpl.getBuilder().
                            portfolioName("Dream Funds").
                            addStocks(StockImpl.getBuilder()
                                    .quantity(59).stockSymbol("GOOG").
                                    purchaseDate(LocalDate.of(2018, 5, 5))
                                    .create())
                            .create())
                    .create();
    assertEquals(checkUser.getUserName(), newUser.getUserName());
    assertEquals(checkUser.getPortfolio("School Funds").getPortfolioName(),
            newUser.getPortfolio("School Funds").getPortfolioName());
    assertEquals(checkUser.getPortfolio("School Funds").getStock("AMZN")
                    .getQuantity(),
            newUser.getPortfolio("School Funds").getStock("AMZN")
                    .getQuantity(), 0);

    assertEquals(checkUser.getUserName(), newUser.getUserName());
    assertEquals(checkUser.getPortfolio("Dream Funds").getPortfolioName(),
            newUser.getPortfolio("Dream Funds").getPortfolioName());
    assertEquals(checkUser.getPortfolio("Dream Funds").getStock("GOOG")
                    .getQuantity(),
            newUser.getPortfolio("Dream Funds").getStock("GOOG")
                    .getQuantity(), 0);
  }

  @Test
  public void readerTestExceptionForWrongFileFormat() {
    XmlReader newReader = new UserXmlReaderImpl();
    User newUser = newReader.readData("users/EmanFile.txt", "");
    assertNull(newUser);
  }

}
