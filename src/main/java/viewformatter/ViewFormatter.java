package viewformatter;

import java.util.Map;

import formatobjects.BottomLayerFirstComparator;
import formatobjects.FormatObject;
import formatobjects.ViewObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import value.ActualValue;
import value.PropertyValue;
import value.ReadOnlyPositionable;

public class ViewFormatter 
{
	private Map<String,ViewObject> viewObjects;
	private FormatObject screen;

	public ViewFormatter()
	{
		viewObjects = new HashMap<String,ViewObject>();
		this.screen = new FormatObject();
		screen.setX(new ActualValue(0));
		screen.setY(new ActualValue(0));
	}
	
	public ViewObjectBuilder addView(Node node, String viewObjectID, double defaultWidth, double defaultHeight)
	{
		ViewObject viewObject = getViewObject(viewObjectID);
		viewObject.connectNode(node);
		viewObject.setWidth(new ActualValue(defaultWidth));
		viewObject.setHeight(new ActualValue(defaultHeight));
		
		return new ViewObjectBuilder(viewObject,this);
	}
	
	
	public ViewObject getViewObject(String viewObjectID)
	{
		if(!viewObjects.containsKey(viewObjectID))
		{
			viewObjects.put(viewObjectID, new ViewObject(viewObjectID));
		}
		
		return viewObjects.get(viewObjectID);
	}
	
	public PropertyValue getScreenWidth()
	{
		return screen.getWidth();
	}
	
	public PropertyValue getScreenHeight()
	{
		return screen.getHeight();
	}
	
	public ReadOnlyPositionable getScreenFormat()
	{
		return screen;
	}
	
	private void setSizeProperties(double width, double height)
	{
		screen.getWidth().setValue(new ActualValue(width));
		screen.getHeight().setValue(new ActualValue(height));
	}
	
	public Pane renderView(double width, double height)
	{
		Pane root = new Pane();
		root.setMinSize(width, height);
		setSizeProperties(width,height);
	
		List<ViewObject> sortedViewObjects = new ArrayList<ViewObject>(viewObjects.values());
		Collections.sort(sortedViewObjects,new BottomLayerFirstComparator());
		
		for(ViewObject viewObject : sortedViewObjects)
		{
			root.getChildren().add(viewObject.renderNode());
		}
		
		return root;
			
	}
	
}
