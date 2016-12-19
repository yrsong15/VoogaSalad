package viewformatter_util.value;


/**
 * This class represents the summation of a list of FormatValues
 * @author Ryan Bergamini
 */
public class SumValue implements FormatValue
{
	private FormatValue[] values;
	
	/**
	 * @param formatValues- list of formatValues that need to be added
	 */
	public SumValue(FormatValue... formatValues)
	{
		this.values = formatValues;
	}

	@Override
	public double getValue() {
		double sum = 0;
	
		for(FormatValue fVal : values)
		{
			sum += fVal.getValue();
		}
		return sum;
	}
	
	
}
