package models;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class Robot {
	private final static double GRIJS = 0.4;
	// private final static double ZWART = 0.2;

	Bewegingsapparaat bwApparaat = new Bewegingsapparaat(100);
	PID_Controller controller = new PID_Controller();
	ColorSensor CS = new ColorSensor();

	// boolean rightSide = true;

	double defaultSpeed = 100;
	double draaisnelheid = 200;

	// Constructor
	public Robot() {
		super();
	}

	// TODO individual engine control
	public void volgLijn() {

		float currentBrightness;

		CS.setBlackWhiteFromCalibration();

		Delay.msDelay(500);
		LCD.drawString("Press Enter to begin", 0, 0);
		while (Button.ENTER.isUp()) {
		}
		LCD.clear();
		Delay.msDelay(500);

		// Zet motoren aan met een snelheid van 0
		bwApparaat.vooruitOfAchteruit('V');

		while (Button.ENTER.isUp()) {
			currentBrightness = CS.getCurrentNormalisedBrightness();
			float correction = controller.getCorrection(currentBrightness);
			
			bwApparaat.setEngineSpeed(defaultSpeed*(1-correction), defaultSpeed*(1+correction));
		}
		//
		// LCD.drawString("Grijswaarde: " + currentBrightness, 0, 0);
		// if (currentBrightness < 0.5) {
		// if (rightSide) {
		// bwApparaat.setEngineSpeed(defaultSpeed + (2 * draaisnelheid * Math.abs(0.5 -
		// currentBrightness)),
		// defaultSpeed);
		// } else {
		// bwApparaat.setEngineSpeed(defaultSpeed,
		// defaultSpeed + (2 * draaisnelheid * Math.abs(0.5 - currentBrightness)));
		// }
		// } else if (currentBrightness > 0.5) {
		// if (!rightSide) {
		// bwApparaat.setEngineSpeed(defaultSpeed + (2 * draaisnelheid * Math.abs(0.5 -
		// currentBrightness)),
		// defaultSpeed);
		// } else {
		// bwApparaat.setEngineSpeed(defaultSpeed,
		// defaultSpeed + (2 * draaisnelheid * Math.abs(0.5 - currentBrightness)));
		// }
		// } else {
		// bwApparaat.setEngineSpeed(defaultSpeed, defaultSpeed);
		// }
		// }
		// bwApparaat.volledigeStop();
	}

	public void close() {
		// sluit de motoren en sensor af
		bwApparaat.close();
		CS.close();
	}
}
