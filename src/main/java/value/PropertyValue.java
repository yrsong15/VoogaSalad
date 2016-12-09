package value;

public class PropertyValue implements FormatValue
{
	private FormatValue trueValue;
	
	public PropertyValue()
	{
		super();
	}
	
	public PropertyValue(FormatValue value)
	{
		this.trueValue = value;
	}
	
	public void setValue(FormatValue value)
	{
		this.trueValue = value;
	}
	
	@Override
	public double getValue() 
	{
		return trueValue.getValue();
	}
	
	public boolean hasValidValue()
	{
		return !(trueValue == null);
	}

}
