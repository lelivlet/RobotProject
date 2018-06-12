package models;

import java.io.File;

import Programmas.*;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import lejos.utility.TextMenu;

public class FollowLine {

	PID_Controller pidController;
	ColorSensor CS;
	MotionController bwApparaat;

	// default istellingen
	private static final float SPEED_DEFAULT = (float) 100;
	private static final float KP_VALUE_DEFAULT = (float) 1.0;
	private static final float KI_VALUE_DEFAULT = (float) 0.0;
	private static final float KD_VALUE_DEFAULT = (float) 0.0;
	File tune_default;
	// instellingen normale track
	private static final float SPEED_NORMAL = (float) 375;
	private static final float KP_VALUE_NORMAL = (float) 1.5;
	private static final float KI_VALUE_NORMAL = (float) 0.0;
	private static final float KD_VALUE_NORMAL = (float) 2.2;
	File tune_normal;
	// instellingen moeilijk track
	private static final float SPEED_DIFFICULT = (float) 200;
	private static final float KP_VALUE_DIFFICULT = (float) 0.5;
	private static final float KI_VALUE_DIFFICULT = (float) 0.0;
	private static final float KD_VALUE_DIFFICULT = (float) 1.0;
	File tune_difficult;
	// test instellingen
	private float speedTest = (float) 375;
	private float kpTest = (float) 1.4;
	private float kiTest = (float) 0.5;
	private float kdTest = (float) 2.3;

	// variabele om te kijken welke kant de robot oprijdt
	boolean rightSide;

	public FollowLine(PID_Controller pidController, ColorSensor CS, MotionController bwApparaat) {
		super();
		this.pidController = pidController;
		this.CS = CS;
		this.bwApparaat = bwApparaat;
		this.tune_default = new File("star_wars.wav");
		this.tune_normal = new File("carnaval_festival.wav");
		this.tune_difficult = new File("mission_impossible.wav");
		this.rightSide = true;
	}

	public void run() {
		calibrate();
		Sound.twoBeeps();
		followLine();
	}

	// hiermee kan de robot gecalibreerd worden
	public void calibrate() {

		// Calibratie aanvraag
		LCD.clear();
		CS.setBlackWhiteFromCalibration();

		// maak een keuze of de track linksom gaat of rechtsom
		LCD.clear();
		String[] items2 = { "Track Rechtsom", "Track Linkssom" };
		TextMenu selectMenu2 = new TextMenu(items2, 2, "Type Track");
		int selectedItem2 = selectMenu2.select();
		if (selectedItem2 == 0) {
			rightSide = false;
		}

		// hier wordt een keuze gemaakt voor het type track
		LCD.clear();
		String[] items1 = { "Normale Track", "Moeilijke Track", "Test", "Default" };
		TextMenu selectMenu1 = new TextMenu(items1, 2, "Racemodus");
		int selectedItem1 = selectMenu1.select();
		if (selectedItem1 == 0) {
			pidController.setKp(KP_VALUE_NORMAL);
			pidController.setKi(KI_VALUE_NORMAL);
			pidController.setKd(KD_VALUE_NORMAL);
			bwApparaat.setSnelheid(SPEED_NORMAL);
			if (tune_normal.exists()) {
				Sound.playSample(tune_normal);
			}
		} else if (selectedItem1 == 1) {
			pidController.setKp(KP_VALUE_DIFFICULT);
			pidController.setKi(KI_VALUE_DIFFICULT);
			pidController.setKd(KD_VALUE_DIFFICULT);
			bwApparaat.setSnelheid(SPEED_DIFFICULT);
			if (tune_difficult.exists()) {
				Sound.playSample(tune_difficult);
			}
		} else if (selectedItem1 == 2) {
			pidController.setKp(kpTest);
			pidController.setKi(kiTest);
			pidController.setKd(kdTest);
			bwApparaat.setSnelheid(speedTest);
		} else if (selectedItem1 == 3) {
			pidController.setKp(KP_VALUE_DEFAULT);
			pidController.setKi(KI_VALUE_DEFAULT);
			pidController.setKd(KD_VALUE_DEFAULT);
			bwApparaat.setSnelheid(SPEED_DEFAULT);
			if (tune_default.exists()) {
				Sound.playSample(tune_default);
			}
		}
		LCD.clear();
		LCD.drawString("Druk ENTER", 3, 3);
		LCD.drawString("om te starten", 2, 4);
		Delay.msDelay(500);
		Button.ENTER.waitForPress();

	}

	public void followLine() {
		LCD.clear();
		LCD.drawString("Volg de lijn!", 3, 3);
		float speed = bwApparaat.getSnelheid();
		float midpoint = PID_Controller.getMidpoint();
		// Zet motoren aan
		bwApparaat.vooruitOfAchteruit('V');
		// Zo lang de knop niet ingedrukt wordt, volgt de robot een lijn
		while (Button.ENTER.isUp()) {
			// variable om het resultaat van de sensor op te slaan
			float currentBrightness = CS.getCurrentNormalisedBrightness();
			float correction = pidController.getCorrection(currentBrightness);
			// rightSide = true, dan rijdt de robot rechtsom, ander linksom
			// beide motoren worden bijgestuurd met de cirrectiefactor
			if (rightSide) {
				bwApparaat.setEngineSpeed(speed * (midpoint - correction), speed * (midpoint + correction));
			} else {
				bwApparaat.setEngineSpeed(speed * (midpoint + correction), speed * (midpoint - correction));
			}
		}
		LCD.clear();
		LCD.drawString("Gestopt", 3, 3);
		Delay.msDelay(1000);
		LCD.clear();
		bwApparaat.close();
		CS.close();

	}

}
