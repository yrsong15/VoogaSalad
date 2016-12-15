package side;

import value.FormatValue;
import value.ReadOnlyPositionable;
import value.SumValue;
import viewformatter.FormatPoint;

public class BottomSide implements Side
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
