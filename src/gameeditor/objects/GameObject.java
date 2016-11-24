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
	private double myImageWidth;
	private double myImageHeight;
	private IDesignArea myDesignArea;
	private String myType;

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
        double ratio = Math.min(widthRatio, heightRatio);
        myImageWidth = myImage.getWidth()*ratio;
        myImageHeight = myImage.getHeight()*ratio;
		myImageView.setFitWidth(fitWidth);
		myImageView.setFitHeight(fitHeight);
		myDesignArea.addSprite(this);
	}
	
	public void removeSelf(){
		myDesignArea.removeSprite(this);
	}
	
	public ImageView getImageView(){
		return myImageView;
	}
	
	public String getType(){
		return myType;
	}

}
