package view;

import controller.Features;

/**
 * Interface representing functionalities for GUI.
 */
public interface GuiView {

  /**
   * Function to change form one screen to next screen.
   *
   * @param transScreen - the screen to which you want to transition to.
   */
  void transition(String transScreen);

  /**
   * Function to set feature object which has all the functionalities offered by the application.
   * @param features - The feature object which has all the functionalities
   *                 offered by the application.
   */
  void addFeatures(Features features);

  /**
   * Function to get the input string from a textbox.
   *
   * @return - the string entered by the user.
   */
  String getinputString();

  /**
   * Function to get input from textbox2.
   *
   * @return - the string present in the textbox2.
   */
  String getinput1String();

  /**
   * Popup showing the error to be displayed.
   *
   * @param error - the message to be displayed.
   */
  void popUp(String error);

  /**
   * Prints a table.
   *
   * @param data - dato to be presented in the table.
   * @param col  - column names
   * @param port - title for the table
   */
  void table(String[][] data, String[] col, String port);

  /**
   * pop for success.
   *
   * @param error - error message to be displayed.
   */
  void popUpSuccess(String error);

  void addStockButtons(String symbol, String percent);

  /**
   * Showing Buttons to edit the stocks adede to a plan.
   */
  void addPercentageEditButtons();

  /**
   * function to disable buttons.
   */
  void disableButtons();

  /**
   * Function to reset stocks present in the plan.
   */
  void resetStocks();

  /**
   * Reset all the texts fields.
   */
  void resetStockDetails();

  /**
   * Shows the performance.
   *
   * @param chart - the chart which needs to be displayed
   */
  void performance(BarChart chart);

  /**
   * Function to set details to the text boxes for edinting them back.
   * @param sym   - stock symbol to be added
   * @param quant - quantity of the stock
   * @param fee   -  the commission fee to be added to the
   *              stock purchased
   * @param day   - day in numbers
   * @param mon   - month
   * @param yr    -year
   */
  void setStockDetails(String sym, String quant, String fee, String day, String mon, String yr);

  /**
   * Function to add edit buttons to add stocks in portfolio.
   */
  void addEditButtons();

  /**
   * Function to enable buttons in the Add portfolio and add plan.
   */
  void enableButtons();
}
