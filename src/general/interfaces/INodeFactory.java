package general.interfaces;

import javafx.scene.image.ImageView;

/**
 * Created by Delia on 11/30/2016.
 */
public interface INodeFactory {
    String IMAGE_LABEL_FILE = "Image";

    ImageView makeBackgroundImage(String property);
}
