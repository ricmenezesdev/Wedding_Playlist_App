package com.techelevator.model;

import javax.validation.constraints.NotNull;

public class Song {
    @NotNull
    private String songId;
    @NotNull
    private String playlistId;

    @NotNull
    private String title;

    @NotNull
    private String imageUrl;

    @NotNull
    private String artist;
    private boolean mustPlay = false;
    private boolean doNotPlay = false;
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }



    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public boolean isMustPlay() {
        return mustPlay;
    }

    public void setMustPlay(boolean mustPlay) {
        this.mustPlay = mustPlay;
    }

    public boolean isDoNotPlay() {
        return doNotPlay;
    }

    public void setDoNotPlay(boolean doNotPlay) {
        this.doNotPlay = doNotPlay;
    }
}
