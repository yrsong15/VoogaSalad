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
	
	protected FormatValue getInternalValue()
	{
		return trueValue;
	}
	
	@Override
	public double getValue() 
	{
		if(trueValue == null)
		{
			return 0;
		}
		return trueValue.getValue();
	}
	
	public boolean hasValidValue()
	{
		return !(trueValue == null);
	}
	
}
