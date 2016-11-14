package general;

import javafx.event.Event;
import javafx.event.EventType;

public class GameFileEvent extends Event
{
	private GameFile gameFile;
	
	/**
	 * This is triggered when a game file wants to be removed from the gallery
	 */
	public static final EventType<GameFileEvent> REMOVE_FROM_GALLERY = new EventType<>("REMOVE_FROM_GALLERY");
	
	/**
	 * Signals when a game file wants to be copied in the gallery
	 */
	public static final EventType<GameFileEvent> COPY = new EventType<>("COPY");

	
	public GameFileEvent(EventType<? extends Event> eventType, GameFile gameFile) 
	{
		super(eventType);
		this.gameFile = gameFile;
	}
	
	public GameFile getGameFile()
	{
		return gameFile;
	}
	
}
