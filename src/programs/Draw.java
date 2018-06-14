/*
 * A class that enables the drawing of shapes. 
 * 
 * Author: Veronique Roelvink en Joey Weidema
 */

package programs;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import models.MotionController;

public class Draw {

	// nieuwe MotionController aanmaken, noemen we pathFinder
	MotionController pathFinder;

	private final int SPEED = 200;
	private final double TURN_FACTOR = 1.75;
	private final int DELAY = 8000;

	// constructor
	public Draw(MotionController BW) {
		this.pathFinder = BW;
	}

	// A method to draw a circle
	// deze methode is perfect zoals ie is.
	public void drawCircle() {

		pathFinder.turnCircularRight(SPEED, TURN_FACTOR);

		Delay.msDelay(DELAY);

		pathFinder.volledigeStop();
	}

	// kan beter maar doet wat ie moet doen.
	public void drawNestedSpiral() {
	
		double SPIRAL_TURN_FACTOR = 1.75;
	
		for (int i = 0; i < 4; i++) {
	
			pathFinder.turnCircularRight(SPEED, SPIRAL_TURN_FACTOR);
	
			Delay.msDelay(DELAY);
			SPIRAL_TURN_FACTOR -= -.25;
		}
		pathFinder.volledigeStop();
	}

	// A method to draw a pentagram
	public void drawPentagram(double length, char side) {

		int[] correctionValues = { 36, 36, 36, 36, 36 };

		for (int i = 0; i < 5; i++) {

			pathFinder.setRotations(pathFinder.getRotationDegreesFromLength(length));
			pathFinder.waitComplete();

			pathFinder.rotateTo(side, correctionValues[i]);
			pathFinder.waitComplete();

		}
		// pathFinder.close();
		// insert the tune here
	}

	// A method to draw a pentagon
	// // maybe some minor adjusting in the array for degrees; notably
	// the last one, also the length of the last line needs adjusting.
	public void drawSix(double length, char side) {
	
		// hier moeten waardes aangepast worden om te compenseren voor het gebrek aan
		// nauwkeurigheid vd motoren;
		int[] correctionValues = { 72, 72, 72, 72, 72 };
	
		for (int i = 0; i < 5; i++) {
	
			pathFinder.setRotations(pathFinder.getRotationDegreesFromLength(length));
			pathFinder.waitComplete();
	
			pathFinder.rotateTo(side, correctionValues[i]);
			pathFinder.waitComplete();
	
		}
		//pathFinder.close();
		pathFinder.setRotations(pathFinder.getRotationDegreesFromLength(14));

	}

	// A method to draw a triangle
	public void drawTriangle(int length, char side) {

		// hier moeten waardes aangepast worden om te compenseren voor het gebrek aan
		// nauwkeurigheid vd motoren;
		int[] correctionValues = { 120, 120, 50 };

		for (int i = 0; i < 3; i++) {

			pathFinder.setRotations(pathFinder.getRotationDegreesFromLength(length));
			pathFinder.waitComplete();
			pathFinder.backward(SPEED);

			pathFinder.rotateTo(side, correctionValues[i]);
			pathFinder.waitComplete();

		}
		// pathFinder.close();
	}

	public void nowDrawing() {
	
		LCD.clear();
		LCD.drawString("Now drawing ...", 0, 3);
	}

	
	// TRIANGLE??
	public void runDrawSequence() {

//		while (Button.ESCAPE.isUp()) {
			LCD.clear();
			LCD.drawString("Press ENTER to draw fig1: PENTAGON", 0, 3);
			waitForEnter();
			nowDrawing();

			drawSix(20, 'R');

			LCD.clear();
			LCD.drawString("Press ENTER to draw fig2: TRIANGLE", 0, 3);
			waitForEnter();
			nowDrawing();

			drawTriangle(20, 'R');

			LCD.clear();
			LCD.drawString("Press ENTER to draw fig3: NESTED SPIRAL", 0, 3);
			waitForEnter();
			nowDrawing();

			drawNestedSpiral();
		//}
		System.exit(1);
	}

	public void waitForEnter() {

		while (Button.ENTER.isUp()) {
		}

		Delay.msDelay(2000);
	}
}
