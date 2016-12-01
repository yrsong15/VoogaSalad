package gameengine.model.rules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;
import utils.ReflectionUtil;
import utils.ResourceReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * Created by Soravit on 11/22/2016.
 */
public class MovementRulebook {

    private ResourceReader resources;

    public MovementRulebook() {
        resources = new ResourceReader("GameEngineMovementProperties");
    }

    public void applyRules(GameObject obj) throws ClassNotFoundException, InstantiationException {
    	Iterator<String> itr = obj.getPropertiesList().iterator();
    	while ( itr.hasNext()) {
    	String property = itr.next();
            if(resources.containsResource(property)) {
                String ruleName = resources.getResource(property);
                ruleName = "gameengine.model.rules.movementrules." + ruleName;
                try {
                    Object o = ReflectionUtil.newInstanceOf(ruleName);
                    Method method = ReflectionUtil.getMethodFromClass(ruleName, "applyRule", GameObject.class);
                    method.invoke(o, obj);

                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | ClassNotFoundException | NoSuchMethodException
                        | SecurityException e) {
                    e.printStackTrace();
                    //System.out.print(ruleName);
                    //throw new ClassNotFoundException();
                }
            }
        }
    }
}
