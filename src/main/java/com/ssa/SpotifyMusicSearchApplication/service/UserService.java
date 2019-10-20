package com.ssa.SpotifyMusicSearchApplication.service;

import com.ssa.SpotifyMusicSearchApplication.dto.UserDTO;
import com.ssa.SpotifyMusicSearchApplication.exceptions.ArtistAlreadyExistsInFavoriteCollectionException;
import com.ssa.SpotifyMusicSearchApplication.exceptions.TrackAlreadyExistsInFavoriteCollectionException;
import com.ssa.SpotifyMusicSearchApplication.exceptions.UserDoesNotExistsException;
import com.ssa.SpotifyMusicSearchApplication.exceptions.UsernameIsAlreadyTakenException;
import com.ssa.SpotifyMusicSearchApplication.model.Artist;
import com.ssa.SpotifyMusicSearchApplication.model.Track;
import com.ssa.SpotifyMusicSearchApplication.model.User;
import com.ssa.SpotifyMusicSearchApplication.repository.ArtistRepository;
import com.ssa.SpotifyMusicSearchApplication.repository.TrackRepository;
import com.ssa.SpotifyMusicSearchApplication.repository.UserRepository;
import com.ssa.SpotifyMusicSearchApplication.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ArtistRepository artistRepository;
    private TrackRepository trackRepository;
    private SpotifyDataService spotifyDataService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       TrackRepository trackRepository,
                       ArtistRepository artistRepository,
                       SpotifyDataService spotifyDataService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
        this.spotifyDataService = spotifyDataService;
    }

    public User createNewUser(UserDTO userDTO) throws UsernameIsAlreadyTakenException {
        log.info("Creating user: {}", userDTO.getUsername());

        if (userRepository.findByUsername(userDTO.getUsername()).isPresent())
            throw new UsernameIsAlreadyTakenException(new ErrorResponse("This username is already taken"));

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();

        return userRepository.save(user);
    }

    public Set<Track> getAllFavoriteTracks(String username) {
        return findUserByUsername(username).getFavoriteTracks();
    }

    public Set<Artist> getAllFavoriteArtists(String username) {
        return findUserByUsername(username).getFavoriteArtist();
    }

    public Artist addArtistToFavorites(String spotifyArtistId, String username) {
        User user = findUserByUsername(username);
        checkForArtistDuplicatesInFavoriteCollection(user, spotifyArtistId);
        Artist artist = artistRepository.save(spotifyDataService.findArtistById(spotifyArtistId));
        user.getFavoriteArtist().add(artist);
        userRepository.save(user);
        return artist;
    }

    public Track addTrackToFavorites(String spotifyTrackId, String username) {
        User user = findUserByUsername(username);
        checkForTrackDuplicatesInFavoriteCollection(user, spotifyTrackId);
        Track track = trackRepository.save(spotifyDataService.findTrackById(spotifyTrackId));
        user.getFavoriteTracks().add(track);
        userRepository.save(user);
        return track;
    }

    private void checkForArtistDuplicatesInFavoriteCollection(User user, String spotifyArtistId) {
        if (user.getFavoriteArtist().stream().anyMatch(artist -> artist.getSpotifyId().equals(spotifyArtistId)))
            throw new ArtistAlreadyExistsInFavoriteCollectionException();
    }

    private void checkForTrackDuplicatesInFavoriteCollection(User user, String spotifyTrackId) {
        if (user.getFavoriteTracks().stream().anyMatch(track -> track.getSpotifyId().equals(spotifyTrackId)))
            throw new TrackAlreadyExistsInFavoriteCollectionException();
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserDoesNotExistsException::new);
    }

}