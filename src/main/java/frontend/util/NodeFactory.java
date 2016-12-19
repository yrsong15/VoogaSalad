package frontend.util;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ResourceBundle myImageResources, myTooltipResources, myAlertResources;
    private String userDirectoryBackgroundPrefix = "file:"
            + System.getProperty("user.dir")
            + "/images/Background/";
    private String userDirectoryThumbnailPrefix = "file:"
            + System.getProperty("user.dir")
            + "/images/DesignImages/";
    private String userDirectorySpritePrefix = "file:"
            + System.getProperty("user.dir")
            + "/images/Sprite/";
    private String textFieldFill = "-fx-background-color: linear-gradient(#00110e, #4d66cc);" +
            "-fx-background-radius: 20;" +
            "-fx-text-fill: white;";
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
        myAlertResources = ResourceBundle.getBundle(ALERT_LABEL_FILE, Locale.getDefault());
    }

    @Override
    public ImageView makeBackgroundImage(String property){
        Image background = new Image(userDirectoryBackgroundPrefix + myImageResources.getString(property));
        ImageView backgroundImage = new ImageView(background);
        return backgroundImage;
    }

    @Override
    public ImageView makeThumbnailImage(String property, int x, int y, double width, double height){
        ImageView thumbNail = makeThumbnailImage(property, width, height);
        thumbNail.setTranslateX(x);
        thumbNail.setTranslateY(y);
        return thumbNail;
    }

    @Override
    public ImageView makeThumbnailImage(String property, double width, double height){
        Image thumbNailImg = new Image(userDirectoryThumbnailPrefix + myImageResources.getString(property));
        ImageView thumbNail = new ImageView(thumbNailImg);
        thumbNail.setFitWidth(width);
        thumbNail.setFitHeight(height);
        return thumbNail;
    }

    @Override
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

    @Override
    public Text makeLabel(String text, int x, int y, int fontSize){
        Text label = new Text(text);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, fontSize));
        label.setFill(Color.LIGHTBLUE);
        label.setTranslateX(x);
        label.setTranslateY(y);

        return label;
    }

    @Override
    public Text makeLabel(String text, int x, int y){
        Text label = new Text(text);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        label.setFill(Color.LIGHTBLUE);
        label.setTranslateX(x);
        label.setTranslateY(y);

        return label;
    }

    @Override
    public TextField makeTextField(String prompt, int x, int y){
        TextField addServer = new TextField();
        addServer.setPromptText(prompt);
        addServer.setStyle(textFieldFill);
        addServer.setTranslateX(x);
        addServer.setTranslateY(y);
        return addServer;
    }

    @Override
    public Tooltip makeTooltip(String property) {
        Tooltip t = new Tooltip(myTooltipResources.getString(property));
        return t;
    }

    @Override
    public Tooltip makeTooltip(String property, ImageView icon){
        Tooltip t = makeTooltip(property);
        t.setGraphic(icon);
        return t;
    }

    @Override
    public Text bigNameTitle(String name, int x, int y) {
        Text titleText = new Text(name);
        titleText.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        titleText.setFill(textAndBoxGradient);
        titleText.setTranslateX(x);
        titleText.setTranslateY(y);
        return titleText;
    }

    @Override
    public String getUserDirectorySpritePrefix(){
        return userDirectorySpritePrefix;
    }

    public Alert makeInfoAlert(String property1, String property2){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(myAlertResources.getString(property1));
        alert.setContentText(myAlertResources.getString(property2));
        return alert;
    }
}
