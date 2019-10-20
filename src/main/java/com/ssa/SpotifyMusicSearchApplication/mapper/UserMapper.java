package com.ssa.SpotifyMusicSearchApplication.mapper;

import com.ssa.SpotifyMusicSearchApplication.dto.UserDTO;
import com.ssa.SpotifyMusicSearchApplication.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO map(User user) {
        return UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

}