package gameeditor.commanddetails;

import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;

//This entire file is part of my masterpiece.
//Pratiksha Sharma
// This factory class uses reflection to create an instance of CreateDetail class.The use of reflection here hides the implementation of the 
// CreateDetail Class, dynamically allowing the creation of Objects without caring much about what objects they are.
/**
 * @author John Martin, Pratiksha Sharma
 * 
 *
 */
public class DetailFactory {

	public AbstractCommandDetail create(String name, IGameEditorData ged, IDesignArea myDesignArea, IDetailPane idp) {
		try {
				Class<?> c = Class.forName(name);
				AbstractCommandDetail detail = (AbstractCommandDetail) c.newInstance();
				detail.setDetailPane(idp);
				detail.setDataStore(ged);
				detail.setDesignArea(myDesignArea);
				detail.init();
				return detail;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
		 GameEditorException exception = new GameEditorException();
		    exception.showError(e.getMessage());
		
			}
		}
		return null;
      
    }

}
