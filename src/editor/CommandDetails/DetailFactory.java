package editor.CommandDetails;

public class DetailFactory {

	public AbstractCommandDetail create(String name) {
		try {
				Class c = Class.forName(name);
				AbstractCommandDetail detail = (AbstractCommandDetail) c.newInstance();
				return detail;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
      
    }

}
