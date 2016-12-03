package general;

import frontend.util.ButtonTemplate;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
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
        backdrop = myFactory.makeBackdrop(65, 65, 590, 470, Color.MIDNIGHTBLUE);
        splashWindow.getChildren().addAll(backgroundImageMainScreen, backdrop);
        addTitle();
        addButtons();
        addThumbnails();
        addThumbnailLabels();
        return splashWindow;
    }

    private void addTitle() {
        Text title = myFactory.makeLabel("Select a game model to create:", 75, 100);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        title.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        splashWindow.getChildren().add(title);
    }

    private void addButtons(){
        ButtonTemplate newTemplate = new ButtonTemplate("ForcedScroll", 120, 150);
        Button forced = newTemplate.getButton();
        forced.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        forced.setOnMouseClicked(e -> myMainController.presentEditor());
        Tooltip TForced = myFactory.makeTooltip("Forced");
        Tooltip.install(forced, TForced);
        newTemplate = new ButtonTemplate("LimitedScroll", 120, 350);
        Button limited = newTemplate.getButton();
        limited.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        Tooltip TLimited = myFactory.makeTooltip("Limited");
        Tooltip.install(limited, TLimited);
        newTemplate = new ButtonTemplate("FreeScroll", 120, 250);
        Button free = newTemplate.getButton();
        free.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        Tooltip TFree = myFactory.makeTooltip("Free");
        Tooltip.install(free, TFree);
        splashWindow.getChildren().addAll(forced, limited, free);
    }

    private void addThumbnails(){
        ImageView flappy = myFactory.makeThumbnailImage("Flappy", 420, 170, 150, 50);
        flappy.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        ImageView mario = myFactory.makeThumbnailImage("Mario", 420, 270, 150, 50);
        mario.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        ImageView doodle = myFactory.makeThumbnailImage("Doodle", 440, 370, 98, 130);
        doodle.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        splashWindow.getChildren().addAll(flappy, mario, doodle);
    }

    private void addThumbnailLabels(){
        Text flappyLabel = myFactory.makeLabel("e.g. Flappy Bird", 420, 160);
        flappyLabel.setFont(Font.font("Century Gothic", 15));
        flappyLabel.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        Text marioLabel = myFactory.makeLabel("e.g. Super Mario", 420, 260);
        marioLabel.setFont(Font.font("Century Gothic", 15));
        marioLabel.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        Text doodleLabel = myFactory.makeLabel("e.g. Doodle Jump", 420, 360);
        doodleLabel.setFont(Font.font("Century Gothic", 15));
        doodleLabel.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        splashWindow.getChildren().addAll(flappyLabel, marioLabel, doodleLabel);
    }
}
