package controller;

import java.util.Map;

import view.BarChart;
import view.Draw;

/**
 * Adapter class to convert the existing text based bar-graph to a
 * graphical bar graph.
 */
public class BarGraphAdapter {

  static BarChart transform(Draw graph) {
    String title = graph.getPortfolioName() + " Performance from " + graph.getStartDate()
            + " to " + graph.getEndDate();
    String xAxis = "Total cost (Scale - thousands)";
    String source = "Alphavantage";
    BarChart chart = new BarChart(title, xAxis, source);

    Map<String, Float> points = graph.getGraphpoints();

    for (Map.Entry<String, Float> val : points.entrySet()) {
      float portVal;
      portVal = val.getValue();
      chart.add(val.getKey(), (int) portVal, "");
    }
    return chart;
  }

}
