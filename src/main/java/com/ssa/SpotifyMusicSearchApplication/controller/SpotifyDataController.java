package com.ssa.SpotifyMusicSearchApplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssa.SpotifyMusicSearchApplication.dto.SpotifySearchDTO;
import com.ssa.SpotifyMusicSearchApplication.exceptions.ValidationException;
import com.ssa.SpotifyMusicSearchApplication.model.LogLevel;
import com.ssa.SpotifyMusicSearchApplication.request.SpotifySearchRequest;
import com.ssa.SpotifyMusicSearchApplication.service.LogService;
import com.ssa.SpotifyMusicSearchApplication.service.SpotifyDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

@RestController
@RequestMapping("/spotify")
@Slf4j
@Api(value = "Operations pertaining on Spotify WEB API", description = "Operations pertaining on Spotify WEB API")
@RequiredArgsConstructor
public class SpotifyDataController extends AbstractController {

    private final SpotifyDataService spotifyDataService;
    private final LogService logService;

    @GetMapping
    @ApiOperation(value = "Operation which allow to get special authentication token required on Spotify API", response = String.class)
    public String getAuthToken(@ApiIgnore Principal principal) {

        String logMsg = String.format("User [%s] is generating spotify auth token!", principal.getName());
        logService.createLog(LogLevel.INFO, logMsg);

        return spotifyDataService.getAuthToken();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Operation which allow to search artists and tracks on Spotify", response = SpotifySearchDTO.class)
    public SpotifySearchDTO search(@Valid SpotifySearchRequest spotifySearchRequest, @ApiIgnore Errors errors, @ApiIgnore Principal principal)
            throws JsonProcessingException, UnsupportedEncodingException, ValidationException {

        validateFieldErrors(errors);

        String logMsg = String.format("User [%s] searched for %s with parameters [limit = %s, offset = %s, query = %s]",
                principal.getName(),
                spotifySearchRequest.getQueryType(),
                spotifySearchRequest.getLimit(),
                spotifySearchRequest.getOffset(),
                spotifySearchRequest.getQuery());
        logService.createLog(LogLevel.INFO, logMsg);

        return spotifyDataService.search(spotifySearchRequest.getQuery(),
                spotifySearchRequest.getQueryType().getType(),
                spotifySearchRequest.getLimit(),
                spotifySearchRequest.getOffset());
    }

}