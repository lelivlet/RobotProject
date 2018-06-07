package models;

import javax.management.MBeanAttributeInfo;

import lejos.hardware.motor.*;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class Bewegingsapparaat {
	// Constanten
	// private static final int INTERVAL = 200;
	// private final int Gradenstapje = 360; // Dit is ��n rotatie van een wiel,
	// dat staat gelijk aan 30 graden
	private float snelheid;
	private static final int INTERVAL = 200;
	private final int Gradenstapje = 360; // Dit is een rotatie van een wiel, dat staat gelijk aan 30 graden

	// Motor initialization
	EV3LargeRegulatedMotor mA = new EV3LargeRegulatedMotor(MotorPort.A);
	EV3LargeRegulatedMotor mD = new EV3LargeRegulatedMotor(MotorPort.D);

	// no args Constructor
	public Bewegingsapparaat() {
		super();
		this.snelheid = 10;
	}

	public Bewegingsapparaat(float snelheid) {
		super();
		this.snelheid = snelheid;
	}

	// Methodes:
	/* Beweeg naar voren of naar achteren */
	public void vooruitOfAchteruit (char voorOfAchter) {
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

	// Set engine speed
	public void setEngineSpeed(double engineLeft, double engineRight) {
		this.mA.setSpeed((float) engineLeft);
		this.mD.setSpeed((float) engineRight);
	}

	/* Roteer methode */
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

	/* Volledige stop methode */
	public void volledigeStop() {
		mA.stop();
		mD.stop();
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
