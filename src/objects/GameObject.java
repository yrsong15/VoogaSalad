package objects;

import java.util.Map;
import java.util.Set;

public class GameObject{
	
	double xPosition;
	double yPosition;
	String imageFileName;
	Map<String,String> properties;
    
	public GameObject(double xPosition, double yPosition, String imageFileName, Map<String,String> properties){
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.imageFileName = imageFileName;
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

	public String getImageFileName() {
		return imageFileName;
	}

	public Set<String> getPropertiesList() {
		return properties.keySet();
	}
}
