package gameeditor.commanddetails;

public class DetailFactory {

	public AbstractCommandDetail create(String name, IDetailStore detailStore) {
		try {
				Class c = Class.forName(name);
				AbstractCommandDetail detail = (AbstractCommandDetail) c.newInstance();
				detail.setDetailStore(detailStore);
				detail.init();
				return detail;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
      
    }

}
