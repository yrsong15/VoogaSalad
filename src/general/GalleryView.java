package general;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class GalleryView 
{
	private static final int GALLERY_WIDTH = 1200;
	private static final int GALLERY_HEIGHT = 600;
	private Gallery gallery;
	private Pane galleryWindow;
	private Scene scene;
	
	public GalleryView(Gallery gallery)
	{
		this.gallery = gallery;
		setUpWindow();
		configureEventListeners();
	}
	
	private void configureEventListeners()
	{
		scene.addEventHandler(GameFileEvent.REMOVE_FROM_GALLERY, e -> removeGameFile(e));
	}

	private void setUpWindow(){
		galleryWindow = new Pane();
		galleryWindow.setPrefSize(GALLERY_WIDTH, GALLERY_HEIGHT);
		String userDirectoryString = "file:" +  System.getProperty("user.dir")+ "/images/Background/bg.png";
//			String userDirectoryString = myFileOpener.chooseFile(IMAGE_FILE_TYPE, BG_IMAGE_LOCATION).toURI().toURL().toString();//"file:" +  System.getProperty("user.dir")+ "/images/Background/floatingCubes.jpg";
		Image background = new Image(userDirectoryString);
//		Image background = new Image(getClass().getClassLoader()
//				.getResourceAsStream("images/background/bg.png"));
		ImageView backgroundImageMainScreen = new ImageView(background);
		backgroundImageMainScreen.fitWidthProperty().bind(galleryWindow.widthProperty());
		backgroundImageMainScreen.fitHeightProperty().bind(galleryWindow.heightProperty());
		galleryWindow.getChildren().add(backgroundImageMainScreen);
		scene = new Scene(galleryWindow);
	}
	
	private void removeGameFile(GameFileEvent gameFileEvent)
	{
		gallery.removeFromGallery(gameFileEvent.getGameFile());
		updateView();
	}
	
	private void updateView()
	{
		// This method reconfigures the GalleryView so that it accurately presents all files in the gallery
	}

	public Scene getScene()
	{
		return scene;
	}

	
}
