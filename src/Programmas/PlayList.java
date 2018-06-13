package Programmas;

import java.io.File;
import java.util.ArrayList;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

/**
 * @author Harmen 
 * hier in kan een playlist worden samengesteld en afgespeeld
 *
 */
public class PlayList implements Runnable, PlayMusic {

	private ArrayList<Song> songs;
	private boolean play = true;
	// hier de liedjes toevoegen

	public PlayList() {
		super();
		// voeg liedjes toe aan de playlist
		this.songs = new ArrayList<Song>();

	}

	public void run() {
		play();
	}
	
	
	@Override
	public void play() {
		Button.ESCAPE.waitForPress();
		while (Button.ESCAPE.isUp() && play == true) {
			for (int i = 0; i < songs.size(); i++) {
				if (getSongFile(i).exists()) {
					Sound.playSample(getSongFile(i), Sound.VOL_MAX);
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see Programmas.PlayMusic#stopPlay()
	 */
	@Override
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
