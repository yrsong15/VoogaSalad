package gameengine.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public abstract class ScrollerController {
	private Scene myScene;
	
	public void setScene(Scene myScene){
		this.myScene = myScene;
	}
	
	private void setUpKeystrokeListeners(){
		this.myScene.setOnKeyReleased(event -> {
	      	  if (event.getCode() == KeyCode.UP){
	      		  UPKeyPressed();
	      	  }
	      	  else if (event.getCode() == KeyCode.DOWN){
	      		DOWNKeyPressed();
	       	  }
	      	  else if (event.getCode() == KeyCode.LEFT){
	      		LEFTKeyPressed();
	          }
	      	  else if (event.getCode() == KeyCode.RIGHT){
	      		RIGHTKeyPressed();
	          }
	       });
	}
	
	public abstract void UPKeyPressed();
	public abstract void DOWNKeyPressed();
	public abstract void RIGHTKeyPressed();
	public abstract void LEFTKeyPressed();
}
