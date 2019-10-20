package com.ssa.SpotifyMusicSearchApplication.controller;

import com.ssa.SpotifyMusicSearchApplication.dto.UserDTO;
import com.ssa.SpotifyMusicSearchApplication.exceptions.UsernameIsAlreadyTakenException;
import com.ssa.SpotifyMusicSearchApplication.exceptions.ValidationException;
import com.ssa.SpotifyMusicSearchApplication.mapper.UserMapper;
import com.ssa.SpotifyMusicSearchApplication.model.Artist;
import com.ssa.SpotifyMusicSearchApplication.model.Track;
import com.ssa.SpotifyMusicSearchApplication.model.User;
import com.ssa.SpotifyMusicSearchApplication.request.SpotifyIdSearchRequest;
import com.ssa.SpotifyMusicSearchApplication.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController {

    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @PostMapping
    @ApiOperation(value = "Create new user", response = User.class)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, @ApiIgnore Errors errors)
            throws ValidationException, UsernameIsAlreadyTakenException {

        validateFieldErrors(errors);

        User savedUser = userService.createNewUser(userDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/{username}").buildAndExpand(savedUser.getUsername()).toUri();
        return ResponseEntity.created(location).body(userMapper.map(savedUser));
    }

    @GetMapping("/favoriteTracks")
    public Set<Track> getAllFavoriteTracks(@ApiIgnore Principal principal) {
        return userService.getAllFavoriteTracks(principal.getName());
    }

    @GetMapping("/favoriteArtists")
    public Set<Artist> getAllFavoriteArtists(@ApiIgnore Principal principal) {
        return userService.getAllFavoriteArtists(principal.getName());
    }

    @GetMapping("/artist")
    public Artist addArtistToFavorites(@Valid SpotifyIdSearchRequest spotifyArtistId, @ApiIgnore Principal principal, @ApiIgnore Errors errors)
            throws ValidationException {

        validateFieldErrors(errors);

        return userService.addArtistToFavorites(spotifyArtistId.getId(), principal.getName());
    }

    @GetMapping("/track")
    public Track addTrackToFavorites(@Valid SpotifyIdSearchRequest spotifyTrackId, @ApiIgnore Principal principal, @ApiIgnore Errors errors)
            throws ValidationException {

        validateFieldErrors(errors);

        return userService.addTrackToFavorites(spotifyTrackId.getId(), principal.getName());
    }

}