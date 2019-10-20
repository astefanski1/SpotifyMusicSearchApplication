package com.ssa.SpotifyMusicSearchApplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssa.SpotifyMusicSearchApplication.dto.SpotifySearchDTO;
import com.ssa.SpotifyMusicSearchApplication.exceptions.ValidationException;
import com.ssa.SpotifyMusicSearchApplication.request.SpotifySearchRequest;
import com.ssa.SpotifyMusicSearchApplication.service.SpotifyDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/spotify")
@Slf4j
public class SpotifyDataController extends AbstractController {

    private SpotifyDataService spotifyDataService;

    public SpotifyDataController(SpotifyDataService spotifyDataService) {
        this.spotifyDataService = spotifyDataService;
    }

    @GetMapping
    public String getAuthToken() {
        return spotifyDataService.getAuthToken();
    }

    @GetMapping("/search")
    public SpotifySearchDTO search(@Valid SpotifySearchRequest spotifySearchRequest, @ApiIgnore Errors errors)
            throws JsonProcessingException, UnsupportedEncodingException, ValidationException {

        validateFieldErrors(errors);

        return spotifyDataService.search(spotifySearchRequest.getQuery(),
                spotifySearchRequest.getQueryType().getType(),
                spotifySearchRequest.getLimit(),
                spotifySearchRequest.getOffset());
    }

}