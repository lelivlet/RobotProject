package models;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Robot {
	private final static double GRIJS = 0.4;
	// private final static double ZWART = 0.2;

	Bewegingsapparaat bwApparaat = new Bewegingsapparaat();

	ColorSensor CS = new ColorSensor();

	boolean rightSide = true;

	double defaultSpeed = 50;
	// max adjust. 0.5 = 100 ; 0 = 150
	double draaisnelheid = 400;

	// Constructor
	public Robot() {
		super();
	}

	// TODO individual engine control
	public void volgLijn2() {

		float currentBrightness;
		StringBuilder stringBuilder = new StringBuilder("normalized brightness: ");

		CS.setBlackWhiteFromCalibration();

		Delay.msDelay(500);
		LCD.drawString("Press Enter to begin", 0, 0);
		while (Button.ENTER.isUp()) {
		}
		LCD.clear();
		Delay.msDelay(500);

		bwApparaat.vooruitOfAchteruit('V');

		while (Button.ENTER.isUp()) {
			currentBrightness = CS.getCurrentNormalisedBrightness();
			LCD.drawString("Grijswaarde: " + currentBrightness, 0, 0);
			if (currentBrightness < 0.5) {
				if (rightSide) {
					bwApparaat.setEngineSpeed(defaultSpeed + (2 * draaisnelheid * Math.abs(0.5 - currentBrightness)),
							defaultSpeed);
				} else {
					bwApparaat.setEngineSpeed(defaultSpeed,
							defaultSpeed + (2 * draaisnelheid * Math.abs(0.5 - currentBrightness)));
				}
			} else if (currentBrightness > 0.5) {
				if (!rightSide) {
					bwApparaat.setEngineSpeed(defaultSpeed + (2 * draaisnelheid * Math.abs(0.5 - currentBrightness)),
							defaultSpeed);
				} else {
					bwApparaat.setEngineSpeed(defaultSpeed,
							defaultSpeed + (2 * draaisnelheid * Math.abs(0.5 - currentBrightness)));
				}
			} else {
				bwApparaat.setEngineSpeed(defaultSpeed, defaultSpeed);
			}
		}
		bwApparaat.volledigeStop();
	}

	public void close() {
		// sluit de motoren en sensor af
		bwApparaat.close();
		CS.close();
	}

}
