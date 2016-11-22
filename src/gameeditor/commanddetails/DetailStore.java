package gameeditor.commanddetails;

import java.util.ArrayList;
import java.util.Map;

public class DetailStore implements IDetailStore {
	
	private ArrayList<Map<String, String>> myTypes = new ArrayList<Map<String, String>>();
	
	public DetailStore() {
		
	}
	
	public void storeType(Map<String, String> typeMap){
		myTypes.add(typeMap);
	}
	
	public Map<String, String> getType(String inputTypeName){
		for (Map<String, String> type : myTypes){
			String testTypeName = type.get(DetailResources.TYPE_NAME.getResource());
			if (inputTypeName.equals(testTypeName)){
				return type;
			}
		}
		return null;
	}
	
	public ArrayList<String> getTypes(){
		ArrayList<String> types = new ArrayList<String>();
		for (Map<String, String> type : myTypes){
			String typeName = type.get(DetailResources.TYPE_NAME.getResource());
			types.add(typeName);
		}
		return types;
	}

}
