package general;

import base.integration.ISplashScreen;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Delia on 11/15/2016.
 */
public class SplashScreen implements ISplashScreen {
    private static final int SPLASH_WIDTH = 700;
    private static final int SPLASH_HEIGHT = 600;
    private Pane startWindow;
    
    public SplashScreen(Stage myStage){

    }

    @Override
    public Parent setUpWindow() {

        startWindow = new Pane();
        startWindow.setPrefSize(SPLASH_WIDTH, SPLASH_HEIGHT);
        Image background = new Image(getClass().getClassLoader()
                .getResourceAsStream("images/floatingCubes.jpg"));
        ImageView backgroundImageMainScreen = new ImageView(background);
        backgroundImageMainScreen.setFitWidth(SPLASH_WIDTH + 50);
        backgroundImageMainScreen.setFitHeight(SPLASH_HEIGHT);
        startWindow.getChildren().add(backgroundImageMainScreen);
        return startWindow;
    }

    @Override
    public void launchWith() {

    }

    @Override
    public void launch() {

    }

    @Override
    public void launchGallery() {

    }

    @Override
    public void launchSelectedGalleryItem() {

    }
}