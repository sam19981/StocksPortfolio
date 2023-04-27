package connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;

import model.LRUCache;
import utilities.Utility;

/**
 * Fetches the input stream from Alpha Vantage API for a given symbol.
 * The fetch is done from the API only if the value for the ticker symbol is
 * not available in the cache.
 */

public class AlphaVantageImpl implements Connection {
  private final LRUCache cache = LRUCache.getInstance();

  @Override
  public StringBuilder fetch(String stockSymbol, LocalDate date) {
    StringBuilder cacheFetch = cache.get(stockSymbol);
    //Fetch from the cache if the value for the stock is already cached.
    if (cacheFetch != null) {
      return cacheFetch;
    }
    URL url;
    //Make the API call if the value was not cached.
    try {
      String apiKey = "QC6PHJMTIZHC8S6B";
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=json");
    } catch (Exception e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    try {
      InputStream in = url.openStream();
      StringBuilder output = Utility.readJSONData(in);
      //convert the input stream to readable Stringbuilder output and
      // put it in to cache, return the same.
      cache.put(stockSymbol, output);
      return output;
    } catch (IOException e) {
      return null;
    }
  }
}
