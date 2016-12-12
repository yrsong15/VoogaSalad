package gameengine.view.interfaces;

import javafx.scene.layout.HBox;
import objects.Level;

/**
 * Created by Delia on 12/10/2016.
 */
public interface IHUD {

    HBox getHUD();

    void update(Level level);

    void resetTimer();
}
