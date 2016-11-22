package objects;

/**
 * @author pratikshasharma, Soravit
 */
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import com.sun.javafx.scene.traversal.Direction;
import javafx.beans.property.StringProperty;

public class Settings {
    private String musicFilePath;
    private String backgroundFilePath;

    public void setMusicFile(String musicFilePath){
        this.musicFilePath = musicFilePath;
    }

    public String getMusicFilePath(){
        return musicFilePath;
    }

    public void setBackgroundFilePath(String backgroundFilePath){
        this.backgroundFilePath = backgroundFilePath;
    }

    public String getBackgroundFilePath(){
        return backgroundFilePath;
    }
}
