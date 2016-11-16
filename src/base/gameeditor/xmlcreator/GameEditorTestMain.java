package base.gameeditor.xmlcreator;

public class GameEditorTestMain {
	public static void main(String[] args){
		GameEditorXMLManager test = new GameEditorXMLManager();
		test.addNewElement("gobject");
		test.addAttributeToElem("gobject", "isdestructible", "true");
		test.addAttributeToElem("gobject", "isstatic", "false");
		test.addAttributeToElem("gobject", "ismainchar", "true");
		test.addTextToElem("gobject", "This thing works!");
		test.testWriteXML();
	}
}
