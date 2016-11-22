package objects;

/**
 * @author pratikshasharma 
 */
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import com.sun.javafx.scene.traversal.Direction;
import javafx.beans.property.StringProperty;
import objects.interfaces.ISettings;

public class LevelSettings implements ISettings{
    private String musicFilePath;
    private String imagePath;
    private String scrollType;
    private ScrollType myScrollType;
    
    public LevelSettings(StringProperty musicFilePath, StringProperty scrollType, List<Direction> directionList){
        musicFilePath.bind(musicFilePath);
        scrollType.bindBidirectional(scrollType);
        myScrollType = new ScrollType(scrollType.get());    
    }
    
    public void addDirection(Direction direction){
        myScrollType.addScrollDirection(direction);
    }
   
    @Override
    public void setImageFile (String imageFilePath) {
       this.imagePath = imageFilePath;   
    }

    @Override
    public void setMusicFile (String musicFilePath) {
        this.musicFilePath = musicFilePath;   
    }
      
}
