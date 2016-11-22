package gameeditor.commanddetails;

import java.util.ArrayList;
import java.util.Map;

public interface IDetailStore {

	public void storeType(Map<String, String> typeMap);
	
	public Map<String, String> getType(String inputTypeName);
	
	public ArrayList<String> getTypes();
	
}
