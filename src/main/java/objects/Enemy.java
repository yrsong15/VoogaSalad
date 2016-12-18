package objects;

import java.util.Map;

public class Enemy extends GameObject{

	public Enemy(double xPosition, double yPosition, double width, double height, String imageFileName,
			Map<String, String> properties) {
		super(xPosition, yPosition, width, height, imageFileName, properties);
		setProperty("enemy","30");
		setProperty("removeobject","");}

}
