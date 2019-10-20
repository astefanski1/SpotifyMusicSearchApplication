package com.ssa.SpotifyMusicSearchApplication.request;

import com.ssa.SpotifyMusicSearchApplication.model.QueryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotifySearchRequest {

    @NotNull(message = "Query can not be empty!")
    private String query;

    @NotNull(message = "Limit can not be empty!")
    @Min(value = 1, message = "Minimum value for searching limit is 1!")
    @Max(value = 50, message = "Maximum value for searching limit is 50!")
    private int limit;

    @Min(value = 0, message = "Minimum value for offset is 0.")
    @Max(value = 10000, message = "Maximum value for offset is 10 000.")
    private int offset;

    @NotNull(message = "Query type can not be empty!")
    private QueryType queryType;

}