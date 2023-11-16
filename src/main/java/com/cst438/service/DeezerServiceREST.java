package com.cst438.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cst438.domain.Song;
import java.util.ArrayList;
import java.util.List;

@Service
@CrossOrigin 
public class DeezerServiceREST {
	
	private final RestTemplate restTemplate;
	private final ObjectMapper mapper;

	public DeezerServiceREST() {
		this.restTemplate = new RestTemplate();
		this.mapper = new ObjectMapper();
	}

	public List<Song> getTracks(String search) {
		String url = "https://api.deezer.com/search?q=" + search;
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		
		List<Song> songs = new ArrayList<>();
		
		if (response.getStatusCode() == HttpStatus.OK) {
			try {
				JsonNode root = mapper.readTree(response.getBody());
				JsonNode dataArray = root.path("data");
				
				if (dataArray.isArray()) {
					for (JsonNode node : dataArray) {
						Song song = new Song();
						song.setDeezer_id(node.get("id").asLong());
						song.setTitle(node.get("title").asText());
						song.setDuration(node.get("duration").asInt());
						song.setArtist(node.get("artist").get("name").asText());
						song.setCover_art(node.get("album").get("cover_medium").asText());
						song.setPreview(node.get("preview").asText());
						
						songs.add(song);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Failed to fetch data: " + response.getStatusCode());
		}
		
		return songs;
	}

	public List<Song> getChart() {
		String url = "https://api.deezer.com/chart";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		
		List<Song> songs = new ArrayList<>();
		
		if (response.getStatusCode() == HttpStatus.OK) {
			try {
				JsonNode root = mapper.readTree(response.getBody());

				JsonNode tracksNode = root.path("tracks");
				JsonNode dataArray = tracksNode.path("data");
				
				if (dataArray.isArray()) {
					for (JsonNode node : dataArray) {
						Song song = new Song();
						song.setDeezer_id(node.get("id").asLong());
						song.setTitle(node.get("title").asText());
						song.setDuration(node.get("duration").asInt());
						song.setArtist(node.get("artist").get("name").asText());
						song.setCover_art(node.get("album").get("cover_medium").asText());
						song.setPreview(node.get("preview").asText());
						
						songs.add(song);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Failed to fetch data: " + response.getStatusCode());
		}
		
		return songs;
	}

	public Song getTrack(long deezer_id) {
		String url = "https://api.deezer.com/track/" + deezer_id;
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		Song song = new Song();
		
		if (response.getStatusCode() == HttpStatus.OK) {
			try {
				JsonNode root = mapper.readTree(response.getBody());
	
				song.setDeezer_id(root.get("id").asLong());
				song.setTitle(root.get("title").asText());
				song.setDuration(root.get("duration").asInt());
				song.setPreview(root.get("preview").asText());
			
				JsonNode artistNode = root.path("artist");
				song.setArtist(artistNode.get("name").asText());
	
				JsonNode albumNode = root.path("album");
				song.setCover_art(albumNode.get("cover_medium").asText());
	
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Failed to fetch data: " + response.getStatusCode());
		}
		
		return song;
	}
	

}
