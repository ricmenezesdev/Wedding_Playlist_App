package com.techelevator.model;

import javax.validation.constraints.NotNull;

public class BasicPlaylist {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    private String id;

}
