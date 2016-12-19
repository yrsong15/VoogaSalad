package general;

import frontend.util.ButtonTemplate;
import frontend.util.NodeFactory;
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
    public static final int EDITOR_CORNER_X = 60;
    public static final int EDITOR_CORNER_Y = 115;
    private Pane splashWindow;
    private Rectangle backdrop;
    private NodeFactory myFactory;
    private MainController myMainController;

    public EditorSplash(MainController mainController, Pane startwindow){
        this.myFactory = new NodeFactory();
        this.myMainController = mainController;
        this.splashWindow = startwindow;
        backdrop = myFactory.makeBackdrop(EDITOR_CORNER_X, EDITOR_CORNER_Y, 890, 170, Color.MIDNIGHTBLUE);
        splashWindow.getChildren().addAll(backdrop);
        addTitle();
        addButtons();
    }

    private void addTitle() {
        Text title = myFactory.makeLabel("To start from scratch, select a game model", EDITOR_CORNER_X + 15,
                EDITOR_CORNER_Y + 40);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        title.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        splashWindow.getChildren().add(title);
    }

    private void addButtons(){
        ButtonTemplate newTemplate = new ButtonTemplate("ForcedScroll",
                EDITOR_CORNER_X + 60, EDITOR_CORNER_Y + 70);
        Button forced = newTemplate.getButton();
        forced.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        forced.setOnMouseClicked(e -> myMainController.presentEditor(null));
        ImageView flappy = myFactory.makeThumbnailImage("Flappy", 150, 50);
        Tooltip TForced = myFactory.makeTooltip("Forced", flappy);
        Tooltip.install(forced, TForced);
        newTemplate = new ButtonTemplate("LimitedScroll",
                EDITOR_CORNER_X + 660, EDITOR_CORNER_Y + 70);
        Button limited = newTemplate.getButton();
        limited.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        ImageView doodle = myFactory.makeThumbnailImage("Doodle", 98, 130);
        Tooltip TLimited = myFactory.makeTooltip("Limited", doodle);
        Tooltip.install(limited, TLimited);
        newTemplate = new ButtonTemplate("FreeScroll",
                EDITOR_CORNER_X + 360, EDITOR_CORNER_Y + 70);
        Button free = newTemplate.getButton();
        free.setOnMouseEntered(e -> backdrop.setOpacity(0.8));
        free.setOnMouseClicked(e -> myMainController.presentEditor(null, "RPG"));
        ImageView mario = myFactory.makeThumbnailImage("Mario", 150, 50);
        Tooltip TFree = myFactory.makeTooltip("Free", mario);
        Tooltip.install(free, TFree);
        splashWindow.getChildren().addAll(forced, limited, free);
    }
}
