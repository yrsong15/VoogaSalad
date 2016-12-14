package value;

public class DifferenceValue implements FormatValue
{
	private FormatValue difference;
	
	public DifferenceValue(FormatValue a, FormatValue b)
	{
		this.difference = new SumValue(a,new ProductValue(new ActualValue(-1),b));
	}
	
	@Override
	public double getValue() 
	{
		return difference.getValue();
	}
	

}
