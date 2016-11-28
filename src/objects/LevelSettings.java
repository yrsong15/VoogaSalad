package objects;

/**
 * @author pratikshasharma, Soravit
 */
import objects.interfaces.ISettings;


public class LevelSettings implements ISettings{
    private String musicFilePath;
    private String backgroundFilePath;
    private double scrollWidth;

  
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
    
    public void setScrollWidth(double scrollWidth){
        this.scrollWidth = scrollWidth;
    }
    
    public double getScrollWidth(){
        return this.scrollWidth;
    }
}
