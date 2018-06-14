package models;

import lejos.hardware.Button;
import java.io.File;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.internal.ev3.EV3Battery;
import lejos.utility.Delay;
import menu.TrickMenu;
import programs.Draw;
import programs.MasterMind;
import programs.MusicPlayer;
import programs.PlayList;
import programs.Song;
import lejos.utility.TextMenu;

public class Robot {
	// Initialisatie van het bijbehorende bewegingsapparaat, sensor en controller
	private MotionController motionController;
	private PID_Controller pidController;
	private ColorSensor CS;
	private MasterMind mastermind;
	private Draw draw;
	private TrickMenu trickMenu;
	private Dragon dragon;
	private MusicPlayer musicPlayer;

	// Constructor
	public Robot() {
		super();
		this.motionController = new MotionController();
		this.pidController = new AdvPID_Controller();
		this.CS = new ColorSensor();
		this.dragon = new Dragon(motionController);
		this.mastermind = new MasterMind(CS, motionController);
		this.draw = new Draw(motionController);
		this.trickMenu = new TrickMenu(this);
		this.musicPlayer = new MusicPlayer();
	}

	public Draw getDraw() {
		return draw;
	}

	public MotionController getMotionController() {
		return motionController;
	}

	// maak een keuze voor een programma
	public void run() { 
		LCD.clear();
		String[] items = { "Volg een lijn", "Speel Mastermind", "Ga tekenen", "Speel playlist", "Demo Dragon" };
		TextMenu selectMenu = new TextMenu(items, 2, "Wat wil je doen?");
		int selectedItem = selectMenu.select();
		if (selectedItem == 0) {

			FollowLine followline1 = new FollowLine(pidController, CS, motionController, dragon);
			followline1.calibrate();

			File sample = dragon.sample_dragons_daughter.getFile();

			Sound.playSample(sample, Sound.VOL_MAX);

			LCD.clear();
			LCD.drawString("Druk ENTER", 3, 3);
			LCD.drawString("om te starten", 2, 4);
			Delay.msDelay(500);
			Button.ENTER.waitForPress();

			Runnable playlistDragon = dragon.playlist;
			Runnable followLine2 = new FollowLine(pidController, CS, motionController, dragon);

			Thread thread1 = new Thread(playlistDragon);
			Thread thread2 = new Thread(followLine2);

			// Start threads
			thread1.start();
			thread2.start();

		} else if (selectedItem == 1) {
			// TODO
			// Mastermind mastermind = = new MasterMind(CS);
			// mastermind.run();
		} else if (selectedItem == 2) {
			// TODO
			// Draw draw = new Draw(motionController);
			// draw.run();
		} else if (selectedItem == 3) {
			MusicPlayer musicPlayer = new MusicPlayer();
			musicPlayer.run();
		} else if (selectedItem == 4) {
			Runnable demoDragon = dragon;
			Runnable playlistDragon = dragon.playlist;

			Thread thread1 = new Thread(playlistDragon);
			Thread thread2 = new Thread(demoDragon);

			LCD.clear();
			LCD.drawString("Druk ENTER", 3, 3);
			LCD.drawString("om te starten", 2, 4);
			Delay.msDelay(500);
			Button.ENTER.waitForPress();

			while (Button.ENTER.isUp()) {
				thread1.start();
				Delay.msDelay(1000);
				thread2.start();
			}
			motionController.close();
		}
	}

	public void runDraw() {
		draw.runDrawSequence();
	}

	public void initAndRunFollowLine() {
		FollowLine newFollowline = new FollowLine(pidController, CS, motionController, dragon);
		newFollowline.run();
	}

	public void runMasterMind() {
		mastermind.playMasterMind();
	}

	// runners for TrickMenu
	public void runMusic() {
		MusicPlayer musicPlayer = new MusicPlayer();
		musicPlayer.run();
	}
}