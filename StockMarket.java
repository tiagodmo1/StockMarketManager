package stockmarketsimulation;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * 
 * @author Kirill Temlyakov
 * StockMarket class simulates behavior of the stock market and shows that
 * we only need to have momentum traders to get momentum effect and mean reversion.
 * the simulation also shows how momentum traders can increase volatility in
 * the market.
 */ 

public class StockMarket
{
	/**
	 * List of all stocks in the market
	 */
	private List<Stock> allStocks;
	private Random random;
	
	/**
	 * List of all momentumTraders in the Market
	 */
	private List<Investor> allMomentumTraders; //Testing this part
	
	StockMarket(int numberOfPeriods,
			    double buyAndSellVolatility, 
			    double randomShockVolatility,
			    double startPrice,
			    int numberOfStocks, 
			    int numberOfMomentumTraders, 
			    double threshHold,
			    int basePeriodLength,
			    Random random
			    )
	{
		this.random = random;
		allStocks = createAllStocks(numberOfStocks, randomShockVolatility, startPrice, random);
		allMomentumTraders = createAllMomentumTraders(buyAndSellVolatility,
				                                      numberOfMomentumTraders,
				                                      threshHold,
				                                      allStocks,
				                                      basePeriodLength,
				                                      random);
		this.trading(numberOfPeriods);
	}
	
	public List<Stock> getAllStocks()
	{
		return this.allStocks;
	}
	
	public List<Investor> getAllMomentumTraders() //This part
	{
		return this.allMomentumTraders;
	}
	
	
	/**
	 * Crates a list of all stocks in the stock market
	 * @param numberOfStocks
	 * @param randomShockVolatility
	 * @param startPrice
	 * @param random
	 * @return
	 */
	
	
	public List<Stock> createAllStocks(int numberOfStocks, double randomShockVolatility,
			                           double startPrice, Random random)
	{
		allStocks = new ArrayList<Stock>();
	
		for (int i = 0; i < numberOfStocks; i++)
		{
			allStocks.add(new Stock(randomShockVolatility, startPrice, random));
		}
		
		return allStocks;
	}
	
	/**
	 * Creates a list of all momentum traders in the market
	 * @param buyAndSellVolatility
	 * @param numberOfMomentumTraders
	 * @param threshHold
	 * @param allStocks
	 * @param basePeriodLength
	 * @param random
	 * @return
	 */
	
	// testing here
	public List<Investor> createAllMomentumTraders(double buyAndSellVolatility,
			                                             int numberOfMomentumTraders,
			                                             double threshHold,
			                                             List<Stock> allStocks,
			                                             int basePeriodLength,
			                                             Random random)
	{
	    allMomentumTraders = new ArrayList<Investor>();              
		
		for (int i = 0; i < numberOfMomentumTraders; i++)
		{
			//shortPeriod, longPeriod, and threshHold should be random,
			//so that our MomentumTraders are not all the same
			
			Random randomGenerator = random; // new Random();
			
			// short period is 1 or greater
			// long period is 1 or more greater than short period
			int shortPeriod = randomGenerator.nextInt(basePeriodLength) + 1; 
			
			int longPeriod = shortPeriod +  randomGenerator.nextInt(basePeriodLength * 2) + 1;
			
			allMomentumTraders.add(new MomentumTrader(buyAndSellVolatility,
					                                  shortPeriod,
					                                  longPeriod, 
					                                  threshHold,
					                                  allStocks,
					                                  random));
		}
		
		return allMomentumTraders;
	}
	
	/**
	 * Simulates the stock market for a given number of rounds.
	 * @param numberOfRounds
	 */
	
    public void trading(int numberOfRounds)
    {
    	for (int i = 0; i < numberOfRounds; i++)
    	{
    		for (Investor mt : allMomentumTraders)
    		{
    			mt.tradingMethod();
    		}
    		
    		for(Stock stock : allStocks)
    		{
    			stock.recordPrice();
    			stock.randomEffect();
    		}
    		
    		Collections.shuffle(this.allStocks, this.random);
    		Collections.shuffle(this.allMomentumTraders, this.random);   		
    	}
    }
    
    /**
     * 
     * @param stockId
     * Shows the plot of the actual price of the given stock,
     * the blue line, this is the price that take the effect of 
     * momentum. And shows the plot of the price of that stock 
     * without the effect of momentum, this is the red plot.
     */
	
    public void showPlot(int stockId)
    {
    	List<Double> actualPrice = allStocks.get(stockId).getHistory();
    	List<Double> noMomentumPrice = allStocks.get(stockId).getHistoryIfNoMomentum();
    	
    	MakePlot.twoLinePlot(actualPrice, noMomentumPrice);
    }
    
	
}