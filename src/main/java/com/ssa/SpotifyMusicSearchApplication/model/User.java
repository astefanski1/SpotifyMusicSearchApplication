package com.ssa.SpotifyMusicSearchApplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"id"})
public class User {

    @Id
    private ObjectId id;

    private String username;

    private String password;

    private Set<Artist> favoriteArtist = new HashSet<>();

    private Set<Track> favoriteTracks = new HashSet<>();

    public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

}