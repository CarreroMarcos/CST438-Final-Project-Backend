package com.cst438.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "song")
public class Song {
	
	@Id
	private long deezer_id;
	private String title;
	private int duration;
	private String artist;
	private String cover_art;
	private String preview;
	
	public Song() {
		super();
	}

	public long getDeezer_id() {
		return deezer_id;
	}

	public void setDeezer_id(long deezer_id) {
		this.deezer_id = deezer_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getCover_art() {
		return cover_art;
	}

	public void setCover_art(String cover_art) {
		this.cover_art = cover_art;
	}
	

	public String getPreview() {
		return preview;
	}

	public void setPreview(String previewUrl) {
		this.preview = previewUrl;
	}

	@Override
	public String toString() {
		return "Song [deezer_id=" + deezer_id + ", title=" + title + ", duration=" + duration + ", artist=" + artist
				+ ", cover_art=" + cover_art + ", preview=" + preview + "]";
	}
	
	
	
}
