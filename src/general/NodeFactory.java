package general;

import general.interfaces.INodeFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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


}
