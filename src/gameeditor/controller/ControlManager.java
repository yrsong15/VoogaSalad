package gameeditor.controller;
import java.util.HashMap;
import gameeditor.controller.interfaces.IControlManager;

public class ControlManager implements IControlManager{
    HashMap<String, String > myControlMap;
    
    public ControlManager(){
        myControlMap = new HashMap<String,String>();
    }
    
    public HashMap<String, String> getLevelControls(){
        return this.myControlMap;
    }
    
    public void setLevelsControls( HashMap<String,String> newControlMap){
        this.myControlMap= newControlMap;
    }
    
}
