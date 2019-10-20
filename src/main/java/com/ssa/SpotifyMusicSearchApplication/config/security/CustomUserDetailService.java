package com.ssa.SpotifyMusicSearchApplication.config.security;

import com.ssa.SpotifyMusicSearchApplication.exceptions.UserDoesNotExistsException;
import com.ssa.SpotifyMusicSearchApplication.model.User;
import com.ssa.SpotifyMusicSearchApplication.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;


    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserDoesNotExistsException {
        User user = userRepository.findByUsername(username).orElseThrow(UserDoesNotExistsException::new);

        try {
            return new CustomUserDetails(user);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

}