package value;

import java.util.Arrays;

public class SumValue implements FormatValue
{
	private FormatValue[] values;
	
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
