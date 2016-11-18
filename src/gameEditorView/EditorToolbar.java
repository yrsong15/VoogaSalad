package gameEditorView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.concurrent.Callable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class EditorToolbar implements IEditorToolbar {

	// TODO: Remove hardcoding of the following values
	// Min Width, Max Width, Min Height
	
	private IToolbarParent myOutput;
	
	private Pane myPane;
	
	private ImageView myBackgroundImageView;
	private ImageView myAvatarImageView;
	private ImageView myMusicImageView;
	
	public EditorToolbar(IToolbarParent toolOut) {
		myOutput = toolOut;
		myPane = new Pane();
		myPane.setMinWidth(TOOLBAR_WIDTH); myPane.setMaxWidth(TOOLBAR_WIDTH);
		myPane.setMinHeight(TOOLBAR_HEIGHT); myPane.setMaxHeight(TOOLBAR_HEIGHT);
		myPane.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		createButton(myBackgroundImageView, "/Background.png", BG_IMAGE_WIDTH, BG_IMAGE_XOFFSET, e -> myOutput.setBackground());
		createButton(myAvatarImageView, "/Avatar.png", AVATAR_IMAGE_WIDTH, AVATAR_IMAGE_XOFFSET, e -> myOutput.setAvatar());
		createButton(myMusicImageView, "/Music.png", MUSIC_IMAGE_WIDTH, MUSIC_IMAGE_XOFFSET, e -> myOutput.setBackground());
	}
	
	
	private void createButton(ImageView myImageView, String fileLocation, 
		double imageWidth, double imageXOffset, EventHandler<MouseEvent> handler){
		Image buttonImage;
		try {
			buttonImage = new Image(new FileInputStream(IMAGE_FILE_LOCATION + fileLocation));
			myImageView = new ImageView(buttonImage);
			myImageView.setFitHeight(BUTTON_IMAGE_HEIGHT);
			myImageView.setFitWidth(imageWidth);
			myImageView.setLayoutX(imageXOffset);
			myImageView.setLayoutY(BUTTON_IMAGE_YOFFSET);
			myImageView.setOnMouseClicked(handler);
			myPane.getChildren().add(myImageView);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Pane getPane(){
		return myPane;
	}
}
