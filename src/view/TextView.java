package view;

import java.io.PrintStream;
import java.util.List;

import model.Portfolio;
import model.Stock;

/**
 * Contains all the functions essential for displaying the results
 * of user operations on models
 * and prompts to guide the user to use the program.
 * The functions the class does not take any argument and does not
 * perform any operations and
 * other than displaying text and prompts
 * given by the controller.
 */
public class TextView implements IView {
  private final PrintStream out;

  public TextView(PrintStream out) {
    this.out = out;
  }

  /**
   * displays the prompts for the user to follow while
   * logging in.
   */
  public void showPreLoginOptions() {
    //print the UI
    createSpace();
    out.println("Menu: ");
    out.println("N: Are you a new user?");
    out.println("E: Are you an existing user?");
    out.println("Q: Quit the view");
    out.print("Enter your choice: ");
  }

  public void requestUsername() {
    out.print("\nEnter your unique username: ");
  }

  @Override
  public void showDataEntryOptions() {
    createSpace();
    out.println("Menu: ");
    out.println("F: Do you wish to provide your information in form of a file?");
    out.println("I: Do you wish to input your Stock information step-by-step?");
    out.println("P: Do you wish to create a dollar cost averaging plan?");
    out.print("Enter your choice: ");
  }

  public void showOptionError() {
    out.print("\nInvalid option. Please try again.");
  }

  @Override
  public void getNoPortfolios() {
    out.println("How many Portfolios do you wish to hold?");
  }

  @Override
  public void showUserOperations() {
    createSpace();
    out.println("Menu: ");
    out.println("D: Examine a portfolio");
    out.println("C: Compute the total value of a portfolio");
    out.println("A: Add new portfolios");
    out.println("P: Apply a Dollar Cost Averaging plan on a flexible portfolio");
    out.println("N: Add new stocks to an existing flexible portfolio");
    out.println("S: Sell stocks from a flexible portfolios");
    out.println("B: Compute cost basis of a flexible portfolio");
    out.println("G: Show performance of a portfolio");
    out.println("L: Logout");
    out.print("Enter your choice: ");
  }

  @Override
  public void displayPortfolios(List<Portfolio> portfolio) {
    out.println("These are your Portfolio details:");
    for (Portfolio p : portfolio) {
      printStocksForPortfolio(p);
    }
  }

  @Override
  public void displayIndividualPortfolio(List<Portfolio> portfolios, String portfolioName) {
    for (Portfolio p : portfolios) {
      if (p.getPortfolioName().equals(portfolioName)) {
        printStocksForPortfolio(p);
      }
    }
  }

  @Override
  public void printStocksForPortfolio(Portfolio p) {
    out.println(p.getPortfolioName());
    List<Stock> stocks = p.getAllInFlexibleStocks();
    out.println("------------------------------------------------");
    out.println("Stock Name     " + " | " + "Quantity" + " | ");
    out.println("------------------------------------------------");
    //DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM-dd-uuuu");
    for (Stock s : stocks) {
      out.println(String.format("%-15s", s.getStockSymbol()) + " | "
              + String.format("%-8s", (int) s.getQuantity()) + " | ");
      //+ String.format("%-8s", s.getPurchaseDate().format(formatters)));
    }
  }


  @Override
  public void displayPortfolioResults(float result) {
    out.println("------------------------------------------------");
    out.println("The Total Value of the Portfolio is" + String.format("%5s", " $" + result));
  }

  @Override
  public void displayAllPortfolioNames(List<Portfolio> portfolios) {
    for (Portfolio p : portfolios) {
      System.out.println(p.getPortfolioName());
    }
  }

  public void fetchPortfolioForComputation() {
    out.println("Which of the above Portfolio's value do you wish to see?");
  }

  @Override
  public void fetchPortfolioForDisplay() {
    out.println("Which of the above Portfolio do you wish to examine?");
  }

  @Override
  public void fetchDate() {
    out.println("Please input the date for computation");
  }


  @Override
  public void fetchMonth() {
    out.println("Please input the month for computation");
  }

  @Override
  public void pleaseInputCorrectDetails(String d) {
    out.println("Please input correct " + d);
  }

  @Override
  public void pleaseUseAValidUserName() {
    out.println("Please enter another User Name. The given username is already taken.");
  }

  @Override
  public void pleaseUseADifferentPortfolioName() {
    out.println("Please use a different Portfolio Name");
  }


  @Override
  public void fileInstructions() {
    out.println("Please place the input file in the users folder");
  }

  @Override
  public void showGreeting(String username) {
    createSpace();
    out.println("Hello " + username + "!");
  }

  @Override
  public void askPortfolioType() {
    out.println("Which type of portfolio do you want to hold? Enter 1 or 2");
    out.println("1. Inflexible Portfolio (You will not be able to edit this portfolio later)");
    out.println("2. Flexible Portfolio (You can edit this type of portfolio anytime you want)");
  }

  @Override
  public void noFlexiblePortfolios() {
    out.println("You do not have any flexible portfolios");
  }

  @Override
  public void enterPortfoliotoDeleteStock() {
    out.println("Please enter the Portfolio you want to delete the stock from");
  }

  @Override
  public void enterStockToDelete() {
    out.println("Enter the Stock you want to sell");
  }

  @Override
  public void enterDateFor(String s, String cause) {
    out.println("To " + cause + " enter the " + s);

  }

  public void performanceStartDate() {
    out.println("Enter the start date for portfolio dd-mm-yyyy ");
  }

  public void performanceEndDate() {
    out.println("Enter the end date for portfolio dd-mm-yyyy ");
  }

  @Override
  public void incorrectDateEntered() {
    out.println("Incorrect date entered");
  }

  @Override
  public void enterDateToGetCostBasis(String s) {
    out.println("Enter the " + s + " on which you want to calculate the cost basis");
  }

  @Override
  public void cannotDeleteStocksFromNonExistentStock() {
    out.println("Cannot Sell Stock quantity from non-existent Stock in Portfolio");
  }

  @Override
  public void yourTransactionsAreNotValid() {
    out.println("Your transactions are not valid please correct it.");
  }

  @Override
  public void pleaseCheckFromTheAboveTransactionsAndRectify() {
    out.println("One of the above transaction is incorrect. Please add a stock to rectify it");
  }

  @Override
  public void enterPortfolioForCostBasis() {
    out.println("Enter the portfolio whose Cost Basis has to be determined");
  }

  @Override
  public void displayCostBasis(float value) {
    out.println("The total Cost Basis for the selected portfolio is: $" + value);
  }

  @Override
  public void incorrectNumberInput() {
    out.println("Incorrect number entered");
  }

  @Override
  public void cannotExamineThePortfolioInvalidState() {
    out.println("The Portfolio cannot be examined at the moment. The transactions entered are " +
            "not consistent");
  }

  @Override
  public void cannotViewThePortfolioPerformanceInvalidState() {
    out.println("The performance of the portfolio cannot be viewed at the moment. " +
            "The transactions entered are not consistent.");
  }

  @Override
  public void printErrorMessage(String message) {
    out.println(message);
  }

  @Override
  public void pleaseEnterAValidPortfolioName() {
    out.println("Please enter a valid Portfolio name");
  }

  @Override
  public void pleaseAddValidTransactions() {
    out.println("Please a for the mentioned stock to validate it");
  }

  @Override
  public void pressQtoQuit() {
    out.println("Please press :q if you want to go back to previous menu");
  }

  @Override
  public void couldNotCreateStockDueToUnsupportedPurchaseDate() {
    out.println("Could not create the stock as the specified date is unsupported");
  }

  @Override
  public void getCommissionFees() {
    out.println("Please enter the commission fees charged. Enter a value between $2 and $40.");
  }

  @Override
  public void pleaseEnterAValidCommissionFees() {
    out.println("The entered commission fees is invalid. Enter a value between $2 and $40.");
  }

  @Override
  public void fetchPortfolioForDCAPlan() {
    out.println("Please enter the portfolio name in which you want to pursue this plan.");
  }

  @Override
  public void showperformaceGraph(Draw draw) {
    draw.setOut(out);
    draw.draw();
  }

  @Override
  public void pleaseEnterAValidPassword() {
    out.println("Please enter a valid password");
  }

  @Override
  public void fetchYear() {
    out.println("Please input the year for computation");
  }

  @Override
  public void getPortfolioNames() {
    out.println("" +
            "Please enter the name of your portfolios");
  }

  @Override
  public void getPortfolioNumber(String n) {
    out.println("Please enter the name of portfolio " + n);
  }

  @Override
  public void getStockforPortfolio(String pName) {
    out.println("Please input the number of Stocks for Portfolio: " + pName);
  }

  @Override
  public void pleaseEnterString(String s) {
    out.println("Please enter the " + s);
  }

  private void createSpace() {
    out.println();
    out.println("-------------------");
    out.println();
  }

}
