package gameeditor.view;

import gameeditor.view.interfaces.ICommandDetailDisplay;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import objects.interfaces.ILevel;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import gameeditor.commanddetails.*;
import gameeditor.controller.GameEditorData;
import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.view.interfaces.ICommandDetailDisplay;
import gameeditor.view.interfaces.IDetailPane;



public class DetailPane implements IDetailPane, ICommandDetailDisplay {

    // TODO: Remove hardcoding of the following values
    // Min Width, Max Width, Min Height, AvatarZoneX, AvatarZoneY, AvatarZoneWidth, AvatarZoneHeight
    // Make Pane backgrounds to be CSS controlled

    private Pane myPane;
    private double myPaneWidth = ViewResources.DETAIL_PANE_WIDTH.getDoubleResource();
    private Rectangle myAvatarZone;
    private ScrollPane myDetailPane;
    private IGameEditorData myDataStore;
    private IDesignArea myDesignArea;
    
    private boolean mainCharPropActive = false;
    private Button myCharPropertiesButton;

    private ImageView myAvatarView;

    public DetailPane(IDesignArea da, ILevel currentLevel) {
    	myDesignArea = da;
    	myDataStore = new GameEditorData(currentLevel);
    	
        myPane = new Pane();
        myPane.setMinWidth(myPaneWidth); myPane.setMaxWidth(myPaneWidth);
        myPane.setBackground(new Background(new BackgroundFill(ViewResources.DETAIL_PANE_BG.getColorResource(), CornerRadii.EMPTY, Insets.EMPTY)));
        createAvatarZone();
        myPane.getChildren().add(myAvatarZone);
    }

    private void createAvatarZone(){
        double padding = ViewResources.AVATAR_ZONE_PADDING.getDoubleResource();
        double zoneWidth = ViewResources.AVATAR_ZONE_WIDTH.getDoubleResource();
        double zoneHeight = ViewResources.AVATAR_ZONE_HEIGHT.getDoubleResource();;
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
        double buttonPadding = 50;
        padding += buttonPadding;
        createAvatarButton(buttonPadding);
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
        myAvatarView.setLayoutY(myAvatarZone.getY()+ buttonPadding + (myAvatarZone.getHeight()-buttonPadding)/2 - endHeight/2);
        myPane.getChildren().add(myAvatarView);
    }
    
    public void createAvatarButton(double padding){
    	myCharPropertiesButton = new Button();
    	double buttonWidth = 150;
    	double buttonHeight = 30;
    	myCharPropertiesButton.setText("Main Character Properties");
    	myCharPropertiesButton.setMinWidth(buttonWidth);
    	myCharPropertiesButton.setMinHeight(buttonHeight);
    	myCharPropertiesButton.setOnAction((e) -> {handleAvatar();});
    	myCharPropertiesButton.setLayoutX(myAvatarZone.getX()/2 + myAvatarZone.getWidth()/2 - buttonWidth/2);
    	myCharPropertiesButton.setLayoutY(myAvatarZone.getY() + padding/2 - buttonHeight/2);
    	myPane.getChildren().add(myCharPropertiesButton);
    }
    
    public void handleAvatar(){
    	if (mainCharPropActive){
    		removeDetail();
    	} else {
    		setDetail("MainCharacter");
    	}
    	mainCharPropActive = !mainCharPropActive;
    }

    @Override
    public void setDetail(String paneType) {
        String className = "gameeditor.commanddetails." + paneType + "Detail";
        myPane.getChildren().remove(myDetailPane);
        myPane.getChildren().remove(myCharPropertiesButton);
        AbstractCommandDetail detailPane = new DetailFactory().create(className, myDataStore, myDesignArea);
        myDetailPane = detailPane.getPane();
        myPane.getChildren().add(myDetailPane);
    }
    
    private void removeDetail(){
    	myPane.getChildren().remove(myDetailPane);
    }

    
}
