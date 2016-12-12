package general;
import frontend.util.ButtonTemplate;
import frontend.util.FileOpener;
import gameeditor.view.ViewResources;
import general.interfaces.ISplashScreen;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javafx.event.EventHandler;
/**
 * Created by Delia on 11/15/2016.
 */
public class SplashScreen implements ISplashScreen {
    public static final int SPLASH_WIDTH = 1030;
    public static final int SPLASH_HEIGHT = 600;
    private static final String BG_IMAGE_LOCATION = ViewResources.BG_FILE_LOCATION.getResource();
    private static final String IMAGE_FILE_TYPE = ViewResources.IMAGE_FILE_TYPE.getResource();
    private Pane startWindow;
    private ImageView backgroundImageMainScreen;
    private MainController mainController;
    private NodeFactory myFactory;
    private Gallery galleryItem;
    private GalleryView myGallery;
    private EditorSplash editorSplash;

    public SplashScreen(Gallery galleryItem, MainController mainController) {
        this.myFactory = new NodeFactory();
        this.galleryItem = galleryItem;
        this.mainController = mainController;
    }
    @Override
    public Parent setUpWindow() {
        startWindow = new Pane();
        startWindow.setPrefSize(SPLASH_WIDTH, SPLASH_HEIGHT);
        backgroundImageMainScreen = myFactory.makeBackgroundImage("Animated");
        backgroundImageMainScreen.fitWidthProperty().bind(startWindow.widthProperty());
        backgroundImageMainScreen.fitHeightProperty().bind(startWindow.heightProperty());
        startWindow.getChildren().add(backgroundImageMainScreen);
        addNodes();
        this.editorSplash = new EditorSplash(mainController, startWindow);
        this.myGallery = new GalleryView(galleryItem, mainController, startWindow);
        return startWindow;
    }

    private void addNodes() {
        Text title = myFactory.bigNameTitle("Welcome to VoogaSalad", 35, 75);
        ImageView bgSelect = myFactory.makeThumbnailImage("ChangeBackground", 900, 20, 70, 50);
        Tooltip t = myFactory.makeTooltip("ChangeBackground");
        Tooltip.install(bgSelect, t);
        bgSelect.setOnMouseClicked(e -> selectImage());
        startWindow.getChildren().addAll(title, bgSelect);
    }

    private void selectImage(){
        String imageFile = getFilePath(IMAGE_FILE_TYPE, BG_IMAGE_LOCATION);
        backgroundImageMainScreen.setImage(new Image(imageFile));
    }

    private String getFilePath(String fileType, String fileLocation){
        FileOpener myFileOpener = new FileOpener();
        File file = (myFileOpener.chooseFile(fileType, fileLocation));
        if(file != null){
            return file.toURI().toString();
        }
        return null;
    }
}