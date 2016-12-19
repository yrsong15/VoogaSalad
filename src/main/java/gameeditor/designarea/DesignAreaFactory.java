package gameeditor.designarea;

//This entire file is part of my masterpiece.
//JOHN MARTIN
//This is the design area factory class I created to implement the factory pattern in the GameEditorView class,
//enabling creation of a game-type specific design area simply, and easily.

public class DesignAreaFactory {

	public AbstractDesignArea create(String name) {
		try {
				Class<?> c = Class.forName(name);
				AbstractDesignArea designArea = (AbstractDesignArea) c.newInstance();
				designArea.init();
				return designArea;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {

		}
		return null;
      
    }

}
