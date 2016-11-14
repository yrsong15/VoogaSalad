package general;

import javafx.scene.Node;

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
}
