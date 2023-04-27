package viewtest;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import model.Portfolio;
import model.PortfolioImpl;
import view.IView;
import view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * The below file tests thoroughly for the working of the View of the Project.
 * By capturing the output stream we are able to confirm if the right messages are getting printed.
 */
public class TextViewTest {
  IView view;
  ByteArrayOutputStream baos;
  Portfolio p;
  List<Portfolio> l;

  @Before
  public void setup() {
    baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);
    System.setOut(printStream);
    view = new TextView(System.out);
    p = PortfolioImpl.getBuilder().portfolioName("ABC").create();
    l = new ArrayList<>();
    l.add(p);
  }

  @Test
  public void testFetchYear() {
    view.fetchYear();
    assertEquals("Please input the year for computation\n", baos.toString());
  }

  @Test
  public void showPreLoginOptions() {
    view.showPreLoginOptions();
    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString());
  }

  @Test
  public void testshowDataEntryOptions() {
    view.showDataEntryOptions();
    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "F: Do you wish to provide your information in form of a file?\n" +
            "I: Do you wish to input your Stock information step-by-step?\n" +
            "Enter your choice: ", baos.toString());
  }

  @Test
  public void testrequestUsername() {
    view.requestUsername();
    assertEquals("\n" +
            "Enter your unique username: ", baos.toString());
  }

  @Test
  public void testshowOptionError() {
    view.showOptionError();
    assertEquals("\n" +
            "Invalid option. Please try again.", baos.toString());
  }

  @Test
  public void testgetNoPortfolios() {
    view.getNoPortfolios();
    assertEquals("How many Portfolios do you wish to hold?\n", baos.toString());
  }

  @Test
  public void testdisplayPortfolios() {
    view.displayPortfolios(l);
    assertEquals("These are your Portfolio details:\n" +
            "ABC\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n", baos.toString());
  }

  @Test
  public void testdisplayIndividualPortfolio() {
    view.displayIndividualPortfolio(l, p.getPortfolioName());
    assertEquals("ABC\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n", baos.toString());
  }

  @Test
  public void testdisplayPortfolioResults() {
    view.displayPortfolioResults(2);
    assertEquals("------------------------------------------------\n" +
            "Total              | 2.0\n", baos.toString());
  }

  @Test
  public void testfetchPortfolioForComputation() {
    view.fetchPortfolioForComputation();
    assertEquals("Which of the above Portfolio's value do you wish to see?\n", baos.toString());
  }

  @Test
  public void testfetchDate() {
    view.fetchDate();
    assertEquals("Please input the date for computation\n", baos.toString());
  }

  @Test
  public void testfetchMonth() {
    view.fetchMonth();
    assertEquals("Please input the month for computation\n", baos.toString());
  }

  @Test
  public void testpleaseInputCorrectDetails() {
    view.pleaseInputCorrectDetails("Stock name");
    assertEquals("Please input correct Stock name\n", baos.toString());
  }

  @Test
  public void testpleaseUseAValidUserName() {
    view.pleaseUseAValidUserName();
    assertEquals("Please use a valid User Name\n", baos.toString());
  }

  @Test
  public void testpleaseUseADifferentPortfolioName() {
    view.pleaseUseADifferentPortfolioName();
    assertEquals("Please use a different Portfolio Name\n", baos.toString());
  }

  @Test
  public void testfileInstructions() {
    view.fileInstructions();
    assertEquals("Please place the input file in the users folder\n", baos.toString());
  }

  @Test
  public void testshowGreeting() {
    view.showGreeting("Messi");
    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Hello Messi!\n", baos.toString());
  }

  @Test
  public void testpleaseEnterAValidPassword() {
    view.pleaseEnterAValidPassword();
    assertEquals("Please enter a valid password\n", baos.toString());
  }

  @Test
  public void testgetPortfolioNames() {
    view.getPortfolioNames();
    assertEquals("Please enter the names of your portfolios\n", baos.toString());
  }

  @Test
  public void testgetPortfolioNumber() {
    view.getPortfolioNumber("Emergency Funds");
    assertEquals("Please enter the name of portfolio Emergency Funds\n", baos.toString());
  }

  @Test
  public void testgetStockforPortfolio() {
    view.getStockforPortfolio("Emergency Funds");
    assertEquals("Please input the number of Stocks for Portfolio: Emergency Funds\n",
            baos.toString());
  }

  @Test
  public void testpleaseEnterString() {
    view.pleaseEnterString("Password");
    assertEquals("Please enter the Password\n", baos.toString());
  }


}
