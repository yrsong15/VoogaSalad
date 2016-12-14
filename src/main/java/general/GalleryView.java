package general;

import java.util.ArrayList;

import frontend.util.ButtonTemplate;
import general.interfaces.IGalleryView;
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

public class GalleryView implements IGalleryView{
    public static final int GALLERY_CORNER_X = 60;
    public static final int GALLERY_CORNER_Y = 325;
//    private Scene scene;
    private Gallery gallery;
    private Pane galleryWindow;
    private MainController myMainController;
    private ScrollPane gameFileWindow;
    private NodeFactory myFactory;
    private Rectangle backdrop;
    private ArrayList<GameFileView> mySelectedFiles;


    public GalleryView(Gallery gallery, MainController MC, Pane startwindow) {
        this.myMainController = MC;
        this.gallery = gallery;
        this.mySelectedFiles = new ArrayList<GameFileView>();
        this.myFactory = new NodeFactory();
        galleryWindow = startwindow;
//        galleryWindow.setPrefSize(GALLERY_WIDTH, GALLERY_HEIGHT);
//        addGalleryBackgroundImage();
        addGalleryBackdrop();
        addGalleryButtons();
//        scene = new Scene(galleryWindow);
//        scene.getStylesheets().add(MainController.STYLESHEET);

        addGameFileViews();
//        setUpWindow();
//        configureEventListeners();
    }

//    @Override
//    public Scene getScene() {
//        return scene;
//    }

//    private void configureEventListeners() {
////        scene.addEventHandler(GameFileViewEvent.REMOVE_FROM_GALLERY, e -> removeGameFile());
////        scene.addEventHandler(GameFileViewEvent.VIEW_CLICKED_ON, e -> gameFileViewClicked(e.getGameFileView()));
//    }

    private void gameFileViewClicked(GameFileView gameFileView) {
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

//    private void setUpWindow() {
////        galleryWindow = new Pane();
////        galleryWindow.setPrefSize(GALLERY_WIDTH, GALLERY_HEIGHT);
////        addGalleryBackgroundImage();
////        addGalleryBackdrop();
////        addGalleryButtons();
////        scene = new Scene(galleryWindow);
////        scene.getStylesheets().add(MainController.STYLESHEET);
////
////        addGameFileViews();
//    }

    private GameFileView createGameFileView(GameFile gameFile)
    {
        GameFileView gameFileView = new GameFileView(gameFile);
        gameFileView.addEventHandlerToGameView(MouseEvent.MOUSE_ENTERED, e -> gameFileView.highlight());
        gameFileView.addEventHandlerToGameView(MouseEvent.MOUSE_EXITED, e -> gameFileView.dehighlight());
        return gameFileView;

    }
    private void addGameFileViews() {
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

        setUpGameFileWindow(gameFileBox);
        galleryWindow.getChildren().add(gameFileWindow);
    }

    private void setUpGameFileWindow(HBox gameFileBox){
        gameFileWindow = new ScrollPane();
        gameFileWindow.setContent(gameFileBox);
        gameFileWindow.setPrefViewportHeight(SCROLL_WINDOW_HEIGHT);
        gameFileWindow.setPrefViewportWidth(SCROLL_WINDOW_WIDTH);
        gameFileWindow.setTranslateX(GALLERY_CORNER_X + 30);
        gameFileWindow.setTranslateY(GALLERY_CORNER_Y + 30);
        gameFileWindow.setOpacity(0.5);
        gameFileWindow.setOnMouseEntered(e -> {
            gameFileWindow.setOpacity(0.8);
            backdrop.setOpacity(0.8);
        });
        gameFileWindow.setOnMouseExited(e -> gameFileWindow.setOpacity(0.5));
    }

//    private void removeGameFile() {
//        //gallery.removeFromGallery(gameName);
//        updateView();
//    }
//
//    private void updateView() {
//        // This method reconfigures the GalleryView so that it accurately presents all files in the gallery
//    }

    private void addGalleryBackdrop() {
        backdrop = myFactory.makeBackdrop(GALLERY_CORNER_X, GALLERY_CORNER_Y - 15, 890, 260, Color.MIDNIGHTBLUE);
        Text label = myFactory.makeLabel("To edit or load an existing game, select from the gallery",
                GALLERY_CORNER_X + 10, GALLERY_CORNER_Y + 15, 20);
        label.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        galleryWindow.getChildren().addAll(backdrop, label);
    }

    private void addGalleryButtons() {
        ButtonTemplate newB = new ButtonTemplate("GalleryGameEdit", 400, 400);
        Button edit = newB.getButton();
        edit.translateYProperty().bind(galleryWindow.heightProperty().subtract(120));
        edit.translateXProperty().bind(galleryWindow.widthProperty().divide(2).subtract(300));
        edit.setOnMouseClicked(e -> myMainController.editGame());
        edit.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        newB = new ButtonTemplate("GalleryGameEngine", 600, 400);
        Button engine = newB.getButton();
        engine.translateYProperty().bind(galleryWindow.heightProperty().subtract(120));
        engine.translateXProperty().bind(galleryWindow.widthProperty().divide(2).add(100));
        engine.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        //TODO: Change this later to be flexible
  
        engine.setOnMouseClicked(e -> launchSelectedFiles());
        galleryWindow.getChildren().addAll(edit, engine);
    }

    private void launchSelectedFiles(){
    	for(GameFileView gameFileView : mySelectedFiles) {
    		myMainController.launchEngine(gameFileView.getGameFile().getGameData());
    	}
    }
}
