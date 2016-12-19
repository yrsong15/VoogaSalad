package gameeditor.commanddetails;

import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.designarea.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;
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
