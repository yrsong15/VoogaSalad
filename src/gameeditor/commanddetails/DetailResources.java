package gameeditor.commanddetails;

import javafx.scene.paint.Color;

/**
 * @author John Martin (jfm41)
 *
 */

public enum DetailResources {
		PROPERTIES(new String [] {"Destructible", "Damage", "Points", "Time", "Random", "Health", "Movable"}),
		DESTRUCTIBLE(new String [] {"True", "False"}),
		DAMAGE(new String [] {"True", "False"}),
		POINTS(new String [] {"True", "False"}),
		TIME(new String [] {"True", "False"}),
		RANDOM(new String [] {"True", "False"}),
		HEALTH(new String [] {"True", "False"}),
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
