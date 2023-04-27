package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import model.IModel;
import model.Portfolio;
import model.Stock;
import utilities.Utility;
import view.BarChart;
import view.Draw;
import view.GuiView;

/**
 *  Controller acts as a delegator between the GUI and the
 *  model class and makes the necessary features available to the user.
 */
public class GUIcontrolller implements Features {

  IModel model;
  GuiView view;

  List<String> operationsList = Arrays.asList(
          "Examine a portfolio",
          "Compute the total value of a portfolio",
          "Add new portfolios",
          "Add a new dollar cost averaging plan",
          "Buy stocks",
          "Sell stocks",
          "Compute cost basis of a flexible portfolio",
          "Show performance of a portfolio",
          "Logout");

  public GUIcontrolller(IModel m) {
    model = m;
  }

  public void setView(GuiView v) {
    view = v;
    view.addFeatures(this);
  }

  @Override
  public void login() {
    String user = view.getinputString();
    String pass = view.getinput1String();
    try {
      int status = model.setUser(user, pass);
      if (status >= 0) {
        view.transition("Operations");
      } else if (status == -1) {
        throw new IllegalArgumentException("Invalid user login details");
      } else if (status == -2) {
        throw new IllegalArgumentException("Invalid File provided");
      } else if (status == -3) {
        throw new IllegalArgumentException("PLease enter valid login details");
      }
    } catch (Exception e) {
      view.popUp(e.getMessage());
    }
  }


  @Override
  public void createUser(String user, String password, String filepath) {
    try {
      if (!model.checkValidUserName(user)) {
        throw new IllegalArgumentException("Please provide a valid username");
      }
      if (!model.checkExistingUser(user)) {
        throw new IllegalArgumentException("The given user name already exists. " +
                "Please pick another one");
      }
      if (!model.checkValidPassword(password)) {
        throw new IllegalArgumentException("PLease enter a valid password");
      }
      int status = model.createUser(user, password, filepath);
      if (status >= 0) {
        if (!filepath.equals("")) {
          int st = model.setUser(user, password);
          if (st < 0) {
            throw new IllegalArgumentException("Invalid user details provided");
          }
        }
        view.transition("Operations");
      } else if (status == -1) {
        throw new IllegalArgumentException("Invalid username/password provided. Please re-enter.");
      } else if (status == -2) {
        throw new IllegalArgumentException("The username is taken. Please pick another one");
      } else if (status == -3) {
        throw new IllegalArgumentException("The File could not be created for the user. " +
                "Please ensure that the user has valid permissions to create file");
      }
    } catch (Exception e) {
      view.popUp(e.getMessage());
    }
  }

  @Override
  public int addPortfolio(String pName) {
    try {
      if (pName.isEmpty()) {
        throw new IllegalArgumentException("Cannot create a portfolio with empty name");
      }
      Portfolio p = model.getExistingPortfolio(pName);
      if (p != null) {
        throw new IllegalArgumentException("Portfolio of the given name exists. " +
                "Please pick another portfolio name.");
      } else {
        model.addPortfolios(pName, "2");
      }
      p = model.getExistingPortfolio(pName);
      model.addStock(p, null);
      save("Added Portfolio " + pName +
              " successfully");
      view.transition("addPortfolio");
    } catch (Exception e) {
      view.popUp(e.getMessage());
      return -1;
    }
    return 0;
  }

  @Override
  public int addStockToNewPortfolio(String pName, String symbol, String fees, String day,
                                    String month, String year, String quantity) {
    try {
      if (model.validateStocksymbol(symbol) == 0) {
        throw new IllegalArgumentException("Please enter a valid stock symbol");
      }
      Float cFees = model.checkCommissionFees(fees);
      if (cFees == null) {
        throw new IllegalArgumentException("Please enter valid commission fees");
      }

      String date = day + "-" + month + "-" + year;
      LocalDate d = Utility.validateDates(date);
      if (d == null) {
        throw new IllegalArgumentException("Please enter valid date");
      }

      float q = model.checkValidNaturalNumber(quantity);
      if (q < 0) {
        throw new IllegalArgumentException("Please enter valid quantity");
      }

      if (model.createStock(quantity, d, symbol, 0) == null) {
        throw new IllegalArgumentException("Cannot create the stock as " +
                "the date is not supported by the API");
      }

      model.addStockCache(model.createStock(quantity,
              d, symbol, cFees));

      view.resetStockDetails();
      view.popUpSuccess("Added Stock:" + symbol);
      view.addEditButtons();
      view.enableButtons();
      return model.getStockCache().size();
    } catch (Exception e) {
      view.popUp(e.getMessage());
      view.disableButtons();
    }
    return 0;
  }


  @Override
  public void addStocksToPortfolio(String portfolio, String stock, String quantity, String day,
                                   String month, String year, String commissionFees) {
    try {
      Portfolio p = model.getExistingPortfolio(portfolio);
      LocalDate date;
      if (p == null) {
        throw new IllegalArgumentException("The portfolio selected does not exist.");
      }
      try {
        date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),
                Integer.parseInt(day));
      } catch (Exception e) {
        throw new IllegalArgumentException("Please enter a valid date.");
      }

      float f;
      try {
        f = model.checkCommissionFees(commissionFees);
      } catch (Exception e) {
        throw new IllegalArgumentException("Please enter valid commission fees");
      }

      Stock s = model.createStock(quantity, date, stock, f);
      if (s == null) {
        throw new IllegalArgumentException("Please enter valid Stock details");
      }
      model.addStock(p, s);

      model.setCommissionFees(portfolio, stock, f);
      view.popUpSuccess("Successfully bought the stock");
    } catch (Exception e) {
      view.popUp(e.getMessage());
    }
  }

  @Override
  public float getCostBasis(String portfolio, String day, String month, String year) {
    float val = 0;
    try {
      if (model.getExistingPortfolio(portfolio) == null) {
        throw new IllegalArgumentException("Please enter valid portfolio name");
      }
      String date = day + "-" + month + "-" + year;
      LocalDate d = Utility.validateDatesFutureNotEmpty(date);
      if (d != null) {
        val = model.getCostBasis(portfolio, d);
        if (val < 0) {
          throw new IllegalArgumentException("Please enter valid inputs");
        }
      } else {
        throw new IllegalArgumentException("Please enter valid date");
      }
    } catch (Exception e) {
      view.popUp(e.getMessage());
    }
    return val;
  }




  @Override
  public float computePortfolio(String portfolio, String day, String month, String year) {
    float val = 0;
    try {
      if (model.getExistingPortfolio(portfolio) == null) {
        throw new IllegalArgumentException("Please enter valid portfolio name");
      }
      String date = day + "-" + month + "-" + year;
      LocalDate d = Utility.validateDates(date);
      if (d != null) {
        val = model.computePortfolioValues(portfolio, d);
        if (val < 0) {
          throw new IllegalArgumentException("Please enter valid inputs");
        }
      } else {
        throw new IllegalArgumentException("Please enter valid date");
      }
    } catch (Exception e) {
      view.popUp(e.getMessage());
    }
    return val;
  }

  @Override
  public void showPerformace() {
    view.transition("Performance");
  }


  @Override
  public void performance(LocalDate stateDate, LocalDate endDate, String portfolio) {
    try {
      if (stateDate == null || endDate == null || portfolio.equals("")) {
        view.popUp("Invalid date or Portfolio");
      } else {
        Draw graph = model.drawBarGraph(stateDate, endDate, portfolio);
        BarChart newGraph = BarGraphAdapter.transform(graph);
        view.performance(newGraph);
      }
    } catch (Exception e) {
      view.popUp("Invalid transaction for portfolio or invalid date");
    }
  }

  @Override
  public void examinePortfolio() {
    view.transition("examinePortfolio");
  }

  @Override
  public void displayPortfolio(String portName) {

    try {
      if (model.computePortfolioValues(portName, LocalDate.now().minusDays(1)) < 0) {
        view.popUp("Cannot examine portfolio.");
      } else {
        List<Stock> stock = model.getExistingPortfolio(portName).getAllInFlexibleStocks();

        String[][] data = new String[stock.size()][2];

        for (int i = 0; i < stock.size(); i++) {
          data[i] = new String[2];
          data[i][0] = stock.get(i).getStockSymbol();
          data[i][1] = String.valueOf(stock.get(i).getQuantity());
        }

        String[] col = {"Symbol", "Quantity"};
        view.table(data, col, portName);
      }
    } catch (Exception e) {
      view.popUp(e.getMessage());
      view.transition("examinePortfolio");
    }

  }

  @Override
  public List<String> operationsList() {
    return operationsList;
  }

  @Override
  public void options(String option) {
    if (operationsList.get(0).equals(option)) {
      examinePortfolio();
    } else if (operationsList.get(1).equals(option)) {
      view.transition("Compute");
    } else if (operationsList.get(2).equals(option)) {
      view.transition("addPortfolio");
    } else if (operationsList.get(3).equals(option)) {
      view.transition("addPlan");
    } else if (operationsList.get(4).equals(option)) {
      view.transition("buyStock");
    } else if (operationsList.get(5).equals(option)) {
      view.transition("sellStock");
    } else if (operationsList.get(6).equals(option)) {
      view.transition("ComputeBasis");
    } else if (operationsList.get(7).equals(option)) {
      showPerformace();
    } else if (operationsList.get(8).equals(option)) {
      view.transition("logout");
    }

  }

  @Override
  public List<String> getPortfolios() {
    List<String> portfolioList = new ArrayList<>();
    for (Portfolio portfolio : model.getFlexiblePortfolios()) {
      portfolioList.add(portfolio.getPortfolioName());
    }
    return portfolioList;
  }

  @Override
  public void sellStockFromPortfolio(String portfolioName, String symbol, String quantity,
                                     String day, String month, String year, String fees) {
    try {
      Portfolio p = model.getExistingPortfolio(portfolioName);
      LocalDate date;
      float q;
      if (p == null) {
        throw new IllegalArgumentException("The portfolio selected does not exist.");
      }
      try {
        date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),
                Integer.parseInt(day));
      } catch (Exception e) {
        throw new IllegalArgumentException("Please enter a valid date.");
      }
      try {
        q = Integer.parseInt(quantity);
      } catch (Exception e) {
        throw new IllegalArgumentException("Please enter a valid quantity.");
      }
      int status = model.sellStockFromPortfolio(portfolioName, symbol, q, date);
      if (status < 0) {
        throw new IllegalArgumentException("The portfolio is in illegal state. Please rectify it.");
      } else {
        view.popUpSuccess("Successfully sold the stock");
      }
      float f;
      try {
        f = model.checkCommissionFees(fees);
      } catch (Exception e) {
        throw new IllegalArgumentException("Please enter valid commission fees");
      }
      model.setCommissionFees(portfolioName, symbol, f);
    } catch (Exception e) {
      view.popUp(e.getMessage());
    }
  }

  @Override
  public float updatePercent(String symbol, String percent) {
    float p;
    try {
      int status = model.validateStocksymbol(symbol);
      if (status <= 0) {
        throw new IllegalArgumentException("Please enter a valid stock symbol");
      }
      p = model.validatePercentage(percent);
      if (p < 0) {
        throw new IllegalArgumentException("Please enter a valid percentage");
      }
      float s = model.updateMap(symbol, p);
      if (s < 0) {
        throw new IllegalArgumentException("Cannot add same stock again");
      }
      view.resetStocks();
      view.addPercentageEditButtons();
      //view.addStockButtons(symbol, percent);
    } catch (Exception e) {
      view.popUp(e.getMessage());
    }
    return model.fetchMapTotalValue();
  }

  @Override
  public void addPlan(String sDay, String sMonth, String sYear,
                      String eDay, String eMonth, String eYear,
                      String pName, String amount, String fees, String f) {
    try {
      LocalDate date;
      LocalDate date2;

      try {
        date = LocalDate.of(Integer.parseInt(sYear), Integer.parseInt(sMonth),
                Integer.parseInt(sDay));
      } catch (Exception e) {
        throw new IllegalArgumentException("Please enter a valid start date.");
      }

      if (eDay.equals("-") && eMonth.equals("--") && eYear.equals("----")) {
        date2 = null;
      } else {
        try {
          date2 = LocalDate.of(Integer.parseInt(eYear), Integer.parseInt(eMonth),
                  Integer.parseInt(eDay));
        } catch (Exception e) {
          throw new IllegalArgumentException("Please enter a valid end date.");
        }
      }


      if (date2 != null && date.isAfter(date2)) {
        throw new IllegalArgumentException("The plan end date is before the start date");
      }

      HashMap<String, Float> map = model.getHashMap();
      float a;

      try {
        a = Float.parseFloat(amount);
        a = model.checkValidNaturalNumber(amount);
        if (a < 0) {
          throw new IllegalArgumentException("Please enter a valid amount to invest");
        }
      } catch (Exception e) {
        throw new IllegalArgumentException("Please enter a valid amount to invest");
      }

      float commissionFees;
      try {
        commissionFees = model.checkCommissionFees(fees);
      } catch (Exception e) {
        throw new IllegalArgumentException("Please enter a valid commission fees");
      }

      long frequency;
      try {
        frequency = Long.parseLong(f);
        if (frequency <= 0) {
          throw new IllegalArgumentException("Please enter a valid frequency");
        }
      } catch (Exception e) {
        throw new IllegalArgumentException("Please enter a valid frequency");
      }

      boolean isOngoing = date2 == null;

      float amt_invested =  a - map.size() * commissionFees;
      if (amt_invested <= 0 ) {
        throw new IllegalArgumentException("Amount invested is below the commission fees set");
      }

      float total = 100;
      for (float val : map.values()) {
        total -= val;
      }

      if (total != 0) {
        throw new IllegalArgumentException("Cannot add stocks with invalid percentages");
      }

      /*for(Map.Entry<String, Float> val : map.entrySet()) {
        float amt_invested =  a * val.getValue()/100;
        if(amt_invested - commissionFees <= 0) {
          throw new IllegalArgumentException("Amount
          invested is below the commission fees set for " +
                  ""+ val.getKey());
        }
      }*/

      model.addPlan(date, date2, pName, map, a, commissionFees, frequency, isOngoing);
      model.clearMap();
      view.popUpSuccess("Successfully added the Dollar Cost Average Plan");
      view.transition("addPlan");
    } catch (Exception e) {
      view.popUp(e.getMessage());
    }
  }

  @Override
  public void save(String message) {
    try {
      model.save();
      view.popUpSuccess(message);
    } catch (Exception e) {
      view.popUp(e.getMessage());
    }
  }

  @Override
  public List<Stock> getStockCache() {
    return model.getStockCache();
  }

  @Override
  public HashMap<String, Float> getHashMap() {
    return model.getHashMap();
  }

  @Override
  public void removeStockFromCache(int i) {
    model.removeStockFromCache(i);
  }

  @Override
  public void removeStockFromMap(String stock) {
    model.removeStockFromMap(stock);
  }

  @Override
  public boolean validate(String text, String type) {
    try {
      boolean state = model.validateInput(text, type);
      return state;
    } catch (Exception e) {
      return false;
    }
  }


}
