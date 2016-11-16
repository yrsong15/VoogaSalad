package gameEditorView;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class LeftDetailPane implements ILeftDetailPane {
	
	// TODO: Remove hardcoding of the following values
	// Min Width, Max Width, Min Height, AvatarZoneX, AvatarZoneY, AvatarZoneWidth, AvatarZoneHeight
	// Make Pane backgrounds to be CSS controlled
		
	private Pane myPane;
	private Rectangle myAvatarZone;
	
	private ImageView myAvatarView;

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
		double cornerRadius = 25;
		double avZoneX = (myPane.getWidth()-zoneWidth)/2;
		double avZoneY = GameEditorView.SCENE_HEIGHT-25-zoneHeight;
		myAvatarZone = new Rectangle(25, avZoneY, zoneWidth, zoneHeight);
		myAvatarZone.setFill(Color.GHOSTWHITE);
		myAvatarZone.setArcWidth(cornerRadius);
		myAvatarZone.setArcHeight(cornerRadius);
	}
	
	public Pane getPane(){
		return myPane;
	}
	
	public void setAvatar(Image newAvatar){
		myPane.getChildren().remove(myAvatarView);
		double padding = 20;
		double fitWidth = myAvatarZone.getWidth() - padding;
		double fitHeight = myAvatarZone.getHeight() - padding;
		double widthRatio = fitWidth/newAvatar.getWidth();
		double heightRatio = fitHeight/newAvatar.getHeight();
		double ratio = Math.min(widthRatio, heightRatio);
        double endWidth = newAvatar.getWidth()*ratio;
        double endHeight = newAvatar.getHeight()*ratio;
		myAvatarView = new ImageView(newAvatar);
		myAvatarView.setPreserveRatio(true);
		myAvatarView.setFitWidth(fitWidth);
		myAvatarView.setFitHeight(fitHeight);
		myAvatarView.setLayoutX(myAvatarZone.getX() + myAvatarZone.getWidth()/2 - endWidth/2);
		myAvatarView.setLayoutY(myAvatarZone.getY() + myAvatarZone.getHeight()/2 - endHeight/2);
		myPane.getChildren().add(myAvatarView);

	}

}
