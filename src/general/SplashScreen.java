package general;

import frontend.util.ButtonTemplate;
import gameeditor.view.GameEditorView;
import general.interfaces.ISplashScreen;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Created by Delia on 11/15/2016.
 */
public class SplashScreen implements ISplashScreen {

    public static final String BG_IMAGE_LOCATION = "images";
    public static final int SPLASH_WIDTH = 700;
    public static final int SPLASH_HEIGHT = 600;
    private Pane startWindow;
    private MainController mainController;

    private static final LinearGradient textAndBoxGradient = new LinearGradient(0d, 1d, 1d, 0d, true,
            CycleMethod.NO_CYCLE,
            new Stop(0, Color.WHITE),
            new Stop(0.15, Color.HONEYDEW),
            new Stop(0.3, Color.LIGHTBLUE),
            new Stop(0.45, Color.WHITE),
            new Stop(0.6, Color.LIGHTBLUE),
            new Stop(0.75, Color.HONEYDEW),
            new Stop(1, Color.WHITE));

    public SplashScreen(Stage myStage, MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public Parent setUpWindow() {
        startWindow = new Pane();
        startWindow.setPrefSize(SPLASH_WIDTH, SPLASH_HEIGHT);
        String userDirectoryString = "file:" + System.getProperty("user.dir") + "/images/Background/floatingCubes.jpg";

        Image background = new Image(userDirectoryString);

        ImageView backgroundImageMainScreen = new ImageView(background);
        backgroundImageMainScreen.fitWidthProperty().bind(startWindow.widthProperty());
        backgroundImageMainScreen.fitHeightProperty().bind(startWindow.heightProperty());
        startWindow.getChildren().add(backgroundImageMainScreen);
        addTitle();
        addButtons();
        return startWindow;
    }

    @Override
    public void launchGameEditorWith() {

    }

    //    @Override
    public void launchWith() {
    }

    @Override
    public void launchEditor() {
        mainController.presentEditor();
    }

    @Override
    public void launchGameEditor() {
    	
    }

    @Override
    public void launchGallery() throws IOException {
        mainController.presentGallery();
    }

    //    @Override
    public void launchSelectedGalleryItem() {

    }

    public void launchGameEngine() {
    	mainController.launchEngine("");
    }

    public void launchGameLoader() {

    }

    private void addButtons() {
        // TODO: Change this hash map into reflection where the method of launch + the buttonName is called
        HashMap<String, EventHandler<MouseEvent>> eventHandlerForButton = new HashMap<String, EventHandler<MouseEvent>>();
        eventHandlerForButton.put("GameEngine", e -> launchGameEngine());
        eventHandlerForButton.put("GameEditor", e -> launchEditor());
        eventHandlerForButton.put("GameGallery", e -> {
            try {
                launchGallery();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        eventHandlerForButton.put("GameLoader", e -> launchGameLoader());

        String[] buttonNames = {"GameEngine", "GameEditor", "GameGallery", "GameLoader"};

        double initialX = 100;
        double initialY = 280;
        double xSpacing = 300;
        double ySpacing = 70;
        int buttonsPerCol = 3; // Also rows

        for (int i = 0; i < buttonNames.length; i++) {
            ButtonTemplate buttonTemplate = new ButtonTemplate(buttonNames[i]);
            Button button = buttonTemplate.getButton();
            button.setTranslateX(initialX + (i / buttonsPerCol) * xSpacing);
            button.setTranslateY(initialY + (i % buttonsPerCol) * ySpacing);
            button.setOnMouseClicked(eventHandlerForButton.get(buttonNames[i]));

            startWindow.getChildren().add(button);
        }
    }

    private void addTitle() {
        BigNameText title = new BigNameText("Welcome to \n\tVoogaSalad");
        title.setTranslateX(75);
        title.setTranslateY(75);
        startWindow.getChildren().add(title);
    }

    private static class BigNameText extends StackPane {
        /**
         * @param Name
         */
        public BigNameText(String Name) {
            Text titleText = new Text(Name);
            titleText.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
            titleText.setFill(textAndBoxGradient);
            getChildren().add(titleText);
        }
    }
}