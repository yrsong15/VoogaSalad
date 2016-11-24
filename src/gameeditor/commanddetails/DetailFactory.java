package gameeditor.commanddetails;

import gameeditor.controller.IGameEditorData;
import gameeditor.view.interfaces.IDesignArea;

public class DetailFactory {

	public AbstractCommandDetail create(String name, IGameEditorData ged, IDesignArea da) {
		try {
				Class<?> c = Class.forName(name);
				AbstractCommandDetail detail = (AbstractCommandDetail) c.newInstance();
				detail.setDataStore(ged);
				detail.setDesignArea(da);
				detail.init();
				return detail;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
      
    }

}
