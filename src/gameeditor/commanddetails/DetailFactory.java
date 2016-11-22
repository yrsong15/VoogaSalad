package gameeditor.commanddetails;

import gameeditor.view.interfaces.IDesignArea;

public class DetailFactory {

	public AbstractCommandDetail create(String name, IDetailStore detailStore, IDesignArea da) {
		try {
				Class c = Class.forName(name);
				AbstractCommandDetail detail = (AbstractCommandDetail) c.newInstance();
				detail.setDetailStore(detailStore);
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
