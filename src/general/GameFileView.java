package general;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.InputEvent;
import javafx.event.EventType;

public class GameFileView
{
	private GameFile gameFile;
	private Pane view;
	private Rectangle gameView;
	private Paint gameViewColor;
	private boolean isSelected;

	public GameFileView(GameFile gameFile)
	{
		this.gameFile = gameFile;
		this.view = createView();
		this.isSelected = false;
		configureEventHandlers();
	}

	/**
	 * Regardless of purpose for the GameFileView, a GameFileViewEvent.VIEW_CLICKED_ON event
	 * should always be fired whenever the view is clicked on
	 */
	private void configureEventHandlers()
	{
		gameView.setOnMouseClicked(e -> fireViewClickedOnEvent());
	}

	public void fireDeleteEvent()
	{
		view.fireEvent(new GameFileViewEvent(GameFileViewEvent.REMOVE_FROM_GALLERY,this));
	}

	/**
	 * Adds an event handler directly to the GameView and not the entire node. This allows
	 * the manipulation of a GameView's behavior from outside classes, allowing GameViews to be
	 * used in other settings that the GalleryView.
	 * @param eventType- type of event being added to the gameView
	 * @param eventHandler
	 */
	public void addEventHandlerToGameView(EventType<? extends Event> eventType, EventHandler<Event> eventHandler)
	{
		gameView.addEventHandler(eventType, eventHandler);
	}

	public void highlight()
	{
		gameView.setFill(Color.YELLOW);
	}

	public void dehighlight()
	{
		if(!isSelected)
		{
			gameView.setFill(gameViewColor);
		}
	}

	public void deselect()
	{

		isSelected = false;
		dehighlight();
	}

	public void select()
	{
		isSelected = true;
		highlight();
	}

	private void fireViewClickedOnEvent()
	{
		gameView.fireEvent(new GameFileViewEvent(GameFileViewEvent.VIEW_CLICKED_ON, this));
	}
	private Pane createView()
	{
		Pane view = new Pane();
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
			rect.setFill(Color.BLUE);
		}
		gameViewColor = rect.getFill();
		gameView = rect;
		view.getChildren().add(gameView);
		return view;
	}

	public Node getNode()
	{
		return view;
	}
}