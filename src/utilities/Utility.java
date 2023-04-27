package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Portfolio;

/**
 * Contains all the utility functions which can be reused throughout the program.
 */
public class Utility {

  /**
   * Reads the CSV data from the file returns it for further use.
   *
   * @param fileName - The filename from which the data has to be read.
   * @return - returns the data as a list of strings.
   * @throws IOException - The exception is thrown when the
   *                     file is ot present or cannot be read.
   */
  public static List<String> loadCsvData(String fileName) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(fileName));
    List<String> values = new ArrayList<>();
    String line;
    while ((line = br.readLine()) != null) {
      values.add(line);
    }
    return values;
  }

  /**
   * Reads the response for the API in JSON format and returns a String builder object with the
   * given response.
   *
   * @param in - inpustream object to read the response in JSON format.
   * @return - String builder object with the received response.
   */
  public static StringBuilder readJSONData(InputStream in) {
    StringBuilder output = new StringBuilder();
    int b;
    try {
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
    } catch (Exception e) {
      return new StringBuilder();
    }
    return output;
  }

  /**
   * Reads the stock and its related data present in string builder and
   * gets the values on a given date.
   *
   * @param output - All the data associated with a given stock (coming from cache or API).
   * @param date   - date one which we need to get data from the output.
   * @param next - Indicates if the previous date or the next date has to be picked.
   * @return - the value of the stock on the given date.
   */
  public static float readOnDate(StringBuilder output, LocalDate date, boolean next) {
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    String searchString = date.format(formatters);
    float value = 0;
    boolean flag = false;
    String lastDate = "";
    String[] dates = String.valueOf(output).split("\n");
    for (int ix = dates.length - 1; ix >= 0; ix--) {
      if (dates[ix].contains("-")) {
        lastDate = dates[ix];
        break;
      }
    }
    lastDate = lastDate.split(":")[0].replaceAll("\"", "").strip();
    LocalDate lastKnownDate;
    try {
      lastKnownDate = LocalDate.parse(lastDate, formatters);
    } catch (Exception e) {
      return 0;
    }

    if (lastDate.isEmpty() || date.isBefore(lastKnownDate) || date.isAfter(LocalDate.now())) {
      return 0;
    }
    for (String keyValue : dates) {
      if (keyValue.contains(searchString)) {
        flag = true;
      }
      if (flag & keyValue.contains("close")) {
        String[] s = keyValue.split(": ");
        String st = s[1].split(",")[0].replaceAll("\"", "");
        value = Float.parseFloat(st);
        break;
      }
    }

    if (!flag) {
      if (next) {
        if (date.equals(LocalDate.now()) && (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
                || date.getDayOfWeek().equals(DayOfWeek.SUNDAY))) {
          value = readOnDate(output, date.minusDays(1), true);
        } else {
          value = readOnDate(output, date.plusDays(1), true);
        }
      } else {
        value = readOnDate(output, date.minusDays(1), false);
      }
    }
    return value;
  }

  /**
   * Checks if the entered date is for the format dd-MM-uuuu and validates it.
   *
   * @param date - the date which need to be validated.
   * @return - return the correct date if its validated to be true else null.
   */
  public static LocalDate validateDates(String date) {
    String[] a = date.split("-");
    DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-uuuu");
    DateTimeFormatter f2 = DateTimeFormatter.ofPattern("d-M-uuuu");
    LocalDate date1;
    try {
      LocalDate.parse(date, f);
      date1 = LocalDate.of(Integer.parseInt(a[2]), Integer.parseInt(a[1]), Integer.parseInt(a[0]));
    } catch (Exception e) {
      try {
        LocalDate.parse(date, f2);
        date1 = LocalDate.of(Integer.parseInt(a[2]), Integer.parseInt(a[1]),
                Integer.parseInt(a[0]));
      } catch (Exception ex) {
        return null;
      }
    }
    if (date1.isAfter(LocalDate.now())) {
      return null;
    }
    if (date1.isBefore(LocalDate.of(1995, 1, 1))) {
      return null;
    }
    return date1;
  }

  /**
   * Checks if the entered date is for the format dd-MM-uuuu and validates it.
   * Future dates and null strings are also validated by the below function.
   *
   * @param date - the date which need to be validated.
   * @return - return the correct date if its validated to be true else null.
   */
  public static LocalDate validateDatesFuture(String date) {
    String[] a = date.split("-");
    if (a.length == 1) {
      return null;
    }
    DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-uuuu");
    LocalDate date1;
    try {
      LocalDate.parse(date, f);
      date1 = LocalDate.of(Integer.parseInt(a[2]), Integer.parseInt(a[1]), Integer.parseInt(a[0]));
    } catch (Exception e) {
      return null;
    }
    if (date1.isBefore(LocalDate.of(1995, 1, 1))) {
      return null;
    }
    return date1;
  }

  /**
   * Checks if the gicen portfolio exists in the given list of portfolios.
   *
   * @param list     - The list of portfolios
   *                 form which we need to check the portfolio exits.
   * @param portName - The name of the portfolio which needs to be checked.
   * @return - positive if the portfolio exists zero if it does not exist.
   */
  public static int checkValidportFolioName(List<Portfolio> list, String portName) {
    for (Portfolio val : list) {
      if (val.getPortfolioName().equals(portName)) {
        return 1;
      }
    }
    return 0;
  }

  /**
   * Strictly validates if the given date is valid or not returning null if the date is empty.
   * @param date - Input date to validate.
   * @return - Local date format of the provided string.
   */
  public static LocalDate validateDatesFutureNotEmpty(String date) {
    if (date.isEmpty()) {
      return null;
    }
    return validateDatesFuture(date);
  }


}
