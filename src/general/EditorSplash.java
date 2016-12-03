package general;

import frontend.util.ButtonTemplate;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by Delia on 12/2/2016.
 */
public class EditorSplash {
    public static final int SPLASH_WIDTH = 700;
    public static final int SPLASH_HEIGHT = 600;
    private Pane splashWindow;
    private Rectangle backdrop;
    private NodeFactory myFactory;
    private MainController myMainController;

    public EditorSplash(MainController mainController){
        this.myFactory = new NodeFactory();
        this.myMainController = mainController;
    }

    public Parent setUpWindow() {
        splashWindow = new Pane();
        splashWindow.setPrefSize(SPLASH_WIDTH, SPLASH_HEIGHT);
        ImageView backgroundImageMainScreen = myFactory.makeBackgroundImage("FloatingCubes");
        backgroundImageMainScreen.fitWidthProperty().bind(splashWindow.widthProperty());
        backgroundImageMainScreen.fitHeightProperty().bind(splashWindow.heightProperty());
        backdrop = myFactory.makeBackdrop(65, 65, 590, 400, Color.MIDNIGHTBLUE);
        splashWindow.getChildren().addAll(backgroundImageMainScreen, backdrop);
        addTitle();
        addButtons();
        return splashWindow;
    }

    private void addTitle() {
        Text title = myFactory.makeLabel("Select a game model to create:", 75, 100);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        title.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        splashWindow.getChildren().add(title);
    }

    private void addButtons(){
        ButtonTemplate newTemplate = new ButtonTemplate("ForcedScroll", 150, 150);
        Button forced = newTemplate.getButton();
        forced.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        forced.setOnMouseClicked(e -> myMainController.presentEditor());
        newTemplate = new ButtonTemplate("LimitedScroll", 150, 250);
        Button limited = newTemplate.getButton();
        limited.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        newTemplate = new ButtonTemplate("FreeScroll", 150, 350);
        Button free = newTemplate.getButton();
        free.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        splashWindow.getChildren().addAll(forced, limited, free);
    }
}
