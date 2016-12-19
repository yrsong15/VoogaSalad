
package general.Gallery;

import javafx.event.Event;
import javafx.event.EventType;

public class GameFileViewEvent extends Event {

    private GameFileView gameFileView;

    /**
     * This is triggered when a game file wants to be removed from the gallery
     */
    public static final EventType<GameFileViewEvent> REMOVE_FROM_GALLERY = new EventType<>("REMOVE_FROM_GALLERY");

    /**
     * Signals when a game file wants to be copied in the gallery
     */
    public static final EventType<GameFileViewEvent> COPY = new EventType<>("COPY");

    /**
     * Fires when a File is selected in a gallery
     */
    public static final EventType<GameFileViewEvent> FILE_SELECTED = new EventType<>("FILE_SELECTED");

    /**
     * Fires when a File is deselected in a gallery
     */
    public static final EventType<GameFileViewEvent> FILE_DESELECTED = new EventType<>("FILE_DESELECTED");

    /**
     * Fires when a File is clicked one
     */
    public static final EventType<GameFileViewEvent> VIEW_CLICKED_ON = new EventType<>("VIEW_CLICKED_ON");

    /**
     * Creates a new GameFileViewEvent
     *
     * @param eventType-    Type of the event
     * @param gameFileView- the GameFileView that triggered the event (usually this)
     */
    public GameFileViewEvent(EventType<? extends Event> eventType, GameFileView gameFileView) {
        super(eventType);
        this.gameFileView = gameFileView;
    }

    /**
     * @return the GameFileView instance that triggered the event
     */
    public GameFileView getGameFileView() {
        return gameFileView;
    }

}
