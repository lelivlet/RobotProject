package Programmas;

import lejos.hardware.Button;
import lejos.utility.Delay;
import models.Bewegingsapparaat;

/**
 * @author Véronique en Joey Doel programma: in de as van de robot bevestigen we
 *         een pen, die figuren kan tekenen.
 *
 */
public class Draw {

	// attributes
	// private static final double diameter = 4.1;

	Bewegingsapparaat bwApparaat = new Bewegingsapparaat(100);

	// default constructor no-args
	public Draw() {
		super();
	}

	// methods

	// methode draw square
	public void makeSquare() {
		Button.waitForAnyPress();

		// uit class Robot methode bwApparaat
		// uit class Bewegingsapparaat methodes vooruitOfAchteruit, volledigeStop,
		// reverseOpposite

		for (int i = 0; i < 4; i++) {

			// 4 voor 4 hoeken
			// ga vooruit gedurende 2 seconden, stop, draai naar rechts gedurende 1,5
			// seconden, stop, ga vooruit
			bwApparaat.vooruitOfAchteruit('V');
			Delay.msDelay(2000);
			bwApparaat.volledigeStop();

			bwApparaat.reverseOpposite('R');
			Delay.msDelay(1500);
			bwApparaat.volledigeStop();

			bwApparaat.vooruitOfAchteruit('V');
		}
		bwApparaat.close();
	}
	// public void make
	//
	// Circle() {
	// Button.waitForAnyPress();
	//
	// for (int i = 0; i = )
	// }
}