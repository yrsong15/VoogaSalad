package gameeditor.controller.interfaces;
import java.util.HashMap;

public class ControlManager implements IControlManager{
    HashMap<String, String > myControlMap;
    
    public ControlManager(){
        myControlMap = new HashMap<String,String>();
    }
    
    public HashMap<String, String> getLevelControls(){
        return this.myControlMap;
    }
    
    public void setLevelsControls( HashMap<String,String> newControlMap){
        this.myControlMap = newControlMap;
    }
    
}
