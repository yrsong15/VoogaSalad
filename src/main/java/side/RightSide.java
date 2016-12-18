package side;

import value.FormatValue;
import value.ReadOnlyPositionable;
import value.SumValue;
import viewformatter.FormatPoint;

public class RightSide implements Side
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
