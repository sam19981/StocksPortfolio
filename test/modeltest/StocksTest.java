package modeltest;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;

import model.Stock;
import model.StockImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * calss to check if the stock object is getting set properly
 * using a builder and if its functions are working as expected.
 */
public class StocksTest {
  Stock s;

  @Before
  public void setup() {
    s = StockImpl.getBuilder()
            .purchaseDate(LocalDate.of(2020, 3, 20)).stockSymbol("GOOG")
            .quantity(10)
            .create();
  }

  @Test
  public void TestBaseValues() {
    assertEquals(s.getStockSymbol(), "GOOG");
    assertEquals(s.getPurchaseDate(), LocalDate.of(2020, 3, 20));
    assertEquals(s.getQuantity(), 10, 0);
  }

  @Test
  public void TestGetPurchaseValue() {
    assertEquals(s.getPurchaseValue(),
            s.getValue(LocalDate.of(2020, 3, 20)), 0);
  }

  @Test
  public void TestIncreaseQuality() {
    Random r = new Random(100);
    for (int i = 0; i < 50; i++) {
      int added = r.nextInt();
      assertEquals(s.getQuantity() + added,
              s.increaseQuantity(added).getQuantity(), 0);
    }
  }

  @Test
  public void BlankTestGetValue() {
    Stock s = StockImpl.getBuilder().stockSymbol("GOOG").create();
    assertEquals(s.getStockSymbol(), "GOOG");
    assertEquals(s.getPurchaseDate(), LocalDate.now().minusDays(1));
    assertEquals(s.getQuantity(), 0, 0);
    assertNotEquals(s.getPurchaseValue(), 0);
  }

  @Test
  public void TestGetValueRepeatedCalls() {
    Stock s = StockImpl.getBuilder().stockSymbol("GOOG").create();
    assertEquals(s.getValue(LocalDate.now().minusDays(1)),
            s.getValue(LocalDate.now().minusDays(1)), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestGetValueInvalidStock() {
    Stock s = StockImpl.getBuilder().stockSymbol("GOO").create();
    s.getValue(LocalDate.now().minusDays(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestFutureValue() {
    Stock s = StockImpl.getBuilder()
            .stockSymbol("GOOG")
            .purchaseDate(LocalDate.of(2022, 12, 10)).create();
    s.getPurchaseValue();
  }

  @Test
  public void TestOnWeekend() {
    Stock onSunday = StockImpl.getBuilder()
            .stockSymbol("GOOG")
            .purchaseDate(LocalDate.of(2022, 9, 25)).create();
    Stock onFriday = StockImpl.getBuilder()
            .stockSymbol("GOOG")
            .purchaseDate(LocalDate.of(2022, 9, 23)).create();
    assertEquals(onFriday.getPurchaseValue(), onSunday.getPurchaseValue(), 0);
  }

}
