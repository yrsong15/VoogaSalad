package general;

import base.integration.ISplashScreen;
import buttons.ButtonTemplate;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

/**
 * Created by Delia on 11/15/2016.
 */
public class SplashScreen implements ISplashScreen {
    private static final int SPLASH_WIDTH = 700;
    private static final int SPLASH_HEIGHT = 600;
    private Pane startWindow;

    private static final LinearGradient textAndBoxGradient = new LinearGradient(0d, 1d, 1d, 0d, true,
                                                                                CycleMethod.NO_CYCLE,
                                                                                new Stop(0, Color.WHITE),
                                                                                new Stop(0.15, Color.HONEYDEW),
                                                                                new Stop(0.3, Color.LIGHTBLUE),
                                                                                new Stop(0.45, Color.WHITE),
                                                                                new Stop(0.6, Color.LIGHTBLUE),
                                                                                new Stop(0.75, Color.HONEYDEW),
                                                                                new Stop(1, Color.WHITE));

    public SplashScreen(Stage myStage){

    }

    @Override
    public Parent setUpWindow() {

        startWindow = new Pane();
        startWindow.setPrefSize(SPLASH_WIDTH, SPLASH_HEIGHT);
        
        Image background = new Image(getClass().getClassLoader().getResourceAsStream("images/Background/floatingCubes.jpg"));
        ImageView backgroundImageMainScreen = new ImageView(background);
        
        backgroundImageMainScreen.fitWidthProperty().bind(startWindow.widthProperty());
        backgroundImageMainScreen.fitHeightProperty().bind(startWindow.heightProperty());
        startWindow.getChildren().add(backgroundImageMainScreen);
        addTitle();
        addButtons();
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

    private void addButtons(){
        ButtonTemplate engineButton = new ButtonTemplate("GameEngine");
        Button engine = engineButton.getButton();
        engine.setTranslateX(100);
        engine.setTranslateY(350);

        ButtonTemplate editorButton = new ButtonTemplate("GameEditor");
        Button editor = editorButton.getButton();
        editor.setTranslateX(100);
        editor.setTranslateY(280);

        ButtonTemplate galleryButton = new ButtonTemplate("GameGallery");
        Button gallery = galleryButton.getButton();
        gallery.setTranslateX(100);
        gallery.setTranslateY(420);


        ButtonTemplate loaderButton = new ButtonTemplate("GameLoader");
        Button loader = loaderButton.getButton();
        loader.setTranslateX(400);
        loader.setTranslateY(280);

        startWindow.getChildren().addAll(engine, editor, gallery, loader);

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