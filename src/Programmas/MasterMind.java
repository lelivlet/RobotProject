package Programmas;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.robotics.Color;
import lejos.utility.Delay;
import models.ColorSensor;

public class MasterMind {
	// 1=Zwart 2=Blauw 3=Groen 4=Geel 5=Rood 6=Wit 7=Bruin
	private int[] gok = new int[4];
	ColorSensor CS;

	public MasterMind(ColorSensor CS) {
		super();
		this.CS = CS;
	}

	// Creëer een random raadsel
	public int[] maakOplossing() { // Naamgeving kan anders
		int[] oplossing = new int[4];
		for (int i = 0; i < oplossing.length; i++) {
			oplossing[i] = ((int) (Math.random() * 7) + 1);
			System.out.println(oplossing[i]); //TEST***
		}
		return oplossing;
	}

	// doe 1 keer 4 enkele gokjes (dus 1 beurt als het ware)
	public int[] doeGok() {
		for (int i = 0; i < this.gok.length; i++) {
			System.out.println("Doe gok " + (i + 1) + " onder  de sensor");
			waitForKey(Button.ENTER);
			gok[i] = CS.getColorID();
			System.out.println(gok[i]); //TEST***
		}
		return gok;
	}

	// geeft het aantal exact goed geraden pinnetjes (goede kleur en op juiste
	// positie)
	public int aantalGeraden(int[] oplossing, int[] gok) {
		int aantalGoed = 0;
		for (int x = 0; x < oplossing.length; x++) {
			if (gok[x] == oplossing[x]) {
				aantalGoed++;
			}
		}
		return aantalGoed;
	}

	// geeft het aantal goed geraden kleuren (goede kleur, onjuiste positie)
	public int aantalKleurenGoed(int[] oplossing, int[] gok) {
		int aantalJuist = 0;
		for (int i = 0; i < gok.length; i++) {
			for (int j = 0; j < oplossing.length; j++) {
				if (i != j) {
					if (gok[i] == oplossing[j]) {
						aantalJuist++;
						oplossing[j] = 66; // OP het moment dat deze kleur al gevonden is, wordt de waarde binnen deze
											// methode op 66 gezet
						break;
					}
				} else {
					continue;
				}
			}
		}
		return aantalJuist;
	}

	public void speelMasterMind() {
//		CS.setColorFromCalibration(); // TEST MET COLORCALIBRATIE
		System.out.println("Wil je mastermind spelen, druk dan op de knop!"); // KAN NOG NIET SPELEN BIJ
		waitForKey(Button.ENTER);
		CS.setFloodLight(true);
		CS.setFloodLight(6); // TODO NOG FF AAN SLEUTELEN
		CS.setColorIdMode();
		
		// creëert een raadsel
		int[] oplossing = maakOplossing();

		// Men kan 12 keer raden
		for (int i = 0; i < 12; i++) {
			int[] gok = doeGok();
			int aantalGoedGeraden = aantalGeraden(oplossing, gok);
			if (aantalGoedGeraden == 4) {
				System.out.println("Gefeliciteerd, je hebt gewonnen!!");
			} else {
				System.out.printf(
						"\nJe hebt %d kleuren goed op je juiste plaats \npJe hebt %d kleuren goed, maar niet op de juiste plaats",
						aantalGoedGeraden, aantalKleurenGoed(oplossing, gok));
			}
		}
	}

	// TODO HIER KUNNEN WE EEN INTERFACE VAN MAKEN
	public void waitForKey(Key key) {
		while (key.isUp()) {
			Delay.msDelay(100);
		}
		while (key.isDown()) {
			Delay.msDelay(100);
		}
	}
	
}
