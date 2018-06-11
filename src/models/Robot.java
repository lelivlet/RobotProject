package models;

import java.io.File;

import Programmas.MusicPlayer;
import Programmas.PlayList;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.TextMenu;

public class Robot {

	PID_Controller pidController;
	ColorSensor CS;
	BewegingsApparaat bwApparaat;

	// Constructor
	public Robot() {
		super();
		this.pidController = new PID_Controller();
		this.CS = new ColorSensor();
		this.bwApparaat = new BewegingsApparaat();
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

}