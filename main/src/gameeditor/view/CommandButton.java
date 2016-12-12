package gameeditor.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import gameeditor.view.interfaces.ICommandButton;
import gameeditor.view.interfaces.ICommandButtonOut;
import gameeditor.view.interfaces.ICommandDetailDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import gameeditor.view.interfaces.*;

public class CommandButton implements ICommandButton {

	// TODO: Remove hardcoding of the following values
	private static final String IMAGE_FILE_LOCATION = "images/Commands";
	private static final double BUTTON_HEIGHT = 50;
	private static final double BUTTON_WIDTH = 50;
	private static final double BUTTON_PADDING = 10;
	private static final double BUTTON_IMAGE_PADDING = 5;
	private static final double CORNER_RADIUS = ViewResources.BUTTON_RADIUS.getDoubleResource();
	private static final Color BG_COLOUR = ViewResources.BUTTON_BG_COLOUR.getColorResource();
	private static final Color OFF_COLOUR = ViewResources.BORDER_OFF_COLOUR.getColorResource();
	private static final Color ON_COLOUR = ViewResources.BORDER_ON_COLOUR.getColorResource();
	
	private ICommandButtonOut myCommandOut;
	private ICommandDetailDisplay myDetailDisplay;
	private ImageView myImageView;
	private Rectangle myBorder;
	private Rectangle myBG;
	private Image myButtonImage;
	private String myType;
	
	public CommandButton(String fileLocation, double buttonNumber, double paneWidth,
						 ICommandButtonOut commandOut, ICommandDetailDisplay detailDisplay) {
		int myTypeEnd = fileLocation.length() - 4;
		myType = fileLocation.substring(1, myTypeEnd);
		myCommandOut = commandOut;
		myDetailDisplay = detailDisplay;
		double xOffset = (paneWidth - BUTTON_WIDTH) / 2;
		double yOffset = (buttonNumber + 1) * BUTTON_PADDING + buttonNumber * BUTTON_HEIGHT;
		createBorder(xOffset, yOffset);
		try {
			myButtonImage = new Image(new FileInputStream(IMAGE_FILE_LOCATION + fileLocation));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		double imageX = xOffset + BUTTON_IMAGE_PADDING;
		double imageY = yOffset + BUTTON_IMAGE_PADDING;
		createBG(imageX, imageY);
		createImageView(imageX, imageY);
	}
	
	private void createBorder(double x, double y){
		myBorder = new Rectangle(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
		myBorder.setFill(OFF_COLOUR);
		myBorder.setArcHeight(CORNER_RADIUS);
		myBorder.setArcWidth(CORNER_RADIUS);
		myBorder.setOnMouseClicked(e -> handleClick());
	}
	
	private void createBG(double x, double y){
		myBG = new Rectangle(x, y, BUTTON_WIDTH - 2 * BUTTON_IMAGE_PADDING,
				BUTTON_HEIGHT - 2 * BUTTON_IMAGE_PADDING);
		myBG.setFill(BG_COLOUR);
		myBG.setArcHeight(CORNER_RADIUS);
		myBG.setArcWidth(CORNER_RADIUS);
		myBG.setOnMouseClicked(e -> handleClick());
	}
	
	private void createImageView(double x, double y){
		double fitWidth = myBG.getWidth() - 2 * BUTTON_IMAGE_PADDING;
		double fitHeight = myBG.getHeight() - 2 * BUTTON_IMAGE_PADDING;
		double widthRatio = fitWidth/myButtonImage.getWidth();
		double heightRatio = fitHeight/myButtonImage.getHeight();
		double ratio = Math.min(widthRatio, heightRatio);
        double endWidth = myButtonImage.getWidth() * ratio;
        double endHeight = myButtonImage.getHeight() * ratio;
		myImageView = new ImageView(myButtonImage);
		myImageView.setPreserveRatio(true);
		myImageView.setFitHeight(fitWidth);
		myImageView.setFitWidth(fitHeight);
		myImageView.setLayoutX(x + myBG.getWidth()/2 - endWidth/2);
		myImageView.setLayoutY(y + myBG.getHeight()/2 - endHeight/2);
		myImageView.setOnMouseClicked(e -> handleClick());
	}
	
	public ImageView getImageView(){
		return myImageView;
	}
	
	public Rectangle getBorder(){
		return myBorder;
	}
	
	public Rectangle getBG(){
		return myBG;
	}
	
	public void handleClick(){
		highlight();
		myDetailDisplay.setDetail(myType);
	}
	
	public void highlight(){
		myCommandOut.lowlightButtons();
		myBorder.setFill(ON_COLOUR);
	}
	
	public void lowlight(){
		myBorder.setFill(OFF_COLOUR);
	}

}
