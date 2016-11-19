package general;

import frontend.util.ButtonTemplate;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
//<<<<<<< HEAD
//=======
	private static final double SCROLL_WINDOW_HEIGHT = 100;
	private static final double SCROLL_WINDOW_WIDTH = .4 * GALLERY_WIDTH;
//>>>>>>> b45f95ebac813644570b87065dfe19f10ae9ec85
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

		addGameFileViews();
	}
	
	private void addGameFileViews()
	{
		ScrollPane gameFileWindow = new ScrollPane();
		HBox gameFileBox = new HBox();
		gameFileBox.setSpacing(10);
		for(GameFile gameFile : gallery.getUnmodifiableListOfGameFiles())
//		for(String gameFile : gallery.getUnmodifiableListOfGameFiles())
		{
			gameFileBox.getChildren().add(new GameFileView(gameFile).getNode());	
		}
		gameFileWindow.setContent(gameFileBox);
		gameFileWindow.setPrefViewportHeight(SCROLL_WINDOW_HEIGHT);
		gameFileWindow.setPrefViewportWidth(SCROLL_WINDOW_WIDTH);
		gameFileWindow.prefViewportHeightProperty().bind(galleryWindow.heightProperty().subtract(470));
		gameFileWindow.prefViewportWidthProperty().bind(galleryWindow.widthProperty().subtract(250));
		gameFileWindow.setTranslateX(110);
		gameFileWindow.setTranslateY(130);
		gameFileWindow.opacityProperty().setValue(0.5);
		gameFileWindow.setOnMouseEntered(e -> gameFileWindow.opacityProperty().setValue(0.8));
		gameFileWindow.setOnMouseExited(e -> gameFileWindow.opacityProperty().setValue(0.5));
		/*
		Code for if you implement a Grid
		
		GridPane gameFileGrid = new GridPane();
		int gameFilesPerRow = 4;
		int columnsPerRow = gameFilesPerRow * 2;
		int loc = 0;
		gameFileWindow.setContent(gameFileGrid);
		gameFileWindow.setPrefViewportHeight(SCROLL_WINDOW_HEIGHT);
		gameFileWindow.setPrefViewportWidth(SCROLL_WINDOW_WIDTH);
		
		for(GameFile gameFile : gallery.getUnmodifiableListOfGameFiles())
		{
			gameFileGrid.add(new GameFileView(gameFile).getNode(), loc % columnsPerRow, loc / columnsPerRow);
			//System.out.println("Game File added to: " + loc / columnsPerRow + ", " + loc % columnsPerRow);
			loc += 2;
		}*/
		galleryWindow.getChildren().add(gameFileWindow);
//>>>>>>> b45f95ebac813644570b87065dfe19f10ae9ec85
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
		edit.setOnMouseClicked(e -> myMainController.presentEditor()); //pass in an XML to the editor eventually

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
