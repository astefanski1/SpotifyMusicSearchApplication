package com.ssa.SpotifyMusicSearchApplication.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotifyIdSearchRequest {

    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Id must be only alphanumeric!")
    @NotNull(message = "Id can not be empty!")
    private String id;

}