/*
 * A class that enables the drawing of shapes. 
 * 
 * Author: Veronique Roelvink en Joey Weidema
 */

package programs;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import models.MotionController;

public class Draw {

	// nieuwe MotionController aanmaken, noemen we pathFinder
	MotionController pathFinder;

	private final int SPEED = 200;
	private final double TURN_FACTOR = 1.75;
	private final int DELAY = 10000;

	// constructor 
	public Draw(MotionController BW) {
		this.pathFinder = BW;
	}

	// A method to draw a pentagon

	// Draw Pentagon // maybe some minor adjusting in the array for degrees; notably the last one, also the length of the last line needs adjusting.
	public void drawPentagon(int length, char side) {
		
		// hier moeten waardes aangepast worden om te compenseren voor het gebrek aan nauwkeurigheid vd motoren;
		int[] correctionValues = {90, 90, 90, 95, 60};
		
		// draw a line 4 times
		for (int i = 0; i < 5; i++) {

			pathFinder.setRotations(pathFinder.getRotationDegreesFromLength(length));
			pathFinder.waitComplete();

			pathFinder.rotateTo(side, correctionValues[i]);
			pathFinder.waitComplete();

		}
		// close method
		pathFinder.close();
	}

	// A method to draw a circle
	// deze methode is perfect zoals ie is. 
	public void drawCircle() {

		pathFinder.turnCircularRight(SPEED, TURN_FACTOR);

		Delay.msDelay(DELAY);

		pathFinder.volledigeStop();
	}

	// A method to draw a pentagram
	public void drawPentagram(double length) {

		for (int i = 0; i < 5; i++) {

			// pathFinder.forward(SPEED);
			pathFinder.setEngineSpeed(SPEED, SPEED);

			pathFinder.setRotations(pathFinder.getRotationDegreesFromLength(length));
			// Delay.msDelay(length);

			pathFinder.waitComplete();

			pathFinder.rotateTo('R', 36); // poot pentagram = 36 graden, hij draait naar rechts
		}
		// insert the tune here
	}

	// A method to draw a triangle
	public void drawTriangle(int length, char side) {
		
		// hier moeten waardes aangepast worden om te compenseren voor het gebrek aan nauwkeurigheid vd motoren;
		int[] correctionValues = {160, 150, 120};
		
		// draw a line 4 times
		for (int i = 0; i < 3; i++) {

			pathFinder.setRotations(pathFinder.getRotationDegreesFromLength(length));
			pathFinder.waitComplete();
			pathFinder.backward(SPEED);

			pathFinder.rotateTo(side, correctionValues[i]);
			pathFinder.waitComplete();

		}
		// close method
		pathFinder.close();
	}


	// kan beter maar doet wat ie moet doen. 
	public void drawConcentricSpiral() {
		
		double SPIRAL_TURN_FACTOR = 1.75;

		for (int i = 0; i < 4; i++) {

		pathFinder.turnCircularRight(SPEED, SPIRAL_TURN_FACTOR);

		Delay.msDelay(DELAY);
		SPIRAL_TURN_FACTOR -= -.25; 
		}
	}
}
