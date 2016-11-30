package general;

import java.util.ArrayList;

import frontend.util.ButtonTemplate;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GalleryView {
    private static final int GALLERY_WIDTH = 1200;
    private static final int GALLERY_HEIGHT = 600;
    private static final double SCROLL_WINDOW_HEIGHT = 100;
    private static final double SCROLL_WINDOW_WIDTH = .4 * GALLERY_WIDTH;
    private Scene scene;
    private Gallery gallery;
    private Pane galleryWindow;
    private String gameName;
    private MainController myMainController;

    private ArrayList<GameFileView> mySelectedFiles;


    public GalleryView(Gallery gallery, MainController MC) {
        this.myMainController = MC;
        this.gallery = gallery;
        this.mySelectedFiles = new ArrayList<GameFileView>();
        setUpWindow();
        configureEventListeners();
    }

    public Scene getScene() {
        return scene;
    }

    private void configureEventListeners() {
//        scene.addEventHandler(GameFileViewEvent.REMOVE_FROM_GALLERY, e -> removeGameFile());
//        scene.addEventHandler(GameFileViewEvent.VIEW_CLICKED_ON, e -> gameFileViewClicked(e.getGameFileView()));
    }

    private void gameFileViewClicked(GameFileView gameFileView)
    {
        if(mySelectedFiles.contains(gameFileView))
        {
            mySelectedFiles.remove(gameFileView);
            gameFileView.deselect();
        }
        else
        {
            deselectAllSelectedFiles();
            mySelectedFiles.add(gameFileView);
            gameFileView.select();
        }
    }
    
    private void deselectAllSelectedFiles()
    {
    	for(GameFileView gameFileView : mySelectedFiles)
    	{
    		gameFileView.deselect();
    	}
    	mySelectedFiles.clear();
    }

    private void setUpWindow() {
        galleryWindow = new Pane();
        galleryWindow.setPrefSize(GALLERY_WIDTH, GALLERY_HEIGHT);
        addGalleryBackgroundImage();
        addGalleryBackdrop();
        addGalleryButtons();
        scene = new Scene(galleryWindow);
        scene.getStylesheets().add(MainController.STYLESHEET);

        addGameFileViews();
    }

    private GameFileView createGameFileView(GameFile gameFile)
    {
        GameFileView gameFileView = new GameFileView(gameFile);
        gameFileView.addEventHandlerToGameView(MouseEvent.MOUSE_ENTERED, e -> gameFileView.highlight());
        gameFileView.addEventHandlerToGameView(MouseEvent.MOUSE_EXITED, e -> gameFileView.dehighlight());
        return gameFileView;

    }
    private void addGameFileViews() {
        ScrollPane gameFileWindow = new ScrollPane();
        HBox gameFileBox = new HBox();
        gameFileBox.setSpacing(10);

        for (GameFile gameFile : gallery.getUnmodifiableListOfGameFiles())
//		for(String gameFile : gallery.getUnmodifiableListOfGameFiles())
        {
            GameFileView newGameFileView = createGameFileView(gameFile);
//            Node gameFileNode = createGameFileView(gameFile).getNode();
            Node gameFileNode = newGameFileView.getNode();
            gameFileNode.setOnMouseClicked(e -> gameFileViewClicked(newGameFileView));
            gameFileBox.getChildren().add(gameFileNode);
        }
        // TODO: Extract this into a configure window method
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
        galleryWindow.getChildren().add(gameFileWindow);
    }

    private void removeGameFile() {
        //gallery.removeFromGallery(gameName);
        updateView();
    }

    private void updateView() {
        // This method reconfigures the GalleryView so that it accurately presents all files in the gallery
    }

    private void addGalleryBackgroundImage() {
        String userDirectoryString = "file:"
                + System.getProperty("user.dir")
                + "/images/Background/spinningScreens.jpg";
        Image background = new Image(userDirectoryString);
        //System.out.println(userDirectoryString);
        ImageView backgroundImageGalleryScreen = new ImageView(background);
        backgroundImageGalleryScreen.setTranslateY(0);
        backgroundImageGalleryScreen.setTranslateX(0);
        backgroundImageGalleryScreen.fitWidthProperty().bind(galleryWindow.widthProperty());
        backgroundImageGalleryScreen.fitHeightProperty().bind(galleryWindow.heightProperty());
        galleryWindow.getChildren().add(backgroundImageGalleryScreen);
    }

    private void addGalleryBackdrop() {
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

    private void addGalleryButtons() {
        ButtonTemplate newB = new ButtonTemplate("GalleryGameEdit", 400, 400);
        Button edit = newB.getButton();
        edit.translateYProperty().bind(galleryWindow.heightProperty().subtract(200));
        edit.translateXProperty().bind(galleryWindow.widthProperty().divide(2).subtract(300));
        edit.setOnMouseClicked(e -> myMainController.presentEditor()); //pass in an XML to the editor eventually

        newB = new ButtonTemplate("GalleryGameEngine", 600, 400);
        Button engine = newB.getButton();
        engine.translateYProperty().bind(galleryWindow.heightProperty().subtract(200));
        engine.translateXProperty().bind(galleryWindow.widthProperty().divide(2).add(100));
        //TODO: Change this later to be flexible
  
        engine.setOnMouseClicked(e -> launchSelectedFiles());

        galleryWindow.getChildren().addAll(edit, engine);
    }
    
    private void launchSelectedFiles()
    {
        System.out.println("Launch selected files");
    	for(GameFileView gameFileView : mySelectedFiles)
    	{
    		myMainController.launchEngine(gameFileView.getGameFile().getGameData());
    	}
    }
}
