package gameeditor.commanddetails;

import java.util.HashMap;
import java.util.Map;
import gameeditor.controller.interfaces.IGameEditorData;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
/**
 * 
 * @author Pratiksha Sharma
 *
 */
public class PlatformDetail {
    public static final String [] PLATFORM_INTERSECTABLE_OPTIONS = DetailResources.PLATFORM_INTERSECTABLE_OPTIONS.getArrayResource();
    public static final String PLATFORM_NON_INTERSECTIBLE_LABEL = DetailResources.PLATFORM_NON_INTERSECTIBLE_LABEL.getResource();
    public static final String[] PLATFORM_NON_INTERSECTABLE_OPTIONS = DetailResources.PLATFORM_NON_INTERSECTABLE_OPTIONS.getArrayResource();

    private IGameEditorData myDataStore;
    private VBox myVBox;
    private ImageDetail myImageDetail;
    private DetailFrontEndUtil myDetailFrontEndUtil;
    private TextArea myTypeTextArea;
   
    private BorderPane myNonIntersectableOptionBP;
    private BorderPane myIntersectableBP;
    private ComboBox<String> nonInterSectableCombo;
    private String imageFilePath;


    public PlatformDetail(IGameEditorData dataStore){  
        myDataStore = dataStore;
        myImageDetail = new ImageDetail();
        myDetailFrontEndUtil = new DetailFrontEndUtil();

    }

    public VBox getTabContent(){
        myVBox = new VBox();
        myVBox.setSpacing(IAbstractCommandDetail.MY_DETAIL_PADDING);
        myTypeTextArea = myDetailFrontEndUtil.createTypeName();
        myVBox.getChildren().add(myTypeTextArea);
        createPlatformProperties();
        myVBox.getChildren().add(myImageDetail.createImageChoose());
        createSave(e-> handleSavePlatform()); 

        return myVBox;
    }

    private void createPlatformProperties(){
        String defaultProperty = DetailDefaultsResources.PLATFORM_NON_INTERSECTABLE.getResource();
        ComboBox<String> intersectable = myDetailFrontEndUtil.createComboBox(PLATFORM_INTERSECTABLE_OPTIONS, defaultProperty);

        intersectable.setOnAction(e -> handleIntersectibleProperty(intersectable));
        myIntersectableBP = myDetailFrontEndUtil.createBorderpane(intersectable,(myDetailFrontEndUtil.createPropertyLbl(PLATFORM_NON_INTERSECTIBLE_LABEL)));
        myVBox.getChildren().add(myIntersectableBP);
    }

    private void handleIntersectibleProperty(ComboBox<String> combo){
        if(combo.getValue()=="False"){
            String defaultVal = DetailDefaultsResources.PLATFORM_INTERSECTABLE.getResource();
            nonInterSectableCombo = myDetailFrontEndUtil.createComboBox(PLATFORM_NON_INTERSECTABLE_OPTIONS, defaultVal);
            String label = DetailResources.NON_INTERSECTABLE_SIDES_LABEL.getResource();
            myNonIntersectableOptionBP = myDetailFrontEndUtil.createBorderpane( nonInterSectableCombo,myDetailFrontEndUtil.createPropertyLbl(label));
            int index = myVBox.getChildren().indexOf(myIntersectableBP);
            myVBox.getChildren().add(index+1, myNonIntersectableOptionBP);

        } else if((combo.getValue().equals("True"))){
            if(myVBox.getChildren().contains(myNonIntersectableOptionBP)){
                myVBox.getChildren().remove(myVBox.getChildren().indexOf(myNonIntersectableOptionBP));
            }
        }
    }

    private void handleSavePlatform(){
        if(myDataStore.getType(myTypeTextArea.getText())==null){
            Map<String,String> propertiesMap = new HashMap<String,String>();
            getPlatFormProperties(propertiesMap);
            propertiesMap.put(DetailResources.TYPE_NAME.getResource(),myTypeTextArea.getText());
            imageFilePath = myImageDetail.getFilePath();
            propertiesMap.put(DetailResources.IMAGE_PATH.getResource(), imageFilePath);
            myDataStore.storeType(propertiesMap);
        }
    }

    private void getPlatFormProperties(Map<String,String> propertiesMap){
        if(nonInterSectableCombo!=null){
            if(nonInterSectableCombo.getValue().toString().equals("Both")){
                propertiesMap.put(DetailResources.NON_INTERSECTABLE_KEY.getResource(), "True");
            }else {
                propertiesMap.put(DetailResources.ONE_SIDE_NON_INTERSECTABLEKEY.getResource(), nonInterSectableCombo.getValue().toString());
            }
        }

    }

    private void createSave(EventHandler<MouseEvent> handler){
        Button save = myDetailFrontEndUtil.createButton("SaveCommand",handler);
        myVBox.getChildren().add(save);
    }

}
