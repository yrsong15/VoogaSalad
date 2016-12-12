package general;

import general.interfaces.INodeFactory;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Created by Delia on 11/30/2016.
 */
public class NodeFactory implements INodeFactory{
    private ResourceBundle myImageResources, myTooltipResources;
    private String userDirectoryBackgroundPrefix = "file:"
            + System.getProperty("user.dir")
            + "/images/Background/";
    private String userDirectoryThumbnailPrefix = "file:"
            + System.getProperty("user.dir")
            + "/images/DesignImages/";
    private String userDirectorySpritePrefix = "file:"
            + System.getProperty("user.dir")
            + "/images/Sprite/";
    private static final LinearGradient textAndBoxGradient = new LinearGradient(0d, 1d, 1d, 0d, true,
            CycleMethod.NO_CYCLE,
            new Stop(0, Color.WHITE),
            new Stop(0.15, Color.HONEYDEW),
            new Stop(0.3, Color.LIGHTBLUE),
            new Stop(0.45, Color.WHITE),
            new Stop(0.6, Color.LIGHTBLUE),
            new Stop(0.75, Color.HONEYDEW),
            new Stop(1, Color.WHITE));

    public NodeFactory(){
        myImageResources = ResourceBundle.getBundle(IMAGE_LABEL_FILE, Locale.getDefault());
        myTooltipResources = ResourceBundle.getBundle(TOOLTIP_LABEL_FILE, Locale.getDefault());
    }

    @Override
    public ImageView makeBackgroundImage(String property){
        Image background = new Image(userDirectoryBackgroundPrefix + myImageResources.getString(property));
        ImageView backgroundImage = new ImageView(background);
        return backgroundImage;
    }

//    public ImageView makeBackgroundImage()

    public ImageView makeThumbnailImage(String property, int x, int y, double width, double height){
        ImageView thumbNail = makeThumbnailImage(property, width, height);
//        Image thumbNailImg = new Image(userDirectoryThumbnailPrefix + myImageResources.getString(property));
//        ImageView thumbNail = new ImageView(thumbNailImg);
        thumbNail.setTranslateX(x);
        thumbNail.setTranslateY(y);
//        thumbNail.setFitWidth(width);
//        thumbNail.setFitHeight(height);
        return thumbNail;
    }

    public ImageView makeThumbnailImage(String property, double width, double height){
        Image thumbNailImg = new Image(userDirectoryThumbnailPrefix + myImageResources.getString(property));
        ImageView thumbNail = new ImageView(thumbNailImg);
        thumbNail.setFitWidth(width);
        thumbNail.setFitHeight(height);
        return thumbNail;
    }

    public Rectangle makeBackdrop(int x, int y, int width, int height, Color color){
        Rectangle backdrop = new Rectangle(width, height, color);
        backdrop.setStroke(Color.BLUE);
        backdrop.setStrokeWidth(5);
        backdrop.setTranslateX(x);
        backdrop.setTranslateY(y);
        backdrop.opacityProperty().setValue(0.5);
        backdrop.setOnMouseEntered(e -> backdrop.opacityProperty().setValue(0.8));
        backdrop.setOnMouseExited(e -> backdrop.opacityProperty().setValue(0.5));
        return backdrop;
    }

    public Text makeLabel(String text, int x, int y, int fontSize){
        Text label = new Text(text);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, fontSize));
        label.setFill(Color.LIGHTBLUE);
        label.setTranslateX(x);
        label.setTranslateY(y);

        return label;
    }

    public Text makeLabel(String text, int x, int y){
        Text label = new Text(text);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        label.setFill(Color.LIGHTBLUE);
        label.setTranslateX(x);
        label.setTranslateY(y);

        return label;
    }

    public Tooltip makeTooltip(String property) {
        Tooltip t = new Tooltip(myTooltipResources.getString(property));
        return t;
    }

    public Tooltip makeTooltip(String property, ImageView icon){
        Tooltip t = makeTooltip(property);
        t.setGraphic(icon);
        return t;
    }

    public Text bigNameTitle(String name, int x, int y) {
        Text titleText = new Text(name);
        titleText.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        titleText.setFill(textAndBoxGradient);
        titleText.setTranslateX(x);
        titleText.setTranslateY(y);
        return titleText;
    }

    public String getUserDirectorySpritePrefix(){
        return userDirectorySpritePrefix;
    }
}
