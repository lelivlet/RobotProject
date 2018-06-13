package models;

import Programmas.Draw;
import Programmas.MasterMind;
import lejos.hardware.Button;
import java.io.File;

import Programmas.MusicPlayer;
import Programmas.PlayList;
import Programmas.Song;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.internal.ev3.EV3Battery;
import lejos.utility.Delay;
import menu.TrickMenu;
import lejos.utility.TextMenu;

public class Robot {
	// private fields

	// Initialisatie van het bijbehorende bewegingsapparaat, sensor en controller
	private MotionController motionController;
	private PID_Controller pidController;
	private ColorSensor CS;
	private MasterMind mastermind;
	private Draw draw;
	private TrickMenu trickMenu;
	private Dragon dragon;

	// Constructor
	public Robot() {
		super();
		this.motionController = new MotionController();
		this.pidController = new AdvPID_Controller();
		this.CS = new ColorSensor();
		this.dragon = new Dragon(motionController);
		// this.mastermind = new MasterMind(CS);
		// this.draw = new Draw();
		// this.trickMenu = new TrickMenu(this);

	}

	public void run() { // maak een keuze voor een programma
		LCD.clear();
		String[] items = { "Volg een lijn", "Speel Mastermind", "Ga tekenen", "Speel playlist", "Demo Dragon" };
		TextMenu selectMenu = new TextMenu(items, 2, "Wat wil je doen?");
		int selectedItem = selectMenu.select();
		if (selectedItem == 0) {
			
			Song sample1 = dragon.sample_dragon;
			Song sample2 = dragon.sample_dragons_daughter;
			
			PlayList playListIntro = new PlayList();
			playListIntro.getSongs().add(sample1);
			playListIntro.getSongs().add(sample2);
			
			FollowLine followline1 = new FollowLine(pidController, CS, motionController, dragon);
			followline1.calibrate();
			
			playListIntro.run();

			Runnable playlistDragon = dragon.playlist;
			Runnable followLine2 = new FollowLine(pidController, CS, motionController, dragon);

			Thread thread1 = new Thread(playlistDragon);
			Delay.msDelay(1000);
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
			// Draw draw = new Draw(bwApparaat);
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

}