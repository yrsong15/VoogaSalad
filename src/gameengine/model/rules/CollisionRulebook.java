package gameengine.model.rules;

import java.lang.reflect.InvocationTargetException;

import gameengine.controller.RuleActionHandler;
import gameengine.model.rules.collisionrules.CollisionRule;
import objects.GameObject;
import utils.ReflectionUtil;
import utils.ResourceReader;

public class CollisionRulebook{
	private ResourceReader resources;
	
	public CollisionRulebook() {
		resources = new ResourceReader("resources/GameEngineObjectProperties");
	}

	//might need to fully specify classpath to rule in properties file, instead of just rule name
	 
	public void applyRules(GameObject mainChar, GameObject obj, RuleActionHandler handler) throws ClassNotFoundException{
		for(String property: obj.getPropertiesList()){
			String ruleName = resources.getResource(property);
			try {
				CollisionRule currRule = (CollisionRule) ReflectionUtil.newInstanceOf(ruleName);
				CollisionRule.applyRule(mainChar, obj, handler);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | ClassNotFoundException | NoSuchMethodException
					| SecurityException e) {
				throw new ClassNotFoundException();
			}
		}
	}

}
