package com.ssa.SpotifyMusicSearchApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User with that username does not exists!")
public class UserDoesNotExistsException extends RuntimeException {

}