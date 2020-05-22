package stockmarketsimulation;

import java.util.List;
import java.util.Random;

class StatisticsTools
{
	/**
	 * 
	 * @param mean
	 * @param stdDev
	 * @param random
	 * @return
	 * returns a double chosen from a normal distribution with
	 * mean and standard deviation specified in the parameters
	 */
	public static double randomNormal(double mean, double stdDev, Random random)
	{		
		double number = random.nextGaussian();		
		return number * stdDev + mean;
	}
	
	/**
	 * 
	 * @param myContainer
	 * @return
	 * returns a double, that is a mean of supplied list
	 */
	
	public static double getMean (List<Double> myContainer)
	{
		double sum = 0;
		for (double ele : myContainer)
		{
			sum += ele;
		}
		
		return sum / myContainer.size();
	}
	
	/**
	 * 
	 * @param stock
	 * @param length
	 * @return
	 * returns a double, applied to .getHistory() method of class Stock
	 * returns mean of the specified n latest price observations
	 */
	
	public static double getMeanOfLastPart(Stock stock, int length)
	{
		/*
		 * Calculating sum of the last k elements is complicated that the 
		 * price of the last element changes, so we need to encorporate
		 * the last periods price and the current price
		 */
         return getMean(StockTools.getLatestHistory(stock, length));
	}
	
	/**
	 * 
	 * @param myContainer
	 * @return
	 * 
	 * returns a double that is a standard deviation of the
	 * list supplied in the parameter
	 */
	
	public static double getStdDev(List<Double> myContainer)
	{
		double mean = getMean(myContainer);
		double tempSum = 0;
		for(double d : myContainer)
		{
			tempSum += Math.pow((d - mean), 2);
		}
		
		return Math.sqrt(tempSum / myContainer.size());
	}
}