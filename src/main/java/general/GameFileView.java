package general;

import gameeditor.view.GameCoverView;
import general.interfaces.IGameFileView;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import side.Side;
import viewformatter.ViewFormatter;
import javafx.event.EventType;
import javafx.geometry.Pos;

/**
 * @author Ryan Bergamini
 */


public class GameFileView implements IGameFileView
{
	private GameFile gameFile;
	private Pane view;
	private Rectangle gameView;
	private Paint gameViewColor;
	private boolean isSelected;
	private NodeFactory myFactory = new NodeFactory();

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

	@Override
	public void fireDeleteEvent()
	{
		view.fireEvent(new GameFileViewEvent(GameFileViewEvent.REMOVE_FROM_GALLERY, this));
	}

	@Override
	public void addEventHandlerToGameView(EventType<? extends Event> eventType, EventHandler<Event> eventHandler)
	{
		gameView.addEventHandler(eventType, eventHandler);
	}

	@Override
	public void highlight()
	{
		gameView.setFill(Color.LIGHTBLUE);
	}

	@Override
	public void dehighlight()
	{
		if(!isSelected)
		{
			gameView.setFill(gameViewColor);
		}
	}

	@Override
	public void deselect() 
	{
		isSelected = false;
		dehighlight();
	}

	@Override
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
		ViewFormatter formatter = new ViewFormatter();

		Label name = createGameTitle();
		formatter.addView(name, "Name")
			.centerXInScreen();
		
		ImageView gameCover = new ImageView(gameFile.getGameCoverImage());
		formatter.addView(gameCover, "Game Cover")
			.position(Side.BOTTOM, "Name", 10)
			.centerXInScreen()
			.setHeightAsFractionOfScreen(.5)
			.setWidth(GameCoverView.DEFAULT_IMAGE_WIDTH);
		
		double rectWidth = name.getText().length() * (.8 * name.getFont().getSize());
		Rectangle rect = generateBackground(rectWidth,85);
		formatter.addView(rect, "Background",rectWidth, 85)
			.setZ(-1);
			
		
		gameViewColor = rect.getFill();
		gameView = rect;
		Tooltip Trect = myFactory.makeTooltip("GalleryItem");
		Tooltip.install(rect, Trect);
		
		return formatter.renderView(rectWidth, 85);
	}
	
	private Label createGameTitle()
	{
		Label name = new Label(gameFile.getGameName());
		name.setAlignment(Pos.CENTER);
		name.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		return name;
	}
	private Rectangle generateBackground(double width, double height)
	{

		Rectangle rect = new Rectangle(width,height);
		int randVal = (int)(Math.random() * 3);
		if(randVal == 0)
		{
			rect.setFill(Color.MEDIUMPURPLE);
		}
		else if(randVal == 1)
		{
			rect.setFill(Color.DEEPPINK);
		}
		else
		{
			rect.setFill(Color.BLUEVIOLET);
		}
		
		return rect;
	}

	@Override
	public GameFile getGameFile()
	{
		return gameFile;
	}

	@Override
	public Node getNode()
	{
		return view;
	}
}