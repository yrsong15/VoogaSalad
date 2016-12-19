package viewformatter_util.side;

import viewformatter_util.formatobjects.FormatPoint;
import viewformatter_util.formatobjects.ReadOnlyPositionable;
import viewformatter_util.value.FormatValue;
import viewformatter_util.value.SumValue;

class RightSide implements Side
{
	@Override
	public FormatPoint getPosition(ReadOnlyPositionable object, ReadOnlyPositionable reference, FormatValue distance) 
	{
		FormatValue x = new SumValue(reference.getX(),reference.getWidth(),distance);
		FormatValue y = reference.getY();
		
		return new FormatPoint(x,y);
	}

	@Override
	public FormatValue getReferenceValueForPercentGap(ReadOnlyPositionable reference)
	{
		return reference.getWidth();
	}

}
