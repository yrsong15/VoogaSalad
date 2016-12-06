package objects;

import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Ray Song, Soravit
 *
 */
public class GameObject extends AbstractGameObject{
	
	private String imageFileName;
	private Map<String, String> properties;

	public GameObject(double xPosition, double yPosition, double width, double height, String imageFileName,
			Map<String, String> properties) {
		this(xPosition, yPosition, width, height, properties);
		this.imageFileName = imageFileName;
	}

	public GameObject(double xPosition, double yPosition, double width, double height, Map<String, String> properties) {
		super(xPosition, yPosition, width, height);
		this.properties = properties;
	}

	public GameObject(GameObject gameObject){
		super(gameObject.getXPosition(), gameObject.getXPosition(), gameObject.getWidth(), gameObject.getHeight());
		this.properties = gameObject.getProperties();
	}

	public String getProperty(String propertyName) {
		String val = properties.get(propertyName);
		if(propertyName.equals("fallspeed")&&val==null){
			val = "0.0";
			setProperty("fallspeed",val);
		}
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
}
