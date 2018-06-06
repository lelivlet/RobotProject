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
	double draaisnelheid = 100;

	// Constructor
	public Robot() {
		super();
	}

	/* Methode volg lijn */
	// MOET IN EEN WHILE LOOP
	public void volgLijn() {
		float input = CS.getRed();
		if (input < GRIJS) {
			bwApparaat.vooruitOfAchteruit('V');
			Delay.msDelay(200);
			volgLijn();
		} else if (input >= GRIJS) {
			bwApparaat.volledigeStop();
			bwApparaat.roteer('R', draaisnelheid);
			float inputNieuw = CS.getRed();
			if (inputNieuw < input) { // Als de nieuwe meting donkerder wordt (en dus lager) dan draait het voertuig
										// de juiste kant op
				volgLijn();
			} else { // Als de meting lichter wordt dan draaien we de andere kant op
				bwApparaat.roteer('L', draaisnelheid);
				volgLijn();
			}
		}
	}


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

	// TODO individual engine control (Harmen)
		public void volgLijn3() {
			
			float currentBrightness;

			CS.setBlackWhiteFromCalibration();

			Delay.msDelay(500);
			LCD.drawString("Press Enter to begin", 0, 0);
			while (Button.ENTER.isUp()) {
			}
			LCD.clear();
			Delay.msDelay(500);

			
			
			while (Button.ENTER.isUp()) {
				currentBrightness = CS.getCurrentNormalisedBrightness();
				bwApparaat.vooruitOfAchteruit('V');
				if (currentBrightness < 0.5) {
					bwApparaat.vooruitOfAchteruit('V');
				} else {
					bwApparaat.roteer('L', draaisnelheid);
					Delay.msDelay(200);
					float newBrightness = CS.getCurrentNormalisedBrightness();
					if (newBrightness > 0.5) {
						bwApparaat.roteer('R', draaisnelheid);
						Delay.msDelay(400);
					}
						
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
