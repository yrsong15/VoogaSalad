package gameeditor.commanddetails;

import java.util.Map;

import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.objects.GameObjectView;
import gameeditor.view.ViewResources;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SpriteTypeButton {

    private static final String X_POSITION_KEY =ISelectDetail.X_POSITION_KEY;
    private static final String Y_POSITION_KEY = ISelectDetail.Y_POSITION_KEY;
    private static final String SPRITE_WIDTH_KEY =IGameEditorData.WIDTH_KEY;
    private static final String SPRITE_HEIGHT_KEY =IGameEditorData.HEIGHT_KEY;

    private static final double DEFAULT_X = 0;
    private static final double DEFAULT_Y = 0;
    private static final double DEFAULT_WIDTH = 50;
    private static final double DEFAULT_HEIGHT = 50;
    
    private static final double X_LIMIT = ViewResources.SCENE_WIDTH.getDoubleResource() - ViewResources.TOOLBAR_WIDTH.getDoubleResource();
    private static final double INNER_X_LIMIT = ViewResources.SCENE_WIDTH.getDoubleResource() - ViewResources.TOOLBAR_WIDTH.getDoubleResource()-ViewResources.DETAIL_ZONE_PADDING.getDoubleResource();
    private static final double Y_LIMIT = ViewResources.TOOLBAR_HEIGHT.getDoubleResource();
    
    private double myX = 0;
    private double myY = 0;
    private double myWidth = 50;
    private double myHeight = 50;

    private Pane myPane;
    private Rectangle myBGRectangle;
    private String myFilePath;
    private Image myImage;
    private ImageView myImageView;
    private ImageView myTempImageView;
    private String myType;
    private IDesignArea myDesignArea;
    private IGameEditorData myDataStore;
    private IDetailPane myDetailPane;
    private boolean dragExited = false;
    private boolean dragEntering = false;

    public SpriteTypeButton(double width, double height, String filePath, String type, IDesignArea da, IGameEditorData dataStore, IDetailPane idp) {
        myDetailPane = idp;
        myFilePath = filePath;
        myImage = new Image(filePath);
        myPane = new Pane();
        myPane.setMaxWidth(width);
        myPane.setMaxHeight(height);
        myType = type;
        myDesignArea = da;
        myDataStore = dataStore;
        myPane.setOnMouseDragged((e) -> handlePaneDrag(e.getX(), e.getY(), e.getSceneX(), e.getSceneY()));
        myPane.setOnMousePressed((e) -> handlePress());
        myPane.setOnMouseReleased((e) -> handleRelease());
        setBGRect(width, height, 10);
        setImage(myImage);

    }

    public Pane getPane(){
        return myPane;
    }

    private void setBGRect(double width, double height, double arc){
        myBGRectangle = new Rectangle(width, height); 
        myBGRectangle.setArcWidth(arc);
        myBGRectangle.setArcHeight(arc);
        myBGRectangle.setFill(Color.GHOSTWHITE);
        myPane.getChildren().add(myBGRectangle);
    }

    private void setImage(Image img){             
        double padding = 5;
        double fitWidth = myBGRectangle.getWidth() - padding;
        double fitHeight = myBGRectangle.getHeight() - padding;
        double widthRatio = fitWidth/img.getWidth();
        double heightRatio = fitHeight/img.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);
        double endWidth = img.getWidth()*ratio;
        double endHeight = img.getHeight()*ratio;
        myImageView = new ImageView(img);
        myImageView.setPreserveRatio(true);
        myImageView.setFitWidth(fitWidth);
        myImageView.setFitHeight(fitHeight);
        myImageView.setLayoutX(myBGRectangle.getX() + myBGRectangle.getWidth()/2 - endWidth/2);
        myImageView.setLayoutY(myBGRectangle.getY() + myBGRectangle.getHeight()/2 - endHeight/2);
        myPane.getChildren().add(myImageView);
    }

    private void handlePaneDrag(double x, double y, double sceneX, double sceneY){
        if (!dragEntering && dragExited && (myTempImageView != null && sceneX < X_LIMIT 
        		|| sceneY < Y_LIMIT)){
        	handleReentryLvl1();
        } else if (dragEntering) {
        	handleReentryLvl2();
        	myTempImageView.setLayoutX(x);
            myTempImageView.setLayoutY(y);
        } else if (!dragEntering && !dragExited && myTempImageView != null && sceneX > X_LIMIT 
                && sceneY > Y_LIMIT){
            myDesignArea.addDragIn(myTempImageView);
            myX = sceneX - (X_LIMIT);
            myY = sceneY - Y_LIMIT;
            myTempImageView.setLayoutX(myX);
            myTempImageView.setLayoutY(myY);
            dragExited = true;
        } else if (!dragEntering && myTempImageView != null && sceneX > X_LIMIT 
                && sceneY > Y_LIMIT){
            myX = sceneX - (X_LIMIT);
            myY = sceneY - Y_LIMIT;
            myTempImageView.setLayoutX(myX);
            myTempImageView.setLayoutY(myY);
        } else if (!dragEntering && myTempImageView != null && sceneX < INNER_X_LIMIT){
            myTempImageView.setLayoutX(x);
            myTempImageView.setLayoutY(y);
        }
    }

    private void handlePress(){
        double padding = 5;
        double fitWidth = myBGRectangle.getWidth() - padding;
        double fitHeight = myBGRectangle.getHeight() - padding;
        double widthRatio = fitWidth/myImage.getWidth();
        double heightRatio = fitHeight/myImage.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);
        double endWidth = myImage.getWidth()*ratio;
        double endHeight = myImage.getHeight()*ratio;
        myTempImageView = new ImageView (myImage);
        myTempImageView.setPreserveRatio(true);
        myTempImageView.setFitWidth(fitWidth);
        myTempImageView.setFitHeight(fitHeight);
        myPane.getChildren().add(myTempImageView);
    }
    
    private void handleReentryLvl1(){
    	myDesignArea.removeDragIn(myTempImageView);
    	dragExited = false;
    	dragEntering = true;
    }
    
    private void handleReentryLvl2(){
    	myPane.getChildren().add(myTempImageView);
    	dragEntering = false;
    }

    private void handleRelease(){
    	if (!dragExited){
    		
    	} else {
    		myDesignArea.removeDragIn(myTempImageView);
	        GameObjectView go = new GameObjectView(myFilePath, myX, myY, myWidth, myHeight, myType, false, myDesignArea, myDataStore);
	        Map<String, String> typeMap = myDataStore.getType(myType);

	        // Add the properties to the Map now
	        typeMap.put(X_POSITION_KEY, String.valueOf(myX));
	        typeMap.put(Y_POSITION_KEY, String.valueOf(myY));       
	        typeMap.put(SPRITE_WIDTH_KEY, String.valueOf(myWidth));
	        typeMap.put(SPRITE_HEIGHT_KEY, String.valueOf(myHeight));
    	}
        myX = DEFAULT_X;
        myY = DEFAULT_Y;
        myWidth = DEFAULT_WIDTH;
        myHeight = DEFAULT_HEIGHT;
        myPane.getChildren().remove(myTempImageView);
        dragExited = false;
        myTempImageView = null;
    }

}
