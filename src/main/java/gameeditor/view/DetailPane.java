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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import gameeditor.commanddetails.*;
import gameeditor.controller.interfaces.IGameEditorData;


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
    private MainCharacterDisplay myAvatarDisplay;
    
    private boolean mainCharPropActive = false;
    private Button myCharPropertiesButton;

    private ImageView myAvatarView;

    public DetailPane(IDesignArea da, IGameEditorData dataStore) {
    	myDesignArea = da;
    	myDataStore = dataStore;
    	myAvatarDisplay = new MainCharacterDisplay(dataStore, da, this);
        myPane = new Pane();
        myPane.setMinWidth(myPaneWidth); myPane.setMaxWidth(myPaneWidth);
        myPane.setBackground(new Background(new BackgroundFill(ViewResources.DETAIL_PANE_BG.getColorResource(), CornerRadii.EMPTY, Insets.EMPTY)));
        myPane.getChildren().add(myAvatarDisplay.getPane());
    }

    public Pane getPane(){
        return myPane;
    }

    public void setAvatar(String filePath ){
        myAvatarDisplay.createNewAvatar(filePath);
    }

    @Override
    public void setDetail(String paneType) {
        String className = "gameeditor.commanddetails." + paneType + "Detail";
        myPane.getChildren().remove(myDetailPane);
        AbstractCommandDetail detailPane = new DetailFactory().create(className, myDataStore, myDesignArea, this);
        myDetailPane = detailPane.getPane();
        myPane.getChildren().add(myDetailPane);
    }
    
    @Override
    public void removeDetail(){
    	myPane.getChildren().remove(myDetailPane);
    }
}
