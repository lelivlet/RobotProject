package programs;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import lejos.utility.TextMenu;

public class MusicPlayer {

	private PlayList playlist;

	public MusicPlayer() {
		super();
		playlist = new PlayList();
	}

	public void run() {
		boolean play = true;
		while (play) {
			// LCD.clear();
			// LCD.drawString("play = " + String.valueOf(play),3, 5);
			// Delay.msDelay(1000);
			for (int i = 0; i < playlist.getLenght(); i++) {
				LCD.clear();
				if (playlist.getSongFile(i).exists()) {
					LCD.drawString(playlist.getTitle(i), 0, 3);
					Sound.playSample(playlist.getSongFile(i), Sound.VOL_MAX);
				}
				LCD.clear();
				LCD.drawString("Druk op ESCAPE", 3, 3);
				Button.waitForAnyEvent(3000);
				if (Button.ESCAPE.isDown()) {
					LCD.clear();
					LCD.drawString("STOP", 3, 3);
					play = false;
					// LCD.drawString("play = " + String.valueOf(play),3, 5);
					// Delay.msDelay(1000);
				}
			}
		}

	}

	public PlayList getPlaylist() {
		return playlist;
	}

}
