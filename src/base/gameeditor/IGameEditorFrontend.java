package base.gameeditor;

public interface IGameEditorFrontend {
	
	// Method to set level type: either distinct finite levels or infinite levels
	void setLevelType(String levelType);
	
	//Method to set an interaction between two objects
	void setInteractionBehaviour(String object1, String object2, String behaviourProperty);
	
	// Method to set an XML property on creation of a new object/rule/setting
	void setProperty(String objectName, String propName, String propValue);
	
	// Method to set a sub property for an XML element
	void setSubProperty(String objectName, String parentProperty, String childProperty, String propValue);
	
	// Method to retrieve XML properties to enable loading a game design from file
	void getProperty(String propName);
	
	// Method to reset game design, clearing previously set XML properties
	void reset();
	
}
