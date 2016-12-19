package viewformatter_util.value;

/**
 * This class acts as a wrapper class around numerical constants
 * in order to perform math operations with the PercentageValues, who 
 * value may not be set until the view is rendered.
 * @author Ryan Bergamini
 *
 */
public class ActualValue implements FormatValue
{
	private double value;
	
	public ActualValue(double actual)
	{
		this.value = actual;
	}
	
	@Override
	public double getValue()
	{
		return value;
	}
}
