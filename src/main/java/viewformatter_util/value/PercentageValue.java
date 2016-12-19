package viewformatter_util.value;

/**
 * A formatValue who's value is a percentage of a limit value.
 * This FormatValue class is used when defining the x position of 
 * a view object as 30% of the Screen
 * @author Ryan Bergamini
 */
public class PercentageValue implements FormatValue
{
	private double percentage;
	private FormatValue limit;
	
	/**
	 * Creates a new Percentage value who's value is a percentage
	 * percent of the limit value
	 * @param percent- fraction of the limit value that the PercentageValue represents
	 * @param limit- the value the fraction is multiplied with to get the value for the object
	 */
	public PercentageValue(double fraction, FormatValue limit)
	{
		this.percentage = fraction;
		this.limit = limit;
	}
	
	@Override
	public double getValue()
	{
		return percentage * limit.getValue();
	}
}
