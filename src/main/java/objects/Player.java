package objects;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Soravit on 12/7/2016.
 * @ author Soravit, Pratiksha
 */
public class Player {
    private GameObject mainChar;

    private Map<KeyCode, String> controls;
    public Player(GameObject mainCharacter){
        controls = new HashMap<>();
        mainChar = mainCharacter;
        mainChar.setIsPlayer(true);
        mainChar.setXDistanceMoved(getMainChar().getXPosition());
        mainChar.setYDistanceMoved(this.getMainChar().getYPosition());
    }
    public void setControl(KeyCode key, String action) {
        controls.put(key, action);
    }
    public void removeControl(KeyCode key) {
        controls.remove(key);
    }
    public Map<KeyCode, String> getControls() {
        return controls;
    }
    public GameObject getMainChar(){
        return mainChar;
    }
    public void setControlMap(Map<KeyCode,String> controlsMap){
        controls.clear();
        this.controls = controlsMap;
    }
}