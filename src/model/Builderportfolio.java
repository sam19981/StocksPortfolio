package model;

import java.util.List;

/**
 * Design Changes -------------------------------------------------------------
 * We added Builderportfolio interface to take advantage of the union data type
 * design and dynamic dispatch while creating the different portfolios ,persisting
 * them while reading and writing.
 * justification - with the help of the builder portfolio and dynamic dispatch we
 * were able to reuse the existing xml read and write parsers instead of writing a
 * new one for each type of portfolios.
 * ----------------------------------------------------------------------------
 * Added a Builder Interface for creating Flexible Portfolio and Inflexible Portfolio
 * depending on the nature of portfolio user wants to create.
 */

public interface Builderportfolio {

  /**
   * Returns the name of the portfolio.
   *
   * @return - name of this portfolio.
   */
  String getportfolioName();

  /**
   * Creates a portfolioBuilder for flexible or inflexible portfolio.
   *
   * @param name - Name of the portfolio to be created.
   * @return - returns the builder object eith the desired portfolio name.
   */
  Builderportfolio portfolioName(String name);

  /**
   * Adds the desired list of stocks to the portfolio in which we want to add.
   *
   * @param s - List of stocks to be added to the given portfolio.
   * @return - returns portfolio builder object with the given stock list.
   */
  Builderportfolio stocks(List<Stock> s);

  /**
   * Adds a single stock to the given portfolio.
   *
   * @param s - the stock to be added to the given portfolio.
   * @return - return the portfolio builder object with the with given stock.
   */
  Builderportfolio addStocks(Stock s);

  /**
   * Returns a object of type portfolio interface.
   *
   * @return - returns a portfolio type object (Flexible or Inflexible)
   */
  Portfolio create();

}
