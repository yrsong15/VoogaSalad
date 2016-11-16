package gameEditorView;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LeftDetailPane {
	
	// TODO: Remove hardcoding of the following values
	// Min Width, Max Width, Min Height, AvatarZoneX, AvatarZoneY, AvatarZoneWidth, AvatarZoneHeight
	// Make Pane backgrounds to be CSS controlled
		
	private Pane myPane;
	private Rectangle myAvatarZone;

	public LeftDetailPane() {
		// TODO Auto-generated constructor stub
		myPane = new Pane();
		myPane.setMinWidth(200); myPane.setMaxWidth(200);
		myPane.setBackground(new Background(new BackgroundFill(Color.MEDIUMPURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
		createAvatarZone();
		myPane.getChildren().add(myAvatarZone);
	}
	
	private void createAvatarZone(){
		double zoneWidth = 150;
		double zoneHeight = 200;
		double avZoneX = (myPane.getWidth()-zoneWidth)/2;
		double avZoneY = GameEditorView.SCENE_HEIGHT-25-zoneHeight;
		myAvatarZone = new Rectangle(25, avZoneY, zoneWidth, zoneHeight);
		myAvatarZone.setFill(Color.GHOSTWHITE);
		myAvatarZone.setArcWidth(25);
		myAvatarZone.setArcHeight(25);
	}
	
	public Pane getPane(){
		return myPane;
	}

}
