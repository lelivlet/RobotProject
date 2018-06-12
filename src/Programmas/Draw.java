/*
 * A class that enables the drawing of shapes. 
 * 
 * Author: Veronique Roelvink en Joey Weidema
 */

package Programmas;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import models.MotionController;

public class Draw {
	
	// nieuwe MotionController aanmaken, noemen we pathFinder
	MotionController pathFinder;

	private final int SPEED = 200;
	private final double TURN_FACTOR = 1.75;
	private final int DELAY = 5000;

	public Draw(MotionController BW) {
		this.pathFinder = BW;
	}

	// A method to draw a square
	
	public void drawSquare() {
	
		// draw a line 4 times
		for (int i = 0; i < 4; i++) {

			// set speed mA and mD (final attribute)
//			pathFinder.setEngineSpeed(SPEED, SPEED);
			pathFinder.vooruitOfAchteruit('V');
			Delay.msDelay(2000);
			pathFinder.volledigeStop();
			pathFinder.rotateTo('R', 90);

			// 
//			pathFinder.setRotations(getRotationDegreesFromLength(length));

//			LCD.clear();
//			LCD.drawString("" + i, 3, 3);
			
			// stop mA and mB
			pathFinder.waitComplete();

			// turn 90 degrees to the right
			
		}
		// close method
		pathFinder.close();
	}
	
	// A method to draw a circle
	
					// calibrate turnfactor etc
	public void drawCircle() {

		pathFinder.turnCircularRight(SPEED, TURN_FACTOR);

		Delay.msDelay(DELAY);

		pathFinder.volledigeStop();
	}

	// A method to draw a pentagram
	public void drawPentagram(int length) {

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
	public void drawTriangle(double length) {

		for (int i = 0; i < 3; i++) {

			// pathFinder.forward(SPEED);
			pathFinder.setEngineSpeed(SPEED, SPEED);

			pathFinder.setRotations(pathFinder.getRotationDegreesFromLength(length));

			pathFinder.waitComplete();

			// Delay.msDelay(length);

			pathFinder.rotateTo('R', 60);
		}
		pathFinder.close();
	}

	public void drawCrown(int length) {

	}

	public void drawTest(int length) {

		// pathFinder.setEngineSpeed(SPEED, SPEED);

		pathFinder.forward(SPEED);
		
		Delay.msDelay(length);


		//pathFinder.setRotations(getRotationDegreesFromLength(length));

	// 	pathFinder.waitComplete();

		// Delay.msDelay(length);

	//	pathFinder.rotateTo('R', 60);
		
		//pathFinder.close();

	}

	public void drawConcentricSpiral() {

	}

	// A method to transform rotations to actual length (distance) in cm
	public int getRotationDegreesFromLength(double length) {
	
		double rotations = length / CIRCUMFERENCE;
	
		return (int) (rotations * 360);
	}
}
