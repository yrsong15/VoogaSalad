package gameeditor.commanddetails;

import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.view.ViewResources;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;
import javafx.scene.control.ScrollPane;

public interface IAbstractCommandDetail {

    public static  final double MY_DETAIL_PADDING = DetailResources.DETAIL_CONTENT_PADDING.getDoubleResource();
    public static final  double DETAIL_PANE_WIDTH = ViewResources.DETAIL_PANE_WIDTH.getDoubleResource();
    public static  final double DETAIL_PANE_HEIGHT = ViewResources.SCENE_HEIGHT.getDoubleResource();
    public static  final double DETAIL_ZONE_WIDTH = DETAIL_PANE_WIDTH-2*ViewResources.DETAIL_ZONE_PADDING.getDoubleResource();
    public static  final double DETAIL_ZONE_HEIGHT = ViewResources.DETAIL_ZONE_HEIGHT.getDoubleResource();
    public static  final double DETAIL_PADDING = ViewResources.COMMAND_DETAIL_PADDING.getDoubleResource();
    public static  final double MY_PANE_WIDTH = DETAIL_ZONE_WIDTH;
    public static  final double MY_PANE_HEIGHT = DETAIL_ZONE_HEIGHT;
    


    public static  final double CB_WIDTH = 7*ViewResources.DETAIL_ZONE_WIDTH.getDoubleResource()/15 - MY_DETAIL_PADDING;
    public static  final double CB_HEIGHT = 30;
    public static  final double HBOX_SPACING = DetailResources.DETAIL_CONTENT_PADDING.getDoubleResource();
    public static  final double PADDED_PANE_WIDTH = MY_PANE_WIDTH-2*MY_DETAIL_PADDING;
    public static  final double PADDED_DETAIL_WIDTH = PADDED_PANE_WIDTH-CB_WIDTH-HBOX_SPACING;
    
    public static final String PLATFORM_KEY= DetailResources.PLATFORM_KEY.getResource();
    public static final String PLATFORM_LABEL=DetailResources.PLATFORM_LABEL.getResource();
    
   
    public ScrollPane getPane();
    
    public abstract void init();
    
    public void setDataStore(IGameEditorData ged);
    public void setDesignArea(IDesignArea da);
    public void setDetailPane(IDetailPane idp);
    
    
    
    
 
}
