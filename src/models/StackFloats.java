package models;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * @author Harmen
 * Berekend het zwevende gemiddelde van een aantal waardes
 */
public class StackFloats {
	
	private Queue<Float> queue = new ArrayDeque<>();
	int size;
	
	public StackFloats() {
		super();
	}
	
	public StackFloats(int size) {
		this();
		this.size = size;
	}
	
	
	
	public void addValue(float value) {
		if (queue.size() >= size) {
			queue.remove();
		}
		queue.add(value);
	}

	@Override
	public String toString() {
		String waardes = "";
		for (Float value: queue) {
			waardes += value.toString() + ", ";
		}
		return "[" + waardes + "]";
	}
	
	
}	