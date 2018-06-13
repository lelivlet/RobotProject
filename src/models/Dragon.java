package models;

import java.io.File;

import programs.PlayList;
import programs.Song;
import lejos.hardware.Button;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

/**
 * @author Harmen
 * De Dragon Class bestuurt het hoofd en de staart van de Dragon.
 */
public class Dragon implements Runnable {

	private MotionController motionController;
	private static final int CORRECTION_FACTOR = 4; // correctie factor ivm de tandwielen
	private int speedTail = 200;
	private int speedHead = speedTail * CORRECTION_FACTOR;
	
	public Song sample_dragon;
	public Song sample_dragon_breath;
	public Song sample_dragon_roar;
	public Song sample_dragons_daughter;
	public Song theme_song;
	public PlayList playlist;
	
	
	public Dragon(MotionController motionController) {
		this.motionController = motionController;
		this.sample_dragon = new Song("Dragon", new File("dragon.wav"));
		this.sample_dragon_breath = new Song("Dragon breath", new File("dragon_breath"));
		this.sample_dragon_roar = new Song("Dragon roar", new File("dragon_roar"));
		this.sample_dragons_daughter = new Song("Dragons Daughter", new File("the_dragons_daughter.wav"));
		this.theme_song = new Song("Daenerys theme", new File("daenerys_theme_got"));
		this.playlist = new PlayList();
		playlist.getSongs().add(sample_dragon_roar);
		playlist.getSongs().add(theme_song);
		playlist.getSongs().add(sample_dragon);
		playlist.getSongs().add(theme_song);
		playlist.getSongs().add(sample_dragon_breath);
		playlist.getSongs().add(theme_song);
	}

	public void run() {
		Button.DOWN.waitForPress();
//		er is geen extra motortje voor de staart
//		RegulatedMotor[] syncList = {motionController.getmB()};
//		motionController.getmC().synchronizeWith(syncList );
//		motionController.getmC().startSynchronization();
		while (Button.DOWN.isUp()) {
			headRotateTo(0);
//			tailRotateTo(0);
			Delay.msDelay(500);	
			headRotateTo(90);
//			tailRotateTo(-90);
			Delay.msDelay(500);	
			headRotateTo(-90);
//			tailRotateTo(90);
		}
	}

	public void headRotateTo(int angle) {
		motionController.getmB().setSpeed(speedHead);
		motionController.getmB().rotate(angle * CORRECTION_FACTOR);
	}

}
