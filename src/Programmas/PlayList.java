package Programmas;

import java.io.File;
import java.util.ArrayList;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

/**
 * @author Harmen In deze class worden de liedjes opgeslagen en het keuzemenu
 *         aangemaakt
 *
 */
public class PlayList implements Runnable {

	private ArrayList<Song> songs;
	private boolean play = true;
	// hier de liedjes toevoegen

	public PlayList() {
		super();
		// voeg liedjes toe aan de playlist
		this.songs = new ArrayList<Song>();
		
		
	}

	@Override
	public void run() {

		while (play) {
			for (int i = 0; i < songs.size() && play; i++) {
				if (getSongFile(i).exists()) {
					Sound.playSample(getSongFile(i), Sound.VOL_MAX);
				}
				Button.waitForAnyEvent(3000);
				if (Button.ESCAPE.isDown()) {
					LCD.clear();
					LCD.drawString("STOP", 3, 3);
					play = false;
				}
			}
		}

	}

	public void stopPlay() {
		play = false;
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}

	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}

	public File getSongFile(int index) {
		File songFile = songs.get(index).getFile();
		return songFile;
	}
	
	public int getLenght() {
		int lenght = songs.size();
		return lenght;
	}
	
	public String getTitle(int index) {
		String title = songs.get(index).getTitle();
		return title;
	}
	

}
