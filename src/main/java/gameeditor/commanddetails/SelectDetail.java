package gameeditor.commanddetails;

import java.util.Map;
import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.objects.GameObjectView;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class SelectDetail extends AbstractSelectDetail {

    public SelectDetail() {
        super();
    }
    
    @Override
	public void init() {
        addVBoxSettings();
        myDesignArea.enableClick(this);
        addSelectLabel();
	}
    
    @Override
    public void initLevel2(GameObjectView sprite){
    	init();
        myGO = sprite;
        mySelectLabel.setTextFill(Color.LIGHTGREY);
        createTypeLabel();
        createPos();

        String typeName = myGO.getType();
        Map<String, String> typeMap = myDataStore.getType(typeName);
        String randomGen = typeMap.get(DetailResources.RANDOM_GEN_KEY.getResource());
        if(randomGen != null && randomGen.equals("True")){
            createProperties();
            createUpdate() ;
        } else{
            createUpdate();
        }
    }

    private void createUpdate(){
        Button update = new Button();
        update.setText(DetailResources.UPDATE_BUTTON_TEXT.getResource());
        update.setMinWidth((PADDED_PANE_WIDTH - HBOX_SPACING)/2);
        update.setMaxWidth((PADDED_PANE_WIDTH - HBOX_SPACING)/2);
        update.setMinHeight(CB_HEIGHT);
        update.setOnAction((e) -> {handleUpdate();});
        myVBox.getChildren().add(update);
    }

    private void handleUpdate() {
        String xString = myXTextArea.getText();
        String yString = myYTextArea.getText();
        String widthString = myWidthTextArea.getText();
        String heightString = myHeightTextArea.getText();
        double x = Double.parseDouble(xString.substring(X_LABEL.length()));
        double y = Double.parseDouble(yString.substring(Y_LABEL.length()));
        double width = Double.parseDouble(widthString.substring(WIDTH_LABEL.length()));
        double height = Double.parseDouble(heightString.substring(HEIGHT_LABEL.length()));

        myGO.update(x, y, width, height);

        // Update Data in the Back End
        Map<String, String> typeMap = myDataStore.getType(myGO.getType());
        typeMap.put(ISelectDetail.X_POSITION_KEY,xString.substring(X_LABEL.length()));
        typeMap.put(ISelectDetail.Y_POSITION_KEY, yString.substring(Y_LABEL.length()));
        typeMap.put(IGameEditorData.WIDTH_KEY,widthString.substring(WIDTH_LABEL.length()));
        typeMap.put(IGameEditorData.HEIGHT_KEY, heightString.substring(HEIGHT_LABEL.length()));
        
        
        String randomGen = typeMap.get(DetailResources.RANDOM_GEN_KEY.getResource());
       if(randomGen!=null && randomGen.equals("True")){ 
           myDataStore.addRandomGeneration(myGO.getType(), myRandomGenerationList);
       }
    }   
    
    public void switchSelectStyle(GameObjectView sprite){
    	if (sprite.getIsMainChar()){
        	myDetailPane.setDetail("MainCharacter");
        	
    	}
    }
}
