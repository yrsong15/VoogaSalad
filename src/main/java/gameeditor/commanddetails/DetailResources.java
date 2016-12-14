package gameeditor.commanddetails;

import javafx.scene.paint.Color;

/**
 * @author John Martin (jfm41), Pratiksha sharma
 *
 */

public enum DetailResources {
                //PROPERTIES(new String[]{"Destructible","Damage","Points","Time","Health","Gravity","Speed","Random","Movable"}),
		SPRITE_PROPERTIES_TEXT_INPUT_LABEL(new String [] { "Damage", "Points", "Health","Gravity","Horizontal Speed","Jump","Bounce","Enemy"}),
		CONTROL_OPTIONS( new String[] {"Up", "Down", "Right", "Left", "Jump", "Shoot"}),
		//PROPERTIES_COMBO (new String[]{"Destructible","Random","Movable","Scrollable"}),
		PROPERTIES_COMBO (new String[]{"Destructible","Random","Scrollable"}),
		SCROLLABLE_LABEL("Scrollable"),
		PROPERTIES_COMBO_KEYS(new String[]{"isDestructible","randomgeneration","nonscrollable"}),
		RANDOM_GEN_KEY("randomgeneration"),
		DESTRUCTIBLE(new String [] {"True", "False"}),
		MAIN_CHARACTER_PROPERTIES(new String[]{"Health","Gravity","Movement Speed","Jump"}),
		MAIN_CHAR_MOVEMENT_LABEL("Movement Speed"),
		MAIN_CHAR_MOVEMENT_KEY("movespeed"),
		SCROLL_TYPE_OPTIONS_LABEL("Scroll Type"),
		SCROLL_DIRECTIONS_OPTIONS(new String[] {"LEFT","RIGHT","UP","DOWN"}),
		
		// Platform Properties
		PLATFORM_INTERSECTABLE_OPTIONS(new String[] {"True","False"}),
		PLATFORM_NON_INTERSECTABLE_OPTIONS(new String[]{"Top","Bottom","Both"}),
		PLATFORM_NON_INTERSECTIBLE_LABEL("Non Intersectable"),
		NON_INTERSECTABLE_SIDES_LABEL("Select Side"),
		NON_INTERSECTABLE_KEY("nonintersectable"),
		ONE_SIDE_NON_INTERSECTABLEKEY("onewaynonintersectable"),
		ISPLATFORM_KEY("isplatform"),
		PLATFORM_ENEMY_ALLOWED_KEY("isenemyallowed"),
		PlATFORM_ENEMY_ALLOWED_LABEL("Is Enemy Allowed"),
		
		JUMP_OPTIONS(new String[] {"Jump Once", "Jump Unlimited"}),
		RANDOM_GEN_DIRECTION_OPTIONS(new String[] {"vertical","horizontal"}),
		
		
		GAMEBOUNDARY_OPTIONS(new String[] {"Toroidal","Stop At Edge"}),
		X_POSITION_KEY("xPosition"),
                Y_POSITION_KEY("yPosition"),
                PLATFORM_LABEL("Platform"),
                PLATFORM_KEY("platform"),
                ENEMY_KEY("enemy"),
		
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
		SCROLLABLE(new String[] {"True","False"}),
		RANDOM_PROPERTY_KEY ( "randomgeneration"),
		DETAIL_CONTENT_PADDING(10),
		RANDOM_GENERATION_PARAMETERS(new String[]{"Objects per Frame", "Minimum X ","Maximum X","Minimum Y ","Maximum Y","Minimum Spacing","Maximum Spacing"}),

		//		RANDOM_GENERATION_KEYS(new String[]{"numObjects","minX","minY",)
		
		
		// Projectile Properties
		PROJECTILE_TEXT_INPUT_PROPERTIES_LABEL(new String[] {"Width","Height","Range","Speed","Damage","Time InBetween"}),
		PROJECTILE_IMAGE_KEY("imageFileName"),
		DIRECTION_LABEL("Direction"),
		DIRECTION_KEY("direction"),
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
		IMAGEVIEW_KEY("imageview"), 
		MAIN_CHARACTER_TYPE("Main Character"),
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
