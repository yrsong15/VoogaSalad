package gameeditor.objects;

import gameeditor.view.interfaces.IDesignArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameObject {
	
	private static final double DEFAULT_X = ObjectResources.DEFAULT_X.getDoubleResource();
	private static final double DEFAULT_Y = ObjectResources.DEFAULT_Y.getDoubleResource();
	private static final double DEFAULT_WIDTH = ObjectResources.DEFAULT_WIDTH.getDoubleResource();
	private static final double DEFAULT_HEIGHT = ObjectResources.DEFAULT_HEIGHT.getDoubleResource();

	
	private Image myImage;
	private String myImageFilePath;
	private ImageView myImageView;
	private double myOriginalImageWidth;
	private double myOriginalImageHeight;
	private double myImageWidth;
	private double myImageHeight;
	private double myRatio;
	private IDesignArea myDesignArea;
	private String myType;
	
	private double xDistanceFromCorner = 0;
	private double yDistanceFromCorner = 0;
	
	private BoundingBox myBoundingBox;

	public GameObject(String imageFilePath, String type, IDesignArea da) {
		this(imageFilePath, DEFAULT_X, DEFAULT_Y, type, da);
	}
	
	public GameObject(String imageFilePath, double x, double y, String type, IDesignArea da) {
		this(imageFilePath, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, type, da);
	}
	
	public GameObject(String imageFilePath, double x, double y, double fitWidth, double fitHeight, String type, IDesignArea da) {
		myDesignArea = da;
		myType = type;
		myImageFilePath = imageFilePath;
		myImage = new Image(myImageFilePath); 
		myImageView = new ImageView(myImage);
		myImageView.setPreserveRatio(true);
		myImageView.setLayoutX(x);
		myImageView.setLayoutY(y);
		double widthRatio = fitWidth/myImage.getWidth();
        double heightRatio = fitHeight/myImage.getHeight();
        myOriginalImageWidth = myImage.getWidth();
        myOriginalImageHeight = myImage.getHeight();
        myRatio = Math.min(widthRatio, heightRatio);
        myImageWidth = myOriginalImageWidth*myRatio;
        myImageHeight = myOriginalImageHeight*myRatio;
		myImageView.setFitWidth(myImageWidth);
		myImageView.setFitHeight(myImageHeight);
		myDesignArea.addSprite(this);
	}
	
	public void setOn(){
		myImageView.setOnMousePressed((e) -> handlePress(e.getX(), e.getY()));
		myImageView.setOnMouseDragged((e) -> handleDrag(e.getX(), e.getY()));
	}
	
	public void setOff(){
		myImageView.setOnMousePressed(null);
		myImageView.setOnMouseDragged(null);
	}
	
	private void handlePress(double x, double y){
		// TODO: Sort this shit out
		initBound();
		xDistanceFromCorner = x;
		yDistanceFromCorner = y;
	}
	
	public void setDistanceFromCorner(double x, double y){
		xDistanceFromCorner = x - getX();
		yDistanceFromCorner = y - getY();
	}
	
	public void handleDrag(double x, double y){
		double newX = getX() + x - xDistanceFromCorner;
		double newY = getY() + y - yDistanceFromCorner;
		setLayout(newX, newY);
		myBoundingBox.updateLayout();
	}
	
	public void initBound(){
		myDesignArea.initSelectDetail2(this);
		myBoundingBox = new BoundingBox(this, myDesignArea);
		myBoundingBox.show();
	}
	
	public void removeBound(){
		myBoundingBox.hide();
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
	
	public void update(double x, double y, double width, double height){
		setLayout(x, y);
		setDimensions(width, height);
		myDesignArea.removeSprite(this);
		myDesignArea.addSprite(this);
	}

}
