package side;

import value.DifferenceValue;
import value.FormatValue;
import value.ReadOnlyPositionable;
import value.SumValue;
import viewformatter.FormatPoint;

public class LeftSide implements Side
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
