package objects;

import java.util.Map;

public class GameObject{
	
	double xPosition;
	double yPosition;
	String fileName;
	Map<String,String> properties;
    
	public GameObject(double xPosition, double yPosition, String fileName, Map<String,String> properties){
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.fileName = fileName;
		this.properties = properties;
	}

	public double getXPosition() {
		return xPosition;
	}

	public void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getYPosition() {
		return yPosition;
	}

	public void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public String getProperty(String propertyName) {
		return properties.get(propertyName);
	}

	public void setProperty(String propertyName, String propertyValue) {
		properties.put(propertyName, propertyValue);
	}

	public String getFileName() {
		return fileName;
	}
}
	
//=======
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//
//public class GameObject implements IGameObject {
//    private ImageView myGameObjectImageView;
//
//    //Constructor
//    public GameObject(String filePath){
//        if(filePath!=null){
//            myGameObjectImageView = new ImageView(new Image(filePath));
//
//        }
//    }
//    
//    @Override
//    public ImageView getImageView () {
//        // TODO Auto-generated method stub
//
//        return null;
//    }
//
//    @Override
//    public void setXPosition () {
//        // TODO Auto-generated method stub
//
//
//    }
//
//    @Override
//    public void setYPosition () {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void init (String filePath) {
//        // TODO Auto-generated method stub
//
//    }
//>>>>>>> 87a24cd705eb855fd5db50bac91a4d4031bdf751
//
//}
