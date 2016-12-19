package gameengine.scrolling;


//This entire file is part of my masterpiece.
//Chalena Scholl

/**
 * I chose to create an enum for ScrollDirection instead of using the Direction enum that Java has already 
 * defined because Java's Direction enum also had other values as part of the enum that are not valid values
 * for scrolling. Using this enum ensures that errors are avoided from passing in invalid constants and the 
 * valid values that can be used are also automatically documented, thus making them easy to see and choose
 * from. Creating this enum also makes it much more readable and accessible by other classes who need to access
 * available directions for scrolling.
 */


public enum ScrollDirection {
	UP, DOWN, LEFT, RIGHT
}
