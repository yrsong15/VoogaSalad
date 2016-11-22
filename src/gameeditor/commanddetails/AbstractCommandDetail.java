package gameeditor.commanddetails;

import gameeditor.view.ViewResources;
import gameeditor.view.interfaces.IDesignArea;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;



public abstract class AbstractCommandDetail {
	
	protected double myDetailPadding = DetailResources.DETAIL_CONTENT_PADDING.getDoubleResource();
	protected ScrollPane myContainerPane;
	protected double myPaneWidth;
	protected IDetailStore myDetailStore;
	protected IDesignArea myDesignArea;
	
	public AbstractCommandDetail() {
		double detailPaneWidth = ViewResources.DETAIL_PANE_WIDTH.getDoubleResource();
		double detailPaneHeight = ViewResources.SCENE_HEIGHT.getDoubleResource();
		double avatarZoneWidth = detailPaneWidth-2*ViewResources.AVATAR_ZONE_PADDING.getDoubleResource();
		double avatarZoneHeight = avatarZoneWidth;
		double detailPadding = ViewResources.COMMAND_DETAIL_PADDING.getDoubleResource();
		myPaneWidth = avatarZoneWidth;
		double myPaneHeight = detailPaneHeight - 4*detailPadding - avatarZoneHeight;
		myContainerPane = new ScrollPane();
		myContainerPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		myContainerPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		myContainerPane.setMinWidth(myPaneWidth);
		myContainerPane.setMaxWidth(myPaneWidth);
		myContainerPane.setMinHeight(myPaneHeight);
		myContainerPane.setMaxHeight(myPaneHeight);
		myContainerPane.setHmax(0);
		myContainerPane.setLayoutX(detailPaneWidth/2 - myPaneWidth/2 + 10);
		myContainerPane.setLayoutY(detailPadding);
		myContainerPane.setBackground(new Background(new BackgroundFill(ViewResources.DETAIL_PANE_BG.getColorResource(), 
									CornerRadii.EMPTY, Insets.EMPTY)));
		myContainerPane.getStylesheets().add("gameeditor/commanddetails/DetailPane.css");
	}
	
	abstract public void init();
    
    public void setDetailStore(IDetailStore detailStore){
    	myDetailStore = detailStore;
    }
    
    public void setDesignArea(IDesignArea da){
    	myDesignArea = da;
    }
	
	public ScrollPane getPane(){
		return myContainerPane;
	}

}
