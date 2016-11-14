package usecases;

import base.gameengine.controller.GameEngineController;

public class UseCaseMainCharacterCollision{
	
	
	/**
	 * The XMLEditor will be created at the beginning based on the selections the
	 * user makes. The parser will then be created and given the XML document for parsing.
	 * The GameEngineController is created and passed this parser to reference in order
	 * to create the appropriate objects (rules, sprites, etc.). Once update is called,
	 * the gameEngine updates all things necessary and calls ApplyConsequences on any rules
	 * that were created based on the XML file.
	 */
	
	public void useCaseMethod(){
		GameEditorXML XMLEditor = new GameEditorXML();
		BasicParser parser = new BasicParser(); 
		parser.processXML(XMLEditor.getXML().toString());
		GameEngineController engineController = new GameEngineController(parser);
		engineController.update(null, null);
	}
	
	
	

	
	

}
