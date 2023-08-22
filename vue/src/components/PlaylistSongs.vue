<template>
<div class="playlist-songs">
  
    
    <div v-for="song in songs" v-bind:key="song.track.id" class="song-card" v-bind:id="song.track.id">
    
    <img :src="song.track.album.images[0].url" alt="Album Cover" />
    <span>{{song.track.name}} - {{song.track.artists[0].name}}
      
    </span>
    <button v-show="PlaylistOwner == $store.state.user.id" @click="removeSong(song.track.uri, song.track.id)"><i class="fa-regular fa-trash-can fa-2xl"></i></button>
  </div>
  
 </div>
  
  

  
</template>

<script>
import SpotifyService from '../services/SpotifyService'



export default {
  name: "PlaylistSongs",
  props: ["Playlist", "PlaylistOwner"],
  data(){
   return {
     milk: "milk",
     
  }  
  },
  computed:{
    songs(){
      return this.Playlist.tracks.items
    }
  },
  
  
  methods:{
    removeSong(songId, id){
      SpotifyService.removeSongFromPlaylist(localStorage.getItem("bearer"), this.Playlist.id,songId,this.Playlist.snapshot_id)
      
        document.getElementById(id).style.display = "none";
      
      
      
    }
    
  }

}

</script>

<style scoped>
.song-card{
  display: flex;
  flex-wrap: wrap;
  font-size: 26px;
  align-items: center;
  padding-bottom: 5px ;
  border-bottom: 1px solid #ccc;
  margin-bottom: 5px;
}
.song-card img {
  height: 60px;
  width: 60px;
  padding-right: 10px;
}
.song-card span{
  flex-grow: 1;
  text-align: center;
  font-family: Georgia, 'Times New Roman', Times, serif;
  font-style: italic;
    
  padding-right: 20px;
  

}
button{
  border: none;
  
}
.playlist-songs{
  overflow-y: scroll;
}
.playlist-songs::-webkit-scrollbar {
  display: none;
}


</style>