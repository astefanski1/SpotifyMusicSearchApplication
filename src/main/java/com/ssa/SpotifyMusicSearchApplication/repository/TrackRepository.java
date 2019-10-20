package com.ssa.SpotifyMusicSearchApplication.repository;

import com.ssa.SpotifyMusicSearchApplication.model.Track;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TrackRepository extends MongoRepository<Track, String> {

    Optional<Track> findBySpotifyId(String id);

}