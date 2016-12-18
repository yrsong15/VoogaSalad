package side;

import formatobjects.ViewObject;
import value.FormatValue;
import value.ReadOnlyPositionable;
import viewformatter.FormatPoint;

public interface Side 
{
	public static final Side RIGHT = new RightSide();
	public static final Side LEFT = new LeftSide();
	public static final Side TOP = new TopSide();
	public static final Side BOTTOM = new BottomSide();
	
	public FormatPoint getPosition(ReadOnlyPositionable object, ReadOnlyPositionable reference, FormatValue distance);
	/**
	 * Given a Positionable object, this method will return the appropriate property value to base a
	 * percent gap off of. For example, for a RightSide, this method would return the positionable object's
	 * width because a percent gap off the right side of an object is typically in the horizontal direction.
	 * This may reduce some flexibility on the user end about defining gaps for sides but it will make defining
	 * the percent gap off a side exponetially easier.
	 * 
	 * @param reference- the object who's properties will serve as the reference for the percent gap
	 * @return
	 */
	public FormatValue getReferenceValueForPercentGap(ReadOnlyPositionable reference);
}
