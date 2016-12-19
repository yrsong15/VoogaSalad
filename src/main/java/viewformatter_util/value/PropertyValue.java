package viewformatter_util.value;

/**
 * This class is used to store the values of the FormatObjects. The PropertyValue class
 * maintains a reference to a FormatValue that represents the property value, in order to 
 * set the width and the height or other properties of an object after the object is created.
 * @author Ryan Bergamini
 */
public class PropertyValue implements FormatValue
{
	private FormatValue trueValue;
	
	/**
	 * 
	 * @param value- value the PropertyValue contains
	 */
	public PropertyValue(FormatValue value)
	{
		this.trueValue = value;
	}
	
	/**
	 * Sets the value the PropertyValue represents
	 * @param value- value the PropertyValue sets to
	 */
	public void setValue(FormatValue value)
	{
		this.trueValue = value;
	}
	
	
	@Override
	/**
	 * If the value the PropertyValue represents hasn't been set, getValue() returns 0.0
	 */
	public double getValue() 
	{
		if(trueValue == null)
		{
			return 0;
		}
		return trueValue.getValue();
	}
	
	/**
	 * 
	 * @return true if the value that the Property value represents has been set
	 */
	public boolean hasValidValue()
	{
		return !(trueValue == null);
	}
	
}
