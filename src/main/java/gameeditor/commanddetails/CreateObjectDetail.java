package gameeditor.commanddetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import gameeditor.objects.GameObject;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CreateObjectDetail extends AbstractCommandDetail {
        
        private VBox myVBox;
        private GameObject myGO;
        
        private List<TextArea>myRandomGenerationList = new ArrayList<TextArea>();
        String[] myRandomGenerationParameters = DetailResources.RANDOM_GENERATION_PARAMETERS.getArrayResource();
        
        public CreateObjectDetail() {
                super();
        }
        
        public void init(){
                myVBox = new VBox();
                myVBox.setSpacing(myDetailPadding);
                myVBox.setAlignment(Pos.CENTER);
                myContainerPane.setContent(myVBox);
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
                        double size = paddedPaneWidth/2-myDetailPadding;
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
                        bp.setMinWidth(paddedPaneWidth);
                        bp.setMaxWidth(paddedPaneWidth);
                        myVBox.getChildren().add(bp);
                }
                if (sprites.size()%2 != 0){
                        myVBox.getChildren().add(sprites.get(sprites.size()-1).getPane());
                }
        }

}
