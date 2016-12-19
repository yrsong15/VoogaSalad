package viewformatter_util.side;

import viewformatter_util.formatobjects.FormatPoint;
import viewformatter_util.formatobjects.ReadOnlyPositionable;
import viewformatter_util.value.FormatValue;
import viewformatter_util.value.SumValue;

class BottomSide implements Side
{
	@Override
	public FormatPoint getPosition(ReadOnlyPositionable object, ReadOnlyPositionable reference, FormatValue distance) 
	{
		FormatValue x = reference.getX();
		FormatValue y = new SumValue(reference.getY(),reference.getHeight(),distance);
		
		return new FormatPoint(x,y);
	}
	
	@Override
	public FormatValue getReferenceValueForPercentGap(ReadOnlyPositionable reference)
	{
		return reference.getHeight();
	}
}
