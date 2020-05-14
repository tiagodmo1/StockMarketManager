package stockmarketsimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class Investor
{
	protected double money = 0;
	protected List<Stock> myStocks = new ArrayList<Stock>();
	protected double buyAndSellVolatility; // If investor buys an asset she bids up the price
	                                       // by a random amount
	                                       // If investor sells the asset she bids down the price
	                                       // by a random amount
	protected final double MEAN = 0;
	protected Random random;
	
	abstract void tradingMethod();
	
	Investor(double buyAndSellVolatility, Random random)
	{
		this.buyAndSellVolatility = buyAndSellVolatility;
		this.random = random;
	}
	
	/**
	 * @param stock
	 * updates the price of a stock by a positive amount, reflecting the fact
	 * that since the investor wanted to buy the stock, he or she bid it up.
	 * The amount by how much the investor would have to bid up the stock 
	 * varies, and therefore the price of the stock increases by the absolute
	 * value of a number chosen from the normal distribution with mean 0 and 
	 * standard deviation buyAndSellVolatility.
	 */
	
	public void buyStock(Stock stock)
	{
		this.myStocks.add(stock);
		double temp = StatisticsTools.randomNormal(MEAN, buyAndSellVolatility, this.random);
		double increment = Math.abs(temp * stock.getCurrentPrice());
		double newStockPrice = stock.getCurrentPrice() + increment;
		this.money -= newStockPrice;
		stock.updatePrice(newStockPrice);
	}
	
	/**
	 * @param stock
	 * updates the price of a stock by a negative amount, reflecting the fact
	 * that since the investor wanted to sell the stock, he or she bid it down.
	 * The amount by how much the investor would have to bid down the stock 
	 * varies, and therefore the price of the stock decreases by the absolute
	 * value of a number chosen from the normal distribution with mean 0 and 
	 * standard deviation buyAndSellVolatility.
	 */
	
	public void sellStock(Stock stock)
	{
		this.myStocks.remove(stock);
		double temp = StatisticsTools.randomNormal(MEAN, buyAndSellVolatility, this.random);
		double increment = Math.abs(temp * stock.getCurrentPrice());
		double newStockPrice = stock.getCurrentPrice() - increment;
		this.money += newStockPrice;
		stock.updatePrice(newStockPrice);
	}
	
	public double getMoney()
	{
		return this.money;
	}
	
	public List<Stock> getStockList()
	{
		return this.myStocks;
	}
}