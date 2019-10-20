package com.ssa.SpotifyMusicSearchApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Artists with that spotify id does not exists!")
public class ArtistDoesNotExists extends RuntimeException {

}