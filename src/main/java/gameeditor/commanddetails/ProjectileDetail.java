package gameeditor.commanddetails;

import gameeditor.controller.interfaces.IGameEditorData;
import javafx.scene.layout.VBox;

public class ProjectileDetail {
    private IGameEditorData myDataStore;
    private VBox myVBox;
    private DetailFrontEndUtil myDetailFrontEndUtil;
    private ImageDetail myImageDetail;
    
    public ProjectileDetail(IGameEditorData dataStore){
        myVBox = new VBox();
        myDataStore = dataStore;
        myImageDetail= new ImageDetail();
        myDetailFrontEndUtil = new DetailFrontEndUtil();
    }
    
    public VBox getTabContent(){
        
        return myVBox;
    }
    

}
