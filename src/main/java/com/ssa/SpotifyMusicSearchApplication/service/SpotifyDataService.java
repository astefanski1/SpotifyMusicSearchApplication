package com.ssa.SpotifyMusicSearchApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssa.SpotifyMusicSearchApplication.dto.SpotifySearchDTO;
import com.ssa.SpotifyMusicSearchApplication.exceptions.ValidationException;
import com.ssa.SpotifyMusicSearchApplication.model.Artist;
import com.ssa.SpotifyMusicSearchApplication.model.Track;
import com.ssa.SpotifyMusicSearchApplication.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpotifyDataService {

    private final ApiRequestService apiRequestService;

    public String getAuthToken() {
        return apiRequestService.getAuthToken();
    }

    public SpotifySearchDTO search(String query, String type, int limit, int offset) throws JsonProcessingException, UnsupportedEncodingException {
        return apiRequestService.prepareSearchRequest(query, type, limit, offset);
    }

    public Track findTrackById(String spotifyTrackId) throws ValidationException {

        try {
            return apiRequestService.findTrackById(spotifyTrackId);
        } catch (Exception e) {
            throw new ValidationException(new ErrorResponse("Bad request! Make sure that your track ID is correct!"));
        }
    }

    public Artist findArtistById(String spotifyArtistId) throws ValidationException {

        try {
            return apiRequestService.findArtistById(spotifyArtistId);
        } catch (Exception e) {
            throw new ValidationException(new ErrorResponse("Bad request! Make sure that your artist ID is correct!"));
        }
    }

}