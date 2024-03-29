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

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"id"})
public class Track {

    @Id
    private ObjectId id;

    private String spotifyId;

    private String name;

    private String trackNumber;

    private String albumName;

    private String imgUrl;

    private List<Artist> artists;

}