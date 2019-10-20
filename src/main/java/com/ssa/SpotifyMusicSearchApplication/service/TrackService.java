package com.ssa.SpotifyMusicSearchApplication.service;

import com.ssa.SpotifyMusicSearchApplication.exceptions.TrackDoesNotExists;
import com.ssa.SpotifyMusicSearchApplication.model.Track;
import com.ssa.SpotifyMusicSearchApplication.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final TrackRepository trackRepository;

    public Track findTrackBySpotifyId(String spotifyId) {
        return trackRepository.findBySpotifyId(spotifyId).orElseThrow(TrackDoesNotExists::new);
    }

}