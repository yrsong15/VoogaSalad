package gameeditor.view;


import java.io.File;

import frontend.util.FileOpener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import viewformatter_util.side.Side;
import viewformatter_util.viewformatter.ViewFormatter;

/**
 * 
 * @author Ryan Bergamini
 */
public class GameCoverView 
{
	public static double DEFAULT_IMAGE_WIDTH = 30;
	public static double DEFAULT_IMAGE_HEGIHT = 45;
	
	private static final String BG_IMAGE_LOCATION = ViewResources.BG_FILE_LOCATION.getResource();
	private static final String IMAGE_FILE_TYPE = ViewResources.IMAGE_FILE_TYPE.getResource();
	
	private ImageView gameCover;
	private Pane root;
	private double width;
	private double height;
	
	/**
	 * Creates a new GameCoverView
	 * @param width- width of the view
	 * @param height- height of the view
	 */
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
				.setHeightAsFractionOfScreen(.75)
				.setWidthAsFractionOfScreen(.5)
				.centerXBasedOnWidthOf("Label");
		
		Label gameCoverLabel = new Label("Game Cover:");
		gameCoverLabel.setFont(new Font(20));
		formatter.addView(gameCoverLabel, "Label")
				.centerXInScreen()
				.setZ(2);
		
		Button loadButton = createLoadButton();
		formatter.addView(loadButton, "Load Button")
			.positionWithPercentGapOfScreen(Side.BOTTOM, "Game Cover", 0.075)
			.centerXInScreen();
		
		return formatter.renderView(width,height);
	}
	
	public Node getNode()
	{
		return root;
	}
	
	private Button createLoadButton()
	{
		Button button = new Button("Load Cover");
		button.setOnMouseClicked(e -> updateCoverImage());
		return button;
	}
	
	private void updateCoverImage()
	{
		String filePath = getFilePath(IMAGE_FILE_TYPE,BG_IMAGE_LOCATION);
		Image coverImage = new Image(filePath);
		gameCover.setImage(coverImage);
	}
	
	public Image getGameCoverImage()
	{
		return gameCover.getImage();
	}

	private String getFilePath(String fileType, String fileLocation){
        FileOpener myFileOpener = new FileOpener();
        File file = (myFileOpener.chooseFile(fileType, fileLocation));
        if(file != null){
            return file.toURI().toString();
        }
        return null;
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
