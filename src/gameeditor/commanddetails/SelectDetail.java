package gameeditor.commanddetails;

import gameeditor.objects.GameObject;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SelectDetail extends AbstractCommandDetail implements ISelectDetail {
	
	private VBox myVBox = new VBox();
	
	private Label mySelectLabel;
	private TextArea myXTextArea;
	private TextArea myYTextArea;
	private ImageView myIV;
	private GameObject myGO;
	private Pane myImagePane;

	private String myType;
	public static final String X_POSITON_KEY = "xPosition";
	public static final String y_POSITION_KEY = "yPosition";
	

	public SelectDetail() {
		super();
	}

	@Override
	public void init() {
		myVBox = new VBox();
		myVBox.setSpacing(myDetailPadding);
		myVBox.setAlignment(Pos.CENTER);
		myContainerPane.setContent(myVBox);	
		myDesignArea.enableClick(this);
		addSelectLabel();
	}
	
	public void initLevel2(GameObject sprite){
		myGO = sprite;
		mySelectLabel.setTextFill(Color.LIGHTGREY);
		createTypeLabel();
		createPos();
		createImageZone();
		createImageView();
		createUpdate();
	}
	
	public void createUpdate(){
		Button update = new Button();
		update.setText(DetailResources.UPDATE_BUTTON_TEXT.getResource());
		update.setMinWidth((paddedPaneWidth - hboxSpacing)/2);
		update.setMaxWidth((paddedPaneWidth - hboxSpacing)/2);
		update.setMinHeight(cbHeight);
		update.setOnAction((e) -> {handleUpdate();});
		myVBox.getChildren().add(update);
	}
	
	private void handleUpdate() {
		myGO.getImageView().setLayoutX(Double.parseDouble(myXTextArea.getText()));
		myGO.getImageView().setLayoutY(Double.parseDouble(myYTextArea.getText()));		
	}

	private void addSelectLabel(){
		BorderPane bp = new BorderPane();
		mySelectLabel = new Label(DetailResources.SELECT_LABEL_TEXT.getResource());
		bp.setCenter(mySelectLabel);
		bp.setMinWidth(paddedPaneWidth);
		bp.setMaxWidth(paddedPaneWidth);
		myVBox.getChildren().add(bp);
	}	
	
	public void createPos(){
		myXTextArea = createPosBP("X Pos: ", myGO.getImageView().getLayoutX());
		myYTextArea = createPosBP("Y Pos: ", myGO.getImageView().getLayoutY());
	}
	
	public TextArea createPosBP(String label, double locationValue){
		BorderPane bp = new BorderPane();
		bp.setMinWidth(paddedPaneWidth);
		bp.setMaxWidth(paddedPaneWidth);
		Label labl = createLbl(label);
		TextArea ta = new TextArea();
		ta.setText(Double.toString(locationValue));
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
		Image i = myGO.getImageView().getImage();
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
	
	public void createTypeLabel(){
		myType = myGO.getType();
		BorderPane bp = new BorderPane();
		mySelectLabel = new Label(myType);
		bp.setCenter(mySelectLabel);
		bp.setMinWidth(paddedPaneWidth);
		bp.setMaxWidth(paddedPaneWidth);
		myVBox.getChildren().add(bp);
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
