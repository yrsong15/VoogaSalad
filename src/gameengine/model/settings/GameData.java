package gameengine.model.settings;

import java.util.List;

import gameengine.model.interfaces.Rule;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameData {

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
