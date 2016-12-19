package gameengine.view.interfaces;

import javafx.scene.Scene;

/**
 * Created by Delia on 12/18/2016.
 */
public interface IGameCoverSplash {
    String COVER_SPLASH_STYLE = "default.css";
    int COVER_WIDTH = 700;

    Scene createSplashScene();

    String getTitle();
}
