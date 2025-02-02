package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.entity.Artist;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.entity.Playlist;
import com.ciandt.summit.bootcamp2022.exception.MusicOrPlaylistNotFoundException;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.ciandt.summit.bootcamp2022.repository.PlaylistRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {

    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private MusicRepository musicRepository;

    private static Set<Music> musics;

    private static Music m;
    @Mock
    private static Playlist playlist;

    private static final String PLAYLISTID = "playlist01";

    @BeforeAll
    public static void init() {
        Artist artist = new Artist("30ab1678-c616-4314-adcc-918aff5a7a13", "M1");
        m = new Music("4ffb5d4f-8b7f-4996-b84b-ecf751f52eea", "Leave the Door Open", artist);

        musics = new HashSet<>();
        musics.add(m);
    }

    @Test
    void addMusicIntoPlaylistShouldReturnMusicOrPlaylistNotFoundExceptionWhenMusicNotFound() {
        when(musicRepository.findById(m.getId()))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(MusicOrPlaylistNotFoundException.class,
                () -> playlistService.addMusicIntoPlaylist(m, PLAYLISTID)
        );

        assertEquals(
                "Music with id " + m.getId() + " not found", exception.getMessage());
    }

    @Test
    void addMusicIntoPlaylistShouldReturnPlaylistNotFoundExceptionWhenPlaylistNotFound() {
        when(musicRepository.findById(m.getId()))
                .thenReturn(Optional.ofNullable(m));

        when(playlistRepository.findById(PLAYLISTID))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(MusicOrPlaylistNotFoundException.class,
                () -> playlistService.addMusicIntoPlaylist(m, PLAYLISTID)
        );

        assertEquals(
                "Playlist with id " + PLAYLISTID + " not found", exception.getMessage());
    }

    @Test
    void addMusicIntoPlaylistShouldAddMusic() {

        when(musicRepository.findById(m.getId()))
                .thenReturn(Optional.ofNullable(m));

        when(playlistRepository.findById(PLAYLISTID))
                .thenReturn(Optional.ofNullable(playlist));

        when(playlist.getMusics()).thenReturn(musics);

        assertDoesNotThrow(() -> playlistService.addMusicIntoPlaylist(m, PLAYLISTID));
    }
}
