package base.integration;

import javafx.scene.Scene;
import javafx.stage.FileChooser;

/**
 * Created by Delia on 11/13/2016.
 */
public interface IVoogaController {

    /**
     * access the start screen and get a preference indicated  by the user
     */
    void getUserPref();

    Scene launchGameEditor();

    Scene launchGameEngine();

    FileChooser loadGameFile();

    void saveDataToGallery();

    void updateXMLData();

    void getXMLFactoryObjects();

    void makeGameCopy();

    void deleteGameFile();


}
