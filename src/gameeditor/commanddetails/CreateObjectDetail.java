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

	private ComboBox<String> myType;
	public static final String X_POSITION_KEY = "xPosition";
	public static final String Y_POSITION_KEY = "yPosition";
	public static final String SPRITE_WIDTH_KEY ="width";
	public static final String SPRITE_HEIGHT_KEY ="height";
	
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
//		String value = cb.getValue();
//		Map<String, String> myType = myDataStore.getType(value);
//		myFilePath = myType.get(DetailResources.IMAGE_PATH.getResource());
		for (String typeName : types){
			Map<String, String> type = myDataStore.getType(typeName);
			String filePath = type.get(DetailResources.IMAGE_PATH.getResource());
			double size = paddedPaneWidth/2-myDetailPadding;
			SpriteTypeButton stb = new SpriteTypeButton(size, size, filePath, typeName, myDesignArea, myDataStore);
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
	
//	public void createSavePreview(){
//		Button save = createSave();
//		Button preview = createPreview();
//		HBox container = new HBox();
//		container.setSpacing(hboxSpacing);
//		container.setAlignment(Pos.CENTER);
//		container.getChildren().add(preview);
//		container.getChildren().add(save);
//		myVBox.getChildren().add(container);
//	}
	
//	public Button createPreview(){
//		Button preview = new Button();
//		preview.setText(DetailResources.PREVIEW_BUTTON_TEXT.getResource());
//		preview.setMinWidth((paddedPaneWidth - hboxSpacing)/2);
//		preview.setMaxWidth((paddedPaneWidth - hboxSpacing)/2);
//		preview.setMinHeight(cbHeight);
//		preview.setOnAction((e) -> {handlePreview();});
//		return preview;
//	}
//	
//	public Button createSave(){
//		Button save = new Button();
//		save.setText(DetailResources.SAVE_BUTTON_TEXT.getResource());
//		save.setMinWidth((paddedPaneWidth - hboxSpacing)/2);
//		save.setMaxWidth((paddedPaneWidth - hboxSpacing)/2);
//		save.setMinHeight(cbHeight);
//		save.setOnAction((e) -> {handleSave();});
//		return save;
//	}
	
//	//TODO: ADD DATA VERIFICATION TO SAVE
//    public void handleSave(){
//        Map<String, String> typeMap = myDataStore.getType(myType.getValue());
//        
//        String xString = myXTextArea.getText();
//        String yString = myYTextArea.getText();
//        String width = mySpriteWidth.getText();
//        String height = mySpriteHeight.getText();
//        
//        double x = Double.parseDouble(xString);
//        double y = Double.parseDouble(yString);
//        typeMap.put(X_POSITION_KEY, String.valueOf(x));
//        typeMap.put(Y_POSITION_KEY, String.valueOf(y));
//        
//        // Create Random Generation here
//        
//        typeMap.put(SPRITE_HEIGHT_KEY,height);
//        typeMap.put(SPRITE_WIDTH_KEY, width);
//         
//        myDataStore.addGameObjectToLevel(typeMap,myRandomGenerationList);
//        
//    }
	
//	public void handlePreview(){
//		String xString = myXTextArea.getText();
//		String yString = myYTextArea.getText();
//	    String width = mySpriteWidth.getText();
//	    String height = mySpriteHeight.getText();
//		double x = Double.parseDouble(xString);
//		double y = Double.parseDouble(yString);
//		double spriteWidth = Double.parseDouble(width);
//        double spriteHeight = Double.parseDouble(height);
//		if (myGO == null){
//			myGO = new GameObject(myFilePath, x, y, spriteWidth, spriteHeight, myType.getValue(), myDesignArea);
//		} else {
//			myGO.removeSelf();
//			myGO = new GameObject(myFilePath, x, y, spriteWidth, spriteHeight, myType.getValue(), myDesignArea);
//		}
//	}

}
