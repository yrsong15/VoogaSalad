package side;

import value.DifferenceValue;
import value.FormatValue;
import value.ReadOnlyPositionable;
import value.PropertyValue;
import value.SumValue;
import viewformatter.FormatPoint;

public class TopSide implements Side
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
