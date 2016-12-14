package objects;

import com.sun.javafx.scene.traversal.Direction;

public class ClientGameObject {
	
	private double xPosition;
	private double yPosition;
	private double width;
	private double height;
	private Direction direction;
	private int id;
	private String imageFileName;
	private Double health;


	public ClientGameObject(int id, double xPosition, double yPosition, double width, double height, Direction direction, String imageFileName, Double health) {
		this.id = id;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = height;
		this.imageFileName = imageFileName;
		this.direction = direction;
		this.health = health;
	}

	public int getID(){
		return id;
	}

	public void setID(int id){
		this.id = id;
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
	}
	public Direction getDirection(){
		return direction;
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

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
}
