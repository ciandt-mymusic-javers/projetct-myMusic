package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.entity.Playlist;
import com.ciandt.summit.bootcamp2022.exception.MusicOrPlaylistNotFoundException;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.ciandt.summit.bootcamp2022.repository.PlaylistRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Log4j2
@Service
public class PlaylistService implements IPlaylistService {
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private MusicRepository musicRepository;

    @Override
    public Playlist addMusicIntoPlaylist(Music music, String playlistId){
        Optional<Music> musicFound = musicRepository.findById(music.getId());
        if(!musicFound.isPresent()) {
            log.error("Music was not found.");
            throw new MusicOrPlaylistNotFoundException("Music with id " + music.getId() + " not found");
        }

        Optional<Playlist> playlistFound = playlistRepository.findById(playlistId);
        if(!playlistFound.isPresent()) {
            log.error("Playlist was not found.");
            throw new MusicOrPlaylistNotFoundException("Playlist with id " + playlistId + " not found");
        }
        else{
            log.info("Music added into the playlist.");
            Playlist playlist =  playlistFound.get();
            playlist.getMusics().add(music);
            return playlistRepository.save(playlist);
        }
    }
}
