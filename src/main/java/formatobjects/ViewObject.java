package formatobjects;

import javafx.scene.Node;
import javafx.scene.layout.Region;

public class ViewObject extends FormatObject
{
	private String viewObjectID;
	private Node node;
	
	
	public ViewObject(String viewObjectID)
	{
		super();
		this.viewObjectID = viewObjectID;
	}
	
	public ViewObject(Node node, String viewObjectID)
	{
		this(viewObjectID);
		connectNode(node);
	}
	
	/**
	 * THIS METHOD IS CURRENTLY UNUSED
	 * @return
	 */
	public String getViewObjectID()
	{
		return viewObjectID;
	}
	
	public void connectNode(Node node)
	{
		this.node = node;
	}
	
	public Node renderNode()
	{
		updateWidth();
		updateHeight();
		updateX();
		updateY();
		updateSize();
		
		return getNode();
	}
	public Node getNode()
	{
		return node;
	}
	
	private void updateSize()
	{
		if(node instanceof Region)
		{
			((Region)node).setPrefSize(getWidth().getValue(), getHeight().getValue());
		}
	}
	
	private void updateWidth()
	{
		if(getWidth().hasValidValue())
		{
			node.minWidth(getWidth().getValue());
			node.prefWidth(getWidth().getValue());
			node.maxWidth(getWidth().getValue());
		}
	}
	
	private void updateHeight()
	{
		if(getHeight().hasValidValue())
		{
			node.minHeight(getHeight().getValue());
			node.prefHeight(getHeight().getValue());
			node.maxHeight(getHeight().getValue());
		}
	}
	
	private void updateX()
	{
		if(getX().hasValidValue())
		{
			node.setLayoutX(getX().getValue());
		}
	}
	
	private void updateY()
	{
		if(getY().hasValidValue())
		{
			node.setLayoutY(getY().getValue());
		}
	}

}
