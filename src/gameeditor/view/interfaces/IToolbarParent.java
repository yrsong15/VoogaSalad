package gameeditor.view.interfaces;

import java.util.Map;

public interface IToolbarParent {

    public void setBackground();

    public void setAvatar();

    public void setMusic ();
    
    public void saveLevelData (Map<String, String> myLevelData);



}
