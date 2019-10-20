package com.ssa.SpotifyMusicSearchApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This artist already exists in your favorites artist collection!")
public class ArtistAlreadyExistsInFavoriteCollectionException extends RuntimeException {

}