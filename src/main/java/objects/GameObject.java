package objects;

import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Ray Song, Soravit
 *
 */
public class GameObject {

	private double xPosition;
	private double yPosition;
	private double width;
	private double height;
	private String imageFileName;
	private Map<String, String> properties;
    private double xDistanceMoved;
    private double yDistanceMoved;

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

	public String getProperty(String propertyName) {
		String val = properties.get(propertyName);
		return val;
	}

	public void setProperty(String propertyName, String propertyValue) {
		properties.put(propertyName, propertyValue);
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public Map<String, String> getProperties(){
		return properties;
	}

	public Set<String> getPropertiesList() {
		return properties.keySet();
	}

	public void setPropertiesList(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public void killSpeed(){
		setProperty("fallspeed", "0.0");
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

    public double getXDistanceMoved(){
        return xDistanceMoved;
    }
    public double getYDistanceMoved(){
        return yDistanceMoved;
    }

    public void setXDistanceMoved(double xDistanceMoved){
        this.xDistanceMoved = xDistanceMoved;
    }

    public void setYDistanceMoved(double yDistanceMoved){
        this.yDistanceMoved = yDistanceMoved;
    }
}
