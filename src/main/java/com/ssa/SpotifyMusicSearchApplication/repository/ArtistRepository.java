package com.ssa.SpotifyMusicSearchApplication.repository;

import com.ssa.SpotifyMusicSearchApplication.model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ArtistRepository extends MongoRepository<Artist, String> {

    Optional<Artist> findBySpotifyId(String id);

}