package gameeditor.commanddetails;

import gameeditor.view.ViewResources;
import javafx.scene.paint.Color;

/**
 * @author John Martin (jfm41)
 * Modified Pratiksha Sharma
 *
 */

public enum DetailDefaultsResources {
	// Design Area
	BG_IMAGE("/bg.png"),
	
	// Main Character
	AVATAR_IMAGE("/mario1.png"),
	INIT_X(100),
	INIT_Y(200),
	
	// Sprite Type Defaults
	       
        DESTRUCTIBLE_DEFAULT("False"),
        RANDOM_DEFAULT("False"),
        MOVABLE_DEFAULT("False"),
        SCROLLABLE_DEFAULT("True"),
        DAMAGE("0"),
        POINTS("0"),
        HEALTH("0"),
        GRAVITY("0"),
        HORIZONTAL_SPEED("0"),
        JUMP("0"),
        
        TEXT_BOX_NUMBER_DEFAULT_INPUT("0"),
        
	SPRITE_DEFAULT_PROPERTIES(new String [] {"False", "False", "False", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0"}),
	SPRITE_DEFAULT_COMBO_PROPERTIES(new String[]{DESTRUCTIBLE_DEFAULT.getResource(), RANDOM_DEFAULT.getResource(),SCROLLABLE_DEFAULT.getResource()}),
	
	CONTROLS_DEFAULT_COMBO_OPTIONS(new String[] {"Up","Down","Right","Left","Jump","Shoot"}),
	CONTROLS_DEFAULT_VALUES(new String[]{"UP","DOWN","RIGHT","LEFT","SPACE","S"}),
	
	// Random Generation
	OBJECTS_PER_FRAME("0"),
	MINIMUM_X("0"),
	MAXIMUM_X("0"),
	MINIMUM_Y("0"),
	MAXIMUM_Y("0"),
	MINIMUM_SPACING("0"),
	MAXIMUM_SPACING("0"),
	PROJECTILE_DIRECTION("RIGHT"),
	
	// Level Properties Defaults
	LIMIT_WIDTH("False"),
	GAME_BOUNDARY("Stop At Edge"),
	SCROLL_HEIGHT(String.valueOf(ViewResources.SCROLL_PANE_HEIGHT.getDoubleResource())),
	SCROLL_WIDTH(String.valueOf(ViewResources.SCROLL_PANE_WIDTH.getDoubleResource())),
	DEFAULT_TIME_VALUE ("400"),
	DEFAULT_POINTS_VALUE("20"),
	DEFAULT_SCROLL_SPEED("10"),
	
	// Platform Default Properties
	PLATFORM_NON_INTERSECTABLE("True"),
	PLATFORM_INTERSECTABLE("Bottom"),
	
	

	
	
	// Destructible, Random, Movable, Damage, Points, Health, Gravity, Horizontal Speed, Jump
	

	;

    private double resourceDouble;
    private String resourceString;
    private Color resourceColor;
    private String [] resourceArray;

    DetailDefaultsResources(String resource) {
        resourceString = resource;
        resourceDouble = -1;
        resourceColor = null;
        resourceArray = null;
    }
    
    DetailDefaultsResources(String[] resource){
    	resourceString = "";
        resourceDouble = -1;
        resourceColor = null;
        resourceArray = resource;
    }

    DetailDefaultsResources(double resource) {
        resourceString = null;
        resourceDouble = resource;
        resourceColor = null;
        resourceArray = null;
    }
    
    DetailDefaultsResources(Color resource){
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
