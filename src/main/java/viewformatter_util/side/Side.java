package viewformatter_util.side;

import viewformatter_util.formatobjects.FormatPoint;
import viewformatter_util.formatobjects.ReadOnlyPositionable;
import viewformatter_util.formatobjects.ViewObject;
import viewformatter_util.value.FormatValue;

//This entire file is part of my masterpiece.
// Ryan Bergamini

/* Masterpiece justification: 
 * 
 * As mentioned in the ViewObjectBuilder masterpiece justification, the Side interface allows
 * for flexibility in the definition of a Side. Whenever a new Side is created for the user to use,
 * it can be added at as a public static constant for easy reference.
 * 
 * More importantly, the Side interface in tandem with the ViewObjectBuilder represent a SOLID design. For example,
 * S - Single Responsibility Principle
 * 		> Although the ViewObjectBuilder is a large class, all the methods contain the singular purpose of configuring
 * 		  a ViewObject. As for the Side interface, it is only tasked with handling Side-related calculations.
 * O - Open/Closed Principle ("Open for extension, closed for modification")
 * 		> The Side class helps satisfy the Open/Closed principle because its interface 
 * 		  design allows for users to create their own Side classes without disrupting the
 * 		  implementation of the Side class and its usage in the ViewObjectBuilder class
 * L - Liskov Substitution Principle ("Make things without invalidating old things")
 * 		> Liskov Substitution Principle ties in with the previous open-close principle in
 * 		  the sense that users can create their own Side classes without disrupting to original
 * 		  implementation
 * I - Interface Segregation Principle
 * 		> The Side class applies the Interface Segregation Principle by taking in ReadOnlyPositionable objects
 * 		  instead of FormatObjects. This not only allows the Side class to potentially be reused in another project
 * 		  but it also limits the Side class to know only what it needs to know about the objects it is positioning.
 * D - Dependency Inversion Principle
 * 		> The Side and ViewObjectBuidler class exhibits two instances of the Dependency Inversion Principle. First 
 * 		  the Side interface itself provides a layer of abstraction between the actual side class and the ViewObjectBuilder.
 * 		  This way if the implementation of a Side completely changes, all you would have to do is make the new of the implementation
 * 		  conform to the methods getPosition() and getReferenceValueForPercentGap() required by the Side interface. The
 * 		  second example of the Dependency Inversion Principle is with the ReadOnlyPositionable interface. Since the Side
 * 		  object was defined by taking in a ReadOnlyPositionable object, I did not have to change anything in the Side class
 * 		  when I decided to add a super class FormatObject to the ViewObject class. All I did was ensure FormatObject implemented
 * 		  the ReadOnlyPositionable interface.
 */
public interface Side 
{
	public static final Side RIGHT = new RightSide();
	public static final Side LEFT = new LeftSide();
	public static final Side TOP = new TopSide();
	public static final Side BOTTOM = new BottomSide();
	
	/**
	 * Give an object, a reference object, and a distance, getPosition returns the FormatPoint of the (x,y) coordinates
	 * if it were to by positioned on the desired Side of the reference
	 * @param object
	 * @param reference
	 * @param distance
	 * @return the FormatPoint the ReadOnlyPositionable object should be located
	 */
	public FormatPoint getPosition(ReadOnlyPositionable object, ReadOnlyPositionable reference, FormatValue distance);
	/**
	 * Given a Positionable object, this method will return the appropriate property value to base a
	 * percent gap off of. For example, for a RightSide, this method would return the positionable object's
	 * width because a percent gap off the right side of an object is typically in the horizontal direction.
	 * This may reduce some flexibility on the user end about defining gaps for sides but it will make defining
	 * the percent gap off a side exponetially easier.
	 * 
	 * @param reference- the object who's properties will serve as the reference for the percent gap
	 * @return the reference value
	 */
	public FormatValue getReferenceValueForPercentGap(ReadOnlyPositionable reference);
}
