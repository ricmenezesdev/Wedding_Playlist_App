package com.techelevator.dao;

import com.techelevator.model.BasicUser;
import com.techelevator.model.Playlist;
import com.techelevator.model.Song;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcGetDao {

    JdbcTemplate jdbcTemplate;

    public JdbcGetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Playlist> getUserPlaylist(int userId) throws DaoException {
        List<Playlist> playlists = new ArrayList<Playlist>();
        String sql = "SELECT * FROM playlists WHERE playlist_id IN (SELECT playlist_id FROM playlist_users WHERE user_id = ?)";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
            while (rowSet.next()) {
                playlists.add(mapRowToPlaylist(rowSet));

            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return playlists;
    }
    public List<Song> getPlaylistSongs( String id) throws DaoException {
        List<Song> songs = new ArrayList<Song>();
        String sql = "SELECT * FROM Songs WHERE playlist_id = ?";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,id);
            while (rowSet.next()) {
                songs.add(mapRowToSong(rowSet));

            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return songs;
    }

    public Playlist mapRowToPlaylist(SqlRowSet rowSet) {
        Playlist playlist = new Playlist();
        playlist.setDj_id(rowSet.getInt("dj_id"));
        playlist.setPlaylistId(rowSet.getString("playlist_id"));
        playlist.setPlaylistName(rowSet.getString("playlist_name"));
        return playlist;
    }

    public List<BasicUser> getOtherUsers(int id) throws DaoException {
        List<BasicUser> users = new ArrayList<BasicUser>();
        String sql = "SELECT * FROM Users WHERE user_id != ?";
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,id);
            while (rowSet.next()) {
                BasicUser user = new BasicUser();
                user.setId(rowSet.getInt("user_id"));
                user.setUsername(rowSet.getString("username"));
                users.add(user);

            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return users;
    }



    public Song mapRowToSong(SqlRowSet rowSet) {
        Song song = new Song();
        song.setSongId(rowSet.getString("song_id"));
        song.setPlaylistId(rowSet.getString("playlist_id"));
        song.setTitle(rowSet.getString("title"));
        song.setArtist(rowSet.getString("artist"));
        song.setImageUrl(rowSet.getString("imageUrl"));
        song.setMustPlay(rowSet.getBoolean("must_play"));
        song.setDoNotPlay(rowSet.getBoolean("do_not_play"));
        return song;
    }

}
