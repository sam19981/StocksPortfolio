package parsertest;

import org.junit.Test;

import java.time.LocalDate;

import javax.xml.parsers.ParserConfigurationException;

import model.FlexiblePortfolioImpl;
import model.PortfolioImpl;
import model.Stock;
import model.StockImpl;
import model.User;
import model.UserImpl;
import parsers.UserXmlReaderImpl;
import parsers.UserXmlWriterImpl;
import parsers.XmlReader;
import parsers.XmlWriter;

import static org.junit.Assert.assertEquals;

/**
 * Class to check the xmlWriter by writing the given user object to a file
 * and then reading it from the file and comparing if the user objects
 * are same.
 */
public class UserXmlWriterImplTest {

  @Test
  public void testWriter() throws ParserConfigurationException {
    XmlWriter newWriter = new UserXmlWriterImpl();
    XmlReader newReader = new UserXmlReaderImpl();
    User checkUser =
            UserImpl.createBuilder().setUserName("emand").setPassword("emand123").
                    addPortfolioList(PortfolioImpl.getBuilder().
                            portfolioName("Port1").
                            addStocks(StockImpl.getBuilder()
                                    .purchaseValue(53.616f)
                                    .quantity(100.0f).stockSymbol("GOOG").
                                    purchaseDate(LocalDate.of(2020, 3, 20))
                                    .create())
                            .create())
                    .addPortfolioList(FlexiblePortfolioImpl.getBuilder().
                            portfolioName("Dream Funds").
                            addStocks(StockImpl.getBuilder()
                                    .quantity(59f).stockSymbol("GOOG").
                                    purchaseDate(LocalDate.of(2018, 5, 5))
                                    .create())
                            .create()).
                    create();
    newWriter.writeData("users/writeTestFile.txt", checkUser);

    User newUser = newReader.readData("users/writeTestFile.txt", "emand123");
    assertEquals(checkUser.getUserName(), newUser.getUserName());
    assertEquals(checkUser.getUserName(), newUser.getUserName());
    assertEquals(checkUser.getPortfolio("Port1").getPortfolioName(),
            newUser.getPortfolio("Port1").getPortfolioName());
    assertEquals(checkUser.getPortfolio("Port1").getStock("GOOG").getQuantity(),
            newUser.getPortfolio("Port1").getStock("GOOG").getQuantity(), 0);
    assertEquals(checkUser.getUserName(), newUser.getUserName());
    assertEquals(checkUser.getPortfolio("Dream Funds").getPortfolioName(),
            newUser.getPortfolio("Dream Funds").getPortfolioName());
    assertEquals(checkUser.getPortfolio("Dream Funds").getStock("GOOG")
                    .getQuantity(),
            newUser.getPortfolio("Dream Funds").getStock("GOOG")
                    .getQuantity(), 0);
  }

  @Test
  public void testWritertestNegative() throws ParserConfigurationException {
    XmlWriter newWriter = new UserXmlWriterImpl();
    XmlReader newReader = new UserXmlReaderImpl();

    Stock stock = StockImpl.getBuilder()
            .quantity(-30f).stockSymbol("GOOG").
            purchaseDate(LocalDate.of(2018, 5, 5))
            .create();
    User checkUser =
            UserImpl.createBuilder().setUserName("emand").setPassword("emand123").
                    addPortfolioList(PortfolioImpl.getBuilder().
                            portfolioName("Port1").
                            addStocks(StockImpl.getBuilder()
                                    .purchaseValue(53.616f)
                                    .quantity(100.0f).stockSymbol("GOOG").
                                    purchaseDate(LocalDate.of(2020, 3, 20))
                                    .create())
                            .create())
                    .addPortfolioList(FlexiblePortfolioImpl.getBuilder().
                            portfolioName("Dream Funds").
                            addStocks(StockImpl.getBuilder()
                                    .quantity(59f).stockSymbol("GOOG").
                                    purchaseDate(LocalDate.of(2018, 5, 5))
                                    .create())
                            .create()).
                    create();
    newWriter.writeData("users/writeTestFile.txt", checkUser);

    User newUser = newReader.readData("users/writeTestFile.txt", "emand123");
    assertEquals(checkUser.getUserName(), newUser.getUserName());
    assertEquals(checkUser.getUserName(), newUser.getUserName());
    assertEquals(checkUser.getPortfolio("Port1").getPortfolioName(),
            newUser.getPortfolio("Port1").getPortfolioName());
    assertEquals(checkUser.getPortfolio("Port1").getStock("GOOG").getQuantity(),
            newUser.getPortfolio("Port1").getStock("GOOG").getQuantity(), 0);
    assertEquals(checkUser.getUserName(), newUser.getUserName());
    assertEquals(checkUser.getPortfolio("Dream Funds").getPortfolioName(),
            newUser.getPortfolio("Dream Funds").getPortfolioName());
    float value = newUser.computePortfolioValue("Dream Funds", LocalDate.now());
    newUser.getPortfolio("Dream Funds")
            .sellStockQuantity("GOOG", 30,
                    LocalDate.of(2018, 5, 5));
    assertEquals(newUser.getPortfolio("Dream Funds")
            .getPortfolioValue(LocalDate.now()) + stock
            .getValue(LocalDate.now()), value, 0);
  }

}
