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
	 * @param viewObjectID- the viewObjectID of the ViewObject who's side you are refering to
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
