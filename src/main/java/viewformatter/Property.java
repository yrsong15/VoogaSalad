package viewformatter;

/**
 * 
 * @author Ryan Bergamini
 * This class allows for delayed initialization of a property value
 * @param <T> - the type of object the property is containing
 */
public class Property<T>
{
	private T value;
	
	public Property()
	{
		this.value = null;
	}
	
	public T getValue()
	{
		return value;
	}
	
	public void setValue(T value)
	{
		this.value = value;
	}
}
