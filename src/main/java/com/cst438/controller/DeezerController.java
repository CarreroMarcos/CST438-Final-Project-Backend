package com.cst438.controller;

import com.cst438.domain.Song;
import com.cst438.domain.SongDTO;
import com.cst438.domain.SongRepository;
import com.cst438.domain.User;
import com.cst438.domain.UserLibrary;
import com.cst438.domain.UserLibraryDTO;
import com.cst438.domain.UserLibraryRepository;
import com.cst438.domain.UserRepository;
import com.cst438.service.DeezerServiceREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.security.Principal;
import java.util.ArrayList;

import javax.transaction.Transactional;

@RestController
//@CrossOrigin 
public class DeezerController {

    @Autowired
    private DeezerServiceREST deezerService;
    
    @Autowired
    SongRepository songRepository;

    @Autowired
    UserLibraryRepository userLibraryRepository;

    @Autowired
	private UserRepository userRepository;
    
    

    @RequestMapping(value="/search/{query}", method=RequestMethod.GET)
    public List<Song> getSongsBySearch(@PathVariable("query") String query) {
        return deezerService.getTracks(query);
    }

    @RequestMapping(value="/chart", method=RequestMethod.GET)
    public List<Song> getChart() {
        return deezerService.getChart();
    }


    @GetMapping("/userlibrary")
    public SongDTO[] getUserLibrary(Principal principal){
		String alias = principal.getName();
		User currentUser = userRepository.findByAlias(alias);

        List<UserLibrary> songLibrary = userLibraryRepository.findByUserId(currentUser.getId());

        if(songLibrary.size() == 0){
            return new SongDTO[0];
        }
        
        SongDTO[] songs = createLibrary(songLibrary);
        return songs;
    }

    @PostMapping("/userlibrary")
    @Transactional
    public void addSongToUserLibrary(Principal principal, @RequestBody UserLibraryDTO request) {
		String alias = principal.getName();
		User currentUser = userRepository.findByAlias(alias);
        Song song = songRepository.findByDeezerId(request.deezer_id());
        
        if (song == null) {
            song = deezerService.getTrack(request.deezer_id());
            songRepository.save(song);
        }
        
        User user = userRepository.findByEmail(request.email());
        if (user == null) {
            throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "user doesn't exist "+ request.email() );
        }

        List<UserLibrary> songLibrary = userLibraryRepository.findByUserId(user.getId());
        
        for (UserLibrary s : songLibrary){
            if(s.getSong().getDeezer_id() == song.getDeezer_id()) {
                return;
            }
        }
    
        UserLibrary userLibrary = new UserLibrary();
        userLibrary.setUser(user);
        userLibrary.setSong(song);

        userLibraryRepository.save(userLibrary);
         
    }

    @DeleteMapping("/userLibrary/delete/{library_id}")
    @Transactional
    public void removeSongFromUserLibrary(Principal principal, @PathVariable int library_id){
		String alias = principal.getName();
		User currentUser = userRepository.findByAlias(alias);

        UserLibrary userLibrary = userLibraryRepository.findByLibraryId(library_id);

        if (userLibrary == null) {
            throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "error when deleting" );
        }
        
        userLibraryRepository.delete(userLibrary);
    }

    private SongDTO[] createLibrary(List<UserLibrary> userLibrary){
        SongDTO[] songs = new SongDTO[userLibrary.size()];
        for(int i=0; i < songs.length; i++){
            SongDTO dto = createLibrary(userLibrary.get(i));
            songs[i] = dto;
        }
        return songs;
    }

    private SongDTO createLibrary(UserLibrary l){
        Song s = l.getSong();

        SongDTO dto = new SongDTO(
            s.getDeezer_id(),
            s.getTitle(),
            s.getDuration(),
            s.getArtist(),
            s.getCover_art(),
            s.getPreview(),
            l.getLibrary_id()
        );

        return dto;

    }





}
