package gameEditorView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class EditorToolbar implements IEditorToolbar {

	// TODO: Remove hardcoding of the following values
	// Min Width, Max Width, Min Height
	
	private IToolbarOutput myOutput;
	
	private Pane myPane;
	
	private ImageView myBackgroundImageView;
	private ImageView myAvatarImageView;
	
	private double myButtonSpacing = 25;

	public EditorToolbar(IToolbarOutput toolOut) {
		myOutput = toolOut;
		myPane = new Pane();
		myPane.setMinWidth(TOOLBAR_WIDTH); myPane.setMaxWidth(TOOLBAR_WIDTH);
		myPane.setMinHeight(TOOLBAR_HEIGHT); myPane.setMaxHeight(TOOLBAR_HEIGHT);
		myPane.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		createBGButton();
		createAvatarButton();
	}
	
	private void createBGButton(){
		Image myBackgroundImage;
		try {
			myBackgroundImage = new Image(new FileInputStream(IMAGE_FILE_LOCATION + "/Background.png"));
			myBackgroundImageView = new ImageView(myBackgroundImage);
			myBackgroundImageView.setFitHeight(BUTTON_IMAGE_HEIGHT);
			myBackgroundImageView.setFitWidth(BG_IMAGE_WIDTH);
			myBackgroundImageView.setLayoutX(BG_IMAGE_XOFFSET);
			myBackgroundImageView.setLayoutY(BUTTON_IMAGE_YOFFSET);
			myBackgroundImageView.setOnMouseClicked(e -> myOutput.setBackground());
			myPane.getChildren().add(myBackgroundImageView);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createAvatarButton(){
		Image myAvatarImage;
		try {
			myAvatarImage = new Image(new FileInputStream(IMAGE_FILE_LOCATION + "/Avatar.png"));
			myAvatarImageView = new ImageView(myAvatarImage);
			myAvatarImageView.setFitHeight(BUTTON_IMAGE_HEIGHT);
			myAvatarImageView.setFitWidth(AVATAR_IMAGE_WIDTH);
			myAvatarImageView.setLayoutX(AVATAR_IMAGE_XOFFSET);
			myAvatarImageView.setLayoutY(BUTTON_IMAGE_YOFFSET);
			myAvatarImageView.setOnMouseClicked(e -> myOutput.setAvatar());
			myPane.getChildren().add(myAvatarImageView);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Pane getPane(){
		return myPane;
	}
}
