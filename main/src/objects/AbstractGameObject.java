package objects;

/**
 * This is a template abstract class that contains xPos, yPos, width, and height.
 * It is used to merge AbstractGameBlock and GameObject in order to avoid duplicate code.
 * 
 * @author Ray Song
 *
 */
public class AbstractGameObject {
	private double xPosition;
	private double yPosition;
	private double width;
	private double height;
	
	public AbstractGameObject(double xPosition, double yPosition, double width, double height){
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
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
