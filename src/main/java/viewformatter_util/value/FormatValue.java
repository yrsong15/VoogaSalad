package viewformatter_util.value;

/**
 * This interface defines an object that will return a value when the 
 * getValue() method is called. The purpose of this interface is to create
 * a system of values that won't actually have a double value until runtime
 * when the getValue() method is called. In this set up, objects can have values
 * for the width that are fractions of the overall screen width, which is set at
 * runtime.
 * 
 * @author Ryan Bergamini
 */
public interface FormatValue 
{
	public double getValue();
}


