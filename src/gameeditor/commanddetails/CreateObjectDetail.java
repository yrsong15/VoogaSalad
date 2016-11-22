package gameeditor.commanddetails;

import java.util.ArrayList;
import java.util.Map;

import gameeditor.objects.GameObject;
import gameeditor.view.ViewResources;
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
	
	private double cbWidth = 7*ViewResources.AVATAR_ZONE_WIDTH.getDoubleResource()/15 - myDetailPadding;
	private double cbHeight = 30;
	private Pane myImagePane;
	
	private TextArea myXTextArea;
	private TextArea myYTextArea;
	private String myFilePath = "";
	private ImageView myIV;
	private ImageView myPreviewImageView;
	private VBox myPropertiesVBox;
	private GameObject myGO;

	private ComboBox<String> myType;
	
	public CreateObjectDetail() {
		super();
	}
	
	public void init(){
		myPropertiesVBox = new VBox();
		myPropertiesVBox.setSpacing(myDetailPadding);
		myPropertiesVBox.setAlignment(Pos.CENTER);
		myContainerPane.setContent(myPropertiesVBox);
		createTypeChoice();
		createPos();
		createImageZone();
		//TODO: Create Preview
		createSavePreview();
	}
	
	public void createSavePreview(){
		Button save = createSave();
		Button preview = createPreview();
		HBox container = new HBox();
		double hboxSpacing = DetailResources.DETAIL_CONTENT_PADDING.getDoubleResource();
		container.setSpacing(hboxSpacing);
		container.setAlignment(Pos.CENTER);
		container.getChildren().add(preview);
		container.getChildren().add(save);
		myPropertiesVBox.getChildren().add(container);
	}
	
	public Button createPreview(){
		Button preview = new Button();
		preview.setText("Preview Object");
		preview.setMinWidth(cbWidth);
		preview.setMinHeight(cbHeight);
		preview.setOnAction((e) -> {handlePreview();});
		return preview;
	}
	
	public Button createSave(){
		Button save = new Button();
		save.setText("Save Object");
		save.setMinWidth(cbWidth);
		save.setMinHeight(cbHeight);
		save.setOnAction((e) -> {handleSave();});
		return save;
	}
	
	//TODO: ADD DATA VERIFICATION TO SAVE
	public void handleSave(){
		Map<String, String> typeMap = myDetailStore.getType(myType.getValue());
		String xString = myXTextArea.getText();
		String yString = myYTextArea.getText();
		double x = Double.parseDouble(xString);
		double y = Double.parseDouble(yString);
	}
	
	public void handlePreview(){
		String xString = myXTextArea.getText();
		String yString = myYTextArea.getText();
		double x = Double.parseDouble(xString);
		double y = Double.parseDouble(yString);
		if (myGO == null){
			myGO = new GameObject(myFilePath, x, y, 50, 50, myDesignArea);
		} else {
			myGO.removeSelf();
			myGO = new GameObject(myFilePath, x, y, 50, 50, myDesignArea);
		}
	}
	
	public void createPos(){
		myXTextArea = createPosBP("X Pos: ");
		myYTextArea = createPosBP("Y Pos: ");
	}
	
	public TextArea createPosBP(String label){
		BorderPane bp = new BorderPane();
		bp.setMinWidth(myPaneWidth-4*myDetailPadding);
		bp.setMaxWidth(myPaneWidth-4*myDetailPadding);
		Label labl = createLbl(label);
		TextArea ta = new TextArea();
		ta.setText("0");
		ta.setMinWidth(cbWidth); ta.setMaxWidth(cbWidth);
		ta.setMinHeight(cbHeight); ta.setMaxHeight(cbHeight);
		ta.setOnMouseClicked((e) -> handleClick(ta));
		bp.setLeft(labl);
		bp.setRight(ta);
		bp.setAlignment(labl, Pos.CENTER_LEFT);
		myPropertiesVBox.getChildren().add(bp);
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
		myPropertiesVBox.getChildren().add(myImagePane);
	}
	
	public void createImageView(){
		Image i = new Image(myFilePath);
		double aspectRatio = i.getWidth()/i.getHeight();
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
		ImageView iv = new ImageView(i);
		myIV = new ImageView(i);
		iv.setFitWidth(fitWidth);
		iv.setFitHeight(fitHeight);
		iv.setPreserveRatio(true);
		iv.setLayoutX(imageZoneWidth/2 - endWidth/2);
        iv.setLayoutY(imageZoneHeight/2 - endHeight/2);
		iv.setLayoutX(imageZonePadding/2); iv.setLayoutY(imageZonePadding/2);
		myImagePane.getChildren().add(iv);
		System.out.println(myImagePane.getChildren());
	}
	
	public void createTypeChoice(){
		myType = new ComboBox<String>();
		myType.setMinWidth(myPaneWidth-4*myDetailPadding);
		myType.setMaxWidth(myPaneWidth-4*myDetailPadding);
		myType.setMinHeight(cbHeight);
		myType.setMaxHeight(cbHeight);
		myType.getItems().addAll(myDetailStore.getTypes());
		myType.setValue(DetailResources.DEFAULT_OBJECT_TYPE.getResource());
		myType.setOnAction((e) -> {handleTypeSelection(myType);});
		myPropertiesVBox.getChildren().add(myType);
	}
	
	public void handleTypeSelection(ComboBox<String> cb){
		String value = cb.getValue();
		Map<String, String> myType = myDetailStore.getType(value);
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
