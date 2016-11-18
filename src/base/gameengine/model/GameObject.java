package base.gameengine.model;

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
