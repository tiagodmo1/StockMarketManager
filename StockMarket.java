package sos.stock.dated.util;

import java.util.*;
import sos.dated.util.Iterator;

/**
 *program, illustrating the use of <code>DatedMap</code> and <code>DatedValue<code>.
 * An investor's portfolio, which also changes over time, is represented 
 * by a <code>DatedMap</code>, mapping a stock to the number of shares owned.
 */
public class StockMarket
{
  /**
   * The stock market, mapping stocks to dated values of prices.
   */
  private Map<String,DatedValue<Double,Date>> stockMarket;

  /**
   * A convenient array of dates.
   */
  private static Date[] d;
  
  /**
   * The largest possible date to be used as the end of time.
   */
  private static final Date END_OF_TIME = new Date( Long.MAX_VALUE );

  /**
   * Number of dates to store in the array.
   */
  private static final int NUM_DATES = 100;
  
  /**
   * A random number generator that generates the change in stock prices.
   */
  private static Random r = new Random( 1 );

  static
  {
    // for convenience, add the dates of weekdays to a static array
    Calendar calendar = Calendar.getInstance();
    calendar.set( 2003, 1, 1 );
    d = new Date[NUM_DATES];
    for( int i = 0; i < NUM_DATES; )
    {
      calendar.add( Calendar.DAY_OF_MONTH, 1 );
      int day = calendar.get( Calendar.DAY_OF_WEEK );
      if( day == Calendar.SATURDAY || day == Calendar.SUNDAY )
        continue;
      Date date = calendar.getTime();
      d[i] = date;
      i++;
    }
  }
  
  /** 
   * Creates a new instance of StockMarket 
   */
  public StockMarket()
  {
    initializeStockMarket();
  }
  
  /**
   * Initializes the stock market with the specified stock and initial
   * stock price and randomly generates prices for the dates stored
   * in <code>d</code>.
   */
  private void initializeStock( String stock, double initialValue )
  {
    // stock prices vary over time, so use a dated value
    DatedValue<Double,Date> values = new ValueByDate<Double,Date>();
    
    // add the stock to the stock market
    stockMarket.put( stock, values );
    
    // set the stock prices
    double value = initialValue;
    for( int i = 0; i < d.length - 1; i++ )
    {
      values.set( Double.valueOf( value ), d[i], d[i+1] );
      double change = r.nextDouble();
      value += change < 0.6? -change : change;
      if( value < 0.0 )
        value = 0.0;
    }
  }
  
  /**
   * Initializes the stock market with three fictitious companies.
   */
  private void initializeStockMarket()
  {
    stockMarket = new HashMap<String,DatedValue<Double,Date>>();
    
    initializeStock( "Company 1", 100.00 );
    initializeStock( "Company 2", 504.00 );
    initializeStock( "Company 3", 104.50 );
    initializeStock( "Company 4", 144.50 );
    initializeStock( "Company 5", 134.50 );
    initializeStock( "Company 6", 144.50 );
    initializeStock( "Company 7", 164.50 );
    initializeStock( "Company 8", 154.50 );
    initializeStock( "Company 9", 144.50 );
    initializeStock( "Company 10", 204.50 );
  }
  
  /**
   * Returns the value of the specified stock at the specified date.
   */
  private double lookupStockPrice( String stock, Date date )
  {
    DatedValue<Double,Date> values = stockMarket.get( stock );
    if( values == null )
      throw new NoSuchElementException();
    
    Double value = values.get( date );
    assert value != null;
    
    return value.doubleValue();
  }

  /**
   * Calculates and returns the value of the specified portfolio at the
   * specified date.
   */
  private double getPortfolioValue( DatedMap<String,Integer,Date> portfolio, Date date )
  {
    double total = 0.0;
    
    // for each entry in the dated map
    DatedSet<DatedMap.Entry<String,Integer,Date>,Date> entrySet = portfolio.entrySet();
    Iterator<DatedMap.Entry<String,Integer,Date>,Date> iter = entrySet.iterator( date );
    while( iter.hasNext() )
    {
      DatedMap.Entry<String,Integer,Date> entry = iter.next();
      
      // what stock is it?
      String stock = entry.getKey();
      
      // for how many shares?
      Integer shares = entry.getValue();
      
      // look up the value
      double price = lookupStockPrice( stock, date );
      double value = price * shares.intValue();
      
      // keep a running total
      total += value;
    }
    return total;
  }
  
  /**
   * Increments the number of stock shares in the specified portfolio.
   */
  private void buyStock( DatedMap<String,Integer,Date> portfolio, String stock, int numShares, Date date )
  {
    // get the current number of shares owned
    Integer currentShares = portfolio.get( stock, date );
    
    // if none owned, add an entry to the dated map
    if( currentShares == null )
    {
      portfolio.put( stock, Integer.valueOf( numShares ), date, END_OF_TIME );
      return;
    }
    
    // add to the current number of shares
    int totalShares = currentShares.intValue() + numShares;
    
    // update the dated map
    portfolio.put( stock, Integer.valueOf( totalShares ), date, END_OF_TIME );
  }
  
  /**
   * Run the example program.
   */
  public void run()
  {
    // Create an empty portfolio. Since we're using a dated sorted map, the
    // stocks should print out in alphabetical order
    DatedMap<String,Integer,Date> portfolio = new TreeMapByKey<String,Integer,Date>();
    
    // the portfolio on d[0] should be empty
    double value = getPortfolioValue( portfolio, d[0] );
    assert value == 0.0;
    System.out.println( "Portfolio on " + d[0] + ": " + portfolio.toString( d[0] ));
    
    // buy some shares of stock on d[10]
    buyStock( portfolio, "Compewter Materials", 100, d[10] );
    buyStock( portfolio, "Mice With Wheels", 250, d[10] );
    
    // check the portfolio value after the purchase of shares
    System.out.println( "Portfolio on " + d[15] + ": " + portfolio.toString( d[15] ));
    value = getPortfolioValue( portfolio, d[15] );
    System.out.println( "Portfolio value on " + d[15] + " is " + value );

    // buy a third company's shares
    buyStock( portfolio, "Global Software For Geeks", 10, d[50] );
    
    // check the value on d[50]
    System.out.println( "Portfolio on " + d[50] + ": " + portfolio.toString( d[50] ));
    value = getPortfolioValue( portfolio, d[50] );
    System.out.println( "Portfolio value on " + d[50] + " is " + value );
    
    // buy some more stock on d[75]
    buyStock( portfolio, "Mice With Wheels", 20, d[75] );
    
    // print out the value on d[80]
    System.out.println( "Portfolio on " + d[80] + ": " + portfolio.toString( d[80] ));
    value = getPortfolioValue( portfolio, d[80] );
    System.out.println( "Portfolio value on " + d[80] + " is " + value );
    
    // dump the entire portfolio and stock market
    System.out.println( "Entire portfolio: " + portfolio );
    System.out.println( "Entire stock market: " + stockMarket );
  }
  
  /**
   * Runs the StockMarket program, which illustrates a use of <code>DatedMap</code>
   * and <code>DatedValue</code>.
   *
   * @param args the command line arguments
   */

  
}