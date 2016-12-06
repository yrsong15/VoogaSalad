package objects;

import java.util.Map;
import java.util.Set;

public class BreakableGameBlock extends AbstractGameBlock{

	private Map<String, String> properties;
	
	//TODO: currently hardcoded to only be breakable from the bottom; maybe refine later
	public BreakableGameBlock(double xPosition, double yPosition, double width, double height, String imageFileName) {
		super(xPosition, yPosition, width, height, imageFileName);
		west = false;
		east = false;
		south = true;
		north = false;
		isBreakable = false;
	}
	
	public String getProperty(String propertyName) {
		return properties.get(propertyName);
	}
	
	public void setProperty(String propertyName, String propertyValue) {
		properties.put(propertyName, propertyValue);
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
