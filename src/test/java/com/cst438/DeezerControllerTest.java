package com.cst438;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.controller.DeezerController;
import com.cst438.domain.Song;
import com.cst438.domain.SongDTO;
import com.cst438.domain.SongRepository;
import com.cst438.domain.User;
import com.cst438.domain.UserLibrary;
import com.cst438.domain.UserLibraryDTO;
import com.cst438.domain.UserLibraryRepository;
import com.cst438.domain.UserRepository;
import com.cst438.service.DeezerServiceREST;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class DeezerControllerTest {

    @Mock
    private DeezerServiceREST deezerService;

    @Mock
    private SongRepository songRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLibraryRepository userLibraryRepository;

    @InjectMocks
    private DeezerController deezerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSongsByArtist() {
        String artist = "Daft Punk";
        List<Song> mockSongs = Arrays.asList(new Song(), new Song());
        when(deezerService.getTracks(artist)).thenReturn(mockSongs);

        List<Song> songs = deezerController.getSongsBySearch(artist);

        assertNotNull(songs);
        assertEquals(2, songs.size());
        verify(deezerService).getTracks(artist);
    }

    @Test
    void testGetChart() {
        List<Song> mockChartSongs = Arrays.asList(new Song(), new Song(), new Song());
        when(deezerService.getChart()).thenReturn(mockChartSongs);

        List<Song> chartSongs = deezerController.getChart();

        assertNotNull(chartSongs);
        assertEquals(3, chartSongs.size());
        verify(deezerService).getChart();
    }
    
    @Test
    public void getUserLibrary() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("userAlias");

        User currentUser = new User(); 
        when(userRepository.findByAlias("userAlias")).thenReturn(currentUser);

 
        Song song1 = new Song();
        song1.setDeezer_id(1111111); 
        Song song2 = new Song();
        song2.setDeezer_id(1122222);

        UserLibrary library1 = new UserLibrary();
        library1.setSong(song1);
        UserLibrary library2 = new UserLibrary();
        library2.setSong(song2);

        List<UserLibrary> userLibraries = Arrays.asList(library1, library2);
        when(userLibraryRepository.findByUserId(currentUser.getId())).thenReturn(userLibraries);

        SongDTO[] result = deezerController.getUserLibrary(principal);

        assertNotNull(result);
        assertEquals(userLibraries.size(), result.length);
    }


    @Test
    public void addSongToUserLibrary() {
    	
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("userAlias");

        User user = new User();
        when(userRepository.findByAlias("userAlias")).thenReturn(user);

        UserLibraryDTO request = new UserLibraryDTO("user@email.com", 111111);

        Song song = new Song();
        when(songRepository.findByDeezerId(111111)).thenReturn(song);

        when(userRepository.findByEmail("user@email.com")).thenReturn(user);
        when(userLibraryRepository.findByUserId(user.getId())).thenReturn(new ArrayList<>());

        deezerController.addSongToUserLibrary(principal, request);

        ArgumentCaptor<UserLibrary> captor = ArgumentCaptor.forClass(UserLibrary.class);
        verify(userLibraryRepository).save(captor.capture());
        UserLibrary savedUserLibrary = captor.getValue();
        assertEquals(song, savedUserLibrary.getSong());
        assertEquals(user, savedUserLibrary.getUser());
    }

    @Test
    public void removeSongFromUserLibrary() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("userAlias");

        User currentUser = new User(); 
        when(userRepository.findByAlias("userAlias")).thenReturn(currentUser);

        UserLibrary userLibrary = new UserLibrary();
        int libraryId = 1;
        when(userLibraryRepository.findByLibraryId(libraryId)).thenReturn(userLibrary);

        deezerController.removeSongFromUserLibrary(principal, libraryId);

        verify(userLibraryRepository).delete(userLibrary);
    }



}
