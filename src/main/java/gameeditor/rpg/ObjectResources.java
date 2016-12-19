package gameeditor.rpg;

import javafx.scene.paint.Color;

/**
 * @author John Martin (jfm41)
 */

public enum ObjectResources {
    DEFAULT_X(0),
    DEFAULT_Y(0),
    DEFAULT_WIDTH(50),
    DEFAULT_HEIGHT(50),;

    private double resourceDouble;
    private String resourceString;
    private Color resourceColor;
    private String[] resourceArray;

    ObjectResources(String resource) {
        resourceString = resource;
        resourceDouble = -1;
        resourceColor = null;
        resourceArray = null;
    }

    ObjectResources(String[] resource) {
        resourceString = "";
        resourceDouble = -1;
        resourceColor = null;
        resourceArray = resource;
    }

    ObjectResources(double resource) {
        resourceString = null;
        resourceDouble = resource;
        resourceColor = null;
        resourceArray = null;
    }

    ObjectResources(Color resource) {
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

    public Color getColorResource() {
        return resourceColor;
    }

    public String[] getArrayResource() {
        return resourceArray;
    }

    public void setResource(String resource) {
        resourceString = resource;
    }

    public void setDoubleResource(double resource) {
        resourceDouble = resource;
    }

    public void setColorResource(Color resource) {
        resourceColor = resource;
    }

}
