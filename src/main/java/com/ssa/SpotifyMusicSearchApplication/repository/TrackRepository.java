package com.ssa.SpotifyMusicSearchApplication.repository;

import com.ssa.SpotifyMusicSearchApplication.model.Track;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrackRepository extends MongoRepository<Track, String> {

}