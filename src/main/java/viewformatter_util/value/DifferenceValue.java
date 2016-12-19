package viewformatter_util.value;

/**
 * This class takes the difference between the values of the two format value objects
 * @author Ryan Bergamini
 */
public class DifferenceValue implements FormatValue
{
	private FormatValue difference;
	
	/**
	 * Creates a new Difference Value such that its equal to subtracting the value of a from b
	 * (aka. a-b)
	 * @param a- the value being subtracted from 
	 * @param b- the value being subtracted
	 */
	public DifferenceValue(FormatValue a, FormatValue b)
	{
		this.difference = new SumValue(a,new ProductValue(new ActualValue(-1),b));
	}
	
	@Override
	public double getValue() 
	{
		return difference.getValue();
	}
	

}
