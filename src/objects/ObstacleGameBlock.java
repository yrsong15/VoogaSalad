package objects;

public class ObstacleGameBlock extends AbstractGameBlock{
	public ObstacleGameBlock(double xPosition, double yPosition, double width, double height, String imageFileName){
		super(xPosition, yPosition, width, height, imageFileName);
		west = false;
		east = false;
		south = false;
		north = false;
	}
	
}
