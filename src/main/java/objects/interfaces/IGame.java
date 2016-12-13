package objects.interfaces;

import objects.Level;
import objects.Player;
/**
 * @author pratikshasharma
 */

public interface IGame {
    public void addLevel(Level level) ;
    public void removeLevel(int level);
    public Level getCurrentLevel();
    public void addPlayer(Player player);
    public void removePlayer(Player player);
    public void setCurrentLevel(Level currentLevel);
    public void setGameName(String name);
    public int getNumberOfLevels();
    public String getGameName();
    public Level getLevelByIndex(int index);
    public void addPlayerToClient(long ID, Player player);
    
}
