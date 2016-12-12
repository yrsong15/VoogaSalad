package gameeditor.view;

import java.util.ArrayList;

import gameeditor.view.interfaces.ICommandButton;
import gameeditor.view.interfaces.ICommandButtonOut;
import gameeditor.view.interfaces.ICommandDetailDisplay;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
/**
 * @author John Martin
 *
 */
public class CommandPane implements ICommandButtonOut {
	
	private Pane myPane;
	private double myPaneWidth = ViewResources.COMMAND_PANE_WIDTH.getDoubleResource();
	
	private ArrayList<ICommandButton> myButtons = new ArrayList<ICommandButton>();
	private int numButtons = 0;

	public CommandPane(ICommandDetailDisplay commandDetailDisplay) {
		myPane = new Pane();
		myPane.setMinWidth(myPaneWidth); myPane.setMaxWidth(myPaneWidth);
		myPane.setBackground(new Background(new BackgroundFill(ViewResources.COMMAND_PANE_BG.getColorResource(),
				CornerRadii.EMPTY, Insets.EMPTY)));
		String [] buttonLocations = ViewResources.BUTTON_FILE_LOCATIONS.getArrayResource();
		for (String location : buttonLocations){
			ICommandButton button = new CommandButton(location, numButtons++, myPaneWidth, this, commandDetailDisplay);
			myButtons.add(button);
			myPane.getChildren().add(button.getBorder());
			myPane.getChildren().add(button.getBG());
			myPane.getChildren().add(button.getImageView());
		}
	}
	
	public void lowlightButtons(){
		for (ICommandButton b : myButtons){
			b.lowlight();
		}
	}
	
	public Pane getPane(){
		return myPane;
	}
}
