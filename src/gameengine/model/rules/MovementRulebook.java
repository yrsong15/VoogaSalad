package gameengine.model.rules;

import gameengine.controller.interfaces.RuleActionHandler;
import objects.GameObject;
import utils.ReflectionUtil;
import utils.ResourceReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Soravit on 11/22/2016.
 */
public class MovementRulebook {

    private ResourceReader resources;

    public MovementRulebook() {
        resources = new ResourceReader("GameEngineMovementProperties");
    }

    public void applyRules(GameObject obj) throws ClassNotFoundException, InstantiationException {
        for(String property: obj.getPropertiesList()){
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
                    //throw new ClassNotFoundException();
                }
            }
        }
    }
}
