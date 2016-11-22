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

	public GameObject(String imageFilePath, IDesignArea da) {
		this(imageFilePath, DEFAULT_X, DEFAULT_Y, da);
	}
	
	public GameObject(String imageFilePath, double x, double y, IDesignArea da) {
		this(imageFilePath, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, da);
	}
	
	public GameObject(String imageFilePath, double x, double y, double fitWidth, double fitHeight, IDesignArea da) {
		myDesignArea = da;
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
		myDesignArea.addSprite(myImageView);
	}
	
	public void removeSelf(){
		myDesignArea.removeSprite(myImageView);
	}

}
