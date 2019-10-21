package com.ssa.SpotifyMusicSearchApplication.controller;

import com.ssa.SpotifyMusicSearchApplication.dto.UserDTO;
import com.ssa.SpotifyMusicSearchApplication.exceptions.UsernameIsAlreadyTakenException;
import com.ssa.SpotifyMusicSearchApplication.exceptions.ValidationException;
import com.ssa.SpotifyMusicSearchApplication.mapper.UserMapper;
import com.ssa.SpotifyMusicSearchApplication.model.Artist;
import com.ssa.SpotifyMusicSearchApplication.model.LogLevel;
import com.ssa.SpotifyMusicSearchApplication.model.Track;
import com.ssa.SpotifyMusicSearchApplication.model.User;
import com.ssa.SpotifyMusicSearchApplication.request.SpotifyIdSearchRequest;
import com.ssa.SpotifyMusicSearchApplication.service.LogService;
import com.ssa.SpotifyMusicSearchApplication.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Set;

@RestController
@RequestMapping("/users")
@Api(value = "User Management System", description = "All operations to manage user account")
@RequiredArgsConstructor
@Slf4j
public class UserController extends AbstractController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final LogService logService;

    @PostMapping("/register")
    @ApiOperation(value = "Operation pertaining creating new user", response = UserDTO.class)
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, @ApiIgnore Errors errors)
            throws ValidationException, UsernameIsAlreadyTakenException {

        validateFieldErrors(errors);

        User savedUser = userService.createNewUser(userDTO);

        String logMsg = String.format("Created new user: [%s]", userDTO.getUsername());
        logService.createLog(LogLevel.INFO, logMsg);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/{username}").buildAndExpand(savedUser.getUsername()).toUri();
        return ResponseEntity.created(location).body(userMapper.map(savedUser));
    }

    @GetMapping("/favorite-tracks")
    @ApiOperation(value = "Retrieve list of user favorite tracks assigned to logged account", response = Set.class)
    public Set<Track> getAllFavoriteTracks(@ApiIgnore Principal principal) {

        String logMsg = String.format("User [%s] retrieved personal list of favorite tracks", principal.getName());
        logService.createLog(LogLevel.INFO, logMsg);

        return userService.getAllFavoriteTracks(principal.getName());
    }

    @GetMapping("/favorite-artists")
    @ApiOperation(value = "Retrieve list of user favorite artists assigned to logged account", response = Set.class)
    public Set<Artist> getAllFavoriteArtists(@ApiIgnore Principal principal) {

        String logMsg = String.format("User [%s] retrieved personal list of favorite artists", principal.getName());
        logService.createLog(LogLevel.INFO, logMsg);

        return userService.getAllFavoriteArtists(principal.getName());
    }

    @GetMapping("/artist")
    @ApiOperation(value = "Operation of adding artist to user favorite collection by using spotify id", response = Artist.class)
    public Artist addArtistToFavorites(@Valid SpotifyIdSearchRequest spotifyArtistId, @ApiIgnore Principal principal, @ApiIgnore Errors errors)
            throws ValidationException {

        validateFieldErrors(errors);

        String logMsg = String.format("User [%s] is trying to add artist [id = %s] to list of favorite artists",
                principal.getName(),
                spotifyArtistId.getId());
        logService.createLog(LogLevel.INFO, logMsg);

        return userService.addArtistToFavorites(spotifyArtistId.getId(), principal.getName());
    }

    @GetMapping("/track")
    @ApiOperation(value = "Operation of adding track to user favorite collection by using spotify id", response = Track.class)
    public Track addTrackToFavorites(@Valid SpotifyIdSearchRequest spotifyTrackId, @ApiIgnore Principal principal, @ApiIgnore Errors errors)
            throws ValidationException {

        String logMsg = String.format("User [%s] is adding track [id = %s] to list of favorite tracks",
                principal.getName(),
                spotifyTrackId.getId());
        logService.createLog(LogLevel.INFO, logMsg);

        validateFieldErrors(errors);

        return userService.addTrackToFavorites(spotifyTrackId.getId(), principal.getName());
    }

    @DeleteMapping("/track-delete")
    @ApiOperation(value = "Operation of deleting track from user favorite collection", response = String.class)
    public String deleteTrackFromFavorites(@RequestParam String id, @ApiIgnore Principal principal) throws ValidationException {

        String logMsg = String.format("User [%s] is trying to delete track [id = %s] from list of favorite tracks",
                principal.getName(),
                id);
        logService.createLog(LogLevel.INFO, logMsg);

        userService.deleteTrackFromFavorites(id, principal.getName());

        logMsg = String.format("User [%s] deleted track [id = %s] from list of favorite tracks",
                principal.getName(),
                id);
        logService.createLog(LogLevel.INFO, logMsg);

        return "Track deleted";
    }

    @DeleteMapping("/artist-delete")
    @ApiOperation(value = "Operation of deleting artist from user favorite collection", response = String.class)
    public String deleteArtistFromFavorites(@RequestParam String id, @ApiIgnore Principal principal) throws ValidationException {

        String logMsg = String.format("User [%s] is trying to delete artist [id = %s] from list of favorite artists",
                principal.getName(),
                id);
        logService.createLog(LogLevel.INFO, logMsg);

        userService.deleteArtistFromFavorites(id, principal.getName());

        logMsg = String.format("User [%s] deleted artist [id = %s] from list of favorite artists",
                principal.getName(),
                id);
        logService.createLog(LogLevel.INFO, logMsg);

        return "Artist deleted";
    }

}