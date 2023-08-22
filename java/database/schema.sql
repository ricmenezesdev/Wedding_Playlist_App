BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS playlist_song;
DROP TABLE IF EXISTS songs;
DROP TABLE IF EXISTS playlists;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);
CREATE TABLE playlists(
	playlist_id  VARCHAR(255) PRIMARY KEY,
	playlist_name VARCHAR(255),
	dj_id int, 
	CONSTRAINT FK_dj_id FOREIGN KEY(dj_id) REFERENCES users(user_id)
	
);
CREATE TABLE songs(
	song_id  VARCHAR(255) PRIMARY KEY,
	playlist_id  VARCHAR(255),
	title VARCHAR(255),
	artist VARCHAR(255),
	imageUrl VARCHAR(255),
	must_play boolean,
	do_not_play boolean,
	CONSTRAINT FK_playlist_id FOREIGN KEY(playlist_id) REFERENCES playlists(playlist_id)
);
CREATE TABLE playlist_users(
	user_id INT, 
	playlist_id VARCHAR(255),
	CONSTRAINT FK_playlist_id FOREIGN KEY(playlist_id) REFERENCES playlists(playlist_id),
	CONSTRAINT FK_user_id FOREIGN KEY(user_id) REFERENCES users(user_id),
	CONSTRAINT PK_playlist_users PRIMARY KEY (user_id, playlist_id)
);

COMMIT TRANSACTION;
