package gameEditorView;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class LeftButtonPane {
	
	// TODO: Remove hardcoding of the following values
	// Min Width, Max Width, Min Height
	
	private Pane myPane;

	public LeftButtonPane() {
		myPane = new Pane();
		myPane.setMinWidth(100); myPane.setMaxWidth(100);
		myPane.setBackground(new Background(new BackgroundFill(Color.PALEVIOLETRED, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	
	public Pane getPane(){
		return myPane;
	}

}
