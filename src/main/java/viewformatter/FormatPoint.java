package viewformatter;

import value.FormatValue;

public class FormatPoint 
{
	private FormatValue x;
	private FormatValue y;
	
	public FormatPoint(FormatValue x, FormatValue y)
	{
		this.x = x;
		this.y = y;
	}
	
	public FormatValue getX()
	{
		return x;
	}
	
	public FormatValue getY()
	{
		return y;
	}
	
}
