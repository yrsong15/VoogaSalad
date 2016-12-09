package viewformatter;

import side.Side;
import value.ActualValue;
import value.DifferenceValue;
import value.FormatValue;
import value.PercentageValue;
import value.ProductValue;
import value.ReadOnlyPositionable;
import value.SumValue;
import formatobjects.ViewObject;
import javafx.scene.Node;

public class ViewObjectBuilder 
{
	private ViewObject viewObject;
	private ViewFormatter formatter;
	
	
	/**
	 * TODO: I want to make the ViewFormatter the only one to access this constructor
	 * @param viewObject
	 * @param formatter
	 */
	public ViewObjectBuilder(ViewObject viewObject,ViewFormatter formatter)
	{
		this.viewObject = viewObject;
		this.formatter = formatter;
	}
	
	public ViewObjectBuilder(Node node, String viewObjectID)
	{
		this.viewObject = new ViewObject(node,viewObjectID);
		
	}
	
	public ViewObjectBuilder position(Side side, String viewObjectID, double distance)
	{
		return position(side,viewObjectID,new ActualValue(distance));
	}
	
	public ViewObjectBuilder positionWithPercentGap(Side side, String viewObjectID, double distance, String referenceViewObjectID)
	{
		ReadOnlyPositionable reference = formatter.getViewObject(referenceViewObjectID);
		FormatValue referenceValue = side.getReferenceValueForPercentGap(reference);
		FormatValue gap = new PercentageValue(distance,referenceValue);
		return position(side,viewObjectID,gap);
	}
	
	public ViewObjectBuilder positionWithPercentGapOfScreen(Side side, String viewObjectID, double distance)
	{
		ReadOnlyPositionable reference = formatter.getScreenFormat();
		FormatValue referenceValue = side.getReferenceValueForPercentGap(reference);
		FormatValue gap = new PercentageValue(distance,referenceValue);
		return position(side,viewObjectID,gap);
	}
	
	public ViewObjectBuilder centerXInScreen()
	{
		ReadOnlyPositionable reference = formatter.getScreenFormat();
		return centerX(reference);
	}
	
	public ViewObjectBuilder setZ(int z)
	{
		viewObject.setZ(z);
		return this;
	}
	
	public ViewObjectBuilder centerXBasedOnWidthOf(String viewObjectID)
	{
		ReadOnlyPositionable reference = formatter.getViewObject(viewObjectID);
		return centerX(reference);
	}
	
	private ViewObjectBuilder centerX(ReadOnlyPositionable reference)
	{
		FormatValue baseValue = reference.getX();
		FormatValue limit = reference.getWidth();
		FormatValue midpoint = new ProductValue(new ActualValue(.5), limit);
		FormatValue halfOfWidth = new ProductValue(new ActualValue(.5), viewObject.getWidth());
		FormatValue newX = new SumValue(baseValue, new DifferenceValue(midpoint,halfOfWidth));
		viewObject.setX(newX);
		return this;
	}
	
	public ViewObjectBuilder centerYInScreen()
	{
		ReadOnlyPositionable reference = formatter.getScreenFormat();
		return centerY(reference);
	}
	
	public ViewObjectBuilder centerYBasedOnWidthOf(String viewObjectID)
	{
		ReadOnlyPositionable reference = formatter.getViewObject(viewObjectID);
		return centerY(reference);
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
	
	
	public ViewObjectBuilder setSize(double width, double height)
	{
		viewObject.setWidth(new ActualValue(width));
		viewObject.setHeight(new ActualValue(height));
		
		return this;
	}
	
	public ViewObjectBuilder setWidth(double widthPercent, String viewObjectID)
	{
		ReadOnlyPositionable reference = formatter.getViewObject(viewObjectID);
		viewObject.setWidth(new PercentageValue(widthPercent,reference.getWidth()));
		
		return this;
	}
	
	public ViewObjectBuilder setHeight(double heightPercent, String viewObjectID)
	{
		ReadOnlyPositionable reference = formatter.getViewObject(viewObjectID);
		viewObject.setWidth(new PercentageValue(heightPercent,reference.getWidth()));
		
		return this;
	}
	
	public ViewObjectBuilder setPosition(double x, double y)
	{
		viewObject.setX(new ActualValue(x));
		viewObject.setY(new ActualValue(y));
		
		return this;
	}
	
	public ViewObjectBuilder setXAsFractionOfWidth(double xPercent, String viewObjectID)
	{
		ViewObject other = formatter.getViewObject(viewObjectID);
		FormatValue otherWidth = other.getWidth();
		FormatValue newWidth = new PercentageValue(xPercent,otherWidth);
		FormatValue otherX = other.getX();
		viewObject.setX(new SumValue(otherX,newWidth));
		return this;
	}
	
	public ViewObjectBuilder setXAsFractionOfWidthFromFarSide(double xPercent, String viewObjectID)
	{
		ViewObject other = formatter.getViewObject(viewObjectID);
		return setXAsFraction(xPercent,other);
	}
	
	public ViewObjectBuilder setXAsFractionOfScreenWidth(double xPercent)
	{
		return setXAsFraction(xPercent,formatter.getScreenFormat());
	}
	
	public ViewObjectBuilder setX(double xPosition)
	{
		viewObject.setX(new ActualValue(xPosition));
		return this;
	}
	
	public ViewObjectBuilder setY(double yPosition)
	{
		viewObject.setY(new ActualValue(yPosition));
		return this;
	}
	
	private ViewObjectBuilder setXAsFraction(double xPercent, ReadOnlyPositionable reference)
	{
		FormatValue otherWidth = reference.getWidth();
		FormatValue farX = new SumValue(otherWidth,reference.getX());
		FormatValue newWidth = new PercentageValue(xPercent,otherWidth);
		FormatValue newX = new DifferenceValue(farX, new SumValue(newWidth,viewObject.getWidth()));
		viewObject.setX(newX);
		return this;
	}
	
	public ViewObjectBuilder setYAsFractionOfHeight(double yPercent, String viewObjectID)
	{
		return setYAsFraction(yPercent, formatter.getViewObject(viewObjectID));
	}
	
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
	
}
