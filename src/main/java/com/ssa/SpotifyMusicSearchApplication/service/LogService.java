package com.ssa.SpotifyMusicSearchApplication.service;

import com.ssa.SpotifyMusicSearchApplication.model.Log;
import com.ssa.SpotifyMusicSearchApplication.model.LogLevel;
import com.ssa.SpotifyMusicSearchApplication.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {

    private final LogRepository logRepository;

    public void createLog(LogLevel logLevel, String msg) {
        Log newLog = Log.builder()
                .logLevel(logLevel)
                .localDateTime(LocalDateTime.now())
                .msg(msg)
                .build();

        logRepository.save(newLog);
        log.info(msg);
    }

    public List<Log> findLogsToDelete() {
        return logRepository.findByLocalDateTimeLessThanEqual(LocalDateTime.now().minusMinutes(60));
    }

    public void deleteLogs(List<Log> logs) {
        try {
            logRepository.deleteAll(logs);
            log.info("Logs with time longer than 1 hour were deleted!");
        } catch (Exception e) {
            log.error("Operation of deleting old logs refused");
        }
    }
}