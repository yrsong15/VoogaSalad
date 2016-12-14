package value;

public class ViewObjectPropertyValue implements FormatValue
{
	private boolean hasValueToUpdate;
	private PropertyValue propertyValue;
	
	public ViewObjectPropertyValue(PropertyValue propertyValue, FormatValue defaultValue)
	{
		this.propertyValue = propertyValue;
		this.propertyValue.setValue(defaultValue);
		this.hasValueToUpdate = false;
	}
	
	public void setValue(FormatValue formatValue)
	{
		propertyValue.setValue(formatValue);
		hasValueToUpdate = true;
	}
	
	public boolean hasValueToUpdate()
	{
		return hasValueToUpdate && propertyValue.hasValidValue();
	}
	
	public void cancelNeedToUpdate()
	{
		hasValueToUpdate = false;
	}

	@Override
	public double getValue() 
	{
		return propertyValue.getValue();
	}

}
