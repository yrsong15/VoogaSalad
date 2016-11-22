package gameeditor.controller.interfaces;


/**
 * 
 * @author Ray Song(ys101)
 *
 */
public interface ICreateGame {
    public void createGame(String title);
    public void addCurrentLevelToGame();
    void setCurrentLevelToGame ();
    void setCurrentGameObjectToMainCharacter ();
}
