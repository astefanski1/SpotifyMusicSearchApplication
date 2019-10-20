package com.ssa.SpotifyMusicSearchApplication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class Artist {

    @Id
    private ObjectId id;

    private String spotifyId;

    private String name;

}