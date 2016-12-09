package value;

public class ProductValue implements FormatValue
{
	private FormatValue[] values;
	
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
