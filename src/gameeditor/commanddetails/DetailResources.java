package gameeditor.commanddetails;

import javafx.scene.paint.Color;

/**
 * @author John Martin (jfm41)
 *
 */

public enum DetailResources {
                //PROPERTIES(new String[]{"Destructible","Damage","Points","Time","Health","Gravity","Speed","Random","Movable"}),
		PROPERTIES_TEXT_INPUT(new String [] { "Damage", "Points", "Time", "Health","Gravity","Speed","Jump"}),
		PROPERTIES_COMBO (new String[]{"Destructible","Random","Movable"}),
		PROPERTIES_COMBO_LABELS(new String[]{"isDestructible","randomgeneration","movable"}),
		DESTRUCTIBLE(new String [] {"True", "False"}),
		//DAMAGE(new String [] {"0", "1"}),
		//POINTS(new String [] {"0", "1"}),
		//TIME(new String [] {"0", "1"}),
		//HEALTH(new String [] {"0", "1"}),
		//GRAVITY(new String [] {"0", "1"}),
		//SPEED(new String [] {"0", "1"}),
		RANDOM(new String [] {"True", "False"}),
		MOVABLE(new String [] {"True", "False"}),
		DETAIL_CONTENT_PADDING(10),
		
		
		
		TYPE_IMAGE_ZONE_WIDTH(50),
		TYPE_IMAGE_ZONE_HEIGHT(50),
		TYPE_IMAGE_ZONE_PADDING(10),
		
		OBJECT_IMAGE_ZONE_WIDTH(75),
		OBJECT_IMAGE_ZONE_HEIGHT(75),
		OBJECT_IMAGE_ZONE_PADDING(10),

		TYPE_NAME("Type Name"),
		IMAGE_PATH("Image Path"),
		
		DEFAULT_OBJECT_TYPE("Select Object Type"),
		
		PREVIEW_BUTTON_TEXT("Preview"),
		SAVE_BUTTON_TEXT("Save"),
		
		SELECT_LABEL_TEXT("Select An Object"),
		UPDATE_BUTTON_TEXT("Update"),
		;

    private double resourceDouble;
    private String resourceString;
    private Color resourceColor;
    private String [] resourceArray;

    DetailResources(String resource) {
        resourceString = resource;
        resourceDouble = -1;
        resourceColor = null;
        resourceArray = null;
    }
    
    DetailResources(String[] resource){
    	resourceString = "";
        resourceDouble = -1;
        resourceColor = null;
        resourceArray = resource;
    }

    DetailResources(double resource) {
        resourceString = null;
        resourceDouble = resource;
        resourceColor = null;
        resourceArray = null;
    }
    
    DetailResources(Color resource){
    	resourceString = null;
    	resourceDouble = -1;
    	resourceColor = resource;
    	resourceArray = null;
    }

    public String getResource() {
        return resourceString;
    }

    public double getDoubleResource() {
        return resourceDouble;
    }
    
    public Color getColorResource(){
    	return resourceColor;
    }
    
    public String [] getArrayResource(){
    	return resourceArray;
    }
    
    public void setResource(String resource){
    	resourceString = resource;
    }
    
    public void setDoubleResource(double resource){
    	resourceDouble = resource;
    }
    
    public void setColorResource(Color resource){
    	resourceColor = resource;
    }
    
}
