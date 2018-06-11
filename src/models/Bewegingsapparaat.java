package models;

import lejos.hardware.motor.*;
import lejos.hardware.port.MotorPort;

/**
 * @author Jorik en Joey
 *
 */

public class Bewegingsapparaat {
	// Constanten
	private float snelheid;

	// Motor initialization
	EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
	EV3LargeRegulatedMotor mD = new EV3LargeRegulatedMotor(MotorPort.D);

	// no args Constructor
	public Bewegingsapparaat() {
		super();
	}

	// Constructor met snelheid
	public Bewegingsapparaat(float snelheid) {
		super();
		this.snelheid = snelheid;
	}

	// Methodes:
	/* Beweeg naar voren of naar achteren */
	public void vooruitOfAchteruit(char voorOfAchter) {
		mA.setSpeed(snelheid);
		mD.setSpeed(snelheid);
		// Geef snelheid in graden/sec
		if (voorOfAchter == 'V') {
			mA.forward();
			mD.forward();
		} else if (voorOfAchter == 'A') {
			mA.backward();
			mD.backward();
		}
	}

	public void forward(int speed) {

		mA.setSpeed(speed);
		mD.setSpeed(speed);

		mA.forward();
		mD.forward();
	}

	// A method to make a circle.
	public void turnCircularRight(int speed, double turnFactor) {

		int turningSpeed = (int) (speed * turnFactor);

		mA.setSpeed(turningSpeed);
		mD.setSpeed(speed);

		mA.forward();
		mD.forward();
	}

	// Set engine speed
	public void setEngineSpeed(double engineLeft, double engineRight) {
		this.mA.setSpeed((float) engineLeft);
		this.mD.setSpeed((float) engineRight);
	}

	// Roteer methode
	public void roteer(char richting, double draaisnelheid) {
		if (richting == 'L') {
			// mA.rotate(Gradenstapje); // 1 rotaties (360 graden) is ongeveer 30 graden in
			// real life
			setEngineSpeed(draaisnelheid, 0);
		} else if (richting == 'R') {
			setEngineSpeed(0, draaisnelheid);
		} else {
			System.out.println("Deze richting bestaat niet!");
		}
	}

	public void waitComlete() {
		mA.waitComplete();
		mD.waitComplete();
	}

	// A method to transform rotations to degrees and then rotate to
	public void rotateTo(char direction, int degrees) {

		int rotations = degrees * 12;

		if (direction == 'L') {

			// forward positive numbers
			mA.rotate(rotations);
			// backward negative numbers
			mD.rotate(rotations * -1);
		}

		else if (direction == 'R') {

			// forward positive numbers
			mA.rotate(rotations * -1);
			// backward negative numbers
			mD.rotate(rotations);
		}

	}

	// Volledige stop methode
	public void volledigeStop() {
		mA.stop();
		mD.stop();
	}

	public void setRotations(int rotations) {
		this.mA.rotate(rotations);
		this.mD.rotate(rotations);
	}

	public float getSnelheid() {
		return snelheid;
	}

	public void setSnelheid(float snelheid) {
		this.snelheid = snelheid;
	}

	public void close() {
		// Geef de motoren vrij
		mA.close();
		mD.close();
	}
}
