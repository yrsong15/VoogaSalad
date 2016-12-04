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

import javafx.event.EventHandler;

/**
 * Created by Delia on 11/15/2016.
 */
public class SplashScreen implements ISplashScreen {

    public static final int SPLASH_WIDTH = 1030;
    public static final int SPLASH_HEIGHT = 600;
    private Pane startWindow;
//    private Rectangle backdrop;
    private MainController mainController;
    private NodeFactory myFactory;
    private Gallery galleryItem;
    private GalleryView myGallery;
    EditorSplash editorSplash;

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
        ImageView backgroundImageMainScreen = myFactory.makeBackgroundImage("FloatingCubes");
        backgroundImageMainScreen.fitWidthProperty().bind(startWindow.widthProperty());
        backgroundImageMainScreen.fitHeightProperty().bind(startWindow.heightProperty());
//        backdrop = myFactory.makeBackdrop(65, 65, 590, 400, Color.MIDNIGHTBLUE);
        startWindow.getChildren().add(backgroundImageMainScreen);
        addTitle();
//        addButtons();
        this.editorSplash = new EditorSplash(mainController, startWindow);
        this.myGallery = new GalleryView(galleryItem, mainController, startWindow);
        return startWindow;
    }

    @Override
    public void launchEditor() {
       // mainController.editorSplash();
//        mainController.presentEditor();
    }

    @Override
    public void launchGallery() throws IOException {
        mainController.presentGallery();

    }

    public void launchGameEngine() {
    	mainController.launchEngine("");
    }

    private void addButtons() {
        // TODO: Change this hash map into reflection where the method of launch + the buttonName is called
        HashMap<String, EventHandler<MouseEvent>> eventHandlerForButton = new HashMap<String, EventHandler<MouseEvent>>();
        eventHandlerForButton.put("GameEditor", e -> launchEditor());
        eventHandlerForButton.put("GameGallery", e -> {
            try {
                launchGallery();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        String[] buttonNames = {"GameEditor", "GameGallery"};

        double initialX = 900;
        double initialY = 280;
        double xSpacing = 300;
        double ySpacing = 100;
        int buttonsPerCol = 2; // Also rows

        for (int i = 0; i < buttonNames.length; i++) {
            ButtonTemplate buttonTemplate = new ButtonTemplate(buttonNames[i],
                    initialX + (i % buttonsPerCol) * xSpacing,
                    initialY + (i / buttonsPerCol) * ySpacing);
            Button button = buttonTemplate.getButton();
            button.setOnMouseClicked(eventHandlerForButton.get(buttonNames[i]));
//            button.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
            button.setFont(Font.font(50));

            startWindow.getChildren().add(button);
        }
    }

    private void addTitle() {
        BigNameText title = new BigNameText("Welcome to VoogaSalad");
        title.setTranslateX(55);
        title.setTranslateY(35);
//        title.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
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