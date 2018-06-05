package models;

import lejos.utility.Delay;

public class Robot {
	private final static double GRIJS = 0.4;
//	private final static double ZWART = 0.2;
	
	Bewegingsapparaat bwApparaat = new Bewegingsapparaat();
	ColorSensor CS = new ColorSensor();
	
	// Constructor
	public Robot() {
		super();
	}
	
	
	/* Methode volg lijn */
	// MOET IN EEN WHILE LOOP
	public void volgLijn() {
		float input = CS.getRed();
		if(input < GRIJS) {
			bwApparaat.vooruitOfAchteruit(250, 'V');
			Delay.msDelay(200);
			volgLijn();
		} else if (input >= GRIJS){
			bwApparaat.volledigeStop();
			bwApparaat.roteer('R');
			float inputNieuw = CS.getRed();
			if(inputNieuw < input) { // Als de nieuwe meting donkerder wordt (en dus lager) dan draait het voertuig de juiste kant op
				volgLijn();
			} else { // Als de meting lichter wordt dan draaien we de andere kant op
				bwApparaat.roteer('L');
				volgLijn();
			}
		}
	}

}
