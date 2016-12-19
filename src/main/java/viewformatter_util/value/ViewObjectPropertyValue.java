package viewformatter_util.value;

/**
 * This class is a FormatValue class specific to ViewObjects. A regular PropertyValue sets the 
 * default value to 0.0 if the value is never set. In the case of the width and the height of
 * the view object, the width and the height value are not directly specified. In order to keep
 * the ViewObject class to apply the default width and height value of a node, the ViewObject
 * class creates new ViewObjectPropertyValue classes from those PropertyValue.
 * @author Ryan Bergamini
 */
public class ViewObjectPropertyValue implements FormatValue
{
	private boolean hasValueToUpdate;
	private PropertyValue propertyValue;
	
	/**
	 * Creates a new ViewObjectPropertyValue class that has the same value as propertyValue. It
	 * sets the default value of that propertyValue to defaultValue without setting hasValueToUpdate
	 * to true
	 * @param propertyValue- the propertyValue the ViewObjectPropertyValue is based on
	 * @param defaultValue- the default value for the propertyValue of the ViewObjectPropertyValue
	 */
	public ViewObjectPropertyValue(PropertyValue propertyValue, FormatValue defaultValue)
	{
		this.propertyValue = propertyValue;
		this.propertyValue.setValue(defaultValue);
		this.hasValueToUpdate = false;
	}
	
	/**
	 * @param formatValue- sets the property value to format value
	 */
	public void setValue(FormatValue formatValue)
	{
		propertyValue.setValue(formatValue);
		hasValueToUpdate = true;
	}
	
	/**
	 * This method is typically checked by the ViewObject class to see if there is a value
	 * it needs to update its node based off of
	 * @return true if the property value has been set with the setValue(FormatValue) method
	 */
	public boolean hasValueToUpdate()
	{
		return hasValueToUpdate && propertyValue.hasValidValue();
	}
	
	/**
	 * This method can be called after the setValue(FormatValue) to set the need to update the
	 * Node based on this ViewObjectPropertyValue to false
	 */
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
