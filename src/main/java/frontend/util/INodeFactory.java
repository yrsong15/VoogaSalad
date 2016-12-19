package frontend.util;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Created by Delia on 11/30/2016.
 */
public interface INodeFactory {
    String IMAGE_LABEL_FILE = "Image";
    String TOOLTIP_LABEL_FILE = "Tooltip";
    String ALERT_LABEL_FILE = "GameEngineUI";

    ImageView makeBackgroundImage(String property);

    ImageView makeThumbnailImage(String property, int x, int y, double width, double height);

    ImageView makeThumbnailImage(String property, double width, double height);

    Rectangle makeBackdrop(int x, int y, int width, int height, Color color);

    Text makeLabel(String text, int x, int y, int fontSize);

    Text makeLabel(String text, int x, int y);

    TextField makeTextField(String prompt, int x, int y);

    Tooltip makeTooltip(String property);

    Tooltip makeTooltip(String property, ImageView icon);

    Text bigNameTitle(String name, int x, int y);

    String getUserDirectorySpritePrefix();
}
