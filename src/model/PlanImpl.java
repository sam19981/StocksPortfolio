package model;

import java.time.LocalDate;
import java.util.HashMap;

import connection.AlphaVantageImpl;
import connection.Connection;
import utilities.Utility;

/**
 * Implements the dollar cost averaging plans to be applied on a portfolio.
 */
public class PlanImpl implements Plan {
  String portfolioName;
  LocalDate startDate;
  LocalDate endDate;
  boolean isOngoing;
  float amount;
  long frequency;
  float commissionFees;
  HashMap<String, Float> percentage;

  private PlanImpl(String pName, LocalDate sDate, LocalDate eDate, boolean isCurrent, float amt,
                   long f, float cFess, HashMap<String, Float> percent) {
    portfolioName = pName;
    startDate = sDate;
    endDate = eDate;
    isOngoing = isCurrent;
    amount = amt;
    frequency = f;
    commissionFees = cFess;
    percentage = percent;
  }

  public static CustomerBuilder getBuilder() {
    return new CustomerBuilder();
  }

  /**
   * Builder class for Flexible portfolio to prevent instantiation errors while creating
   * a flexible portfolio.
   */

  public static class CustomerBuilder {
    private String portfolioName;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isOngoing;
    private float amount;
    private long frequency;
    private float commissionFees;
    private HashMap<String, Float> percentage;

    public String getportfolioName() {
      return this.portfolioName;
    }

    private CustomerBuilder() {
      portfolioName = "";
      startDate = null;
      endDate = null;
      isOngoing = false;
      amount = 0;
      frequency = 0;
      commissionFees = 0;
      percentage = new HashMap<>();
    }

    public CustomerBuilder portfolioName(String name) {
      this.portfolioName = name;
      return this;
    }

    public CustomerBuilder startDate(LocalDate sDate) {
      this.startDate = sDate;
      return this;
    }

    public CustomerBuilder endDate(LocalDate eDate) {
      this.endDate = eDate;
      return this;
    }

    public CustomerBuilder isOngoing(boolean isOngoing) {
      this.isOngoing = isOngoing;
      return this;
    }

    public CustomerBuilder amount(float amount) {
      this.amount = amount;
      return this;
    }

    public CustomerBuilder frequency(long frequency) {
      this.frequency = frequency;
      return this;
    }

    public CustomerBuilder commissionFees(float commissionFees) {
      this.commissionFees = commissionFees;
      return this;
    }

    public CustomerBuilder hashMap(HashMap<String, Float> percentage) {
      this.percentage = percentage;
      return this;
    }

    /**
     * Creates a FlexiblePortfolioImpl object after setting all its fields.
     * Returns FlexiblePortfolioImpl object with all its fields set with.
     * default values or values specified by the user.
     *
     * @return - returns FlexiblePortfolioImpl object.
     */
    public PlanImpl create() {
      if (portfolioName.equals("")) {
        return null;
      }
      if (amount == 0) {
        return null;
      }
      if (frequency == 0) {
        return null;
      }
      if (commissionFees == 0) {
        return null;
      }
      if (percentage.size() == 0) {
        return null;
      }
      if (startDate == null) {
        return null;
      }
      return new PlanImpl(portfolioName, startDate, endDate, isOngoing, amount, frequency,
              commissionFees, percentage);
    }
  }

  @Override
  public void execute(User user) {
    if (isOngoing) {
      if (endDate != null) {
        LocalDate sDate = endDate;
        endDate = LocalDate.now();
        while (!sDate.isBefore(endDate)) {
          addStocks(user, sDate);
          sDate = sDate.plusDays(frequency);
        }
        endDate = sDate;
      } else {
        endDate = LocalDate.now();
        LocalDate iterDate = startDate;
        while (iterDate.isBefore(endDate)) {
          addStocks(user, iterDate);
          iterDate = iterDate.plusDays(frequency);
        }
        endDate = iterDate;
      }
    } else if (endDate != null) {
      LocalDate iterDate = startDate;
      while ((iterDate.isBefore(endDate) && iterDate.isBefore(LocalDate.now())) ||
              (iterDate.equals(endDate)) || iterDate.equals(LocalDate.now())) {
        if (iterDate.isBefore(LocalDate.now()) || iterDate.equals(LocalDate.now())) {
          addStocks(user, iterDate);
          iterDate = iterDate.plusDays(frequency);
        } else {
          break;
        }
      }
      startDate = iterDate;
    }

  }

  private int addStocks(User user, LocalDate date) {
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("Cannot add stocks with future date");
    }
    Portfolio p = user.getPortfolio(portfolioName);
    float total = 100;
    for (float f : percentage.values()) {
      total -= f;
    }
    if (total != 0) {
      throw new IllegalArgumentException("Cannot add stocks with invalid percentages");
    }
    float temp = amount - percentage.size() * commissionFees;
    for (String symbol : percentage.keySet()) {
      float stockAmount = temp * percentage.get(symbol) / 100;
      Connection connection = new AlphaVantageImpl();
      float value = 0;
      try {
        value = Utility.readOnDate(connection.fetch(symbol, date), date, true);
      } catch (Exception e) {
        if (date.isBefore(LocalDate.now())) {
          throw new IllegalArgumentException("No price data found for " + symbol);
        }
      }
      float quantity;
      if (value != 0) {
        quantity = stockAmount / value;
        Stock s = StockImpl.getBuilder().stockSymbol(symbol)
                .quantity(quantity).purchaseDate(date).commissionFees(commissionFees).create();
        p.addStock(s);
      }
    }
    return 0;
  }

  @Override
  public boolean isOngoing() {
    return isOngoing;
  }

  @Override
  public float getAmount() {
    return amount;
  }

  @Override
  public float getCommissionFees() {
    return commissionFees;
  }

  @Override
  public HashMap<String, Float> getPercentage() {
    return percentage;
  }

  @Override
  public LocalDate getEndDate() {
    return endDate;
  }

  @Override
  public LocalDate getStartDate() {
    return startDate;
  }

  @Override
  public long getFrequency() {
    return frequency;
  }

  @Override
  public String getPortfolioName() {
    return portfolioName;
  }
}
