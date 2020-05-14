package stockmarketsimulation;
public final class Parameters
{
	public static final double BUY_AND_SELL_VOLATILITY = 0.01;
	public static final double RANDOM_SHOCK_VOLATILITY = 0.01;
    public static final int NUMBER_OF_STOCKS = 10;
    public static final double START_PRICE = 100;    
    public static final int NUMBER_OF_MOMENTUM_TRADERS = 80; 
    /*
     * The higher the THRESH_HOLD the steeper the momentum that
     * traders require to trade 
     */
    public static final double THRESH_HOLD = 0.01;
    public static final int BASE_PERIOD_LENGTH = 30;
    public static final int TRADING_DAYS = 1000;
}

