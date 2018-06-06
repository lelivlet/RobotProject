package models;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.utility.Delay;

public class MasterMind {
	// 1=Zwart 2=Blauw 3=Groen 4=Geel 5=Rood 6=Wit 7=Bruin
	private int[] kleuren = { 1, 2, 3, 4, 5, 6, 7 };
	private int aantalGoed = 0;
	private int[] gok = new int[4];
	ColorSensor CS = new ColorSensor();

	public MasterMind() {
		super();

	}

	public void speelMasterMind() {
		System.out.println("Wil je mastermind spelen?");
		waitForKey(Button.ENTER);
		int[] kleurenBrick = new int[4];
		for (int i = 0; i < kleurenBrick.length; i++) {
			kleurenBrick[i] = kleuren[(int) (Math.random() * 7)];
		}
		while (aantalGoed < 4) {
			for (int i = 0; i < kleurenBrick.length; i++) {
				System.out.println("Aantal goed is " + aantalGoed);
				System.out.println("Doe gok " + (i + 1) + " onder de sensor");
				waitForKey(Button.ENTER);
				gok[i] = CS.getColorID();
				if (gok[i] == kleurenBrick[i]) {
					System.out.println("Dat is goed");
					aantalGoed++;
				} else {
					for(int x=0;x<kleurenBrick.length;x++) {
						if(gok[i]==kleurenBrick[x]) {
							System.out.println("Kleur is goed maar positie niet");
							
						}else {
							System.out.println("Kleur zit niet in combinatie");
						}
					}
				}
				
					
				

			}
		}
	}

	public void waitForKey(Key key) {
		while(key.isUp()) {
			Delay.msDelay(100);
		}
		while(key.isDown()) {
			Delay.msDelay(100);
		}

}
