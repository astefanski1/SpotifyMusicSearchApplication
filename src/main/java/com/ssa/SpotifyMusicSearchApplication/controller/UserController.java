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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Api(value = "User Management System", description = "All operations to manage user account")
@RequiredArgsConstructor
public class UserController extends AbstractController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    @ApiOperation(value = "Operation pertaining creating new user", response = UserDTO.class)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, @ApiIgnore Errors errors)
            throws ValidationException, UsernameIsAlreadyTakenException {

        validateFieldErrors(errors);

        User savedUser = userService.createNewUser(userDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/{username}").buildAndExpand(savedUser.getUsername()).toUri();
        return ResponseEntity.created(location).body(userMapper.map(savedUser));
    }

    @GetMapping("/favoriteTracks")
    @ApiOperation(value = "Retrieve list of user favorite tracks assigned to logged account", response = Set.class)
    public Set<Track> getAllFavoriteTracks(@ApiIgnore Principal principal) {
        return userService.getAllFavoriteTracks(principal.getName());
    }

    @GetMapping("/favoriteArtists")
    @ApiOperation(value = "Retrieve list of user favorite artists assigned to logged account", response = Set.class)
    public Set<Artist> getAllFavoriteArtists(@ApiIgnore Principal principal) {
        return userService.getAllFavoriteArtists(principal.getName());
    }

    @GetMapping("/artist")
    @ApiOperation(value = "Operation of adding artist to user favorite collection by using spotify id", response = Artist.class)
    public Artist addArtistToFavorites(@Valid SpotifyIdSearchRequest spotifyArtistId, @ApiIgnore Principal principal, @ApiIgnore Errors errors)
            throws ValidationException {

        validateFieldErrors(errors);

        return userService.addArtistToFavorites(spotifyArtistId.getId(), principal.getName());
    }

    @GetMapping("/track")
    @ApiOperation(value = "Operation of adding track to user favorite collection by using spotify id", response = Track.class)
    public Track addTrackToFavorites(@Valid SpotifyIdSearchRequest spotifyTrackId, @ApiIgnore Principal principal, @ApiIgnore Errors errors)
            throws ValidationException {

        validateFieldErrors(errors);

        return userService.addTrackToFavorites(spotifyTrackId.getId(), principal.getName());
    }

    @DeleteMapping("/track-delete")
    @ApiOperation(value = "Operation of deleting track from user favorite collection", response = String.class)
    public String deleteTrackFromFavorites(@RequestParam String id, @ApiIgnore Principal principal) {
        userService.deleteTrackFromFavorites(id, principal.getName());

        return "Track deleted";
    }

    @DeleteMapping("/artist-delete")
    @ApiOperation(value = "Operation of deleting artist from user favorite collection", response = String.class)
    public String deleteArtistFromFavorites(@RequestParam String id, @ApiIgnore Principal principal) {
        userService.deleteArtistFromFavorites(id, principal.getName());

        return "Artist deleted";
    }

}