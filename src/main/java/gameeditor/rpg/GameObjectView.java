package gameeditor.rpg;
import java.util.HashMap;
import java.util.Map;
import gameeditor.commanddetails.DetailResources;
import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.rpg.IGridDesignArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameObjectView {
    private static final double DEFAULT_X = ObjectResources.DEFAULT_X.getDoubleResource();
    private static final double DEFAULT_Y = ObjectResources.DEFAULT_Y.getDoubleResource();
    private static final double DEFAULT_WIDTH = ObjectResources.DEFAULT_WIDTH.getDoubleResource();
    private static final double DEFAULT_HEIGHT = ObjectResources.DEFAULT_HEIGHT.getDoubleResource();

    private static final String X_POSITION_KEY = "xPosition";
    private static final String Y_POSITION_KEY = "yPosition";
    private static final String SPRITE_WIDTH_KEY ="width";
    private static final String SPRITE_HEIGHT_KEY ="height";

    private Image myImage;
    private String myImageFilePath;
    private ImageView myImageView;
    private double myOriginalImageWidth;
    private double myOriginalImageHeight;
    private double myImageWidth;
    private double myImageHeight;
    private double myRatio;
    private IGridDesignArea myDesignArea;
    private String myType;
    private IGameEditorData myDataStore;
    private boolean myIsMainChar;
    private Cell myCell;

    private double xDistanceFromCorner = 0;
    private double yDistanceFromCorner = 0;
    
    private double multiOriginX = 0;
    private double multiOriginY = 0;

    public GameObjectView(String imageFilePath, double x, double y, String type, boolean isMainChar, Cell cell, IGridDesignArea da, IGameEditorData dataStore) {
        myDataStore = dataStore;
        myDesignArea = da;
        myType = type;
        myImageFilePath = imageFilePath;
        myIsMainChar = isMainChar;
        myCell = cell;
        myImage = new Image(myImageFilePath); 
        myImageView = new ImageView(myImage);
        myImageView.setPreserveRatio(true);
        myImageView.setLayoutX(x);
        myImageView.setLayoutY(y);
        double widthRatio = myCell.getSize()/myImage.getWidth();
        double heightRatio = myCell.getSize()/myImage.getHeight();
        myOriginalImageWidth = myImage.getWidth();
        myOriginalImageHeight = myImage.getHeight();
        myRatio = Math.min(widthRatio, heightRatio);
        myImageWidth = myOriginalImageWidth*myRatio;
        myImageHeight = myOriginalImageHeight*myRatio;
        myImageView.setFitWidth(myImageWidth);
        myImageView.setFitHeight(myImageHeight);
        myDesignArea.addSprite(this, myCell);
        storeDimensionData();

    }

    public GameObjectView (GameObjectView sprite, double x, double y, Cell newCell) {
        this(sprite.getFilePath(), sprite.getX(), sprite.getY(), sprite.getType(), sprite.getIsMainChar(), 
        		newCell, sprite.getDesignArea(), sprite.getDataStore());
    }

    public void setOn(double x, double y){
        myImageView.setOnMousePressed((e) -> handlePress(e.getX(), e.getY()));
        myImageView.setOnMouseDragged((e) -> handleDrag(e.getX(), e.getY()));
        handlePress(x, y);
    }

    public void setOff(){
        myImageView.setOnMousePressed(null);
        myImageView.setOnMouseDragged(null);
    }

    public void handlePress(double x, double y){
        // TODO: Sort this shit out
        xDistanceFromCorner = x - getX();
        yDistanceFromCorner = y - getY();
    }

    public void setDistanceFromCorner(double x, double y){
        xDistanceFromCorner = x;
        yDistanceFromCorner = y;
    }

    public void handleDrag(double x, double y){
        double newX = getX() + x - xDistanceFromCorner;
        double newY = getY() + y - yDistanceFromCorner;
        setLayout(newX, newY);

    }
    
    public void setMultiBoxOrigin(){
    	multiOriginX = getX();
    	multiOriginY = getY();
    }
    
    public void handleMultiboxDrag(double deltaX, double deltaY){
        double newX = multiOriginX + deltaX;
        double newY = multiOriginY + deltaY;
        setLayout(newX, newY);

    }

    public void removeSelf(){
        myDesignArea.removeSprite(this);
    }

    public ImageView getImageView(){
        return myImageView;
    }

    public void setLayout(double x, double y){
        myImageView.setLayoutX(x);
        myImageView.setLayoutY(y);
        updateDetails();
    }	

    public void setDimensions(double fitWidth, double fitHeight){
        double widthRatio = fitWidth/myImage.getWidth();
        double heightRatio = fitHeight/myImage.getHeight();
        myOriginalImageWidth = myImage.getWidth();
        myOriginalImageHeight = myImage.getHeight();
        myRatio = Math.min(widthRatio, heightRatio);
        myImageWidth = myOriginalImageWidth*myRatio;
        myImageHeight = myOriginalImageHeight*myRatio;
        myImageView.setFitWidth(myImageWidth);
        myImageView.setFitHeight(myImageHeight);
        updateDetails();
    }

    public void update(double x, double y, double width, double height){
        setLayout(x, y);
        setDimensions(width, height);
        myDesignArea.removeSprite(this);
        myDesignArea.addSprite(this, myCell);
    }

    public void updateDetails(){
        myDesignArea.updateSpriteDetails(this, getX(), getY(), getWidth(), getHeight());
        storeDimensionData();
    }

    private void storeDimensionData(){
        if(myIsMainChar){
            Map<String,String> mainCharMap = myDataStore.getMainCharMap(myImageView.toString());
            if(mainCharMap==null){
                mainCharMap = new HashMap<String,String>();
                mainCharMap.put(DetailResources.IMAGE_PATH.getResource(), myImageFilePath);
                mainCharMap.put(DetailResources.IMAGEVIEW_KEY.getResource(),myImageView.toString());
                myDataStore.storeMainCharater(mainCharMap);
            }
            addCommonValuesToMap(mainCharMap); 
        } else{
            Map<String, String> typeMap = myDataStore.getViewMap(myImageView.toString());
            if(typeMap==null){
                typeMap = myDataStore.createViewMap(myType, myImageView.toString());
                myDataStore.storeImageViewMap(typeMap);
            } 
            addCommonValuesToMap(typeMap);
        }
    }

    private void addCommonValuesToMap(Map<String,String>myMap){
        myMap.put(X_POSITION_KEY, String.valueOf(getX()));
        myMap.put(Y_POSITION_KEY, String.valueOf(getY()));
        myMap.put(SPRITE_WIDTH_KEY, String.valueOf(getWidth()));
        myMap.put(SPRITE_HEIGHT_KEY, String.valueOf(getHeight()));
    }

    public String getFilePath(){
        return myImageFilePath;
    }

    public double getWidth(){
        return myImageWidth;
    }

    public double getHeight(){
        return myImageHeight;
    }

    public double getX(){
        return myImageView.getLayoutX();
    }

    public double getY(){
        return myImageView.getLayoutY();
    }

    public String getType(){
        return myType;
    }

    public IGridDesignArea getDesignArea(){
        return myDesignArea;
    }

    public IGameEditorData getDataStore(){
        return myDataStore;
    }

    public boolean getIsMainChar(){
        return myIsMainChar;
    }
    
    public Cell getCell(){
    	return myCell;
    }

}
