package objects;

/**
 * @author pratikshasharma 
 */
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import com.sun.javafx.scene.traversal.Direction;
import javafx.beans.property.StringProperty;

public class Settings implements Observer{
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

    @Override
    public void update (Observable o, Object arg) {
        // TODO Auto-generated method stub
        
    }
    
}
