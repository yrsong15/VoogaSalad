package general.interfaces;

import javafx.scene.Parent;

import java.io.IOException;

/**
 * Created by Delia on 11/13/2016.
 */
public interface ISplashScreen {

    Parent setUpWindow();

    void launchGameEditorWith(); //take GameData param

//<<<<<<< HEAD:src/general/interfaces/ISplashScreen.java
    void launchEditor();
//=======
    void launchGameEditor();
//>>>>>>> b45f95ebac813644570b87065dfe19f10ae9ec85:src/base/integration/ISplashScreen.java

    void launchGallery() throws IOException;

    void launchSelectedGalleryItem();

}
