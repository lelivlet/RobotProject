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

	double defaultSpeed = 100;
	// max adjust. 0.5 = 100 ; 0 = 150
	double turningSpeed = 50;

	// Constructor
	public Robot() {
		super();
	}

	/* Methode volg lijn */
	// MOET IN EEN WHILE LOOP
	public void volgLijn() {
		float input = CS.getRed();
		if (input < GRIJS) {
			bwApparaat.vooruitOfAchteruit(250, 'V');
			Delay.msDelay(200);
			volgLijn();
		} else if (input >= GRIJS) {
			bwApparaat.volledigeStop();
			bwApparaat.roteer('R');
			float inputNieuw = CS.getRed();
			if (inputNieuw < input) { // Als de nieuwe meting donkerder wordt (en dus lager) dan draait het voertuig
										// de juiste kant op
				volgLijn();
			} else { // Als de meting lichter wordt dan draaien we de andere kant op
				bwApparaat.roteer('L');
				volgLijn();
			}
		}
	}
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes

	// TODO individual engine control
	public void volgLijn2() {

		float currentBrightness;

		CS.setBlackWhiteFromCalibration();

		Delay.msDelay(500);
		LCD.drawString("Press Enter to begin", 0, 0);
		while (Button.ENTER.isUp()) {
		}
		LCD.clear();
		Delay.msDelay(500);

		bwApparaat.vooruitOfAchteruit((float) defaultSpeed, 'V');

		while (Button.ENTER.isUp()) {
			currentBrightness = CS.getCurrentNormalisedBrightness();
			if (currentBrightness < 0.5) {
				if (rightSide) {
					bwApparaat.setEngineSpeed(defaultSpeed + (2 * turningSpeed * Math.abs(0.5 - currentBrightness)),
							defaultSpeed);
				} else {
					bwApparaat.setEngineSpeed(defaultSpeed,
							defaultSpeed + (2 * turningSpeed * Math.abs(0.5 - currentBrightness)));
				}
			} else if (currentBrightness > 0.5) {
				if (!rightSide) {
					bwApparaat.setEngineSpeed(defaultSpeed + (2 * turningSpeed * Math.abs(0.5 - currentBrightness)),
							defaultSpeed);
				} else {
					bwApparaat.setEngineSpeed(defaultSpeed,
							defaultSpeed + (2 * turningSpeed * Math.abs(0.5 - currentBrightness)));
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
