import axios from "axios";
import store from "../store";

export default {

    createPlaylist(playlist) {
      return axios.post('/playlist/add', playlist)
    },
    
    getPlaylists(id){
      return axios.get(`/${id}/playlists`)
    },
   
    getPlaylistSongs(id){
      return axios.get(`/playlist/${id}/songs`);
    },
    addSongToPlaylist(Song){
      return axios.post('/song/add', Song);
    },
    getOtherUsers(id){
      return axios.get(`/${id}/others`);
    },
    removeSong(songId){
      
      return  axios.post(`/playlist/${store.state.currentPlaylist}/remove`,{songId},{headers:{'Content-Type': 'application/json'}})
      

    },
    addUserToPlaylist(user){
      
        return axios.post(`/playlist/${store.state.currentPlaylist}/add`,user,{headers:{'Content-Type': 'application/json'}})
       
    },
    removePlaylist(playlist){
      return axios.post(`/playlist/remove`,playlist,{headers:{'Content-Type': 'application/json'}})
    }
  
  }