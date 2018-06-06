package models;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.utility.Delay;

public class MasterMind {
	// 1=Zwart 2=Blauw 3=Groen 4=Geel 5=Rood 6=Wit 7=Bruin
	private int[] kleuren = { 1, 2, 3, 4, 5, 6, 7 };
	private int aantalGoed = 0;
	private int[] gok = new int[4];
	private boolean[] goed = { false, false, false, false };
	ColorSensor CS = new ColorSensor();

	public MasterMind() {
		super();

	}
	public int[] maakOplossing() {
		int[] kleurenBrick = new int[4];
		for (int i = 0; i < kleurenBrick.length; i++) {
			kleurenBrick[i] = kleuren[(int) (Math.random() * 7)];
		}
	return kleurenBrick;
		
	}

	public void speelMasterMind() {
		System.out.println("Wil je mastermind spelen?");
		waitForKey(Button.ENTER);
		int [] oplossing=maakOplossing();
		
		while (goed[0] == false || goed[1] == false || goed[2] == false || goed[3] == false) {
			for (int i = 0; i < oplossing.length; i++) {
				System.out.println("Aantal goed is " + aantalGoed);
				System.out.println("Positie 1=" + goed[0] + "\nPositie 2=" + goed[1] + "\nPositie 3=" + goed[2]
						+ "\nPositie 4=" + goed[3]);
				System.out.println("Doe gok " + (i + 1) + " onder de sensor");
				waitForKey(Button.ENTER);
				gok[i] = CS.getColorID();
				if (gok[i] == oplossing[i]) {
					System.out.println("Dat is goed");
					goed[i] = true;
					aantalGoed++;
				} else if (gok[i] == oplossing[0] || gok[i] == oplossing[1] || gok[i] == oplossing[2]
						|| gok[i] == oplossing[3]) {

					System.out.println("Kleur is goed maar positie niet");

				} else {
					System.out.println("Kleur zit niet in de oplossing");
				}
			}

		}
		System.out.println("Gewonnen je hebt alles geraden");
	}


	public void waitForKey(Key key) {
		while(key.isUp()) {
			Delay.msDelay(100);
		}
		while(key.isDown()) {
			Delay.msDelay(100);
		}

	}
}
