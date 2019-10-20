package com.ssa.SpotifyMusicSearchApplication.service;

import com.ssa.SpotifyMusicSearchApplication.exceptions.ArtistDoesNotExists;
import com.ssa.SpotifyMusicSearchApplication.model.Artist;
import com.ssa.SpotifyMusicSearchApplication.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public Artist findArtistBySpotifyId(String id) {
        return artistRepository.findBySpotifyId(id).orElseThrow(ArtistDoesNotExists::new);
    }
}