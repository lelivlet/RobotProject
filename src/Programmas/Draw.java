package Programmas;

import lejos.utility.Delay;
import models.Bewegingsapparaat;

public class Draw {

	Bewegingsapparaat pathFinder = new Bewegingsapparaat();

	private final int SPEED = 200;
	private final double TURN_FACTOR = 1.75;
	private final int DELAY = 5000;
	private final double DIAMETER = 4;
	private final double CIRCUMFERENCE = DIAMETER * Math.PI;

	public Draw() {

	}

	public int getRotationDegreesFromLength(double length) {

		double rotations = length / CIRCUMFERENCE;

		return (int) (rotations * 360);
	}

	// calibrate turnfactor etc
	public void drawCircle() {

		pathFinder.turnCircularRight(SPEED, TURN_FACTOR);

		Delay.msDelay(DELAY);

		pathFinder.volledigeStop();
	}

	public void drawTriangle(double length) {

		for (int i = 0; i < 3; i++) {

			// pathFinder.forward(SPEED);
			pathFinder.setEngineSpeed(SPEED, SPEED);

			pathFinder.setRotations(getRotationDegreesFromLength(length));

			pathFinder.waitComlete();

			// Delay.msDelay(length);

			pathFinder.rotateTo('R', 60);
		}
		pathFinder.close();
	}

	public void drawPentagram(int length) {

		for (int i = 0; i < 5; i++) {

			// pathFinder.forward(SPEED);
			pathFinder.setEngineSpeed(SPEED, SPEED);

			pathFinder.setRotations(getRotationDegreesFromLength(length));
			// Delay.msDelay(length);

			pathFinder.waitComlete();

			pathFinder.rotateTo('R', 36);
		}
	}

	public void drawCrown(int length) {

	}

	public void drawConcentricSpiral() {

	}
}
