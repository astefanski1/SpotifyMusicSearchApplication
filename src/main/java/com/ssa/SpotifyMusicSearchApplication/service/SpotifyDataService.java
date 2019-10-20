package com.ssa.SpotifyMusicSearchApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssa.SpotifyMusicSearchApplication.dto.SpotifySearchDTO;
import com.ssa.SpotifyMusicSearchApplication.model.Artist;
import com.ssa.SpotifyMusicSearchApplication.model.Track;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class SpotifyDataService {

    private ApiRequestService apiRequestService;

    public SpotifyDataService(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    public String getAuthToken() {
        return apiRequestService.getAuthToken();
    }

    public SpotifySearchDTO search(String query, String type, int limit, int offset) throws JsonProcessingException, UnsupportedEncodingException {
        return apiRequestService.prepareSearchRequest(query, type, limit, offset);
    }

    public Track findTrackById(String spotifyTrackId) {
        return apiRequestService.findTrackById(spotifyTrackId);
    }

    public Artist findArtistById(String spotifyArtistId) {
        return apiRequestService.findArtistById(spotifyArtistId);
    }

}