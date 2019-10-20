package com.ssa.SpotifyMusicSearchApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This track already exists in your favorites tracks collection!")
public class TrackAlreadyExistsInFavoriteCollectionException extends RuntimeException{

}