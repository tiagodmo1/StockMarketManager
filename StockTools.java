package stockmarketsimulation;
import java.util.ArrayList;
import java.util.List;


class StockTools
{
	/**
	 * Returns "length" last observations of the given stock's price history.
	 * If stock price history [100.3, 100.4, 100.2] and current price is 100.02
	 * getLatestHistory(stock, 2) => [100.4, 100.2]
	 * 
	 * If stock price history [100.3, 100.4, 100.2] and current price is 100.5
	 * getLatestHistory(stock, 2) => [100.2, 100.5]
	 * 
	 * In the first example, we assume there there were current price is the same
	 * price as the last price of the price history, so we treat it as one number. In
	 * the second example the prices are different so we treat them as two different 
	 * prices. 
	 * 
	 * @param stock
	 * @param length
	 * @return
	 */
	public static List<Double> getLatestHistory(Stock stock, int length)
	{
		List<Double> history = stock.getHistory();
		double currentPrice = stock.getCurrentPrice();
		int start = history.size() - length;
		int lastEleIndex = history.size() - 1;
		
		if (length > history.size()) return null;
		
		if(currentPrice != history.get(lastEleIndex))
		{
			List<Double> lastPart = makeCopy(history.subList(start + 1, history.size()));		
			lastPart.add(currentPrice);
			return lastPart;
		}
		else
		{
			return history.subList(start, history.size());
		}
	}
	
	/**
	 * Makes a copy of the list; there returned list has the
	 * same exact contents as the original list, but changing 
	 * elements has no effect on the other list
	 * @param x
	 * @return
	 */
	
	public static List<Double> makeCopy(List<Double> x)
	{
		List<Double> newX = new ArrayList<Double>();
		
		for (double d : x)
		{
			newX.add(d);
		}
		return newX;
	}
}