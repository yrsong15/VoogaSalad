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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
	
	public ViewObjectBuilder addView(Node node, String viewObjectID)
	{
		ViewObject viewObject = getViewObject(viewObjectID);
		viewObject.connectNode(node);
		
		return new ViewObjectBuilder(viewObject,this);
	}
	
	public ViewObjectBuilder addView(Region node, String viewObjectID)
	{
		ViewObject viewObject = getViewObject(viewObjectID);
		viewObject.connectNode(node);
		
		return new ViewObjectBuilder(viewObject,this);
	}
	
	public ViewObjectBuilder addView(ImageView node, String viewObjectID)
	{
		ViewObject viewObject = getViewObject(viewObjectID);
		viewObject.connectNode(node);
		
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
	
	private void updateView(Pane root)
	{
		for(ViewObject viewObject : viewObjects.values())
		{
			viewObject.updateDimensions();
		}
		
		root.getChildren().clear();
	}
	
	private void addViewObjectsToView(Pane root)
	{
		List<ViewObject> sortedViewObjects = new ArrayList<ViewObject>(viewObjects.values());
		Collections.sort(sortedViewObjects, new BottomLayerFirstComparator());
		
		for(ViewObject viewObject : sortedViewObjects)
		{
			root.getChildren().add(viewObject.renderNode());
		}
		
	}
	
	private void calculateSizeOfNodes() throws Exception
	{
		Pane root = new Pane();
		for(ViewObject viewObject : viewObjects.values())
		{
			if(viewObject.getNode() == null)
			{
				throw new Exception("ViewObject \"" + viewObject.getViewObjectID() + "\" does not have a representative Node.");
			}
			else
			{
				root.getChildren().add(viewObject.getNode());
			}
		} 
		

		Stage testStage = new Stage();
		Scene testScene = new Scene(root);
		testStage.setScene(testScene);
		testStage.show();
		updateView(root);
		testStage.close();
		
	}
	
	private double findMaxViewObjectWidth()
	{
		double maxWidth = 0;
		for(ViewObject viewObject : viewObjects.values())
		{
			if(viewObject.getWidth().getValue() > maxWidth)
			{
				maxWidth = viewObject.getWidth().getValue();
			}
			
		}
		return maxWidth;
	}
	
	private double findMaxViewObjectHeight()
	{
		double maxHeight = 0;
		for(ViewObject viewObject : viewObjects.values())
		{
			if(viewObject.getHeight().getValue() > maxHeight)
			{
				maxHeight = viewObject.getHeight().getValue();
			}
			
		}
		return maxHeight;
	}
	
	public Pane renderView()
	{
		try
		{
			calculateSizeOfNodes();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		double maxWidth = findMaxViewObjectWidth();
		double maxHeight = findMaxViewObjectHeight();
		

		return createPaneWithViewObjects(maxWidth,maxHeight);
	}
	
	public Pane renderView(double width, double height)
	{
		try
		{
			calculateSizeOfNodes();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		return createPaneWithViewObjects(width,height);
			
	}
	
	private Pane createPaneWithViewObjects(double width, double height)
	{
		
		Pane root = new Pane();
		root.setMinSize(width, height);
		setSizeProperties(width,height);
		
		addViewObjectsToView(root);
		
		return root;
	}
	
}
