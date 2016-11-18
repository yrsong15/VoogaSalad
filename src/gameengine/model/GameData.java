package gameengine.model;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameData {
	//TODO: ADD OBSERVABLES TO VALUES LATER
	
	private List<Rule> currentRules;
	private Music currentBackgroundMusic;
	private ImageView backgroundImage;
	
	public GameData(String backgroundImageURL, String backgroundMusicURL){
		this.currentBackgroundMusic = new Music(backgroundMusicURL);
		setNewBackground(backgroundImageURL);
	}
	
	//TODO: These setter methods should be unnecessary after adding in the observables
	public void setNewBackground(String backgroundURL){
		Image background = new Image(getClass().getClassLoader()
				.getResourceAsStream(backgroundURL)); 
		this.backgroundImage = new ImageView(background);
	}
	
	public void updateTrack(String backgroundMusicURL){
		currentBackgroundMusic.updateCurrentPlayingTrack(backgroundMusicURL);
	}
}
