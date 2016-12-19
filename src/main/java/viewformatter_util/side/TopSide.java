package viewformatter_util.side;

import viewformatter_util.formatobjects.FormatPoint;
import viewformatter_util.formatobjects.ReadOnlyPositionable;
import viewformatter_util.value.DifferenceValue;
import viewformatter_util.value.FormatValue;
import viewformatter_util.value.PropertyValue;
import viewformatter_util.value.SumValue;

class TopSide implements Side
{
	@Override
	public FormatPoint getPosition(ReadOnlyPositionable object, ReadOnlyPositionable reference, FormatValue distance) 
	{
		FormatValue x = reference.getX();
		FormatValue y = new DifferenceValue(reference.getY(), new SumValue(object.getHeight(),distance));
		
		return new FormatPoint(x,y);
	}
	
	@Override
	public FormatValue getReferenceValueForPercentGap(ReadOnlyPositionable reference)
	{
		return reference.getHeight();
	}
}
