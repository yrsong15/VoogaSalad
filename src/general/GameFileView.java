package general;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
<<<<<<< HEAD
import javafx.scene.control.Label;
=======
>>>>>>> fbcd326b91464825a7efbe558416898f01db3a01
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
<<<<<<< HEAD
import javafx.scene.text.Text;
=======
>>>>>>> fbcd326b91464825a7efbe558416898f01db3a01
import javafx.scene.input.InputEvent;
import javafx.event.EventType;

public class GameFileView
{
	private GameFile gameFile;
	private Pane view;
	private Rectangle gameView;
	private Paint gameViewColor;
	private boolean isSelected;
<<<<<<< HEAD

=======
	
>>>>>>> fbcd326b91464825a7efbe558416898f01db3a01
	public GameFileView(GameFile gameFile)
	{
		this.gameFile = gameFile;
		this.view = createView();
		this.isSelected = false;
		configureEventHandlers();
<<<<<<< HEAD
=======
	}
	
	/**
	 * Regardless of purpose for the GameFileView, a GameFileViewEvent.VIEW_CLICKED_ON event
	 * should always be fired whenever the view is clicked on
	 */
	private void configureEventHandlers()
	{
		gameView.setOnMouseClicked(e -> fireViewClickedOnEvent());
>>>>>>> fbcd326b91464825a7efbe558416898f01db3a01
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
<<<<<<< HEAD

=======
	
>>>>>>> fbcd326b91464825a7efbe558416898f01db3a01
	/**
	 * Adds an event handler directly to the GameView and not the entire node. This allows
	 * the manipulation of a GameView's behavior from outside classes, allowing GameViews to be
	 * used in other settings that the GalleryView.
	 * @param eventType- type of event being added to the gameView
	 * @param eventHandler
	 */
	public void addEventHandlerToGameView(EventType<? extends Event> eventType, EventHandler<Event> eventHandler)
<<<<<<< HEAD
	{
		gameView.addEventHandler(eventType, eventHandler);
	}

	public void highlight()
	{
		gameView.setFill(Color.YELLOW);
	}

=======
	{
		gameView.addEventHandler(eventType, eventHandler);
	}
	
	public void highlight()
	{
		gameView.setFill(Color.YELLOW);
	}
	
>>>>>>> fbcd326b91464825a7efbe558416898f01db3a01
	public void dehighlight()
	{
		if(!isSelected)
		{
			gameView.setFill(gameViewColor);
		}
	}
<<<<<<< HEAD

	public void deselect()
	{

		isSelected = false;
		dehighlight();
	}

=======
	
	public void deselect()
	{
		
		isSelected = false;
		dehighlight();
	}
	
>>>>>>> fbcd326b91464825a7efbe558416898f01db3a01
	public void select()
	{
		isSelected = true;
		highlight();
	}
<<<<<<< HEAD

=======
	
>>>>>>> fbcd326b91464825a7efbe558416898f01db3a01
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
<<<<<<< HEAD
		Label name = new Label(gameFile.getGameName());
		view.getChildren().add(name);
		return view;
	}
	
	public GameFile getGameFile()
	{
		return gameFile;
	}

=======
		return view;
	}
	
>>>>>>> fbcd326b91464825a7efbe558416898f01db3a01
	public Node getNode()
	{
		return view;
	}
}