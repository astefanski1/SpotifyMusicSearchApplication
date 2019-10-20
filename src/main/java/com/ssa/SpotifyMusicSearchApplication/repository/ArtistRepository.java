package com.ssa.SpotifyMusicSearchApplication.repository;

import com.ssa.SpotifyMusicSearchApplication.model.Artist;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArtistRepository extends MongoRepository<Artist, String> {

}