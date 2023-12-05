package com.cst438.domain;

public record SongDTO(
	    long deezer_id,
	    String title,
	    int duration,
	    String artist,
	    String cover_art,
	    String preview,
	    int library_id
	) {
	}

