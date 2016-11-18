package objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soravit on 11/18/2016.
 */
public class Level {

    private int level;
    private List<GameObject> gameObjects;

    public Level(int level){
        gameObjects = new ArrayList<GameObject>();
        this.level = level;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public void addGameObject(GameObject go){
        gameObjects.add(go);
    }

    public void removeGameObject(GameObject go){
        gameObjects.remove(go);
    }
}
