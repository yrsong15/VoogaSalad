package value;

public class ActualValue implements FormatValue
{
	private double value;
	
	public ActualValue(double actual)
	{
		this.value = actual;
	}
	
	public double getValue()
	{
		return value;
	}
}
