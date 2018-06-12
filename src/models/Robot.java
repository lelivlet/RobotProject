package models;

import Programmas.Draw;
import Programmas.MasterMind;
import lejos.hardware.Button;
import java.io.File;

import Programmas.MusicPlayer;
import Programmas.PlayList;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import menu.TrickMenu;
import lejos.utility.TextMenu;

public class Robot {
	// private fields
	private double defaultSpeed = 200;
	private boolean rightSide; // Deze boolean moet true zijn als de rechter kant de buitenkant van de track is
						// en false als de linkerkant de buitenkant is

	// Initialisatie van het bijbehorende bewegingsapparaat, sensor en controller
	private MotionController bwApparaat;
	private PID_Controller pidController;
	private ColorSensor CS;
	private MasterMind mastermind;
	private Draw draw;
	private TrickMenu trickMenu;
	private MusicPlayer musicPlayer;

	// Constructor
	public Robot() {
		super();
		this.bwApparaat = new MotionController(100);
		this.pidController = new PID_Controller();
		this.CS = new ColorSensor();
		this.mastermind = new MasterMind(CS);
		this.draw = new Draw();
		this.trickMenu = new TrickMenu(this);
		this.musicPlayer = new MusicPlayer();		
	}

	public void run() { // maak een keuze voor een programma
		LCD.clear();
		String[] items = { "Volg een lijn", "Speel Mastermind", "Ga tekenen", "Speel playlist" };
		TextMenu selectMenu = new TextMenu(items, 2, "Wat wil je doen?");
		int selectedItem = selectMenu.select();
		if (selectedItem == 0) {
			FollowLine newFollowline = new FollowLine(pidController, CS, bwApparaat);
			newFollowline.run();
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

	public void close() {
		// sluit de motoren en sensor af
		bwApparaat.close();
		CS.close();
	}

	// Set de rightSide op true of false om zo de richting aan te geven
	public void setRightSide(boolean rightSide) {
		this.rightSide = rightSide;
	}

	// De getters
	public MotionController getBwApparaat() {
		return bwApparaat;
	}

	public PID_Controller getPidController() {
		return pidController;
	}

	public ColorSensor getCS() {
		return CS;
	}

	public MasterMind getMastermind() {
		return mastermind;
	}
	
	public Draw getDraw() {
		return draw;
	}
	
	
	// TODO: MAke runners for MAstermind; after all runners made, and marvin is one, we can remove the getters above here for tricks. 
	public void runMusic() {
		MusicPlayer musicPlayer = new MusicPlayer();
		musicPlayer.run();
	}
	
	public void initAndRunFollowLine() {
		FollowLine newFollowline = new FollowLine(pidController, CS, bwApparaat);
		newFollowline.run();
	}
	
	public void runMasterMind() {
		LCD.drawString("Bye Felicia", 0, 0);
		Delay.msDelay(3000);
	}
	
	public void runDraw() {
		LCD.drawString("Bye Felicia", 0, 0);
		Delay.msDelay(3000);
	}
	
}