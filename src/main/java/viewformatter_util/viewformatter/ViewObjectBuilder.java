package viewformatter_util.viewformatter;

import viewformatter_util.formatobjects.FormatPoint;
import viewformatter_util.formatobjects.ReadOnlyPositionable;
import viewformatter_util.formatobjects.ViewObject;
import viewformatter_util.side.Side;
import viewformatter_util.value.ActualValue;
import viewformatter_util.value.DifferenceValue;
import viewformatter_util.value.FormatValue;
import viewformatter_util.value.PercentageValue;
import viewformatter_util.value.ProductValue;
import viewformatter_util.value.SumValue;

import javafx.scene.Node;

//This entire file is part of my masterpiece.
// Ryan Bergamini

/* Masterpiece justification: 
 * 
 * I chose to include the ViewObjectBuilder mainly for the position() method, but I also think this entire class is well designed.
 * 
 * Starting with the position() method, this class does not use a series of if statements or similar methods for positioning each
 * side in order to implement the side positioning feature in the ViewFormatter. Instead, this class passes the ViewObject it is positioning,
 * the reference ViewObject, and the distance between the two all to a Side object which returns the new point for the ViewObject. As Professor
 * Duvall told us in the beginning of the semester, we should aim to have multiple classes with smaller tasks rather than one large brain class.
 * This design works toward that goal by delegating the positioning logic to the Side class itself. Additionally, by delegating that logic to
 * the Side class it makes it easier to change that positioning logic to define various kinds of Side objects (ex. RightSide(), LeftSide(), 
 * TopSide(), and BottomSide()).
 * 
 * I also believe the overall purpose of this class is well designed. The ViewObjectBuilder class is an integral part of a quasi-builder pattern
 * implemented by the ViewFormatter. When a Node is added to the ViewFormatter, a ViewObjectBuilder is returned to configure that Node in the
 * Formatter. Since every method in ViewObjectBuilder returns a reference to itself (the ViewObjectBuilder), the user can chain methods together
 * to configure only the properties they want to configure. This is an advantage over directly configuring the properties of a ViewObject when
 * it is added to the ViewFormatter, which would require methods with very long parameter lists that would harm readability.
 * 
 * The ViewObjectBuilder also supports the open-closed principle by having a protected constructor. With a protected constructor, only classes
 * in the same package as the ViewObjectBuilder can access it. In this case, that is only the ViewFormatter class, which should be the only
 * class that can create a ViewObjectBuilder instance. Because the ViewObjectBuilder should only be created by the ViewFormatter class it naturally
 * built a dependency on the ViewFormatter class. To keep a reference to other ViewObjects in the formatter, the ViewObjectBuilder needs to maintain
 * a reference to the ViewFormatter. I decided against applying the Interface Segregation principle to limit the scope of methods the ViewObjectBuilder
 * has access too and instead made the methods getScreenFormat() and getViewObject(String) protected methods. If I were to apply the Interface Segregation
 * principle to the ViewFormatter reference in this class, then the getScreenFormat() and getViewObject(String) methods would have to be public, giving
 * access to these methods to the user. As a trade off of giving more ViewFormatter access to the ViewObjectBuilder, I can limit the access a
 * random user has in the ViewFormatter class.
 * 
 * As a final touch, in this class I frequently use private helper methods to extract duplicate code from multiple methods that share similar calculations in this class. 
 * For example, position(), positionWithPercentGap(), and positionWithPercentGapOfScreen() all position a ViewObject in the screen, but they each have a different buffer for 
 * that position. Instead of creating a Distance class to consolidate the three public methods into one public method, I decided to leave the three method system because if 
 * I created a Distance class that would require the user to configure a Distance class for each method, which would make the ViewFormatter utility more difficult to use.
 * To keep using the three method system while not duplicating code, I call a helper method that performs the math required for each method.
 */


/**
 * ViewObjectBuilder is a package friendly class that is used to create a ViewObject. Whenever a new View is added to the
 * viewFormatter, the view formatter returns a ViewObjectBuilder for that object. Each method to configure a ViewObject also
 * returns a reference to this ViewObjectBuilder. That way, the user of this view formatter utility can only specific which
 * properties of the view object they want to set without having to set all of them at once. This quasi-builder pattern also
 * keeps a bunch of telescoping setter methods from building up in the ViewFormatter class.
 * 
 * @author Ryan Bergamini
 */
public class ViewObjectBuilder 
{
	private ViewObject viewObject;
	private ViewFormatter formatter;
	
	
	/**
	 * @param viewObject- the ViewObject the Builder is configuring
	 * @param formatter- reference to the ViewFormatter that created this object
	 */
	protected ViewObjectBuilder(ViewObject viewObject,ViewFormatter formatter)
	{
		this.viewObject = viewObject;
		this.formatter = formatter;
	}
	
	/**
	 * This method positions the ViewObject a certain distance away from the specified side of the ViewObject
	 * represented by the viewObjectID
	 * @param side- the Side the viewObject is being positioned off of
	 * @param viewObjectID- the viewObjectID of the ViewObject who's side you are referring to
	 * @param distance- distance from the side of the reference ViewObject you want the ViewObject to be located
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder position(Side side, String viewObjectID, double distance)
	{
		return position(side,viewObjectID,new ActualValue(distance));
	}
	
	/**
	 * This method positions the ViewObject a certain fraction of a reference distance away from the specified side of the ViewObject
	 * represented by the viewObjectID. The reference distance is dependent on the side being positioned. For example, for Side.RIGHT and
	 * Side.LEFT, the reference distance would be the width of the reference object because those are horizontal sides
	 * @param side- the Side the viewObject is being positioned off of
	 * @param viewObjectID- the viewObjectID of the ViewObject who's side you are refering to
	 * @param percentGap- distance from the side of the reference ViewObject you want the ViewObject to be located
	 * @param referenceViewObjectID- the viewObjectID of the referenceViewObject who is acting as a reference for the percentGape
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder positionWithPercentGap(Side side, String viewObjectID, double percentGap, String referenceViewObjectID)
	{
		ReadOnlyPositionable reference = formatter.getViewObject(referenceViewObjectID);
		FormatValue referenceValue = side.getReferenceValueForPercentGap(reference);
		FormatValue gap = new PercentageValue(percentGap,referenceValue);
		return position(side,viewObjectID,gap);
	}
	
	/**
	 * This method positions the ViewObject a certain fraction of a reference distance away from the specified side of the ViewObject
	 * represented by the viewObjectID. The reference distance is dependent on the side being positioned. For example, for Side.RIGHT and
	 * Side.LEFT, the reference distance would be the width of the screen because those are horizontal sides
	 * @param side- the Side the viewObject is being positioned off of
	 * @param viewObjectID- the viewObjectID of the ViewObject who's side you are refering to
	 * @param percentGap- distance from the side of the reference ViewObject you want the ViewObject to be located
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder positionWithPercentGapOfScreen(Side side, String viewObjectID, double percentGap)
	{
		ReadOnlyPositionable reference = formatter.getScreenFormat();
		FormatValue referenceValue = side.getReferenceValueForPercentGap(reference);
		FormatValue gap = new PercentageValue(percentGap,referenceValue);
		return position(side,viewObjectID,gap);
	}
	
	/**
	 * Centers the x value of the view object in the screen
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder centerXInScreen()
	{
		ReadOnlyPositionable reference = formatter.getScreenFormat();
		return centerX(reference);
	}
	
	/**
	 * Sets the z-value of the view object. The smaller the z value the closer the ViewObject is to the back of the screen.
	 * The higher the z value, the closer the view object is to the front of the screen.
	 * @param z- the z location of the view
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setZ(int z)
	{
		viewObject.setZ(z);
		return this;
	}
	
	/**
	 * Centers the x-value of the view object based on the width of the ViewObject represented by viewObjectID
	 * @param viewObjectID- the viewObjectID of the ViewObject acting as a reference
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder centerXBasedOnWidthOf(String viewObjectID)
	{
		ReadOnlyPositionable reference = formatter.getViewObject(viewObjectID);
		return centerX(reference);
	}
	
	
	/**
	 * Sets the width of the ViewObject to width
	 * @param width- desired width of the view object
	 * @return  instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setWidth(double width)
	{
		viewObject.setWidth(new ActualValue(width));
		return this;
	}
	
	/**
	 * Sets the height of the VeiwObject to height
	 * @param height- desired height of the view object
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setHeight(double height)
	{
		viewObject.setHeight(new ActualValue(height));
		return this;
	}
	
	/**
	 * Centers the y value of the ViewObject in the screen
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder centerYInScreen()
	{
		ReadOnlyPositionable reference = formatter.getScreenFormat();
		return centerY(reference);
	}
	
	/**
	 * Centers the y-value of the view object based on the height of the ViewObject represented by viewObjectID
	 * @param viewObjectID- the viewObjectID of the ViewObject acting as a reference
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder centerYBasedOnHeighthOf(String viewObjectID)
	{
		ReadOnlyPositionable reference = formatter.getViewObject(viewObjectID);
		return centerY(reference);
	}
	
	/**
	 * Sets the size of the view to width x height
	 * @param width- desired width
	 * @param height- desired height
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setSize(double width, double height)
	{
		viewObject.setWidth(new ActualValue(width));
		viewObject.setHeight(new ActualValue(height));
		
		return this;
	}
	
	/**
	 * Sets width to a specified percentage of another ViewObject's width
	 * @param widthPercent- percent of the reference width
	 * @param viewObjectID- viewObjectID of ViewObject who's width is acting as a reference
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setWidth(double widthPercent, String viewObjectID)
	{
		ReadOnlyPositionable reference = formatter.getViewObject(viewObjectID);
		return setWidth(widthPercent,reference);
	}
	
	/**
	 * Sets width to a specified percentage of the screen's width
	 * @param widthPercent- percent of the screen width
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setWidthAsFractionOfScreen(double widthPercent)
	{
		ReadOnlyPositionable reference = formatter.getScreenFormat();
		return setWidth(widthPercent,reference);
	}
	
	/**
	 * Sets height to a specified percentage of another ViewObject's height
	 * @param heightPercent- percent of the reference height
	 * @param viewObjectID- viewObjectID of ViewObject who's height is acting as a reference
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setHeight(double heightPercent, String viewObjectID)
	{
		ReadOnlyPositionable reference = formatter.getViewObject(viewObjectID);
		return setHeight(heightPercent,reference);
	}
	
	/**
	 * Sets height to a specified percentage of the screen's height
	 * @param heightPercent- percent of the reference height
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setHeightAsFractionOfScreen(double heightPercent)
	{
		ReadOnlyPositionable reference = formatter.getScreenFormat();
		return setHeight(heightPercent,reference);
	}

	
	/**
	 * Sets the position of the ViewObject to (x,y)
	 * @param x- x-value
	 * @param y- y-value
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setPosition(double x, double y)
	{
		viewObject.setX(new ActualValue(x));
		viewObject.setY(new ActualValue(y));
		
		return this;
	}
	
	/**
	 * Sets the x value of the view object at a percentage of the width of the reference ViewObjectID
	 * @param xPercent- x percentage of the width (0 on left and 1.0 is on the right)
	 * @param viewObjectID- viewObjectID of the reference ViewObject
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setXAsFractionOfWidth(double xPercent, String viewObjectID)
	{
		ViewObject other = formatter.getViewObject(viewObjectID);
		return setXAsFraction(xPercent,other);
	}
	
	/**
	 * Sets the x value of the view object at a percentage of the width of the screen
	 * @param xPercent- x percentage
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setXAsFractionOfScreenWidth(double xPercent)
	{
		return setXAsFraction(xPercent,formatter.getScreenFormat());
	}
	
	/**
	 * Sets the x value of the view object at a percentage of the width of the screen, where 0.0 is now on the right
	 * and 1.0 is now on the left
	 * @param xPercent- x percentage
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setXAsFractionOfWidthFromFarSide(double xPercent, String viewObjectID)
	{
		ReadOnlyPositionable reference = formatter.getViewObject(viewObjectID);
		FormatValue otherWidth = reference.getWidth();
		FormatValue farX = new SumValue(otherWidth,reference.getX());
		FormatValue newWidth = new PercentageValue(xPercent,otherWidth);
		FormatValue newX = new DifferenceValue(farX, new SumValue(newWidth,viewObject.getWidth()));
		viewObject.setX(newX);
		return this;
	}
	
	/**
	 * Sets the viewObject's x position to xPosition
	 * @param xPosition- x position of the ViewObject
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setX(double xPosition)
	{
		viewObject.setX(new ActualValue(xPosition));
		return this;
	}
	
	/**
	 * Sets the viewObject's y position to yPosition
	 * @param yPosition- y position of the ViewObject
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setY(double yPosition)
	{
		viewObject.setY(new ActualValue(yPosition));
		return this;
	}
	
	/**
	 * Sets the y value of the view object at a percentage of the height of the reference ViewObjectID
	 * @param yPercent- y percentage of the height (0 on top and 1.0 is on the bottom)
	 * @param viewObjectID- viewObjectID of the reference ViewObject
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setYAsFractionOfHeight(double yPercent, String viewObjectID)
	{
		return setYAsFraction(yPercent, formatter.getViewObject(viewObjectID));
	}
	
	/**
	 * Sets the y value of the view object at a percentage of the height of the reference ViewObjectID
	 * @param yPercent- y percentage of the height (0 on left and 1.0 is on the right)
	 * @param viewObjectID- viewObjectID of the reference ViewObject
	 * @return instance of this ViewObjectBuilder
	 */
	public ViewObjectBuilder setYAsFractionOfScreenHeight(double yPercent)
	{
		return setYAsFraction(yPercent, formatter.getScreenFormat());
	}

	private ViewObjectBuilder setYAsFraction(double yPercent, ReadOnlyPositionable reference)
	{
		FormatValue otherHeight = reference.getHeight();
		FormatValue newHeight = new PercentageValue(yPercent,otherHeight);
		FormatValue otherY = reference.getY();
		viewObject.setY(new SumValue(otherY,newHeight));
		return this;
	}

	private void setViewObjectPosition(FormatPoint newPosition)
	{
		if(viewObject.getX() != newPosition.getX())
		{
			viewObject.setX(newPosition.getX());
		}

		if(viewObject.getY() != newPosition.getY())
		{
			viewObject.setY(newPosition.getY());
		}
	}

	private ViewObjectBuilder setHeight(double heightPercent, ReadOnlyPositionable reference)
	{
		viewObject.setHeight(new PercentageValue(heightPercent,reference.getHeight()));
		return this;
	}



	private ViewObjectBuilder setWidth(double widthPercent, ReadOnlyPositionable reference)
	{
		viewObject.setWidth(new PercentageValue(widthPercent,reference.getWidth()));
		return this;
	}



	private ViewObjectBuilder centerY(ReadOnlyPositionable reference)
	{
		FormatValue baseValue = reference.getY();
		FormatValue limit = reference.getHeight();
		FormatValue midpoint = new ProductValue(new ActualValue(.5), limit);
		FormatValue halfOfHeight = new ProductValue(new ActualValue(.5), viewObject.getHeight());
		FormatValue newY = new SumValue(baseValue, new DifferenceValue(midpoint,halfOfHeight));
		viewObject.setY(newY);
		return this;
	}

	private ViewObjectBuilder position(Side side, String viewObjectID, FormatValue distance)
	{
		ReadOnlyPositionable other = formatter.getViewObject(viewObjectID);
		FormatPoint position = side.getPosition(viewObject,other,distance);
		setViewObjectPosition(position);
		return this;
	}


	private ViewObjectBuilder centerX(ReadOnlyPositionable reference)
	{
		FormatValue baseValue = reference.getX();
		FormatValue limit = reference.getWidth();
		FormatValue midpoint = new SumValue(baseValue, new ProductValue(new ActualValue(.5), limit));
		FormatValue halfOfWidth = new ProductValue(new ActualValue(.5), viewObject.getWidth());
		FormatValue newX = new DifferenceValue(midpoint,halfOfWidth);
		viewObject.setX(newX);
		return this;
	}

	private ViewObjectBuilder setXAsFraction(double xPercent, ReadOnlyPositionable reference)
	{
		FormatValue referenceWidth = reference.getWidth();
		FormatValue newWidth = new PercentageValue(xPercent,referenceWidth);
		FormatValue referenceX = reference.getX();
		viewObject.setX(new SumValue(referenceX,newWidth));
		return this;
	}
	
}
