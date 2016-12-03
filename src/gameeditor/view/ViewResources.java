package gameeditor.view;

import javafx.scene.paint.Color;

/**
 * @author John Martin (jfm41)
 *
 */

public enum ViewResources {
		IMAGE_FILE_TYPE("Image"),
		MUSIC_FILE_TYPE("Music"),
		MUSIC_FILE_LOCATION("music/"),
		BG_FILE_LOCATION("images/Background"),
		IMAGE_FILE_LOCATION("images/Toolbar"),
		SPRITE_IMAGE_LOCATION("images/Sprite"),
		AVATAR_IMAGE_LOCATION("images/Sprite"),
		SCENE_WIDTH(1200),
		SCENE_HEIGHT(600),
		
		// Detail Pane
		DETAIL_PANE_BG(Color.SLATEGREY),
		DETAIL_PANE_WIDTH(300),
		AVATAR_ZONE_PADDING(25),
		AVATAR_ZONE_WIDTH(ViewResources.DETAIL_PANE_WIDTH.getDoubleResource()-2*ViewResources.AVATAR_ZONE_PADDING.getDoubleResource()),
		AVATAR_ZONE_HEIGHT(ViewResources.AVATAR_ZONE_WIDTH.getDoubleResource()),
		AVATAR_ZONE_RADIUS(25),
		COMMAND_DETAIL_PADDING(25),
		
		// Command Pane
		COMMAND_PANE_BG(Color.STEELBLUE),
		COMMAND_PANE_WIDTH(75),
		BUTTON_FILE_LOCATIONS(new String[] {"/Select.png", "/CreateObject.png", "/Create.png", "/Behavior.png", "/Controls.png"}),
		BUTTON_RADIUS(5),
		BUTTON_BG_COLOUR(Color.WHITESMOKE),
		BORDER_OFF_COLOUR(Color.WHITESMOKE),
		BORDER_ON_COLOUR(Color.LIGHTGREY),
		
		// Toolbar
		TOOLBAR_WIDTH(ViewResources.SCENE_WIDTH.getDoubleResource() - ViewResources.DETAIL_PANE_WIDTH.getDoubleResource() - ViewResources.COMMAND_PANE_WIDTH.getDoubleResource()),
		TOOLBAR_HEIGHT(75),
		BUTTON_IMAGE_HEIGHT(50),
		BUTTON_IMAGE_PADDING(25),
		BUTTON_IMAGE_YOFFSET((ViewResources.TOOLBAR_HEIGHT.getDoubleResource() - ViewResources.BUTTON_IMAGE_HEIGHT.getDoubleResource())/2),
		BG_IMAGE_WIDTH_RATIO(1.15),
		BG_IMAGE_WIDTH(ViewResources.BUTTON_IMAGE_HEIGHT.getDoubleResource()*ViewResources.BG_IMAGE_WIDTH_RATIO.getDoubleResource()),
		BG_IMAGE_XOFFSET(ViewResources.TOOLBAR_WIDTH.getDoubleResource() - ViewResources.BUTTON_IMAGE_PADDING.getDoubleResource() - ViewResources.BG_IMAGE_WIDTH.getDoubleResource()),
		AVATAR_IMAGE_WIDTH_RATIO(0.88),
		AVATAR_IMAGE_WIDTH(ViewResources.BUTTON_IMAGE_HEIGHT.getDoubleResource()*ViewResources.AVATAR_IMAGE_WIDTH_RATIO.getDoubleResource()),
		AVATAR_IMAGE_XOFFSET(ViewResources.BG_IMAGE_XOFFSET.getDoubleResource() - ViewResources.BUTTON_IMAGE_PADDING.getDoubleResource() - ViewResources.AVATAR_IMAGE_WIDTH.getDoubleResource()),
		MUSIC_IMAGE_WIDTH_RATIO(0.91),
		MUSIC_IMAGE_WIDTH(ViewResources.BUTTON_IMAGE_HEIGHT.getDoubleResource()*ViewResources.MUSIC_IMAGE_WIDTH_RATIO.getDoubleResource()),
		MUSIC_IMAGE_XOFFSET(ViewResources.AVATAR_IMAGE_XOFFSET.getDoubleResource() - ViewResources.BUTTON_IMAGE_PADDING.getDoubleResource() - ViewResources.MUSIC_IMAGE_WIDTH.getDoubleResource()),
		LOAD_IMAGE_WIDTH_RATIO(0.90),
		
   	
		LOAD_GAME_IMAGE_WIDTH(ViewResources.BUTTON_IMAGE_HEIGHT.getDoubleResource()*ViewResources.LOAD_IMAGE_WIDTH_RATIO.getDoubleResource()),
		LOAD_GAME_IMAGE_XOFFSET(ViewResources.MUSIC_IMAGE_XOFFSET.getDoubleResource() - ViewResources.BUTTON_IMAGE_PADDING.getDoubleResource() - ViewResources.LOAD_GAME_IMAGE_WIDTH.getDoubleResource()),
             
		
		// Scroll Pane
		AREA_WIDTH(ViewResources.SCENE_WIDTH.getDoubleResource() - ViewResources.DETAIL_PANE_WIDTH.getDoubleResource() - ViewResources.COMMAND_PANE_WIDTH.getDoubleResource()),
		AREA_HEIGHT(ViewResources.SCENE_HEIGHT.getDoubleResource() - ViewResources.TOOLBAR_HEIGHT.getDoubleResource()),
		
		;
	

    private double resourceDouble;
    private String resourceString;
    private Color resourceColor;
    private String [] resourceArray;

    ViewResources(String resource) {
        resourceString = resource;
        resourceDouble = -1;
        resourceColor = null;
        resourceArray = null;
    }
    
    ViewResources(String[] resource){
    	resourceString = "";
        resourceDouble = -1;
        resourceColor = null;
        resourceArray = resource;
    }

    ViewResources(double resource) {
        resourceString = null;
        resourceDouble = resource;
        resourceColor = null;
        resourceArray = null;
    }
    
    ViewResources(Color resource){
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
