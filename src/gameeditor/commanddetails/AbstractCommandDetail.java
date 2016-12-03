package gameeditor.commanddetails;

import gameeditor.controller.interfaces.IGameEditorData;
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
	protected IGameEditorData myDataStore;
	protected IDesignArea myDesignArea;
	
	protected double detailPaneWidth = ViewResources.DETAIL_PANE_WIDTH.getDoubleResource();
	protected double detailPaneHeight = ViewResources.SCENE_HEIGHT.getDoubleResource();
	protected double detailZoneWidth = detailPaneWidth-2*ViewResources.DETAIL_ZONE_PADDING.getDoubleResource();
	protected double detailZoneHeight = ViewResources.DETAIL_ZONE_HEIGHT.getDoubleResource();
	protected double detailPadding = ViewResources.COMMAND_DETAIL_PADDING.getDoubleResource();
	protected double myPaneWidth = detailZoneWidth;
	protected double myPaneHeight = detailZoneHeight;

	
	protected double cbWidth = 7*ViewResources.DETAIL_ZONE_WIDTH.getDoubleResource()/15 - myDetailPadding;
	protected double cbHeight = 30;
	protected double hboxSpacing = DetailResources.DETAIL_CONTENT_PADDING.getDoubleResource();
	protected double paddedPaneWidth = myPaneWidth-2*myDetailPadding;
	protected double paddedDetailWidth = paddedPaneWidth-cbWidth-hboxSpacing;
	
	public AbstractCommandDetail() {
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
    
    public void setDataStore(IGameEditorData ged){
    	myDataStore = ged;
    }
    
    public void setDesignArea(IDesignArea da){
    	myDesignArea = da;
    }
	
	public ScrollPane getPane(){
		return myContainerPane;
	}

}
