package com.cst438;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.controller.DeezerController;
import com.cst438.domain.Song;
import com.cst438.domain.SongRepository;
import com.cst438.domain.User;
import com.cst438.domain.UserLibrary;
import com.cst438.domain.UserLibraryRepository;
import com.cst438.domain.UserRepository;
import com.cst438.service.DeezerServiceREST;

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
    void testAddSongToUserLibrary() {
        int deezerId = 123;
        int userId = 1;

        Song mockSong = new Song();
        User mockUser = new User();

        when(songRepository.findByDeezer_id(deezerId)).thenReturn(null);
        when(deezerService.getTrack(deezerId)).thenReturn(mockSong);
        when(userRepository.findByUser_id(userId)).thenReturn(mockUser);

        deezerController.addSongToUserLibrary(deezerId, userId);

        verify(songRepository).save(any(Song.class));
        verify(userLibraryRepository).save(any(UserLibrary.class));
    }

    @Test
    void testAddSongToUserLibrary_UserNotFound() {
        int deezerId = 123;
        int userId = 1;

        when(userRepository.findByUser_id(userId)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> {
            deezerController.addSongToUserLibrary(deezerId, userId);
        });
    }



}
