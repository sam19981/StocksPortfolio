package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Used to store all the data related to the user who is currently logged in
 * and is using the program.
 */
public class UserImpl implements User {
  private final Map<String, Portfolio> portFolios;
  private final String userName;
  private final ArrayList<Plan> plans;

  /**
   * Constructor to initialize all the data related to a user in the memory.
   *
   * @param userName - userName of the user to be initialized.
   * @param ports    - portfolios of the user to be used.
   * @param plans - plans associated with the user.
   */
  public UserImpl(String userName, List<Portfolio> ports, ArrayList<Plan> plans) {
    this.userName = userName;
    this.portFolios = new LinkedHashMap<>();
    this.plans = plans;
    for (Portfolio portfolio : ports) {
      portFolios.putIfAbsent(portfolio.getPortfolioName(), portfolio);
    }
  }

  @Override
  public Portfolio getPortfolio(String portFolioName) {
    if (portFolios.get(portFolioName) != null) {
      return portFolios.get(portFolioName);
    }
    return null;
  }

  @Override
  public String getUserName() {
    return userName;
  }

  @Override
  public float computeAllPortFolios(LocalDate date) {
    float total = 0;
    for (Portfolio portfolio : portFolios.values()) {
      float value = portfolio.getPortfolioValue(date);
      if (value >= 0) {
        total += value;
      } else if (value == -1) {
        throw new IllegalArgumentException("Please enter correct transactions");
      } else if (value == -2) {
        throw new IllegalArgumentException("Please validate your transactions");
      }
    }
    return total;
  }

  @Override
  public float computePortfolioValue(String portfolioName, LocalDate date) {
    if (date != null) {
      if (portFolios.get(portfolioName) != null) {
        float value = portFolios.get(portfolioName).getPortfolioValue(date);
        if (value >= 0) {
          return value;
        } else {
          return -2;
        }
      }
    }
    return -1;
  }


  @Override
  public Stock removeStock(Portfolio newPort, Stock stock, LocalDate date) {
    if (portFolios.get(newPort.getPortfolioName()) != null) {
      for (Portfolio p : portFolios.values()) {
        if (p.equals(newPort)) {
          p.sellStock(stock, date);
        }
      }
    }
    return null;
  }

  @Override
  public int addPortfolio(Portfolio newPort) {
    if (portFolios.get(newPort.getPortfolioName()) == null) {
      portFolios.put(newPort.getPortfolioName(), newPort);
      return 0;
    } else {
      return -1;
    }
  }

  @Override
  public void addStock(Portfolio port, Stock stock) {
    for (Portfolio p : portFolios.values()) {
      if (p.equals(port)) {
        port.addStock(stock);
      }
    }
  }

  @Override
  public float sellPortfolio(Portfolio port) {
    if (portFolios.get(port.getPortfolioName()) != null) {
      float res = port.getPortfolioValue(LocalDate.now());
      portFolios.remove(port.getPortfolioName());
      return res;
    }
    return -1;
  }

  @Override
  public List<Portfolio> getAllPortfolios() {
    return new ArrayList<>(portFolios.values());
  }

  @Override
  public Portfolio deletePortfolio(String name) {
    Portfolio r = PortfolioImpl.getBuilder().create();
    for (Portfolio p : portFolios.values()) {
      if (p.getPortfolioName().equals(name)) {
        r = p;
        sellPortfolio(p);
        break;
      }
    }
    return r;
  }

  @Override
  public List<Portfolio> removeAllPortfolios() {
    return null;
  }

  @Override
  public int sellStockFromPortfolio(String pName, String symbol, float quantity, LocalDate date) {
    try {
      if (portFolios.containsKey(pName)) {
        for (String p : portFolios.keySet()) {
          if (p.equals(pName)) {
            portFolios.get(p).sellStockQuantity(symbol, quantity, date);
          }
        }
      }
      return 0;
    } catch (Exception e) {
      return -1;
    }
  }

  @Override
  public void addPlan(Plan plan) {
    plans.add(plan);
  }

  @Override
  public ArrayList<Plan> getPlans() {
    return plans;
  }

  @Override
  public void executeAllPlans() {
    for (Plan plan : plans) {
      plan.execute(this);
    }
  }

  public static UserBuilder createBuilder() {
    return new UserBuilder();
  }

  /**
   * Builder to set all the fields in the user without any errors.
   */
  public static class UserBuilder {
    String username;
    List<Portfolio> portfolioList;
    String password;

    ArrayList<Plan> plans;

    UserBuilder() {
      username = "";
      portfolioList = new ArrayList<>();
      password = "";
      plans = new ArrayList<>();
    }


    public UserBuilder setUserName(String username) {
      this.username = username;
      return this;
    }

    public UserBuilder setPassword(String password) {
      this.password = password;
      return this;
    }

    public UserBuilder addAllPortfolioList(List<Portfolio> m) {
      this.portfolioList.addAll(m);
      return this;
    }

    public UserBuilder addPortfolioList(Portfolio m) {
      this.portfolioList.add(m);
      return this;
    }

    public UserBuilder addPlans(List<Plan> m) {
      this.plans.addAll(m);
      return this;
    }

    /**
     * Creates the userImpl object after setting
     * all the values using the builder.
     *
     * @return - new userImpl object.
     */
    public UserImpl create() {
      if (this.username.equals("") || this.password.equals("")) {
        return null;
      }
      return new UserImpl(this.username, this.portfolioList, this.plans);
    }

  }

}
