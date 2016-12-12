package objects;

public class Background {
	
	private String imageFileName;
    private double xPosition;
    private double yPosition;
    private double width;
    private double height;
    private int id;
    
    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }
    
    public String getImageFileName() {
        return imageFileName;
    }
    
    public double getXPosition() {
        return xPosition;
    }
    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }
    public double getYPosition() {
        return yPosition;
    }
    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

}
