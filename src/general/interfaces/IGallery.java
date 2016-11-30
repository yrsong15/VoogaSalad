package general.interfaces;

import general.GameFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Delia on 11/30/2016.
 */
public interface IGallery {

    String readFile(String path) throws IOException;

    void addToGallery(GameFile newGame);

    void removeFromGallery(String ganeName);

    List<GameFile> getUnmodifiableListOfGameFiles();
}
