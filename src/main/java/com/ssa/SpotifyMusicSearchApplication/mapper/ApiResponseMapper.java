package com.ssa.SpotifyMusicSearchApplication.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssa.SpotifyMusicSearchApplication.dto.SpotifySearchDTO;
import com.ssa.SpotifyMusicSearchApplication.model.Artist;
import com.ssa.SpotifyMusicSearchApplication.model.Track;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ApiResponseMapper {

    private ObjectMapper objectMapper = new ObjectMapper();

    public Track mapToTrack(HttpEntity<String> response) {
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            return getTrackValuesFromJSON(rootNode);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Track> mapToTrackList(HttpEntity<String> response) {
        List<Track> tracks = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode itemsNode = rootNode.get("tracks").get("items");
            itemsNode.forEach(trackNode -> tracks.add(getTrackValuesFromJSON(trackNode)));
            return tracks;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return tracks;
    }

    public Artist mapToArtist(HttpEntity<String> response) {
        try {
            JsonNode artistNode = objectMapper.readTree(response.getBody());
            return getArtistValuesFromJSON(artistNode);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Artist> mapToArtistList(HttpEntity<String> response) {
        List<Artist> artistList = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            JsonNode items = rootNode.get("artists").get("items");
            items.forEach(artistNode -> artistList.add(getArtistValuesFromJSON(artistNode)));
            return artistList;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return artistList;
    }

    public SpotifySearchDTO mapToTrackArtist(HttpEntity<String> response, SpotifySearchDTO spotifySearchDTO) {
        spotifySearchDTO.setArtists(mapToArtistList(response));
        spotifySearchDTO.setTracks(mapToTrackList(response));
        return spotifySearchDTO;
    }

    private Artist getArtistValuesFromJSON(JsonNode jsonNode) {
        Artist artist = new Artist();
        artist.setSpotifyId(jsonNode.get("id").textValue());
        artist.setName(jsonNode.get("name").textValue());
        return artist;
    }

    private Track getTrackValuesFromJSON(JsonNode jsonNode) {
        Track track = new Track();
        List<Artist> artistsList = new ArrayList<>();

        track.setSpotifyId(jsonNode.get("id").textValue());
        track.setName(jsonNode.get("name").textValue());
        track.setTrackNumber(jsonNode.get("track_number").textValue());
        track.setAlbumName(jsonNode.get("album").get("name").textValue());
        track.setImgUrl(jsonNode.get("album").get("images").get(1).get("url").textValue());
        JsonNode artistsNode = jsonNode.get("artists");
        artistsNode.forEach(artistNode -> artistsList.add(getArtistValuesFromJSON(artistNode)));
        track.setArtists(artistsList);
        return track;
    }

}