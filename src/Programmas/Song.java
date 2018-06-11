package Programmas;

import java.io.File;

public class Song {
	
	private String title;
	private File file;
	
	public Song(String title, File file) {
		super();
		this.title = title;
		this.file = file;
	}

	public String getTitle() {
		return title;
	}

	public File getFile() {
		return file;
	}

	
	

}
