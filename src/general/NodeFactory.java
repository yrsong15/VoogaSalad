package general;

import general.interfaces.INodeFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
    private ResourceBundle myResources;
    private String userDirectoryBackgroundPrefix = "file:"
            + System.getProperty("user.dir")
            + "/images/Background/";

    public NodeFactory(){
        myResources = ResourceBundle.getBundle(IMAGE_LABEL_FILE, Locale.getDefault());

    }

    @Override
    public ImageView makeBackgroundImage(String property){
        Image background = new Image(userDirectoryBackgroundPrefix + myResources.getString(property));
        ImageView backgroundImage = new ImageView(background);
        return backgroundImage;
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

    public Text makeLabel(String text, int x, int y){
        Text label = new Text(text);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        label.setFill(Color.LIGHTBLUE);
        label.setTranslateX(x);
        label.setTranslateY(y);

        return label;
    }
}
