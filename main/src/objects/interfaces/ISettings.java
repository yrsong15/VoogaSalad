package objects.interfaces;

public interface ISettings{
    
    public void setBackgroundFilePath(String backgroundFilePath);
    public void setMusicFile(String musicFilePath);
    public String getMusicFilePath();
    public String getBackgroundFilePath();
    public void setScrollWidth(double scrollWidth);
    public double getScrollWidth();
    
}
