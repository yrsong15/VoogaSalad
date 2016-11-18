package gameeditor.xmlcreator;

public class GameEditorTestMain {
	public static void main(String[] args){
		GameEditorXMLManager test = new GameEditorXMLManager();
		test.addNewElement("gobject");
		test.addAttributeToElem("gobject", "type", "mainchar");
		test.addElemToElem("gobject", "position");
		test.addAttributeToElem("position", "israndom", "false");
		test.addElemToElem("position", "xcor");
		test.addTextToElem("xcor", "100");
		test.addElemToElem("position", "ycor");
		test.addTextToElem("ycor", "50");
		test.testWriteXML();
	}
}
