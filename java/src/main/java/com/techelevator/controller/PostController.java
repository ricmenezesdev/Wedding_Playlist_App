package com.techelevator.controller;

import com.techelevator.dao.DaoException;
import com.techelevator.dao.JdbcPostDao;
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin

public class PostController {
    JdbcPostDao jdbcPostDao;
    public PostController(JdbcPostDao jdbcPostDao){
        this.jdbcPostDao = jdbcPostDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/playlist/add")
    public void addPlaylist(@Valid @RequestBody Playlist playlist){
        try {
            jdbcPostDao.addPlaylist(playlist);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/song/add")
    public void requestSong(@Valid @RequestBody Song song){
        try{
            jdbcPostDao.addSong(song);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/playlist/{playlistId}/add")
    public void addUserToPlayList( @PathVariable String playlistId,@Valid @RequestBody BasicUser userId){
        try {
            jdbcPostDao.addUserToPlaylist(playlistId,userId.getId());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/playlist/{id}/remove")
    public void removeSong(@PathVariable String id, @RequestBody SongId SongID){
        try{
            jdbcPostDao.removeSong(id,SongID);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/playlist/remove")
    public void removePlaylist(@RequestBody BasicPlaylist playlist){
        try{
            jdbcPostDao.RemovePlaylist(playlist.getId()); ;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }



    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/playlist/{playlistId}")
    public void removeUserFromPlaylist( @PathVariable String playlistId, @Valid @RequestBody int id){
        try{
            jdbcPostDao.RemoveUserFromPlaylist(playlistId,id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
