package com.ssa.SpotifyMusicSearchApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FieldErrorDTO {

    private String message;

    private String field;

}