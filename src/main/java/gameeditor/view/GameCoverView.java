package gameeditor.view;


import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import side.Side;
import viewformatter.ViewFormatter;

public class GameCoverView 
{
	private ImageView gameCover;
	private Pane root;
	private double width;
	private double height;
	
	public GameCoverView(double width, double height)
	{
		
		this.width = width;
		this.height = height;
		this.gameCover = createGameCoverImage();//createGameCoverImage();//new ImageView();
		this.root = renderView();
	}
	
	
	public Pane renderView()
	{
		ViewFormatter formatter = new ViewFormatter();
		
		formatter.addView(gameCover, "Game Cover")
				.positionWithPercentGapOfScreen(Side.BOTTOM, "Label", 0.075)
				.setHeightAsFractionOfScreen(.6)
				.setWidthAsFractionOfScreen(.5)
				.centerXBasedOnWidthOf("Label");
				//.centerXInScreen();
		
		Label gameCoverLabel = new Label("Game Cover:");
		gameCoverLabel.setFont(new Font(20));
		formatter.addView(gameCoverLabel, "Label")
				.centerXInScreen()
				.setZ(2);
		
		Button loadButton = new Button("Load Cover");
		formatter.addView(loadButton, "Load Button")
			.positionWithPercentGapOfScreen(Side.BOTTOM, "Game Cover", 0.075)
			.centerXInScreen();
			//.setX(10);
			
		
		/*
		Rectangle background = generateRectangle(width,height);
		formatter.addView(background, "Background", width, height)
				.setZ(-2); */
		
		return formatter.renderView(width,height);
	}
	
	public Node getNode()
	{
		return root;
	}
	
	public Image getGameCoverImage()
	{
		return gameCover.getImage();
	}

	private Rectangle generateRectangle(double width, double height)
	{
		Rectangle rect = new Rectangle(width,height);
		rect.setFill(Color.GREEN);
		return rect;
	}
	
	private ImageView createGameCoverImage()
	{
		String userDirectoryString = "file:" + System.getProperty("user.dir") + "/images/buttons/gameLevelIcon.png";
		ImageView gameCoverView = new ImageView(new Image(userDirectoryString));
		return gameCoverView;
	}
	
}
