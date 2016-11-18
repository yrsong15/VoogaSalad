package general.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Soravit on 11/18/2016.
 */
public class Game {

    private String name;
    private List<Level> levels;

    public Game(String name){
        levels = new ArrayList<Level>();
        this.name = name;
    }

    public void addLevel(Level level){
        levels.add(level);
    }

    public void removeLevel(Level level){
        levels.remove(level);
    }
}
