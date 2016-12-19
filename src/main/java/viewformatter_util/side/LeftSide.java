package viewformatter_util.side;

import viewformatter_util.formatobjects.FormatPoint;
import viewformatter_util.formatobjects.ReadOnlyPositionable;
import viewformatter_util.value.DifferenceValue;
import viewformatter_util.value.FormatValue;
import viewformatter_util.value.SumValue;

class LeftSide implements Side
{
	@Override
	public FormatPoint getPosition(ReadOnlyPositionable object, ReadOnlyPositionable reference, FormatValue distance) 
	{
		FormatValue x = new DifferenceValue(reference.getX(),new SumValue(object.getWidth(),distance));
		FormatValue y = reference.getY();
		
		return new FormatPoint(x,y);
	}
	
	@Override
	public FormatValue getReferenceValueForPercentGap(ReadOnlyPositionable reference)
	{
		return reference.getWidth();
	}
}
