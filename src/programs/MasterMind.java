package programs;

import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color; // Kan wellicht weg?
import lejos.utility.Delay;
import models.ColorSensor;
import models.MotionController;

public class MasterMind {
	// 0 = rood, 1 = groen, 2 = blauw, 3 = geel, 6 = wit, 7 = zwart
	private int[] guess = new int[4];
	private int[] randomToNumber = { 0, 1, 2, 3, 6, 7 };
	private String[] numberToColor = { "rood", "groen", "blauw", "geel", "", "", "wit", "zwart" };
	private ColorSensor CS;
	private MotionController BW;

	// Constructor
	public MasterMind(ColorSensor CS, MotionController BW) {
		super();
		this.CS = CS;
		this.BW = BW;
	}

	// Cre�er een random raadsel
	public int[] createRiddle() {
		int[] riddle = new int[4];
		for (int i = 0; i < riddle.length; i++) {
			int randomNumber = ((int) (Math.random() * 6));
			riddle[i] = randomToNumber[randomNumber];
			
			// Voor test doeleinden
//			LCD.drawString(String.format("%s", numberToColor[riddle[i]]), 0, i);
//			Delay.msDelay(5000); // Laat de oplossing even 5 seconde zien
		}
		return riddle;
	}

	// doe 1 keer 4 enkele gokjes (dus 1 beurt als het ware)
	public int[] takeAGuess() {
		// Plaats op de stip
		LCD.clear();
		LCD.drawString("Positioneer de", 0, 0);
		LCD.drawString("Robot op de zwarte", 0, 1);
		LCD.drawString("stip en druk op", 0, 2);
		LCD.drawString("ENTER", 0, 3);
		waitForKey(Button.ENTER);
		LCD.clear();
		/* Lees kleuren in en rij verder */
		for (int i = 0; i < guess.length; i++) {
			if(i == 0) {
				// Beweeg naar eerste meetpunt
				BW.setRotations(BW.getRotationDegreesFromLength(4.5));
				BW.waitComplete();
				// Lees de kleur in
				guess[i] = CS.getColorID();
				
				// TODO IS NIET HEEL NETJES! (Kan eventueel ook een muziekje of toontje??)
				// Heel soms wordt geel aangegeven als 13 (bruin) daarom hebben we deze failsafe ingebouwd
				if(guess[i] == 13) {
					guess[i] = 3;
				}
				
				int test = guess[i];
				// Schrijf op display wat er gemeten is
				LCD.drawString(String.format("%s", numberToColor[test]), 0, i);
			} else if(i > 0 && i < guess.length) {
				BW.setRotations(BW.getRotationDegreesFromLength(6.5));
				BW.waitComplete();
				// Lees de kleur in
				guess[i] = CS.getColorID();
				
				// TODO IS NIET HEEL NETJES!
				// Heel soms wordt geel aangegeven als 13 (bruin) daarom hebben we deze failsafe ingebouwd
				if(guess[i] == 13) {
					guess[i] = 3;
				}
				
				int test = guess[i];
				// Schrijf op display wat er gemeten is
				LCD.drawString(String.format("%s", numberToColor[test]), 0, i);
			}
		}
		// Rij terug en geef de guess terug
		//TODO moet er misschien uit aangezien hij niet precies op dezelfde plek eindigt als dat hij start
		BW.setRotations(BW.getRotationDegreesFromLength(-(4.5+(3*6.5))));
		BW.waitComplete();
		return guess;
	}

	// geeft het aantal exact goed geraden pinnetjes (goede kleur en op juiste
	// positie)
	public int[] numbersGuessed(int[] riddle, int[] guess) {
		int numberFullyCorrect = 0;
		for (int i = 0; i < riddle.length; i++) {
			if (guess[i] == riddle[i]) {
				numberFullyCorrect++;
				riddle[i] = 66; // TODO
				guess[i] = 99; // TODO
			}
		}
		// Roep de numberColorsGuessed methode aan
		int numberColorCorrect = numberColorsGuessed(riddle, guess);
		int[] correctGuesses = { numberFullyCorrect, numberColorCorrect };

		return correctGuesses;
	}

	// geeft het aantal goed geraden kleuren (goede kleur, onjuiste positie)
	public int numberColorsGuessed(int[] tempRiddle, int[] guess) {
		int numberColorCorrect = 0;
		for (int i = 0; i < guess.length; i++) {
			for (int j = 0; j < tempRiddle.length; j++) {
				if (i != j) {
					if (guess[i] == tempRiddle[j]) {
						numberColorCorrect++;
						tempRiddle[j] = 66; // OP het moment dat deze kleur al gevonden is, wordt de waarde binnen deze
						// methode op 66 gezet
						break;
					}
				} else {
					continue;
				}
			}
		}
		return numberColorCorrect;
	}	

	public void playMasterMind() {
		LCD.clear();
		LCD.drawString("Mastermind spelen?", 0, 0);
		LCD.drawString("Druk ENTER voor JA", 0, 2);
		LCD.drawString("ESCAPE voor NEE", 0, 3);
		waitForKey(Button.ENTER);
		LCD.clear();

		CS.setFloodLight(true);
		CS.setFloodLight(6);
		CS.setColorIdMode();

		// cre�ert een raadsel
		int[] riddle = createRiddle();

		// Men kan 12 keer raden
		for (int i = 0; i < 12; i++) {
			// creeer eerst een tijdelijke array
			int[] tempRiddle = new int[4];
			for (int j = 0; j < riddle.length; j++) {
				tempRiddle[j] = riddle[j];
			}
			LCD.clear();
			LCD.drawString(String.format("Ronde #%d", i+1), 0, 0);
			LCD.drawString(String.format("Nog %d rondes.", 12-i), 0, 1);
			
			LCD.drawString("Druk op ENTER", 0, 4);
			waitForKey(Button.ENTER);
			LCD.clear();
			
			int[] guess = takeAGuess();
			int[] numberGuessedCorrectly = numbersGuessed(tempRiddle, guess);
			waitForKey(Button.ENTER);
			LCD.clear();
			
			if (numberGuessedCorrectly[0] == 4) {
				LCD.drawString("**Gefeliciteerd**", 0, 2);
				LCD.drawString("je hebt gewonnen!", 0, 3);
				Sound.systemSound(true, 2); // Geeft een descending arpeggio
				Sound.systemSound(true, 2); // Geeft een descending arpeggio
				Delay.msDelay(5000); // Laat eerst 5 sec zien dat je gewonnen hebt
				LCD.clear();
				break;
			} else if (i == 11 && numberGuessedCorrectly[0] != 4) {
				LCD.clear();
				LCD.drawString("Helaas, je hebt de", 0, 1);
				LCD.drawString("kleurencode niet", 0, 2);
				LCD.drawString("kunnen raden.", 0, 3);
				Sound.systemSound(true, 3); // Geeft een ascending arpeggio
				Delay.msDelay(3000); // Laat 3 sec bovenstaand bericht zien
				LCD.clear();
				LCD.drawString("De kleurencode was", 0, 0);
				for (int j = 0; j < riddle.length; j++) {
					LCD.drawString(String.format("%s", numberToColor[riddle[j]]), 0, j+1);
				}
				Delay.msDelay(5000); // Laat eerst 5 sec zien dat je gewonnen hebt
				LCD.clear();
				break;
			} else {
				LCD.clear();
				LCD.drawString(String.format("%d helemaal goed", numberGuessedCorrectly[0]), 0, 0);
				LCD.drawString(String.format("%d kleur(en) goed", numberGuessedCorrectly[1]), 0, 1);

				LCD.drawString("Druk op ENTER", 0, 3);
				waitForKey(Button.ENTER);
				LCD.clear();
			}
		}
		LCD.drawString("Wil je nog een keer spelen?", 0, 1);
		LCD.drawString("ENTER = JA", 0, 3);
		LCD.drawString("Andere knop = NEE", 0, 4);
		Button.waitForAnyPress();
		if (Button.ENTER.isDown()) {
			LCD.clear();
			playMasterMind();
		}
		LCD.clear();
	}
	
	

	// TODO HIER KUNNEN WE EEN INTERFACE VOOR MAKEN
	public void waitForKey(Key key) {
		while (key.isUp()) {
			Delay.msDelay(100);
		}
		while (key.isDown()) {
			Delay.msDelay(100);
		}
	}

}
