package viewformatter_util.viewformatter;

import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import viewformatter_util.exception.ViewFormatterException;
import viewformatter_util.formatobjects.BottomLayerFirstComparator;
import viewformatter_util.formatobjects.FormatObject;
import viewformatter_util.formatobjects.ReadOnlyPositionable;
import viewformatter_util.formatobjects.ViewObject;
import viewformatter_util.value.ActualValue;

/**
 * The ViewFormatter class is used by the user to simply configure a view.
 * To use the ViewFormatter class effectively, first add ViewObjects (which are 
 * wrapper objects for Nodes) to the ViewFormatter through the addView method. Then
 * configure each ViewObject as they're added through a builder pattern. For example,
 * to add a view then set its size and position I would type:
 * 
 * viewFormatter.addView(node,"Node").setSize(10,10).setX(10);
 * 
 * Once each ViewObject is configured, you can then call a renderView(double,double) 
 * method to get back a Pane with the configured view
 * 
 * @author Ryan Bergamini
 *
 */
public class ViewFormatter implements ReadOnlyViewFormatter
{
	private Map<String,ViewObject> viewObjects;
	private FormatObject screen;

	/**
	 * Creates a new ViewFormatter object
	 */
	public ViewFormatter()
	{
		viewObjects = new HashMap<String,ViewObject>();
		this.screen = new FormatObject();
		screen.setX(new ActualValue(0));
		screen.setY(new ActualValue(0));
	}
	
	/**
	 * Adds a new ViewObject to the ViewFormatter to be formatted
	 * @param node- Node that represents that ViewObject
	 * @param viewObjectID- the String that identifies the View Object
	 * @return
	 */
	public ViewObjectBuilder addView(Node node, String viewObjectID)
	{
		ViewObject viewObject = getViewObject(viewObjectID);
		viewObject.connectNode(node);
		
		return new ViewObjectBuilder(viewObject,this);
	}
	
	/**
	 * The returned pane has the width of the largest view object
	 * @return a Pane object with the formatted view
	 */
	public Pane renderView()
	{
		calculateSizeOfNodes();
		
		double maxWidth = findMaxViewObjectWidth();
		double maxHeight = findMaxViewObjectHeight();
		
		return createPaneWithViewObjects(maxWidth,maxHeight);
	}
	
	/**
	 * The returned Pane has the dimensions width x height
	 * @param width- width of the desired Pane
	 * @param height- height of the desired Pane
	 * @return Pane
	 */
	public Pane renderView(double width, double height)
	{
		calculateSizeOfNodes();
		return createPaneWithViewObjects(width,height);	
	}
	
	/**
	 * Only classes in the viewformatter_util.viewformatter package should have
	 * access to this method
	 * @param viewObjectID- viewObjectID for the desired ViewObject
	 * @return the ViewObject that corresponds to that viewObjectID
	 */
	protected ViewObject getViewObject(String viewObjectID)
	{
		if(!viewObjects.containsKey(viewObjectID))
		{
			viewObjects.put(viewObjectID, new ViewObject(viewObjectID));
		}
		
		return viewObjects.get(viewObjectID);
	}
	
	/**
	 * @return the format object that contains the size width x and y of the sceen
	 */
	protected ReadOnlyPositionable getScreenFormat()
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
	
	private void calculateSizeOfNodes()
	{
		try
		{
			findSizeOfNodes();
		}
		catch(ViewFormatterException e)
		{
			// If a ViewFormatter is caught, it is because a viewObjectID did not
			// have a corresponding Node. The program should not run if this is the case
			System.exit(0);
		}
	}
	
	private void findSizeOfNodes() throws ViewFormatterException
	{
		Pane root = new Pane();
		for(ViewObject viewObject : viewObjects.values())
		{
			if(viewObject.getNode() == null)
			{
				throw new ViewFormatterException(viewObject);
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
	
	private Pane createPaneWithViewObjects(double width, double height)
	{
		
		Pane root = new Pane();
		root.setMinSize(width, height);
		setSizeProperties(width,height);
		
		addViewObjectsToView(root);
		
		return root;
	}
	
}
