package models;

import java.io.File;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
import music.PlayList;
import music.Song;

/**
 * @author Harmen
 * De Dragon Class bestuurt het hoofd en de staart van de Dragon.
 */
public class Dragon implements Runnable {

	private MotionController motionController;
	private static final int CORRECTION_FACTOR = 4; // correctie factor ivm de tandwielen
	private int speed = 200;
	private int correctedSpeed = speed * CORRECTION_FACTOR;
	
	public Song sample_dragon;
	public Song sample_dragon_breath;
	public Song sample_dragon_roar;
	public Song sample_dragons_daughter;
	public Song theme_song;
	public PlayList playlist;
	public int numberofTimes = 3;
	private boolean play;
	
	
	public Dragon(MotionController motionController) {
		this.motionController = motionController;
		this.sample_dragon = new Song("Dragon", new File("dragon.wav"));
		this.sample_dragon_breath = new Song("Dragon breath", new File("dragon_breath"));
		this.sample_dragon_roar = new Song("Dragon roar", new File("dragon_roar"));
		this.sample_dragons_daughter = new Song("Dragons Daughter", new File("the_dragons_daughter.wav"));
		this.theme_song = new Song("Daenerys theme", new File("Daenerys_theme_got"));
		this.playlist = new PlayList();
		playlist.addSong(sample_dragon_roar);
		playlist.addSong(theme_song);
		playlist.addSong(sample_dragon);
		playlist.addSong(theme_song);
		playlist.addSong(sample_dragon_breath);
		playlist.addSong(theme_song);
	}

	public void run() {
		Sound.twoBeeps();
//		er is geen extra motortje voor de staart
//		RegulatedMotor[] syncList = {motionController.getmB()};
//		motionController.getmC().synchronizeWith(syncList );
//		motionController.getmC().startSynchronization();
		while (play) {
			for (int i = 0; i < numberofTimes && play; i++) {
				headRotateTo(0);
				Delay.msDelay(500);	
				headRotateTo(90);
				Delay.msDelay(500);	
				headRotateTo(-180);
				headRotateTo(90);
				Delay.msDelay(500);	
				Button.waitForAnyPress(500);
				if (Button.ENTER.isDown()) {
					play = false;
				}
			}

		}
		
		
		while (Button.ENTER.isUp()) {
			
		}
		motionController.getmB().close();
	}

	public void headRotateTo(int angle) {
		motionController.getmB().setSpeed(correctedSpeed);
		motionController.getmB().rotate(angle * CORRECTION_FACTOR);
		motionController.getmB().waitComplete();
	}

	public void setNumberofTimes(int numberofTimes) {
		this.numberofTimes = numberofTimes;
	}
	
	

}
