package objects;

import java.util.Map;
import java.util.Set;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameObject {

	private double xPosition;
	private double yPosition;
	private double width;
	private double height;
	String imageFileName;
	Map<String, String> properties;

	public GameObject(double xPosition, double yPosition, double width, double height, String imageFileName,
			Map<String, String> properties) {
		this(xPosition, yPosition, width, height, properties);
		this.imageFileName = imageFileName;
	}

	public GameObject(double xPosition, double yPosition, double width, double height, Map<String, String> properties) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
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

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getProperty(String propertyName) {
		return properties.get(propertyName);
	}

	public void setProperty(String propertyName, String propertyValue) {
		properties.put(propertyName, propertyValue);
		
		// find a better way to do this
		if (propertyName.equals("gravity") && getProperty("speed")==null) {
			setProperty("speed", "0");
		}
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public Set<String> getPropertiesList() {
		return properties.keySet();
	}

	public void setPropertiesList(Map<String, String> properties) {
		this.properties = properties;
	}
}
