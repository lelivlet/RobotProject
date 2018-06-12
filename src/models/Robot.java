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

	// Constructor
	public Robot() {
		super();
		this.motionController = new MotionController();
		this.pidController = new PID_Controller();
		this.CS = new ColorSensor();
//		this.mastermind = new MasterMind(CS);
//		this.draw = new Draw();
//		this.trickMenu = new TrickMenu(this);
	}

	public void run() { // maak een keuze voor een programma
		LCD.clear();
		String[] items = { "Volg een lijn", "Speel Mastermind", "Ga tekenen", "Speel playlist" };
		TextMenu selectMenu = new TextMenu(items, 2, "Wat wil je doen?");
		int selectedItem = selectMenu.select();
		if (selectedItem == 0) {
			
			PlayList playlist = new MusicPlayer().getPlaylist();
			Song song1 = new Song("Carnaval festival", new File("carnaval_festival.wav"));
			playlist.getSongs().add(song1);
			Song song2 = new Song("Mission Impossible", new File("mission_impossible.wav"));
			playlist.getSongs().add(song2);
			Song song3 = new Song("Star Wars", new File("star_wars.wav"));
			playlist.getSongs().add(song3);
			
			FollowLine followline1 = new FollowLine(pidController, CS, motionController);
			followline1.calibrate();
			
			Runnable playlist1 = playlist;
			Runnable followLine2 = new FollowLine(pidController, CS, motionController);
									
			Thread thread1 = new Thread(playlist1);
			Delay.msDelay(500);
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
		}
	}

	
	
}