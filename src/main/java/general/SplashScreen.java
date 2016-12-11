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
    private static final LinearGradient textAndBoxGradient = new LinearGradient(0d, 1d, 1d, 0d, true,
            CycleMethod.NO_CYCLE,
            new Stop(0, Color.WHITE),
            new Stop(0.15, Color.HONEYDEW),
            new Stop(0.3, Color.LIGHTBLUE),
            new Stop(0.45, Color.WHITE),
            new Stop(0.6, Color.LIGHTBLUE),
            new Stop(0.75, Color.HONEYDEW),
            new Stop(1, Color.WHITE));
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
        BigNameText title = new BigNameText("Welcome to VoogaSalad");
        title.setTranslateX(55);
        title.setTranslateY(35);
        startWindow.getChildren().add(title);
    }
    private static class BigNameText extends StackPane {
        /**
         * @param Name
         */
        public BigNameText(String Name) {
            Text titleText = new Text(Name);
            titleText.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
            titleText.setFill(textAndBoxGradient);
            getChildren().add(titleText);
        }
    }
}