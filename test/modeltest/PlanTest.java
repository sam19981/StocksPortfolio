package modeltest;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.FlexiblePortfolioImpl;
import model.Plan;
import model.PlanImpl;
import model.Portfolio;
import model.StockImpl;
import model.User;
import model.UserImpl;

import static org.junit.Assert.assertEquals;


/**
 * This is the class to test the Plan/ Strategy to be executed on the portfolio.
 */
public class PlanTest {
  Plan plan;
  User newUser;

  @Before
  public void setup() {
    List<Portfolio> newPortfolio = new ArrayList<>();
    newPortfolio.add(FlexiblePortfolioImpl.getBuilder().portfolioName("School Funds")
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("GOOG")
                    .quantity(10)
                    .purchaseDate(LocalDate.of(2019, 11, 2))
                    .purchaseValue((float) 81.3015)
                    .commissionFees(5)
                    .create()).create());

    newUser = UserImpl.createBuilder().setUserName("Sam19981").setPassword("sam123")
            .addAllPortfolioList(newPortfolio).create();
    HashMap<String, Float> map = new HashMap<>();
    map.put("IBM", 50f);
    map.put("AAPL", 50f);
    plan = PlanImpl.getBuilder().portfolioName("School Funds")
            .startDate(LocalDate.of(2020, 10, 2))
            .endDate(LocalDate.now().minusDays(1))
            .isOngoing(false).commissionFees(5).amount(1000).frequency(30).hashMap(map).create();
    newUser.addPlan(plan);
  }

  @Test
  public void TestBeforeNewStrategyExecution() {
    List<Portfolio> newPortfolio = new ArrayList<>();
    newPortfolio.add(FlexiblePortfolioImpl.getBuilder().portfolioName("School Funds")
            .addStocks(StockImpl.getBuilder()
                    .stockSymbol("GOOG")
                    .quantity(10)
                    .purchaseDate(LocalDate.of(2020, 11, 2))
                    .purchaseValue((float) 81.3015)
                    .create()).create());
    assertEquals(newPortfolio.size(), newUser.getAllPortfolios().size());
    assertEquals(newPortfolio.get(0).getPortfolioValue(LocalDate.now()),
            newUser.computePortfolioValue("School Funds", LocalDate.now()), 0);
    assertEquals(newPortfolio.get(0).getCostBasis(LocalDate.now()) + 5,
            newUser.getPortfolio("School Funds").getCostBasis(LocalDate.now()), 0);
  }

  @Test
  public void TestBeforeAndDuringNewStrategyExecution() {
    float oldValueBeforeStartDate = newUser.getPortfolio("School Funds")
            .getPortfolioValue(LocalDate.of(2020, 10, 1));
    float oldCostBasis = newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2020, 10, 1));

    float oldValueOnPlanStartDate = newUser.getPortfolio("School Funds")
            .getPortfolioValue(LocalDate.of(2020, 10, 2));
    float oldCostBasisOnPlanStartDate = newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2020, 10, 2));

    newUser.executeAllPlans();
    // 1 Day Before
    assertEquals(newUser.getPortfolio("School Funds")
                    .getPortfolioValue(LocalDate.of(2020, 10, 1)),
            oldValueBeforeStartDate, 0);
    assertEquals(newUser.getPortfolio("School Funds")
                    .getCostBasis(LocalDate.of(2020, 10, 1)),
            oldCostBasis, 0);

    // 1st Day of Execution
    assertEquals(newUser.getPortfolio("School Funds")
            .getPortfolioValue(LocalDate.of(2020, 10, 2)) +
            plan.getPercentage().size() * plan.getCommissionFees(), oldValueOnPlanStartDate
            + plan.getAmount(), 0.1);
    assertEquals(newUser.getPortfolio("School Funds")
                    .getCostBasis(LocalDate.of(2020, 10, 2)),
            oldCostBasisOnPlanStartDate + plan.getAmount(), 0);
  }

  @Test
  public void TestDuringAndAfterNewStrategyExecutionPortfolioValue() {
    float oldValueBeforeStartDate = newUser.getPortfolio("School Funds")
            .getPortfolioValue(LocalDate.of(2020, 10, 1));
    float oldCostBasis = newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2020, 10, 1));

    float oldValueOnPlanStartDate = newUser.getPortfolio("School Funds")
            .getPortfolioValue(LocalDate.of(2020, 10, 2));
    float oldCostBasisOnPlanStartDate = newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2020, 10, 2));

    float oldValueOnPlanSecondDate = newUser.getPortfolio("School Funds")
            .getPortfolioValue(LocalDate.of(2020, 11, 2));
    float oldCostBasisOnPlanSecondDate = newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2020, 11, 2));

    newUser.executeAllPlans();
    // 1 Day Before
    assertEquals(newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2020, 10, 1)),
            oldCostBasis, 0);

    //After 1st Day of Execution
    assertEquals(newUser.getPortfolio("School Funds")
                    .getCostBasis(LocalDate.of(2020, 10, 2)),
            oldCostBasisOnPlanStartDate + plan.getAmount(), 0);

    //Date of 2nd execution
    assertEquals(18190.240234375, newUser.getPortfolio("School Funds")
            .getPortfolioValue(LocalDate.of(2020, 11, 2)), 0.1);

    //Date after execution of strategy
    assertEquals(30135.71875, newUser.getPortfolio("School Funds")
            .getPortfolioValue(LocalDate.of(2022, 11, 30)), 0.1);
  }

  @Test
  public void TestDuringAndAfterNewStrategyExecutionCostBasis() {
    float oldValueBeforeStartDate = newUser.getPortfolio("School Funds")
            .getPortfolioValue(LocalDate.of(2020, 10, 1));
    float oldCostBasis = newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2020, 10, 1));

    float oldValueOnPlanStartDate = newUser.getPortfolio("School Funds")
            .getPortfolioValue(LocalDate.of(2020, 10, 2));
    float oldCostBasisOnPlanStartDate = newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2020, 10, 2));

    float oldValueOnPlanSecondDate = newUser.getPortfolio("School Funds")
            .getPortfolioValue(LocalDate.of(2020, 11, 2));
    float oldCostBasisOnPlanSecondDate = newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2020, 11, 2));

    newUser.executeAllPlans();
    // 1 Day Before
    assertEquals(newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2020, 10, 1)),
            oldCostBasis, 0);

    //After 1st Day of Execution
    assertEquals(newUser.getPortfolio("School Funds")
                    .getCostBasis(LocalDate.of(2020, 10, 2)),
            oldCostBasisOnPlanStartDate + plan.getAmount(), 0);

    //Date of 2nd execution
    assertEquals(18215.24, newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2020, 11, 2)), 0.1);

    //after end date
    assertEquals(30410.71875, newUser.getPortfolio("School Funds")
            .getCostBasis(LocalDate.of(2022, 11, 30)), 0.1);
  }


}
