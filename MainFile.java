package stockmarketsimulation;

import java.util.Random;

import static stockmarketsimulation.Parameters.*;

class MainFile
{
	public static void main (String[] args)
	{	
		
        Random random = new Random();
			
		StockMarket sm = new StockMarket(TRADING_DAYS,
				                         BUY_AND_SELL_VOLATILITY,
                                         RANDOM_SHOCK_VOLATILITY,
                                         START_PRICE,
                                         NUMBER_OF_STOCKS,
                                         NUMBER_OF_MOMENTUM_TRADERS,
                                         THRESH_HOLD,
                                         BASE_PERIOD_LENGTH,
				                         random);
	
	
		sm.showPlot(0);	
		
 	}	
}