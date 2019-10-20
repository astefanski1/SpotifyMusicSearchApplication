package com.ssa.SpotifyMusicSearchApplication.model;

import lombok.Getter;

@Getter
public enum QueryType {
    TRACK("track"), ARTIST("artist"), TRACK_ARTIST("track,artist");

    private String type;

    QueryType(String type) {
        this.type = type;
    }

}