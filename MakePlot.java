package stockmarketsimulation;
import org.math.plot.*;
import javax.swing.JFrame;
import java.util.List;


class MakePlot
{
	/**
	 * 
	 * @param myList
	 * 
	 * Plots values of a List in a line plot.
	 */
	public static void simplePlot(List<Double> myList)
	{
		//We have to convert the list to array
		
		double[] myArray = listToArray(myList);
		
	    Plot2DPanel plot = new Plot2DPanel();
		
		plot.addLinePlot("my plot", myArray);
		
		JFrame frame = new JFrame("a plot panel");
		frame.setSize(100, 100); // test
		frame.setContentPane(plot);
		frame.setVisible(true); 
	}
	
	/**
	 * 
	 * @param myList1
	 * @param myList2
	 * 
	 * Plots values of myList1 and myList2 as two separate lines
	 * in the same plot.
	 */
	
	public static void twoLinePlot(List<Double> myList1, List<Double> myList2)
	{
		//We have to convert the list to array
		
		double[] myArray1 = listToArray(myList1);
		double[] myArray2 = listToArray(myList2);
		
	    Plot2DPanel plot = new Plot2DPanel();
		
		plot.addLinePlot("my plot", myArray1);
		
		plot.addLinePlot("my plot", myArray2);
		
		JFrame frame = new JFrame("a plot panel");
		frame.setContentPane(plot);
		frame.setVisible(true); 
	}
	
	public static double[] listToArray(List<Double> myList)
	{
		double[] myArray = new double[myList.size()];
			
			for (int i = 0; i < myList.size(); i++)
			{
				myArray[i] = myList.get(i);
			}
			
		return myArray;
	}
}