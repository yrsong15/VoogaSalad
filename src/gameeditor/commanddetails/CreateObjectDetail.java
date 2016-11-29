package gameeditor.commanddetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import gameeditor.objects.GameObject;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CreateObjectDetail extends AbstractCommandDetail {

	private Pane myImagePane;
	
	private TextArea myXTextArea;
	private TextArea myYTextArea;
	private String myFilePath = "";
	private ImageView myIV;
	private VBox myVBox;
	private GameObject myGO;

	private ComboBox<String> myType;
	public static final String X_POSITON_KEY = "xPosition";
	public static final String Y_POSITION_KEY = "yPosition";
	
	public CreateObjectDetail() {
		super();
	}
	
	public void init(){
		myVBox = new VBox();
		myVBox.setSpacing(myDetailPadding);
		myVBox.setAlignment(Pos.CENTER);
		myContainerPane.setContent(myVBox);
		createTypeChoice();
		createPos();
		createImageZone();
		createSavePreview();
	}
	
	public void createSavePreview(){
		Button save = createSave();
		Button preview = createPreview();
		HBox container = new HBox();
		container.setSpacing(hboxSpacing);
		container.setAlignment(Pos.CENTER);
		container.getChildren().add(preview);
		container.getChildren().add(save);
		myVBox.getChildren().add(container);
	}
	
	public Button createPreview(){
		Button preview = new Button();
		preview.setText(DetailResources.PREVIEW_BUTTON_TEXT.getResource());
		preview.setMinWidth((paddedPaneWidth - hboxSpacing)/2);
		preview.setMaxWidth((paddedPaneWidth - hboxSpacing)/2);
		preview.setMinHeight(cbHeight);
		preview.setOnAction((e) -> {handlePreview();});
		return preview;
	}
	
	public Button createSave(){
		Button save = new Button();
		save.setText(DetailResources.SAVE_BUTTON_TEXT.getResource());
		save.setMinWidth((paddedPaneWidth - hboxSpacing)/2);
		save.setMaxWidth((paddedPaneWidth - hboxSpacing)/2);
		save.setMinHeight(cbHeight);
		save.setOnAction((e) -> {handleSave();});
		return save;
	}
	
	//TODO: ADD DATA VERIFICATION TO SAVE
    public void handleSave(){
        Map<String, String> typeMap = myDataStore.getType(myType.getValue());
        String xString = myXTextArea.getText();
        String yString = myYTextArea.getText();
        double x = Double.parseDouble(xString);
        double y = Double.parseDouble(yString);
        typeMap.put(X_POSITON_KEY, String.valueOf(x));
        typeMap.put(Y_POSITION_KEY, String.valueOf(y));

        myDataStore.addGameObjectToLevel(typeMap);

    }
	
	public void handlePreview(){
		String xString = myXTextArea.getText();
		String yString = myYTextArea.getText();
		double x = Double.parseDouble(xString);
		double y = Double.parseDouble(yString);
		if (myGO == null){
			myGO = new GameObject(myFilePath, x, y, 50, 50, myType.getValue(), myDesignArea);
		} else {
			myGO.removeSelf();
			myGO = new GameObject(myFilePath, x, y, 50, 50, myType.getValue(), myDesignArea);
		}
	}
	
	public void createPos(){
		myXTextArea = createPosBP("X Pos: ");
		myYTextArea = createPosBP("Y Pos: ");
	}
	
	public TextArea createPosBP(String label){
		BorderPane bp = new BorderPane();
		bp.setMinWidth(paddedPaneWidth);
		bp.setMaxWidth(paddedPaneWidth);
		Label labl = createLbl(label);
		TextArea ta = new TextArea();
		ta.setText("0");
		ta.setMinWidth(cbWidth); ta.setMaxWidth(cbWidth);
		ta.setMinHeight(cbHeight); ta.setMaxHeight(cbHeight);
		ta.setOnMouseClicked((e) -> handleClick(ta));
		bp.setLeft(labl);
		bp.setRight(ta);
		BorderPane.setAlignment(labl, Pos.CENTER_LEFT);
		myVBox.getChildren().add(bp);
		return ta;
	}
	
	public void createImageZone(){
		double imageZoneWidth = DetailResources.OBJECT_IMAGE_ZONE_WIDTH.getDoubleResource();
		double imageZoneHeight = DetailResources.OBJECT_IMAGE_ZONE_HEIGHT.getDoubleResource();
		myImagePane = new Pane();
		myImagePane.setMinWidth(imageZoneWidth); myImagePane.setMaxWidth(imageZoneWidth);
		myImagePane.setMaxHeight(imageZoneHeight); myImagePane.setMaxHeight(imageZoneHeight);
		Rectangle imageZone = new Rectangle(imageZoneWidth, imageZoneHeight, Color.GHOSTWHITE);
		myImagePane.getChildren().add(imageZone);
		imageZone.setArcHeight(DetailResources.OBJECT_IMAGE_ZONE_PADDING.getDoubleResource()); imageZone.setArcWidth(DetailResources.OBJECT_IMAGE_ZONE_PADDING.getDoubleResource());
		myVBox.getChildren().add(myImagePane);
	}
	
	public void createImageView(){
		Image i = new Image(myFilePath);
		double imageZonePadding = DetailResources.OBJECT_IMAGE_ZONE_PADDING.getDoubleResource();
		double imageZoneWidth = DetailResources.OBJECT_IMAGE_ZONE_WIDTH.getDoubleResource();
		double imageZoneHeight = DetailResources.OBJECT_IMAGE_ZONE_HEIGHT.getDoubleResource();
		double fitWidth = imageZoneWidth-imageZonePadding;
		double fitHeight = imageZoneHeight-imageZonePadding;
		double widthRatio = fitWidth/i.getWidth();
        double heightRatio = fitHeight/i.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);
        double endWidth = i.getWidth()*ratio;
        double endHeight = i.getHeight()*ratio;
		myIV = new ImageView(i);
		myIV.setFitWidth(fitWidth);
		myIV.setFitHeight(fitHeight);
		myIV.setPreserveRatio(true);
		myIV.setLayoutX(imageZoneWidth/2 - endWidth/2);
		myIV.setLayoutY(imageZoneHeight/2 - endHeight/2);
		myIV.setLayoutX(imageZonePadding/2); myIV.setLayoutY(imageZonePadding/2);
		myImagePane.getChildren().add(myIV);
	}
	
	public void createTypeChoice(){
		myType = new ComboBox<String>();
		myType.setMinWidth(paddedPaneWidth);
		myType.setMaxWidth(paddedPaneWidth);
		myType.setMinHeight(cbHeight);
		myType.setMaxHeight(cbHeight);
		myType.getItems().addAll(myDataStore.getTypes());
		myType.setValue(DetailResources.DEFAULT_OBJECT_TYPE.getResource());
		myType.setOnAction((e) -> {handleTypeSelection(myType);});
		myVBox.getChildren().add(myType);
	}
	
	public void handleTypeSelection(ComboBox<String> cb){
		String value = cb.getValue();
		Map<String, String> myType = myDataStore.getType(value);
		myFilePath = myType.get(DetailResources.IMAGE_PATH.getResource());
		createImageView();
	}
	
	public Label createLbl(String property){
		Label labl = new Label (property);
		return labl;
	}
	
	public void handleClick(TextArea field){
		field.setText("");
	}

	public void createTextField(){
		
	}

}
