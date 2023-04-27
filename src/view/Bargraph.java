package view;

import java.io.PrintStream;
import java.util.Map;

/**
 * Class to draw and show performance of the given portfolio.
 */
public class Bargraph implements Draw, Comparable<Bargraph> {
  private final Map<String, Float> barGraph;
  private final float scale;
  private PrintStream out;
  private final String portfolioName;
  private final String endDate;
  private final String startDate;

  /**
   * constructor to create a bargraph object with the necessary details to show the
   * performance of a portfolio.
   *
   * @param barGraph      - Hashmap containg the y axis labels and value for each label.
   * @param scale         - The value of each unit present on the graph.
   * @param portfolioName - Name of the portfolio for which the graph is drawn.
   * @param endDate       - The endDate of the timespan
   * @param startDate     - The startDate of the timespan
   */
  public Bargraph(Map<String, Float> barGraph, float scale, String portfolioName,
                  String endDate, String startDate) {
    this.barGraph = barGraph;
    this.scale = scale;
    this.portfolioName = portfolioName;
    this.endDate = endDate;
    this.startDate = startDate;
  }

  @Override
  public Map<String, Float> getGraphpoints() {
    return barGraph;
  }

  @Override
  public String getPortfolioName() {
    return portfolioName;
  }


  @Override
  public String getEndDate() {
    return endDate;
  }

  @Override
  public String getStartDate() {
    return startDate;
  }

  @Override
  public void setOut(PrintStream out) {
    this.out = out;
  }

  @Override
  public void draw() {
    out.println("\n Performance of portfolio " + portfolioName + " from " +
            this.startDate +
            " to " + this.endDate + "\n");
    for (Map.Entry<String, Float> entry : barGraph.entrySet()) {
      String key = entry.getKey();
      float value = entry.getValue();
      StringBuilder bar = new StringBuilder();
      for (int i = 0; i < value / scale; i++) {
        bar.append("*");
      }
      out.println(key + ": " + bar);
    }
    out.println("\n Absolute Scale: * = $" + scale);
  }

  @Override
  public int compareTo(Bargraph o) {
    if (this.barGraph.equals(o.barGraph) && this.endDate.equals(o.endDate)
            && this.scale == o.scale && this.portfolioName.equals(o.portfolioName)) {
      return 0;
    }
    return -1;
  }
}
