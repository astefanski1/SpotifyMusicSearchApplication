package com.ssa.SpotifyMusicSearchApplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssa.SpotifyMusicSearchApplication.dto.SpotifySearchDTO;
import com.ssa.SpotifyMusicSearchApplication.mapper.ApiResponseMapper;
import com.ssa.SpotifyMusicSearchApplication.model.Artist;
import com.ssa.SpotifyMusicSearchApplication.model.QueryType;
import com.ssa.SpotifyMusicSearchApplication.model.Track;
import com.ssa.SpotifyMusicSearchApplication.response.AccessTokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiRequestService {

    private static final String CLIENT = "b0ac4ea7dd8b411d873606d4ffd96f5f:94ac8c5ae02a4cf39161f36ffb2fd34c";
    private static final String AUTH_TOKEN_URL = "https://accounts.spotify.com/api/token";
    private static final String SEARCH_URL = "https://api.spotify.com/v1/search";
    private static final String ARTIST_FIND_URL = "https://api.spotify.com/v1/artists/";
    private static final String TRACK_FIND_URL = "https://api.spotify.com/v1/tracks/";

    private final ApiResponseMapper apiResponseMapper;

    public String getAuthToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> request = new HttpEntity<>(prepareAuthTokenHeaders());
        ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(prepareAuthUri(), HttpMethod.POST, request, AccessTokenResponse.class);
        return response.getBody().getAccess_token();
    }

    public URI prepareAuthUri() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(AUTH_TOKEN_URL).queryParam("grant_type", "client_credentials");
        return builder.build().encode().toUri();
    }

    public HttpHeaders prepareAuthTokenHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        String token = new String(Base64.getEncoder().encode((CLIENT).getBytes()));
        httpHeaders.add("Authorization", "Basic " + token);

        return httpHeaders;
    }

    public SpotifySearchDTO prepareSearchRequest(String query, String type, int limit, int offset) throws JsonProcessingException, UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        SpotifySearchDTO spotifySearchDTO = new SpotifySearchDTO();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + getAuthToken());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SEARCH_URL)
                .queryParam("q", URLEncoder.encode(query, StandardCharsets.UTF_8.toString()))
                .queryParam("type", type)
                .queryParam("market", "US")
                .queryParam("limit", limit)
                .queryParam("offset", offset);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        if (type.equals(QueryType.TRACK.getType())) {
            spotifySearchDTO.setTracks(apiResponseMapper.mapToTrackList(response));
        } else if (type.equals(QueryType.ARTIST.getType())) {
            spotifySearchDTO.setArtists(apiResponseMapper.mapToArtistList(response));
        } else return apiResponseMapper.mapToTrackArtist(response, spotifySearchDTO);

        return spotifySearchDTO;
    }

    public Track findTrackById(String id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + getAuthToken());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TRACK_FIND_URL + id)
                .queryParam("market", "US");


        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        return apiResponseMapper.mapToTrack(response);
    }

    public Artist findArtistById(String id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + getAuthToken());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                ARTIST_FIND_URL + id,
                HttpMethod.GET,
                entity,
                String.class);

        return apiResponseMapper.mapToArtist(response);
    }

}