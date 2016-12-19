package viewformatter_util.formatobjects;

import viewformatter_util.value.FormatValue;

/**
 * Creates a simple 2D point that contains two FormatValues one for the x coordinate, one for the y coordinate
 * @author Ryan Bergamini
 */
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
