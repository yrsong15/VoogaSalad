package gameEditorView;

import javafx.scene.paint.Color;

/**
 * @author John Martin (jfm41)
 *
 */

public enum ViewResources {
		IMAGE_FILE_TYPE("Image"),
		MUSIC_FILE_TYPE("Music"),
		IMAGE_FILE_LOCATION("images/Toolbar"),
		SCENE_WIDTH(1000),
		SCENE_HEIGHT(600),
		TOOLBAR_WIDTH(0.7*ViewResources.SCENE_WIDTH.getDoubleResource()),
		TOOLBAR_HEIGHT(75),
		BUTTON_IMAGE_HEIGHT(50),
		BUTTON_IMAGE_YOFFSET((ViewResources.TOOLBAR_HEIGHT.getDoubleResource() - ViewResources.BUTTON_IMAGE_HEIGHT.getDoubleResource())/2),
		BG_IMAGE_WIDTH_RATIO(1.15),
		BG_IMAGE_WIDTH(ViewResources.BUTTON_IMAGE_HEIGHT.getDoubleResource()*ViewResources.BG_IMAGE_WIDTH_RATIO.getDoubleResource()),
		BG_IMAGE_XOFFSET(ViewResources.TOOLBAR_WIDTH.getDoubleResource() - 25 - ViewResources.BG_IMAGE_WIDTH.getDoubleResource()),
		AVATAR_IMAGE_WIDTH_RATIO(0.88),
		AVATAR_IMAGE_WIDTH(ViewResources.BUTTON_IMAGE_HEIGHT.getDoubleResource()*ViewResources.AVATAR_IMAGE_WIDTH_RATIO.getDoubleResource()),
		AVATAR_IMAGE_XOFFSET(ViewResources.TOOLBAR_WIDTH.getDoubleResource() - 25 - ViewResources.BG_IMAGE_WIDTH.getDoubleResource() - 25 - ViewResources.AVATAR_IMAGE_WIDTH.getDoubleResource());
	

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
