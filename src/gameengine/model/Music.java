package gameengine.model;

import javafx.scene.media.AudioClip;

public class Music {
	private String track = "DUMMYTRACK.mp3";
	private AudioClip audio;
	//CHANGE THIS STUFF TO OBSERVABLES LATER
	
	public Music(String BGURL){
		updateCurrentPlayingTrack(BGURL);
	}
	
	public AudioClip getPlayingTrack(){
		return this.audio;
	}
	
	public void updateCurrentPlayingTrack(String newTrack){
		this.track = newTrack;
		audio = new AudioClip(getClass().getResource(track).toExternalForm());
	}
}
