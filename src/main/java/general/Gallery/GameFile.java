package general.Gallery;

import general.interfaces.IGameFile;
import javafx.scene.image.Image;

/**
 * @author Ryan Bergamini
 */


public class GameFile implements IGameFile {

    private String myGameName;
    private String myXMLData;
    private Image gameCoverImage;

    public GameFile() {

    }

    public GameFile(String gameName, String XMLData, Image gameCoverImage) {
        this.myGameName = gameName;
        this.myXMLData = XMLData;
        this.gameCoverImage = gameCoverImage;
    }

    @Override
    public String getGameName() {
        return myGameName;
    }

    @Override
    public String getGameData() {
        return myXMLData;
    }

    public Image getGameCoverImage() {
        return gameCoverImage;
    }
}
