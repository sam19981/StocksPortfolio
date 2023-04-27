package view;

import java.io.PrintStream;
import java.util.Map;

/**
 * Design changes ---------------------------------------------------------
 * We introduced a new draw interface to incorporate any new
 * graphical representations which may be requested in the future.
 * Justification - by adding this interface we can incorporate any new
 * graphical representation without making much changes to view as it will
 * be taking only a object of type Draw and will have the draw method
 * to which perform the needed operation without affecting view in any manner.
 * ------------------------------------------------------------------------
 * Class for Showing the performance.
 */
public interface Draw {

  /**
   * Draws the performance of a portfolio in desired format.
   */
  void draw();

  /**
   * Takes an PrintStream object for drawing the performance.
   *
   * @param out - printStream needed to draw.
   */
  void setOut(PrintStream out);

  /**
   * Function to get the x axis scale and y axis scale
   * for the graph.
   *
   * @return - a map of yaxis labels and values to be plotted
   */
  Map<String, Float> getGraphpoints();

  /**
   * Returns the portfolio name for which performance needs to
   * be seen.
   *
   * @return - portfolioname.
   */
  String getPortfolioName();

  /**
   * End date of the performance.
   *
   * @return - endDate
   */
  String getEndDate();

  /**
   * Start date from which the performance.
   *
   * @return - satrtDate
   */
  String getStartDate();

}
