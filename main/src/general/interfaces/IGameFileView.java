package general.interfaces;

import general.GameFile;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;

/**
 * Created by Delia on 11/30/2016.
 */
public interface IGameFileView {

    void fireDeleteEvent();

    /**
     * Adds an event handler directly to the GameView and not the entire node. This allows
     * the manipulation of a GameView's behavior from outside classes, allowing GameViews to be
     * used in other settings that the GalleryView.
     * @param eventType- type of event being added to the gameView
     * @param eventHandler
     */
    void addEventHandlerToGameView(EventType<? extends Event> eventType, EventHandler<Event> eventHandler);

    void highlight();

    void dehighlight();

    void deselect();

    void select();

    GameFile getGameFile();

    Node getNode();
}
