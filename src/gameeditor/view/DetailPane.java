package gameeditor.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import gameeditor.commanddetails.*;



public class DetailPane implements IDetailPane, ICommandDetailDisplay {
	
	// TODO: Remove hardcoding of the following values
	// Min Width, Max Width, Min Height, AvatarZoneX, AvatarZoneY, AvatarZoneWidth, AvatarZoneHeight
	// Make Pane backgrounds to be CSS controlled
		
	private Pane myPane;
	private double myPaneWidth = ViewResources.DETAIL_PANE_WIDTH.getDoubleResource();
	private Rectangle myAvatarZone;
	private ScrollPane myDetailPane;
	
	private ImageView myAvatarView;

	public DetailPane() {
		// TODO Auto-generated constructor stub
		myPane = new Pane();
		myPane.setMinWidth(myPaneWidth); myPane.setMaxWidth(myPaneWidth);
		myPane.setBackground(new Background(new BackgroundFill(ViewResources.DETAIL_PANE_BG.getColorResource(), CornerRadii.EMPTY, Insets.EMPTY)));
		createAvatarZone();
		myPane.getChildren().add(myAvatarZone);
	}
	
	private void createAvatarZone(){
		double padding = ViewResources.AVATAR_ZONE_PADDING.getDoubleResource();
		double zoneWidth = myPaneWidth-2*padding;
		double zoneHeight = 1.25*zoneWidth;
		double cornerRadius = padding;
		double avZoneY = GameEditorView.SCENE_HEIGHT-padding-zoneHeight;
		myAvatarZone = new Rectangle(padding, avZoneY, zoneWidth, zoneHeight);
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

	@Override
	public void setDetail(String paneType) {
		String className = "gameeditor.commanddetails." + paneType + "Detail";
		myPane.getChildren().remove(myDetailPane);
		AbstractCommandDetail detailPane = new DetailFactory().create(className);
		myPane.getChildren().add(detailPane.getPane());
	}

}
