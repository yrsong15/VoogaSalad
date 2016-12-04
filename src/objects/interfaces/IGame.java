package objects.interfaces;

import objects.Level;
/**
 * 
 * @author pratikshasharma
 *
 */

public interface IGame {
    public void addLevel(Level level) ;
    public void removeLevel(int level);
    public Level getCurrentLevel();
    public void setCurrentLevel(Level currentLevel);
    public void setGameName(String name);
    public int getNumberOfLevels();
    public String getGameName();
    
}
