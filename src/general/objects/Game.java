package general.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Soravit on 11/18/2016.
 */
public class Game {

    private String name;
    private Map<Level, Integer> levels;

    public Game(String name){
        levels = new HashMap<Level, Integer>();
        this.name = name;
    }

    public void addLevel(Level level){
        levels.put(level, level.getLevel());
    }

    public void removeLevel(Level level){
        levels.remove(level);
    }
}
