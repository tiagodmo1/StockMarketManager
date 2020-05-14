package stockmarketsimulation;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;


class Stock
{
	private double currentPrice;
	private List<Double> historyOfPrices = new ArrayList<Double>();
	private int momentumTrades;
	private double randomShockVolatility;
	
	// The following two variables track only random shocks and no
	// momentum changes
	private double noMomentumPrice;
	private List<Double> historyIfNoMomentum = new ArrayList<Double>();
	
	private Random random;
	
	Stock(double randomShockVolatility, double startPrice, Random random)
	{
		this.currentPrice = startPrice;
		this.noMomentumPrice = startPrice;
		recordPrice();
		momentumTrades = 0;
		this.randomShockVolatility = randomShockVolatility;
		this.random = random;
	}
	
	/**
	 * 
	 * @param newPrice
	 * updates currentPrice, only updates it, does not record it
	 */
	public void updatePrice(double newPrice)
	{
		this.currentPrice = newPrice;
	}
	
	/**
	 * Records the current price. Adds double currentPrice to 
	 * a List<Double> historyOfPrices
	 */
	public void recordPrice()
	{
		historyOfPrices.add(this.currentPrice);
		historyIfNoMomentum.add(this.noMomentumPrice);
	}
	
	/**
	 * Adds one to int momentum trade, this keeps track of
	 * momentum trades that have occured
	 */
	
	public void addMomentumTrade()
	{
		momentumTrades++;
	}
	
	public double getCurrentPrice()
	{
		return currentPrice;
	}
	
	public List<Double> getHistory()
	{
		return this.historyOfPrices;
	}
	
	public List<Double> getHistory(int start)
	{
		int end = this.historyOfPrices.size();
		return this.historyOfPrices.subList(start, end);
	}
	
	public List<Double> getHistory(int start, int end)
	{
		return this.historyOfPrices.subList(start, end);
	}
	
	public List<Double> getHistoryIfNoMomentum()
	{
		return this.historyIfNoMomentum;
	}
	
	public int getMomentumTrades()
	{
		return this.momentumTrades;
	}
	
	/**
	 * adds a random effect to the price of the stock, randomEffect changes the 
	 * current price, and no momentum price by a random amount. The amount of
	 * randomness is specified by randomShockVolatility.
	 */
	
	public void randomEffect()
	{
		double shock;
		double volatilityParameter = this.randomShockVolatility * this.currentPrice;
		// The greater the current price, the greater the volatility
		shock = StatisticsTools.randomNormal(0, volatilityParameter, this.random);
		this.currentPrice += shock;
		this.noMomentumPrice += shock;
	}
	
}