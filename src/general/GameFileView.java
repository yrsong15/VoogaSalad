package general;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameFileView 
{
	private GameFile gameFile;
	private Node view;
	
	public GameFileView(GameFile gameFile)
	{
		this.gameFile = gameFile;
	}
	
	public void fireDeleteEvent()
	{
		view.fireEvent(new GameFileEvent(GameFileEvent.REMOVE_FROM_GALLERY,gameFile));
	}
	
	public Node getNode()
	{
		Rectangle rect = new Rectangle(100,100);
		int randVal = (int)(Math.random()*3);
		if(randVal == 0)
		{
			rect.setFill(Color.GREEN);
		}
		else if(randVal == 1)
		{
			rect.setFill(Color.RED);
		}
		else
		{
			rect.setFill(Color.YELLOW);
		}
		return rect;
	}
}
