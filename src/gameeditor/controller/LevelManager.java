package gameeditor.controller;
import gameeditor.controller.interfaces.ICreateLevel;
import objects.GameObject;
import objects.Level;


/**
 * This is an intermediate controller that manages levels of the Game.
 * @author Ray Song(ys101)
 *
 */

public class LevelManager implements ICreateLevel{
	private Level myLevel;
	//private LevelSettings mySettings;
	//private Settings mySettings;
	
	@Override
	public void createLevel(int levelNumber) {
		Level level = new Level(levelNumber);
		//mySettings = new LevelSettings(null,null,null);
		myLevel = level;
	}

	public Level getLevel() {
		return myLevel;
	}
	

	@Override
	public void addWinConditions(String type, String action) {
		myLevel.addWinCondition(type, action);
	}

	@Override
	public void addLoseConditions(String type, String action) {
		myLevel.addLoseCondition(type, action);
	}

	@Override
	public void addScore(double score) {
		myLevel.setScore(score);
	}

	@Override
	public void addTime(double time) {
		myLevel.setTime(time);
	}

	@Override
	public void addCurrentGameObjectToLevel() {
		return;
	}

	@Override
	public void addBackgroundMusic(String musicFilePath) {
		myLevel.getViewSettings().setMusicFile(musicFilePath);
	}

	@Override
	public void addBackgroundImage(String backgroundFilePath) {
		myLevel.getViewSettings().setBackgroundFilePath(backgroundFilePath);
	}

    @Override
    public void setCurrentGameObjectToMainCharacter (GameObject object) {
        // TODO Auto-generated method stub
        myLevel.setMainCharacter(object);
        
    }
}
