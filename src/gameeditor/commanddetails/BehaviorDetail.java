package gameeditor.commanddetails;

import javafx.scene.layout.VBox;

public class BehaviorDetail extends AbstractCommandDetail {

	VBox myVBox;
	
	public BehaviorDetail() {
		super();

	}

	public void init() {
		myVBox = new VBox();
	}

}
