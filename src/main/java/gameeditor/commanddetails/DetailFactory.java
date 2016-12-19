package gameeditor.commanddetails;

import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;

//This entire file is part of my masterpiece.
//Pratiksha Sharma
// Even though I did not write this file, I included this file because this factory uses reflection to create an instance of CreateDetail class.
/**
 * @author John Martin
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
			// TODO Auto-generated catch block
		}
		return null;
      
    }

}
