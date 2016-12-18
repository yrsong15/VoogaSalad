package gameengine.model.interfaces;

import java.util.List;

import objects.RandomGeneration;

public interface RandomInterface {
	 public List<RandomGeneration<Integer>> getRandomGenerationRules();
}
