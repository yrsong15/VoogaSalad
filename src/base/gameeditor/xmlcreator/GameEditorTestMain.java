package base.gameeditor.xmlcreator;

public class GameEditorTestMain {
	public static void main(String[] args){
		GameEditorXMLCreator test = new GameEditorXMLCreator();
		test.addElement("aaa");
		test.addAttributeToElem("aaa", "hi", "1");
		test.addTextToElem("aaa", "This thing works!");
		test.testWriteXML();
	}
}
