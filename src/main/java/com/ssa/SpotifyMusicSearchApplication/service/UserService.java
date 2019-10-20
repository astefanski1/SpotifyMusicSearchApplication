package com.ssa.SpotifyMusicSearchApplication.service;

import com.ssa.SpotifyMusicSearchApplication.dto.UserDTO;
import com.ssa.SpotifyMusicSearchApplication.exceptions.ArtistAlreadyExistsInFavoriteCollectionException;
import com.ssa.SpotifyMusicSearchApplication.exceptions.ArtistDoesNotExists;
import com.ssa.SpotifyMusicSearchApplication.exceptions.TrackAlreadyExistsInFavoriteCollectionException;
import com.ssa.SpotifyMusicSearchApplication.exceptions.TrackDoesNotExists;
import com.ssa.SpotifyMusicSearchApplication.exceptions.UserDoesNotExistsException;
import com.ssa.SpotifyMusicSearchApplication.exceptions.UsernameIsAlreadyTakenException;
import com.ssa.SpotifyMusicSearchApplication.model.Artist;
import com.ssa.SpotifyMusicSearchApplication.model.Track;
import com.ssa.SpotifyMusicSearchApplication.model.User;
import com.ssa.SpotifyMusicSearchApplication.repository.ArtistRepository;
import com.ssa.SpotifyMusicSearchApplication.repository.TrackRepository;
import com.ssa.SpotifyMusicSearchApplication.repository.UserRepository;
import com.ssa.SpotifyMusicSearchApplication.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ArtistRepository artistRepository;
    private final TrackRepository trackRepository;
    private final SpotifyDataService spotifyDataService;
    private final TrackService trackService;
    private final ArtistService artistService;

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
        if (isArtistExistsInFavoriteCollection(user, spotifyArtistId))
            throw new ArtistAlreadyExistsInFavoriteCollectionException();
        Artist artist = artistRepository.save(spotifyDataService.findArtistById(spotifyArtistId));
        user.getFavoriteArtist().add(artist);
        userRepository.save(user);
        return artist;
    }

    public Track addTrackToFavorites(String spotifyTrackId, String username) {
        User user = findUserByUsername(username);
        if (isTrackExistsInFavoriteCollection(user, spotifyTrackId))
            throw new TrackAlreadyExistsInFavoriteCollectionException();
        Track track = trackRepository.save(spotifyDataService.findTrackById(spotifyTrackId));
        user.getFavoriteTracks().add(track);
        userRepository.save(user);
        return track;
    }

    public void deleteTrackFromFavorites(String id, String username) {
        User user = findUserByUsername(username);
        if (!isTrackExistsInFavoriteCollection(user, id)) throw new TrackDoesNotExists();
        Track track = trackService.findTrackBySpotifyId(id);
        user.getFavoriteTracks().remove(track);
        trackRepository.delete(track);
    }

    public void deleteArtistFromFavorites(String id, String username) {
        User user = findUserByUsername(username);
        if (!isArtistExistsInFavoriteCollection(user, id)) throw new ArtistDoesNotExists();
        Artist artist = artistService.findArtistBySpotifyId(id);
        user.getFavoriteArtist().remove(artist);
        artistRepository.delete(artist);
    }

    private boolean isArtistExistsInFavoriteCollection(User user, String spotifyArtistId) {
        return user.getFavoriteArtist().stream().anyMatch(artist -> artist.getSpotifyId().equals(spotifyArtistId));

    }

    private boolean isTrackExistsInFavoriteCollection(User user, String spotifyTrackId) {
        return user.getFavoriteTracks().stream().anyMatch(track -> track.getSpotifyId().equals(spotifyTrackId));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserDoesNotExistsException::new);
    }

}