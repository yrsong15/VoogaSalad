package general;
import frontend.util.ButtonTemplate;
import general.interfaces.ISplashScreen;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
    private Pane startWindow;
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
        ImageView backgroundImageMainScreen = myFactory.makeBackgroundImage("Animated");
//        backgroundImageMainScreen.
        backgroundImageMainScreen.fitWidthProperty().bind(startWindow.widthProperty());
        backgroundImageMainScreen.fitHeightProperty().bind(startWindow.heightProperty());
        startWindow.getChildren().add(backgroundImageMainScreen);
        addTitle();
        this.editorSplash = new EditorSplash(mainController, startWindow);
        this.myGallery = new GalleryView(galleryItem, mainController, startWindow);
        return startWindow;
    }
    private void addTitle() {
        Text title = myFactory.bigNameTitle("Welcome to VoogaSalad", 35, 75);
        startWindow.getChildren().add(title);
    }

}