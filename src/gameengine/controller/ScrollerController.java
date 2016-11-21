package gameengine.controller;

<<<<<<< HEAD
import gameengine.view.interfaces.MovementInterface;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public abstract class ScrollerController implements MovementInterface{
=======
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public abstract class ScrollerController {

	private Scene myScene;
	
	public void setScene(Scene myScene){
		this.myScene = myScene;
	}
	
	public abstract void UPKeyPressed();
	public abstract void DOWNKeyPressed();
	public abstract void RIGHTKeyPressed();
	public abstract void LEFTKeyPressed();
}
