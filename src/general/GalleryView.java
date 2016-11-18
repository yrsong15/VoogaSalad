package general;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GalleryView 
{
	private static final int GALLERY_WIDTH = 1200;
	private static final int GALLERY_HEIGHT = 600;
	private static final double SCROLL_WINDOW_HEIGHT = .8 * GALLERY_HEIGHT;
	private static final double SCROLL_WINDOW_WIDTH = .6 * GALLERY_WIDTH;
	private Gallery gallery;

	private Pane galleryWindow;
	private Scene scene;
	
	public GalleryView(Gallery gallery)
	{
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
		addGameFileViews();
	}
	
	private void addGameFileViews()
	{
		ScrollPane gameFileWindow = new ScrollPane();
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
		}
		galleryWindow.getChildren().add(gameFileWindow);
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
	
}
