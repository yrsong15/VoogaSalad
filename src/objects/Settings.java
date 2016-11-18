package objects;


import java.util.List;
import com.sun.javafx.scene.traversal.Direction;
import javafx.beans.property.StringProperty;

public class Settings {
    private StringProperty musicFilePath;
    private StringProperty scrollType;
    private ScrollType myScrollType;
    
    public Settings(StringProperty musicFilePath, StringProperty scrollType, List<Direction> directionList){
        musicFilePath.bind(musicFilePath);
        scrollType.bindBidirectional(scrollType);
        myScrollType = new ScrollType(scrollType.get());    
    }
    
    public void addDirection(Direction direction){
        myScrollType.addScrollDirection(direction);
    }
    
    public void setMusicFile(StringProperty musicFilePath){
        musicFilePath.set(musicFilePath.get());
        
    }
 
    public void setScrollType(StringProperty scrollType){
        scrollType.set(scrollType.get());
    }
    
}
