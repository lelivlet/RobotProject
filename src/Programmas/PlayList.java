package Programmas;

import java.io.File;
import java.util.ArrayList;



/**
 * @author Harmen In deze class worden de liedjes opgeslagen en het keuzemenu
 *         aangemaakt
 *
 */
public class PlayList {

	private ArrayList<Song> songs;
	private String[] titellist;
	// hier de liedjes toevoegen

	public PlayList() {
		super();
		// voeg liedjes toe aan de playlist
		songs = new ArrayList<>();
		Song song0 = new Song("Carnaval festival", new File("carnaval_festival.wav"));
		songs.add(song0);
		Song song1 = new Song("Mission Impossible", new File("mission_impossible.wav"));
		songs.add(song1);
		Song song2 = new Song("Star Wars", new File("star_wars.wav"));
		songs.add(song2);
		//
		titellist = new String[songs.size()];
		titellist[0] = song0.getTitle();
		titellist[1] = song1.getTitle();
		titellist[2] = song2.getTitle();
	}

	public File getSongFile(int index) {
		File songFile = songs.get(index).getFile();
		return songFile;
	}

	public String[] getTitellist() {
		return titellist;
	}
	
	public String getTitle(int index) {
		return titellist[index].toString();
	}
	
	public int getLenght() {
		int lenght = songs.size();
		return lenght;
	}
}
