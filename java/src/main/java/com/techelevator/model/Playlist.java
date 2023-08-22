package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Playlist {
    @NotEmpty
    private String playlistId;
    @NotEmpty
    private String playlistName;
    @NotNull
    private Integer dj_id;





    private String specialEvent;

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public int getDj_id() {
        return dj_id;
    }

    public void setDj_id(Integer dj_id) {
        this.dj_id = dj_id;
    }

    public String getSpecialEvent() {
        return specialEvent;
    }

    public void setSpecialEvent(String specialEvent) {
        this.specialEvent = specialEvent;
    }
}
