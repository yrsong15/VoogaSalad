package gameEditorView;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CommandPane {
	
	// TODO: Remove hardcoding of the following values
	// Min Width, Max Width, Min Height
	
	private Pane myPane;

	public CommandPane() {
		myPane = new Pane();
		double width = ViewResources.COMMAND_PANE_WIDTH.getDoubleResource();
		myPane.setMinWidth(width); myPane.setMaxWidth(width);
		myPane.setBackground(new Background(new BackgroundFill(ViewResources.COMMAND_PANE_BG.getColorResource(), CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public Pane getPane(){
		return myPane;
	}

}
