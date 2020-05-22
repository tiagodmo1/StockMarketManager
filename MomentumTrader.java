package stockmarketsimulation;

import java.util.List;
import java.util.Random;

class MomentumTrader extends Investor
{
	private int longPeriod;
	private int shortPeriod;
	private double threshHold;
	private List<Stock> allStocksList;
	
	MomentumTrader(double buyAndSellVolatility,
			       int shortPeriod,
			       int longPeriod,
			       double threshHold,
			       List<Stock> allStocksList,
			       Random random)	
	{
		/*
		 * short period is the final period, let's say 10 last prices
		 * long period is longer part of the final period, let's say 20 last prices
		 * 
		 * if mean price of the short period exceeds, mean price of long period,
		 * the momentum trader buys the asset
		 * 
		 * threshHold is the value average of short period needs to exceed the long
		 */
		
		super(buyAndSellVolatility, random);
		
		this.shortPeriod = shortPeriod;
		this.longPeriod = longPeriod;
		this.threshHold = threshHold;
		this.allStocksList = allStocksList;
	}
	
	/**
	 * Instance of MomentumTrader class trades stocks when it finds
	 * them attractive given the traders trading method. If the stock is good
	 * the traders buys it and bids up the price, if the stock is bad the trader
	 * sells it or short sells it and bids down the price.
	 */
	
	public void tradingMethod()
	{
		/*
		 * Buying Stocks
		 */
		for (Stock stock : this.allStocksList)
		{					
			if (stock.getHistory().size() >= longPeriod)
			{
				if(getMomentumStatus(stock).equals("Hot") && this.myStocks.contains(stock) == false)
				{	
					// we buy an asset if don't already have it
					this.buyStock(stock);
					stock.addMomentumTrade();
					break;
				}
				if(getMomentumStatus(stock).equals("No Momentum") && this.myStocks.contains(stock) == true)
				{
					// we sell an asset only if we have it
					this.sellStock(stock);
				}
			}
		}
	}
	
	/**
	 * Evaluates whether the stock has momentum, based on the means in the latest (shortPeriod)
	 * period, and the longer latest period (longPeriod)
	 * @param stock
	 * @return
	 * String, "Hot" indicating that the stock has momentum, "Some Momentum" 
	 * indicating the stock has some momentum, and "No Momentum" indicating
	 * that the stock has not momentum at all, or in other words it has zero
	 * or negative momentum
	 */
	
	public String getMomentumStatus(Stock stock)
	{
		double meanOfShort = StatisticsTools.getMeanOfLastPart(stock, this.shortPeriod);
		double meanOfLong = StatisticsTools.getMeanOfLastPart(stock, this.longPeriod);
		
		if ((meanOfShort - meanOfLong) / meanOfLong > this.threshHold)
		{
			return "Hot";
		}
		else
		{
			if (meanOfLong > meanOfShort)
			{
				return "No Momentum";
			}
			else
			{
				return "Some Momentum";
			}
		}		
	}
}