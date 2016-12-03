package gameeditor.commanddetails;

import java.util.Map;

import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.objects.GameObject;
import gameeditor.view.interfaces.IDesignArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SpriteTypeButton {
	
	private static final String X_POSITION_KEY = "xPosition";
	private static final String Y_POSITION_KEY = "yPosition";
	private static final String SPRITE_WIDTH_KEY ="width";
	private static final String SPRITE_HEIGHT_KEY ="height";
	
	private static final double DEFAULT_X = 0;
	private static final double DEFAULT_Y = 0;
	private static final double DEFAULT_WIDTH = 50;
	private static final double DEFAULT_HEIGHT = 50;
	
	private double myX = 0;
	private double myY = 0;
	private double myWidth = 50;
	private double myHeight = 50;
	
	private Pane myPane;
	private Rectangle myBGRectangle;
	private String myFilePath;
	private Image myImage;
	private ImageView myImageView;
	private String myType;
	private IDesignArea myDesignArea;
	private IGameEditorData myDataStore;
	
	public SpriteTypeButton(double width, double height, String filePath, String type, IDesignArea da, IGameEditorData dataStore) {
		myFilePath = filePath;
		myImage = new Image(filePath);
		myPane = new Pane();
		myType = type;
		myDesignArea = da;
		myDataStore = dataStore;
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
        myImageView.setOnMouseClicked((e) -> handleClick());
        myPane.getChildren().add(myImageView);
    }
	
	private void handleClick(){
		GameObject go = new GameObject(myFilePath, myX, myY, myWidth, myHeight, myType, myDesignArea, myDataStore);
		Map<String, String> typeMap = myDataStore.getType(myType);

		typeMap.put(X_POSITION_KEY, String.valueOf(myX));
        typeMap.put(Y_POSITION_KEY, String.valueOf(myY));
        
        // Create Random Generation here
        
        typeMap.put(SPRITE_WIDTH_KEY, String.valueOf(myWidth));
        typeMap.put(SPRITE_HEIGHT_KEY, String.valueOf(myHeight));
         
//        myDataStore.addGameObjectToLevel(typeMap, myRandomGenerationList);
        
        myX = DEFAULT_X;
    	myY = DEFAULT_Y;
    	myWidth = DEFAULT_WIDTH;
    	myHeight = DEFAULT_HEIGHT;
	}

}
