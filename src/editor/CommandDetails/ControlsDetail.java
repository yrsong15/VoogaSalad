package editor.CommandDetails;

import editor.view.ViewResources;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class ControlsDetail extends AbstractCommandDetail{

	private double inputFieldHeight = 35;
	private double nextNodeY = padding;
	private VBox myVBox;
	
	public ControlsDetail() {
		super();
		myVBox = new VBox();
		myVBox.setSpacing(ViewResources.DETAIL_CONTENT_PADDING.getDoubleResource());
		myVBox.setAlignment(Pos.CENTER);
		myContainerPane.setContent(myVBox);
		createDirectionsControl("Up");
		createDirectionsControl("Down");
		createDirectionsControl("Left");
		createDirectionsControl("Right");
		createDirectionsControl("Shoot");
	}
	
	public void createDirectionsControl(String label){
		Label labl = new Label(label);
		myVBox.getChildren().add(labl);
		TextArea inputField = new TextArea(label);
		inputField.setPrefWidth(myPaneWidth-2*padding);
		inputField.setPrefHeight(inputFieldHeight);
		nextNodeY += inputFieldHeight + padding;
		myVBox.getChildren().add(inputField);
	}
}
