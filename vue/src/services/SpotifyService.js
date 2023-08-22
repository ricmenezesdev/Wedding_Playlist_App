import axios from "axios";
import store from "../store";
import QueryString from "qs";
import  {Buffer} from "buffer";


export default{
    getSpotifyUser(token){
        return axios.get("https://api.spotify.com/v1/me", {headers: {'Authorization': `Bearer ${token}`}});
    },
    createPlaylist(bearer, playlist){
        axios.defaults.headers.common['Authorization'] = `Bearer ${bearer}`
        let id = localStorage.getItem("spotifyId");
        let response = axios.post("https://api.spotify.com/v1/users/" + id + "/playlists",
          playlist,);
         axios.defaults.headers.common['Authorization'] = `Bearer ${store.state.token}`
         return response;
    },
    getBearer(){
        let client_id = "206c886882734b0aa03c6c0ed2b36a73";
       let client_secret = "c9494a87470b4694ba80df0fd97aacbd";
       let auth_token =  Buffer.from(`${client_id}:${client_secret}`, 'utf-8').toString('base64');
        
        const data = { grant_type: "client_credentials" };
        const options = {
          method: "POST",
          headers: {
            Authorization:
              "Basic " +
             auth_token,
            "content-type": "application/x-www-form-urlencoded",
          },
          data: QueryString.stringify(data),
          url: "https://accounts.spotify.com/api/token",
        };
         
         axios(options).then((response)=>{
            localStorage.setItem("spotifyBearer", response.data.access_token )
         })
        
        
    },
   getPlaylist(id){
       
       let bearer = localStorage.getItem("spotifyBearer")
       axios.defaults.headers.common['Authorization'] = `Bearer ${bearer}`
       let response =axios.get(`https://api.spotify.com/v1/playlists/${id}`)
       axios.defaults.headers.common['Authorization'] = `Bearer ${store.state.token}`

       return response;
   },
   searchSong(bearer, query) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${bearer}`;
    let response = axios.get(`https://api.spotify.com/v1/search?q=${query}&type=track`);
    axios.defaults.headers.common['Authorization'] = `Bearer ${store.state.token}`;
    return response;
  },
  addSongToPlaylist(bearer, playlistId, trackUri) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${bearer}`;
    let response = axios.post(`https://api.spotify.com/v1/playlists/${playlistId}/tracks`, {
      uris: [trackUri],
    });
    axios.defaults.headers.common['Authorization'] = `Bearer ${store.state.token}`;
    return response;
  },
  removeSongFromPlaylist(bearer,playlistId,songId,snapshot){
   axios.defaults.headers.common['Authorization'] = `Bearer ${bearer}`;
   
   axios.delete(`https://api.spotify.com/v1/playlists/${playlistId}/tracks`, {data:{"tracks": [{"uri":songId}],"snapshot_id": snapshot}})
   axios.defaults.headers.common['Authorization'] = `Bearer ${store.state.token}`;

  }
   

}