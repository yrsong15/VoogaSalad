package general;

import buttons.ButtonTemplate;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GalleryView 
{
	private static final int GALLERY_WIDTH = 1200;
	private static final int GALLERY_HEIGHT = 600;
	private Scene scene;
	private Gallery gallery;
	private Pane galleryWindow;
	private String gameName;
	private MainController myMainController;
	
	public GalleryView(Gallery gallery, MainController MC)
	{
		this.myMainController = MC;
		this.gallery = gallery;
		setUpWindow();
		configureEventListeners();
	}
	
	public Scene getScene()
	{
		return scene;
	}
	
	private void configureEventListeners()
	{
		scene.addEventHandler(GameFileEvent.REMOVE_FROM_GALLERY, e -> removeGameFile());
	}

	private void setUpWindow(){
		galleryWindow = new Pane();
		galleryWindow.setPrefSize(GALLERY_WIDTH, GALLERY_HEIGHT);
		addGalleryBackgroundImage();
		addGalleryBackdrop();
		addGalleryButtons();
		scene = new Scene(galleryWindow);
		scene.getStylesheets().add(MainController.STYLESHEET);

	}
	
	private void removeGameFile()
	{
		gallery.removeFromGallery(gameName);
		updateView();
	}
	
	private void updateView()
	{
		// This method reconfigures the GalleryView so that it accurately presents all files in the gallery
	}

	private void addGalleryBackgroundImage(){
		String userDirectoryString = "file:"
				+ System.getProperty("user.dir")
				+ "/images/Background/spinningScreens.jpg";
		Image background = new Image(userDirectoryString);
		System.out.println(userDirectoryString);
		ImageView backgroundImageGalleryScreen = new ImageView(background);
		backgroundImageGalleryScreen.setTranslateY(0);
		backgroundImageGalleryScreen.setTranslateX(0);
		backgroundImageGalleryScreen.fitWidthProperty().bind(galleryWindow.widthProperty());
		backgroundImageGalleryScreen.fitHeightProperty().bind(galleryWindow.heightProperty());
		galleryWindow.getChildren().add(backgroundImageGalleryScreen);
	}

	private void addGalleryBackdrop(){
		Rectangle backdrop = new Rectangle(1000, 200, Color.MIDNIGHTBLUE);
		backdrop.setTranslateX(100);
		backdrop.setTranslateY(100);
		backdrop.opacityProperty().setValue(0.5);
		backdrop.heightProperty().bind(galleryWindow.heightProperty().subtract(400));
		backdrop.widthProperty().bind(galleryWindow.widthProperty().subtract(200));

		Text label = new Text("Gallery");
		label.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		label.setFill(Color.LIGHTBLUE);
		label.setTranslateX(110);
		label.setTranslateY(115);
		galleryWindow.getChildren().addAll(backdrop, label);
	}

	private void addGalleryButtons(){
		ButtonTemplate newB = new ButtonTemplate("GalleryGameEdit");
		Button edit = newB.getButton();
		edit.setTranslateX(400);
		edit.setTranslateY(400);
		edit.translateYProperty().bind(galleryWindow.heightProperty().subtract(200));
		edit.translateXProperty().bind(galleryWindow.widthProperty().divide(2).subtract(300));

		newB = new ButtonTemplate("GalleryGameEngine");
		Button engine = newB.getButton();
		engine.setTranslateX(600);
		engine.setTranslateY(400);
		engine.translateYProperty().bind(galleryWindow.heightProperty().subtract(200));
		engine.translateXProperty().bind(galleryWindow.widthProperty().divide(2).add(100));
		//TODO: Change this later to be flexible
		engine.setOnMouseClicked(e -> myMainController.launchEngine(gallery.getGalleryItem(gameName)));

		galleryWindow.getChildren().addAll(edit, engine);
	}
}
