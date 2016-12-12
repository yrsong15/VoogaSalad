package gameeditor.rpg.commanddetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import gameeditor.objects.GameObjectView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class CreateObjectDetail extends AbstractCommandDetail {
    private GameObjectView myGO;

    private List<TextArea>myRandomGenerationList = new ArrayList<TextArea>();
    String[] myRandomGenerationParameters = DetailResources.RANDOM_GENERATION_PARAMETERS.getArrayResource();

    public CreateObjectDetail() {
        super();
    }

    public void init(){
        addVBoxSettings();
        createTypeImages();
        //createSavePreview();
    }

    private void createTypeImages(){

        ArrayList<String> types = myDataStore.getTypes();
        
        ArrayList<SpriteTypeButton> sprites = new ArrayList<SpriteTypeButton>();
        //              String value = cb.getValue();
        //              Map<String, String> myType = myDataStore.getType(value);
        //              myFilePath = myType.get(DetailResources.IMAGE_PATH.getResource());
        for (String typeName : types){
            Map<String, String> type = myDataStore.getType(typeName);
            String filePath = type.get(DetailResources.IMAGE_PATH.getResource());
            double size = PADDED_PANE_WIDTH/2-MY_DETAIL_PADDING;
            SpriteTypeButton stb = new SpriteTypeButton(size, size, filePath, typeName, myDesignArea, myDataStore, myDetailPane);
            sprites.add(stb);
        }
        positionTypeImages(sprites);            
    }

    private void positionTypeImages(ArrayList<SpriteTypeButton> sprites){
        for(int i = 0; i+1 < sprites.size(); i += 2){
            BorderPane bp = new BorderPane();
            bp.setLeft(sprites.get(i).getPane());
            bp.setRight(sprites.get(i+1).getPane());
            bp.setMinWidth(PADDED_PANE_WIDTH);
            bp.setMaxWidth(PADDED_PANE_WIDTH);
            myVBox.getChildren().add(bp);
        }
        if (sprites.size()%2 != 0){
            myVBox.getChildren().add(sprites.get(sprites.size()-1).getPane());
        }
    }

}
