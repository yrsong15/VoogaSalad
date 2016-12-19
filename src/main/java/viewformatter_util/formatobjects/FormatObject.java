package viewformatter_util.formatobjects;

import viewformatter_util.value.ActualValue;
import viewformatter_util.value.FormatValue;
import viewformatter_util.value.PropertyValue;

public class FormatObject implements ReadOnlyPositionable
{
	private PropertyValue width;
	private PropertyValue height;
	private PropertyValue x;
	private PropertyValue y;
	private int z;
	
	public FormatObject()
	{
		this.width = new PropertyValue();
		this.height = new PropertyValue();
		this.x = new PropertyValue();
		this.y = new PropertyValue();
		this.z = 0;
	}
	
	@Override
	public PropertyValue getWidth()
	{
		return width;
	}
	
	@Override
	public PropertyValue getHeight()
	{
		return height;
	}
	
	@Override
	public PropertyValue getX()
	{
		return x;
	}
	
	@Override
	public PropertyValue getY()
	{
		return y;
	}
	
	public int getZ()
	{
		return z;
	}
	
	public void setWidth(FormatValue width)
	{
		this.width.setValue(width);
	}
	
	public void setHeight(FormatValue height)
	{
		this.height.setValue(height);
	}
	
	public void setX(FormatValue x)
	{
		this.x.setValue(x);
	}
	
	public void setY(FormatValue y)
	{
		this.y.setValue(y);
	}
	
	public void setZ(int z)
	{
		this.z = z;
	}
	

}
