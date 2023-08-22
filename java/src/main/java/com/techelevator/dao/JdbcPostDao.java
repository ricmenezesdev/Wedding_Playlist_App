package com.techelevator.dao;

import com.techelevator.model.Playlist;
import com.techelevator.model.Song;
import com.techelevator.model.SongId;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcPostDao {
    JdbcTemplate jdbcTemplate;

    public JdbcPostDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addPlaylist(Playlist playlist) throws DaoException {
            String sql = "Insert into playlists (playlist_id, playlist_name, dj_id) values (?,?,?); " +
                    "INSERT into playlist_users(playlist_id, user_id) VALUES (?, ?);";
            try {
                jdbcTemplate.update(sql, playlist.getPlaylistId(), playlist.getPlaylistName(), playlist.getDj_id(), playlist.getPlaylistId(), playlist.getDj_id());

            }  catch (CannotGetJdbcConnectionException e) {
                throw new DaoException("Unable to connect to server or database", e);
            } catch (DataIntegrityViolationException e) {
                throw new DaoException("Data integrity violation", e);
            }
    }

    public void addSong(Song song) throws DaoException {
        String sql = "Insert into songs (song_id,playlist_id,title, artist,imageUrl,must_play,do_not_play) values (?,?,?,?,?,?,?);";
        try {
            jdbcTemplate.update(sql,song.getSongId(), song.getPlaylistId(), song.getTitle(), song.getArtist(), song.getImageUrl(), song.isMustPlay(), song.isDoNotPlay());

        }  catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }
    public void removeSong(String playlistId, SongId id) throws DaoException {
        String sql = "delete from songs where playlist_id = ? AND song_id = ?;";
        try {
            jdbcTemplate.update(sql,playlistId,id.getSongId());

        }  catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }
    public void RemoveUserFromPlaylist(String playlistId, int userId) throws DaoException {
        String sql = "delete from playlist_users where playlist_id = ? AND user_id = ?;";
        try {
            jdbcTemplate.update(sql,playlistId,userId);

        }  catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }
    public void RemovePlaylist(String id) throws DaoException {
        String sql = "delete from playlist_users where playlist_id = ?; delete from songs where playlist_id = ?; delete from playlists where playlist_id = ?";
        try {
            jdbcTemplate.update(sql,id,id,id);

        }  catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }


    public void addUserToPlaylist(String id, int userId) throws DaoException {
        String sql = "INSERT into playlist_users(playlist_id, user_id) VALUES (?, ?);";
        try {
            jdbcTemplate.update(sql,id,userId);

        }  catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

}
