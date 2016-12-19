package viewformatter_util.value;

/**
 * This class is used to represent the multiplication of two FormatValue objects
 * @author Ryan Bergamini
 */
public class ProductValue implements FormatValue
{
	private FormatValue[] values;
	
	/**
	 * Creates a new ProductValue who's value is the product of all the values of all the FormatValue in formatValues
	 * @param formatValues- the FormatValue objects that are being multiplied together
	 */
	public ProductValue(FormatValue... formatValues)
	{
		this.values = formatValues;
	}

	@Override
	public double getValue() {
		double product = 1;
		for(FormatValue fVal : values)
		{
			product *= fVal.getValue();
		}
		return product;
	}
	
	
}
