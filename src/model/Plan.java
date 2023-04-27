package model;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Represent the investment plans which needs to be applied on a portfolio.
 */
public interface Plan {

  /**
   * executes the plan on the given user object.
   *
   * @param user - user object on which the plan needs to be executed on.
   */
  void execute(User user);

  /**
   * To check if the current plan is ongoing or not.
   *
   * @return - true if the plan is ongoing false if its not.
   */
  boolean isOngoing();

  /**
   * The amount to be invested in the plan.
   *
   * @return - amount
   */
  float getAmount();

  /**
   * Return the total commission associated to the plan.
   *
   * @return - the total commission fee amount spent after executing the plan.
   */
  float getCommissionFees();

  /**
   * Function to get the map representing the stocks and their weightage in the plan.
   *
   * @return - map representing the stocks and their weightage in the plan.
   */
  HashMap<String, Float> getPercentage();

  /**
   * Function to return the end date of the plan.
   *
   * @return -  end date of the plan.
   */
  LocalDate getEndDate();

  /**
   * Function to return thr start date of the plan.
   *
   * @return - start date of the plan.
   */
  LocalDate getStartDate();

  /**
   * Function to get the frequency of the current plan.
   *
   * @return - frequency represents interval at which the plan
   *          needs to be executed.
   */
  long getFrequency();

  /**
   * Function to get portfolio to which the plan is associated to.
   *
   * @return - name of the portFolio
   */
  String getPortfolioName();
}
