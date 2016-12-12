package objects;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Soravit on 12/7/2016.
 */
public class Player {
    private GameObject mainChar;
    
    private Map<KeyCode, String> controls;
    public Player(GameObject mainChar){
        controls = new HashMap<>();
        this.mainChar = mainChar;
        this.mainChar.setIsPlayer(true);
        this.mainChar.setXDistanceMoved(this.getMainChar().getXPosition());
        this.mainChar.setYDistanceMoved(this.getMainChar().getYPosition());
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
}