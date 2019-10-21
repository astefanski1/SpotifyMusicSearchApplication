package com.ssa.SpotifyMusicSearchApplication.repository;

import com.ssa.SpotifyMusicSearchApplication.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends MongoRepository<Log, String> {

    List<Log> findByLocalDateTimeLessThanEqual(LocalDateTime time);

}