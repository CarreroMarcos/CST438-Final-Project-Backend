package com.cst438.controller;

import com.cst438.domain.Song;
import com.cst438.domain.SongRepository;
import com.cst438.domain.User;
import com.cst438.domain.UserLibrary;
import com.cst438.domain.UserLibraryRepository;
import com.cst438.domain.UserRepository;
import com.cst438.service.DeezerServiceREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@RestController
@CrossOrigin 
public class DeezerController {

    @Autowired
    private DeezerServiceREST deezerService;
    
    @Autowired
    SongRepository songRepository;

    @Autowired
    UserLibraryRepository userLibraryRepository;

    @Autowired
	private UserRepository userRepository;
    
    

    @GetMapping("/search/{query}")
    public List<Song> getSongsBySearch(@PathVariable("query") String query) {
        return deezerService.getTracks(query);
    }

    @GetMapping("/chart")
    public List<Song> getChart() {
        return deezerService.getChart();
    }

    @PostMapping("/userlibrary")
    @Transactional
    public void addSongToUserLibrary(@RequestParam("deezer_id") int deezer_id, @RequestParam("user_id") int userId) {
        Song song = songRepository.findByDeezer_id(deezer_id);
        
        if (song == null) {
            song = deezerService.getTrack(deezer_id);
            songRepository.save(song);
        }

        User user = userRepository.findByUser_id(userId);
        if (user == null) {
            throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "user doesn't exist "+ userId );
        }
    
        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setUser(user);
        userLibrary.setSong(song);

        userLibraryRepository.save(userLibrary);
         
    }
}
