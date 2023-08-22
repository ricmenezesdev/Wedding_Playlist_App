package com.techelevator.controller;

import com.techelevator.dao.DaoException;
import com.techelevator.dao.JdbcGetDao;
import com.techelevator.model.BasicUser;
import com.techelevator.model.Playlist;
import com.techelevator.model.Song;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class GetController {
    JdbcGetDao jdbcGetDao;
    public GetController(JdbcGetDao jdbcGetDao) {
        this.jdbcGetDao = jdbcGetDao;
    }
    @GetMapping("/{id}/playlists")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Playlist> getUserPlaylists(@PathVariable int id) {
        try {
            return jdbcGetDao.getUserPlaylist(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/playlist/{id}/songs")
    @ResponseStatus(HttpStatus.ACCEPTED )
    public List<Song> getPlaylistSongs(@PathVariable String id){
        try{
            return  jdbcGetDao.getPlaylistSongs(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/others")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<BasicUser> getOtherUsers(@PathVariable int id){
        try {
            return jdbcGetDao.getOtherUsers(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
