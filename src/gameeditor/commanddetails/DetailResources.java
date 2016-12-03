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
		

		SCROLL_TYPE_OPTIONS_LABEL("Scroll Type"),
		SCROLL_DIRECTIONS_OPTIONS(new String[] {"LEFT","RIGHT","UP","DOWN"}),
		
		// Limit Width
		LIMIT_DIMENSION_LABEL("Limit Dimension"),
                LIMIT_DIMENSION_OPTIONS(new String [] {"True", "False"}),
                SCROLL_WIDTH_LABEL("Width"),
                TIME_PROPERTY("time"),
                TIME_LABEL("Time"),
                POINTS_LABEL("points"),
                POINTS_PROPERTY_LABEL("Points"),
                SCROLL_WIDTH_PROPERTY("scrollWidth"),
                SCROLL_SPEED_PROPERTY("scrollspeed"),
                SCROLL_SPEED_LABEL("Scroll Speed"),
		
		
		// Scrolling
                FORCED_SCROLLING_TYPE("ForcedScrolling"),
                LIMITED_SCROLLING_TYPE ("LimitedScrolling"),
                FREE_SCROLLING_TYPE ("FreeScrolling"),
                FORCED_SCROLLING_TYPE_LABEL("Forced Scrolling"),
                LIMITED_SCROLLING_TYPE_LABEL ("Limited Scrolling"),
                FREE_SCROLLING_TYPE_LABEL ("Free Scrolling"),

		RANDOM(new String [] {"True", "False"}),
		MOVABLE(new String [] {"True", "False"}),
		RANDOM_PROPERTY_KEY ( "randomgeneration"),
		DETAIL_CONTENT_PADDING(10),
		RANDOM_GENERATION_PARAMETERS(new String[]{"Objects per Frame", "Minimum X ","Maximum X","Minimum Y ","Maximum Y","Minimum Spacing","Maximum Spacing"}),
//		RANDOM_GENERATION_KEYS(new String[]{"numObjects","minX","minY",)
		
		
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
