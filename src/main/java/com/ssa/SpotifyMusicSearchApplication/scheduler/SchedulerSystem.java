package com.ssa.SpotifyMusicSearchApplication.scheduler;

import com.ssa.SpotifyMusicSearchApplication.model.Log;
import com.ssa.SpotifyMusicSearchApplication.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerSystem {

    private final LogService logService;
    private static final String ONE_MINUTE = "59 * * * * *";

    @Scheduled(cron = ONE_MINUTE)
    public void executeTask() {
        List<Log> logs = logService.findLogsToDelete();
        if (logs.size() > 0) logService.deleteLogs(logs);
    }

}