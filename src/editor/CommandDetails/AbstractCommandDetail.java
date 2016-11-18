package editor.CommandDetails;

import javafx.scene.control.ScrollPane;

abstract class AbstractCommandDetail {
	
	private ScrollPane myContainerPane;
	
	public AbstractCommandDetail(double paneWidth, double paneHeight) {
		myContainerPane = new ScrollPane();
		myContainerPane.setMaxHeight(paneHeight);
		myContainerPane.setMaxWidth(paneWidth);
		myContainerPane.setFitToWidth(true);
		// TODO Auto-generated constructor stub
	}

}
