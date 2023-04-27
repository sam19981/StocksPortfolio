package integrationtest;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import controller.IController;
import controller.TextController;
import model.IModel;
import model.Model;
import view.IView;
import view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * The following class tests the functionality of the program-.
 * -as a whole by using end-end scenarios.
 * It also tests the functionality of the program with end-end scenarios.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationFunctionalityTest {
  String userInput;
  IModel model;
  ByteArrayInputStream bais;
  ByteArrayOutputStream baos;
  IView view;
  IController controller;

  @Before
  public void setup() {
    System.setIn(bais);
    baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);
    System.setOut(printStream);
    view = new TextView(System.out);
    model = new Model();
  }

  @Test
  public void testAGoForInitialQuit() {
    userInput = "Q\n";
    bais = new ByteArrayInputStream(userInput.getBytes());
    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString()); //output of model transmitted correctly
  }

  @Test
  public void testBGoForInvalidUsernamePassword() {
    userInput = "E\nSam199\nS845764\nSam19981\nsam56\nkarthik543\nkarthik123\nL\nQ";
    bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "Please input correct Name/Password\n" +
            "\n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString()); //output of model transmitted correctly
  }

  @Test
  public void testBGoForInvalidInputs() {
    userInput = "x\nE\nkarthik543\nkarthik123\n-10\nD\nABCD\nPort4\nQ\nL\nH\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port1\n" +
            "Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Which of the above Portfolio do you wish to examine?\n" +
            "Please enter a valid Portfolio name\n" +
            "Which of the above Portfolio do you wish to examine?\n" +
            "Port4\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "AAPL            | 10       | \n" +
            "NFLX            | 2        | \n" +
            "IBM             | 2        | \n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString()); //output of model transmitted correctly
  }

  @Test
  public void testBGoForExistingShow() {
    String userInput = "E\nkarthik543\nkarthik123\nD\nPort1\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port1\n" +
            "Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Which of the above Portfolio do you wish to examine?\n" +
            "Port1\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "AAPL            | 1        | \n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString()); //output of model transmitted correctly
  }

  @Test
  public void testCGoForExistingCompute() {
    String userInput = "E\nkarthik543\nkarthik123\nC\nPort3\n20-10-2022\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port1\n" +
            "Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Which of the above Portfolio's value do you wish to see?\n" +
            "To compute total value enter the valid date in format dd-mm-yyyy\n" +
            "------------------------------------------------\n" +
            "The Total Value of the Portfolio is $2867.8\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString()); //output of model transmitted correctly
  }

  @Test
  public void testGoForExistingCompute() {
    String userInput = "E\nkarthik543\nkarthik123\nC\nPort3\n20-10-2022\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port1\n" +
            "Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Which of the above Portfolio's value do you wish to see?\n" +
            "To compute total value enter the valid date in format dd-mm-yyyy\n" +
            "------------------------------------------------\n" +
            "The Total Value of the Portfolio is $2867.8\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString()); //output of model transmitted correctly
  }

  @Test
  public void testGoForExistingComputeBasis() {
    String userInput = "E\nkarthik543\nkarthik123\nB\nPort3\n20-10-2022\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Enter the portfolio whose Cost Basis has to be determined\n" +
            "To compute cost basis enter the valid date in format dd-mm-yyyy\n" +
            "The total Cost Basis for the selected portfolio is: $2873.8\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString()); //output of model transmitted correctly
  }

  @Test
  public void testDGoForExistingAddPortfolio() {
    String userInput = "E\nkarthik543\nkarthik123\nA" +
            "\n1\nVacation Funds\n2\n1\n1\nGOOG\n2\n20-03-2022\n3\nD\nVacation Funds\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: How many Portfolios do you wish to hold?\n" +
            "Please enter the name of your portfolios\n" +
            "Please enter the name of portfolio 1\n" +
            "Which type of portfolio do you want to hold? Enter 1 or 2\n" +
            "1. Inflexible Portfolio (You will not be able to edit this portfolio later)\n" +
            "2. Flexible Portfolio (You can edit this type of portfolio anytime you want)\n" +
            "Please use a different Portfolio Name\n" +
            "Please enter the name of portfolio 1\n" +
            "Please input the number of Stocks for Portfolio: 1\n" +
            "Please enter the stock symbol for stock: 1\n" +
            "Please enter the quantity for GOOG\n" +
            "To add stock enter the valid date in format dd-mm-yyyy\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port1\n" +
            "Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Vacation Funds\n" +
            "1\n" +
            "Which of the above Portfolio do you wish to examine?\n" +
            "Vacation Funds\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "GOOG            | 2        | \n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString());
  }

  @Test
  public void testEGoForAddingStocks() {
    String userInput = "E\nkarthik543\nkarthik123\nA" +
            "\n1\nVacation Funds\n2\n1\n1\nGOOG\n2\n20-03-2022\n-1\n0\n3\nD\nVacation Funds\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: How many Portfolios do you wish to hold?\n" +
            "Please enter the name of your portfolios\n" +
            "Please enter the name of portfolio 1\n" +
            "Which type of portfolio do you want to hold? Enter 1 or 2\n" +
            "1. Inflexible Portfolio (You will not be able to edit this portfolio later)\n" +
            "2. Flexible Portfolio (You can edit this type of portfolio anytime you want)\n" +
            "Please input the number of Stocks for Portfolio: Vacation Funds\n" +
            "Please enter the stock symbol for stock: 1\n" +
            "Please enter the valid Symbol\n" +
            "Please enter the quantity for GOOG\n" +
            "To add stock enter the valid date in format dd-mm-yyyy\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "The entered commission fees is invalid. Enter a value between $2 and $40.\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "The entered commission fees is invalid. Enter a value between $2 and $40.\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port1\n" +
            "Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Vacation Funds\n" +
            "Which of the above Portfolio do you wish to examine?\n" +
            "Vacation Funds\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "GOOG            | 2        | \n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString());
  }

  @Test
  public void testEGoForAddingStocksInvalidInputs() {
    String userInput = "E\nkarthik543\nkarthik123\nA" +
            "\n1\nVacation Funds\n2\n1\n1\nGOOG\n2\n30-02-2023\n10-02-1980\n20-03-2022" +
            "\n-1\n0\n3\nD\nVacation Funds\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: How many Portfolios do you wish to hold?\n" +
            "Please enter the name of your portfolios\n" +
            "Please enter the name of portfolio 1\n" +
            "Which type of portfolio do you want to hold? Enter 1 or 2\n" +
            "1. Inflexible Portfolio (You will not be able to edit this portfolio later)\n" +
            "2. Flexible Portfolio (You can edit this type of portfolio anytime you want)\n" +
            "Please input the number of Stocks for Portfolio: Vacation Funds\n" +
            "Please enter the stock symbol for stock: 1\n" +
            "Please enter the valid Symbol\n" +
            "Please enter the quantity for GOOG\n" +
            "To add stock enter the valid date in format dd-mm-yyyy\n" +
            "Incorrect date entered\n" +
            "To add stock enter the valid date in format dd-mm-yyyy\n" +
            "Incorrect date entered\n" +
            "To add stock enter the valid date in format dd-mm-yyyy\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "The entered commission fees is invalid. Enter a value between $2 and $40.\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "The entered commission fees is invalid. Enter a value between $2 and $40.\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port1\n" +
            "Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Vacation Funds\n" +
            "Which of the above Portfolio do you wish to examine?\n" +
            "Vacation Funds\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "GOOG            | 2        | \n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString());
  }

  @Test
  public void testEGoForSellingStocksInvalidAndValidInputs() {
    String userInput = "E\nkarthik543\nkarthik123\nS" +
            "\nVacation Funds\nGOOG\n10-02-1979\n20-04-2023\n21-03-2022\n1\n-1\n4\nD\n" +
            "Vacation Funds\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Vacation Funds\n" +
            "Please enter the Portfolio you want to delete the stock from\n" +
            "Enter the Stock you want to sell\n" +
            "To record sold transactions enter the valid date in format dd-mm-yyyy\n" +
            "Incorrect date entered\n" +
            "To record sold transactions enter the valid date in format dd-mm-yyyy\n" +
            "Please enter the quantity\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "The entered commission fees is invalid. Enter a value between $2 and $40.\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port1\n" +
            "Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Vacation Funds\n" +
            "Which of the above Portfolio do you wish to examine?\n" +
            "Vacation Funds\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "GOOG            | 1        | \n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString());
  }

  @Test
  public void testBuyStocksInExistingPortfolioInvalidValidInputs() {
    String userInput = "E\nkarthik543\nkarthik123\nN" +
            "\nPort4\n0\n-1\n1\nAAPL\n0\n-10\n2\n10-02-2023\n21-03-2022\n2\n-1\n4\nD\nPort4\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Please enter the name of your portfolios\n" +
            "Please input the number of Stocks for Portfolio: Port4\n" +
            "Incorrect number entered\n" +
            "Please input the number of Stocks for Portfolio: Port4\n" +
            "Incorrect number entered\n" +
            "Please input the number of Stocks for Portfolio: Port4\n" +
            "Please enter the stock symbol for stock: 1\n" +
            "Please enter the quantity for AAPL\n" +
            "Please input correct Quantity\n" +
            "Please input correct Quantity\n" +
            "To add stock enter the valid date in format dd-mm-yyyy\n" +
            "Incorrect date entered\n" +
            "To add stock enter the valid date in format dd-mm-yyyy\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "The entered commission fees is invalid. Enter a value between $2 and $40.\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "The entered commission fees is invalid. Enter a value between $2 and $40.\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port1\n" +
            "Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Which of the above Portfolio do you wish to examine?\n" +
            "Port4\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "AAPL            | 17       | \n" +
            "NFLX            | 2        | \n" +
            "IBM             | 2        | \n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString());
  }

  @Test
  public void testSellMoreStocksThanBought() {
    String userInput = "E\nkarthik543\nkarthik123\nS" +
            "\nPort4\nIBM\n01-01-2020\n2\n2\n2.1\nPort4\n01-02-2021\nPort4\n1\nIBM" +
            "\n2\n10-01-2018\n3" +
            "\nPort4\n01-02-2021\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Please enter the Portfolio you want to delete the stock from\n" +
            "Enter the Stock you want to sell\n" +
            "To record sold transactions enter the valid date in format dd-mm-yyyy\n" +
            "Please enter the quantity\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "The entered commission fees is invalid. Enter a value between $2 and $40.\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString());
  }

  @Test
  public void testEGoForAddNewPortfolioInvalidInputs() {
    String userInput = "x\nE\nkarthik543\nkarthik123\nA" +
            "\n0\n-1\n1\nVacation Funds\n2\n1\n1\nGOOG\n2\n20-03-2022\n-1\n01.9\n" +
            "\n3\nD\nVacation Funds\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello karthik543!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: How many Portfolios do you wish to hold?\n" +
            "Please input correct Portfolio Number\n" +
            "How many Portfolios do you wish to hold?\n" +
            "Please input correct Portfolio Number\n" +
            "How many Portfolios do you wish to hold?\n" +
            "Please enter the name of your portfolios\n" +
            "Please enter the name of portfolio 1\n" +
            "Which type of portfolio do you want to hold? Enter 1 or 2\n" +
            "1. Inflexible Portfolio (You will not be able to edit this portfolio later)\n" +
            "2. Flexible Portfolio (You can edit this type of portfolio anytime you want)\n" +
            "Please input the number of Stocks for Portfolio: Vacation Funds\n" +
            "Please enter the stock symbol for stock: 1\n" +
            "Please enter the valid Symbol\n" +
            "Please enter the quantity for GOOG\n" +
            "To add stock enter the valid date in format dd-mm-yyyy\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "The entered commission fees is invalid. Enter a value between $2 and $40.\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "The entered commission fees is invalid. Enter a value between $2 and $40.\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "The entered commission fees is invalid. Enter a value between $2 and $40.\n" +
            "Please enter the commission fees charged. Enter a value between $2 and $40.\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: Port1\n" +
            "Port2\n" +
            "Port3\n" +
            "Port4\n" +
            "Vacation Funds\n" +
            "Which of the above Portfolio do you wish to examine?\n" +
            "Vacation Funds\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "GOOG            | 2        | \n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "D: Examine a portfolio\n" +
            "C: Compute the total value of a portfolio\n" +
            "A: Add new portfolios\n" +
            "N: Add new stocks to an existing flexible portfolio\n" +
            "S: Sell stocks from a flexible portfolios\n" +
            "B: Compute cost basis of a flexible portfolio\n" +
            "G: Show performance of a portfolio\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString());
  }

  @Test
  public void testNewUser() {

    String userInput = "N\nkarthik54367\nkarthik123\nI\n0\n1\nsam\n0\n1\nsam\n1" +
            "\nAAPL\n11\n01-01-2020" +
            "\nGOOG\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(bais);

    IController controller6 = new TextController(model, bais, view);
    //log for mock model
    controller6.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello Sank123!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "F: Do you wish to provide your information in form of a file?\n" +
            "I: Do you wish to input your Stock information step-by-step?\n" +
            "Enter your choice: How many Portfolios do you wish to hold?\n" +
            "Please enter the names of your portfolios\n" +
            "Please enter the name of portfolio 1\n" +
            "Please input the number of Stocks for Portfolio: sam\n" +
            "Please enter the Stock Name\n" +
            "Please enter the quantity\n" +
            "Please enter the purchase Date\n" +
            "Please enter the purchase Month\n" +
            "Please enter the purchase Year\n" +
            "Please enter the stock symbol\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello Sank123!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "S: Do you want to see all the portfolios under your name?\n" +
            "C: Do you wish to compute the values of any portfolios?\n" +
            "A: Do you wish to add new portfolios?\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString()); //output of model transmitted correctly
  }


  @Test
  public void testNewUserWithInvalidInputs() {

    IModel mockModel = new Model();

    String userInput = String.format("x%sN%sSanka123%sSanka123%sI%s1%ssam%s1" +
                    "%ssam%s1%s11%s1%s2020" +
                    "%sGOOG%sL%sQ",
            System.lineSeparator(),
            System.lineSeparator(),
            System.lineSeparator(),
            System.lineSeparator(),
            System.lineSeparator(), System.lineSeparator(), System.lineSeparator(),
            System.lineSeparator(), System.lineSeparator(), System.lineSeparator(),
            System.lineSeparator(), System.lineSeparator(), System.lineSeparator(),
            System.lineSeparator(), System.lineSeparator());
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(bais);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(baos);
    System.setOut(printStream);
    IView view = new TextView(System.out);

    IController controller6 = new TextController(mockModel, bais, view);
    //log for mock model
    controller6.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello Sanka123!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "F: Do you wish to provide your information in form of a file?\n" +
            "I: Do you wish to input your Stock information step-by-step?\n" +
            "Enter your choice: How many Portfolios do you wish to hold?\n" +
            "Please enter the names of your portfolios\n" +
            "Please enter the name of portfolio 1\n" +
            "Please input the number of Stocks for Portfolio: sam\n" +
            "Please enter the Stock Name\n" +
            "Please enter the quantity\n" +
            "Please enter the purchase Date\n" +
            "Please enter the purchase Month\n" +
            "Please enter the purchase Year\n" +
            "Please enter the stock symbol\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello Sanka123!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "S: Do you want to see all the portfolios under your name?\n" +
            "C: Do you wish to compute the values of any portfolios?\n" +
            "A: Do you wish to add new portfolios?\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString()); //output of model transmitted correctly
  }

  @Test
  public void testNewUserWithFileInput() {
    String userInput = "N\nkarthik543\nkarthik123\nF\nkarthik543File.txt\nL\nQ";
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(bais);
    IController controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();
    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello Sanka123!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "F: Do you wish to provide your information in form of a file?\n" +
            "I: Do you wish to input your Stock information step-by-step?\n" +
            "Enter your choice: Please place the input file in the users folder\n" +
            "Please enter the file from which you want to input information\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello Sanka123!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "S: Do you want to see all the portfolios under your name?\n" +
            "C: Do you wish to compute the values of any portfolios?\n" +
            "A: Do you wish to add new portfolios?\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString());
  }

  @Test
  public void testNewUserWithNewFile() {

    String userInput = String.format("x%sN%sRandom123%srandom123%sF%snoFile%sSam19981" +
                    ".txt%sS%sL%sQ" +
                    "%s1" +
                    "%s2020" +
                    "%sGOOG%sL%sQ",
            System.lineSeparator(),
            System.lineSeparator(),
            System.lineSeparator(),
            System.lineSeparator(),
            System.lineSeparator(), System.lineSeparator(), System.lineSeparator(),
            System.lineSeparator(), System.lineSeparator(), System.lineSeparator(),
            System.lineSeparator(), System.lineSeparator(), System.lineSeparator(),
            System.lineSeparator());
    ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
    System.setIn(bais);

    controller = new TextController(model, bais, view);
    //log for mock model
    controller.connect();

    assertEquals("\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Enter your unique username: Please enter the password\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello Random123!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "F: Do you wish to provide your information in form of a file?\n" +
            "I: Do you wish to input your Stock information step-by-step?\n" +
            "Enter your choice: Please place the input file in the users folder\n" +
            "Please enter the file from which you want to input information\n" +
            "Please input correct file from which you want to input information\n" +
            "Please place the input file in the users folder\n" +
            "Please enter the file from which you want to input information\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Hello Random123!\n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "S: Do you want to see all the portfolios under your name?\n" +
            "C: Do you wish to compute the values of any portfolios?\n" +
            "A: Do you wish to add new portfolios?\n" +
            "L: Logout\n" +
            "Enter your choice: These are your Portfolio details:\n" +
            "College Funds\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "Google          | 14       | \n" +
            "Apple           | 13       | \n" +
            "Microsoft       | 13       | \n" +
            "School Funds\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "Microfocus      | 29       | \n" +
            "Amazon          | 17       | \n" +
            "Port1\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "Google          | 120      | \n" +
            "Vacation Funds\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "Google          | 100      | \n" +
            "Emergency Funds\n" +
            "------------------------------------------------\n" +
            "Stock Name      | Quantity | \n" +
            "------------------------------------------------\n" +
            "Google          | 100      | \n" +
            "\n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "S: Do you want to see all the portfolios under your name?\n" +
            "C: Do you wish to compute the values of any portfolios?\n" +
            "A: Do you wish to add new portfolios?\n" +
            "L: Logout\n" +
            "Enter your choice: \n" +
            "-------------------\n" +
            "\n" +
            "Menu: \n" +
            "N: Are you a new user?\n" +
            "E: Are you an existing user?\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", baos.toString()); //output of model transmitted correctly
  }

}