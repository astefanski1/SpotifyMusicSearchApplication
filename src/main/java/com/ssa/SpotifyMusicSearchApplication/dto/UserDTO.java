package com.ssa.SpotifyMusicSearchApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull(message = "Username can not be empty!")
    @Size(min = 3, max = 12, message = "Username must be between 4 and 12 characters!")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username must be only alphanumeric!")
    private String username;

    @NotNull(message = "Password can not be empty!")
    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters!")
    private String password;

}