package models;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

/**
 * @author Joey, Harmen en Jorik
 *
 */

public class Robot {
	// private fields
	double defaultSpeed = 200;
	boolean rightSide; // Deze boolean moet true zijn als de rechter kant de buitenkant van de track is
						// en false als de linkerkant de buitenkant is

	// Initialisatie van het bijbehorende bewegingsapparaat, sensor en controller
	Bewegingsapparaat bwApparaat = new Bewegingsapparaat(100);
	PID_Controller pidController = new PID_Controller();
	ColorSensor CS = new ColorSensor();

	// Constructor
	public Robot() {
		super();
	}

	// TODO individual engine control
	public void volgLijn() {
		float currentBrightness;
		// Hier kan de Kp van de controller aangepast worden om sneller of langzamer te draaien.
		pidController.setKp(3);

		// Calibratie aanvraag
		CS.setBlackWhiteFromCalibration();
		Delay.msDelay(500);
		
		// Zo lang de knop niet ingedrukt is, begint het programma niet
		LCD.drawString("Press Enter to begin", 0, 0);
		while (Button.ENTER.isUp()) {	}
		LCD.clear();
		Delay.msDelay(500);

		// Zet motoren aan met een snelheid van 0
		bwApparaat.vooruitOfAchteruit('V');

		// Zo lang de knop niet ingedrukt wordt, volgt de robot een lijn
		while (Button.ENTER.isUp()) {
			currentBrightness = CS.getCurrentNormalisedBrightness();
			float correction = pidController.getCorrection(currentBrightness);

			// rightSide = true, rechter kant van de robot is buitenkant van de track
			if (rightSide) {
				bwApparaat.setEngineSpeed(defaultSpeed * (1 - correction), defaultSpeed * (1 + correction));
			} else {
				bwApparaat.setEngineSpeed(defaultSpeed * (1 + correction), defaultSpeed * (1 - correction));
			}
		}
	}

	public void close() {
		// sluit de motoren en sensor af
		bwApparaat.close();
		CS.close();
	}

	// Set de rightSide op true of false om zo de richting aan te geven
	public void setRightSide(boolean rightSide) {
		this.rightSide = rightSide;
	}

	// De getters
	public Bewegingsapparaat getBwApparaat() {
		return bwApparaat;
	}

	public PID_Controller getPidController() {
		return pidController;
	}

	public ColorSensor getCS() {
		return CS;
	}
	
	
	
}
