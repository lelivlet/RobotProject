package programs;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;


/**
 * @author Harmen
 * Speelt music en gefet de titel op het display
 *
 */
public class MusicPlayer implements PlayMusic {

	private PlayList playlist;
	private boolean play = true;

	public MusicPlayer() {
		super();
		playlist = new PlayList();
	}
	
	public void run() {
		play();
	}
	
	public void play() {
		while (play) {
			for (int i = 0; i < playlist.getLenght(); i++) {
				LCD.clear();
				if (playlist.getSongFile(i).exists()) {
					LCD.drawString(playlist.getTitle(i), 0, 3);
					Sound.playSample(playlist.getSongFile(i), Sound.VOL_MAX);
				}
				Button.waitForAnyPress(500);
				if (Button.ESCAPE.isDown()) {
					play = false;
				}

			}
		}

	}

	public PlayMusic getPlaylist() {
		return playlist;
	}
	
	public void stopPlay() {
		play = false;
	}

}
