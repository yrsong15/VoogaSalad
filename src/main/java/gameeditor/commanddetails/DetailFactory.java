package gameeditor.commanddetails;

import gameeditor.controller.interfaces.IGameEditorData;
import gameeditor.view.interfaces.IDesignArea;
import gameeditor.view.interfaces.IDetailPane;
import gameeditor.view.interfaces.IStandardDesignArea;

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
			e.printStackTrace();
		}
		return null;
      
    }

}
