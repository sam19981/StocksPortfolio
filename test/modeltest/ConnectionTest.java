package modeltest;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import connection.AlphaVantageImpl;
import connection.Connection;
import utilities.Utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Class to check the connection with the API and
 * the check the error handling done in various scenarios
 * while calling it.
 */
public class ConnectionTest {
  Connection connection;

  @Before
  public void setup() {
    connection = new AlphaVantageImpl();
  }

  @Test
  public void TestForValidConnection() {
    assertNotNull(connection.fetch("GOOG", LocalDate.of(2020, 3, 20)));
  }

  @Test
  public void TestForInvalidConnection() {
    assertEquals(0, Utility.readOnDate(
            connection.fetch("GOO",
                    LocalDate.of(2020, 3, 20)), LocalDate.now(), false), 0);
  }

  @Test
  public void TestMalformedURLConnection() {
    assertEquals(0, Utility.readOnDate(
            connection.fetch("90()**+\\",
                    LocalDate.of(2020, 3, 20)), LocalDate.now(), false), 0);
  }

  @Test
  public void TestFutureDate() {
    float value = Utility.readOnDate(connection.fetch("GOOG",
                    LocalDate.of(2025, 3, 20)),
            LocalDate.of(2025, 3, 20), false);
    assertEquals(0, value, 0);
  }

  @Test
  public void TestToday() {
    assertNotEquals(0, Utility.readOnDate(
            connection.fetch("GOOG", LocalDate.now()),
            LocalDate.now(), false));
  }

  @Test
  public void Test1970Date() {
    assertNotNull(connection.fetch("GOOG",
            LocalDate.of(2005, 12, 25)));
  }

  @Test
  public void TestHoliday() {
    assertNotEquals(0, Utility.readOnDate(connection.fetch("GOOG",
            LocalDate.of(2022, 1, 1)), LocalDate.of(2022, 1, 1), false));
  }

  @Test
  public void TestStockNotSupported() {
    assertEquals(0, Utility.readOnDate(connection.fetch("ABX",
            LocalDate.of(2022, 1, 1)), LocalDate.of(2022, 1, 1), false), 0);
  }

}
