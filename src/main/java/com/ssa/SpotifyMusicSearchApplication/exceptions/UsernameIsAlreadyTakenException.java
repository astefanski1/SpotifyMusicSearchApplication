package com.ssa.SpotifyMusicSearchApplication.exceptions;

import com.ssa.SpotifyMusicSearchApplication.response.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameIsAlreadyTakenException extends Exception {

    private static final long serialVersionUID = -1656239351648079125L;
    private ErrorResponse errorResponse;

    public UsernameIsAlreadyTakenException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

}