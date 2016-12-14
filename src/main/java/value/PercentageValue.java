package value;

public class PercentageValue implements FormatValue
{
	private double percentage;
	private FormatValue limit;
	
	public PercentageValue(double percent, FormatValue limit)
	{
		this.percentage = percent;
		this.limit = limit;
	}
	
	public double getValue()
	{
		return percentage * limit.getValue();
	}
}
