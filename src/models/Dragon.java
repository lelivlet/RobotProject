package models;

import lejos.hardware.Button;
import lejos.utility.Delay;

/**
 * @author Harmen
 * De Dragon Class bestuurt het hoofd en de staart van de Dragon.
 */
public class Dragon implements Runnable {

	private static final int CORRECTION_FACTOR = 1; // correctie factor ivm de tandwielen
	private MotionController motionController;

	public Dragon() {
		// TODO Auto-generated constructor stub
	}

	public void run() {
		while (Button.DOWN.isUp()) {
			headRotateTo(90);
			tailRotateTo(-90);
			Delay.msDelay(500);	
			Button.DOWN.waitForPress();
		}
	}

	public void headRotateTo(int angle) {
		motionController.getmB().rotate(angle * CORRECTION_FACTOR);
	}

	public void tailRotateTo(int angle) {
		motionController.getmC().rotate(angle);
	}
}
