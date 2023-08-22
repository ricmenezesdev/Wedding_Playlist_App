<template>
<div class="wholeDisplay">
 <div class="box-outer"> <h1>Welcome Hom{{e}} </h1> </div>
  <div class="home">
    
 
    
    <!-- <div class="formfield" id="playlistName">
       <selection-box class="playlistName" v-bind="SpotifyPlaylists" mutation="SET_USER"/>
    </div>
    -->
    <div class="main-content">
      <!-- Left Side: Playlist Creation and Container -->
      <div class="playlist-section">
        <form action="" v-if="$store.state.hasSpotify" id="createForm">
          <input class="label" v-model="playlist.name" type="text" placeholder="New Playlist" id="playlistName">
          <input class="label" v-model="playlist.description" type="text" placeholder="Playlist Description" id="playlistDesc">
          <button @click.prevent="createPlaylist">Create Playlist</button>
        <button v-show="$store.state.currentPlaylist != ''" class="addUser" type="button" @click="changeSelectUsers"><i class="fa-solid fa-user-plus fa-2xl"></i></button>
        </form>
        <div v-else id="normieSelect">
          <h2>
            Selec{{t}}Playlist
          </h2>
        </div>
        <!-- <add-user></add-user> -->
        
        <div class="playlist-container" id="playlist-container">
          <play-list ref="testref" @closeAdd="closeAdd" v-bind:Playlists="DatabasePlaylists"></play-list>
          
          <!-- ... other playlist related elements ... -->
        </div>

        
  
      </div>
      
      <add class="add" v-show="selectUsers && $store.state.currentPlaylist != ''"></add>

      
    

      <!-- Right Side: Song Search, Must-Have, and Do Not Play Containers -->
      <div class="song-containers">
        <!-- Search Box -->
        <div class="search-container">
          <input  class="search-box" v-model="searchQuery" type="text" placeholder="Search for a song..." v-on:keyup.enter="searchSong" >
          <button @click.prevent="searchSong" >{{ buttonText }}</button>
          </div>
        <div class="search-results-container">
        <search-results :results="searchResults" :searchPerformed="searchPerformed" v-if="isDropdownOpen" @add-must-have="addMustHaveSong" @add-do-not-play="addDoNotPlaySong" @add-to-playlist="addSongToSpotify"></search-results>
        </div>
        <!-- Must-Have Songs Container -->
        <div class="must-have-container song-container" @drop="dropMustHave" @dragover.prevent>
          <h2>Must-Have Songs</h2>
          <ul>
            <li v-for="song in $store.state.mustHaveSongs" :key="song.songId"  v-bind:id="'yes'+song.songId"> 
              <div id="picArrow"> 
                 <button v-show="!$store.state.playlistSongs.includes(song.songId) && $store.state.currentPlaylistOwner == $store.state.user.id"  @click="addSongToSpotify(song.songId)" v-bind:id="'left'+song.songId"><i class="fa-solid fa-arrow-left fa-2xl"></i></button>
                <img v-bind:src="song.imageUrl" alt="">
              </div>
              
              <span>{{ song.title}} - {{song.artist}}</span>
              
               
                
               <button @click="removeSong(song.songId, `yes`+song.songId)"><i class="fa-regular fa-trash-can fa-2xl"></i></button>
               
              
              </li>
          </ul>
        </div>

        <!-- Do Not Play Songs Container -->
        <div class="do-not-play-container song-container" @drop="dropDoNotPlay" @dragover.prevent>
          <h2>Do Not Play Songs</h2>
          <ul>
            <li v-for="song in $store.state.doNotPlaySongs" :key="song.songId" v-bind:id="'no'+song.songId">
              <img v-bind:src="song.imageUrl" alt="">
              <span>{{ song.title}} - {{song.artist}}</span>
              
              <button @click="removeSong(song.songId, `no`+song.songId)" ><i class="fa-regular fa-trash-can fa-2xl"></i></button>
              
              </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  </div>
</template>

<script>
import SpotifyService from '../services/SpotifyService';
import DatabaseService from '../services/DatabaseService';
import Add from "../components/Add.vue";
import PlayList from '../components/PlayList.vue';
import SearchResults from '../components/SearchResults.vue';







export default {
  
  components: { 
    PlayList,
    SearchResults,
    Add,
    
    
    
     
  },

  name: "home",
  
  data(){
  return {
  e: '\ue00e',
  t: '\ue01d',
  playlist: {"public": true},
  DatabasePlaylists: {},
  
  spotifySearchResults : {},
  spotifyQuery : "",
  searchQuery: '', 
  mustHaveSongs: [], 
  doNotPlaySongs: [], 
  searchResults: [], 
  searchPerformed: false,
  buttonText: 'Search',
  isDropdownOpen: false,
  selectUsers: false,
  
}
  },
  
  
  methods : {
     removeSong(songId, id){
     DatabaseService.removeSong(songId)
      document.getElementById(id).style.display = "none";
      
      
      
    },
    closeAdd(){
      this.selectUsers = false;
    },
    changeSelectUsers(){
      this.selectUsers = !this.selectUsers
    }
    ,
    createPlaylist(){
      
     
      SpotifyService.createPlaylist(localStorage.getItem('bearer'), this.playlist).then(
        (response)=> {
          if (response.status === 201){console.log(response.data);
          let dataPlay = {"playlistId": response.data.id, "playlistName" : response.data.name, "dj_id": parseInt(this.$store.state.user.id)  };
          DatabaseService.createPlaylist(dataPlay);
          window.location = "http://localhost:8080/";
          }
          

        }
      );
    },
    log(){
      console.log("changed")
    },
    refresh(){
      this.$refs.testref.updateSelectionChoice()
    },
  
    searchSong() {
      SpotifyService.searchSong(localStorage.getItem('spotifyBearer'), this.searchQuery).then((response) => {
      this.searchResults = response.data.tracks.items;
      this.isDropdownOpen = !this.isDropdownOpen;
      this.searchPerformed = this.searchPerformed ? false : true;
      this.buttonText = this.searchPerformed ? 'Reset' : 'Search';
      console.log("Before toggle: searchPerformed =", this.searchPerformed);
      console.log("After toggle: searchPerformed =", this.searchPerformed);
      console.log("Button text:", this.buttonText);

    });

  },
 toggleButtonText() {
    this.buttonText = this.searchPerformed ? 'Search' : 'Reset';
  },

  addMustHaveSong(track) {
      let song = { "playlistId": this.$store.state.currentPlaylist,
    "songId": track.id,
    "title": track.name,
    "imageUrl": track.album.images[0].url,
    "artist": track.artists[0].name,
    "mustPlay":true
    }
  DatabaseService.addSongToPlaylist(song);
    // Add the track to the mustHaveSongs array
    this.$store.state.mustHaveSongs.push(song);
  },

addSongToSpotify(id){

  let uri = `spotify:track:${id}`
  SpotifyService.addSongToPlaylist(localStorage.getItem("bearer"),this.$store.state.currentPlaylist, uri ).then(this.$refs.testref.updateSelectionChoice(),this.$refs.testref.updateSelectionChoice() )
  document.getElementById(`left${id}`).style.display = "none";
  // this.refresh()
  
},

  addDoNotPlaySong(track) {
    
    let song = { "playlistId": this.$store.state.currentPlaylist,
    "songId": track.id,
    "title": track.name,
    "imageUrl": track.album.images[0].url,
    "artist": track.artists[0].name,
    "doNotPlay":true

    }
    DatabaseService.addSongToPlaylist(song);
    // Add the track to the doNotPlay array
    this.$store.state.doNotPlaySongs.push(song);
  
  },
  dropMustHave(event) {
    // Get the track data from the dataTransfer object
    const track = JSON.parse(event.dataTransfer.getData('text/plain'));
    let song = { "playlistId": this.$store.state.currentPlaylist,
    "songId": track.id,
    "title": track.name,
    "imageUrl": track.album.images[0].url,
    "artist": track.artists[0].name,
    "mustPlay":true
    }
  DatabaseService.addSongToPlaylist(song);
    // Add the track to the mustHaveSongs array
    this.$store.state.mustHaveSongs.push(song);
  },
  dropDoNotPlay(event) {
    // Get the track data from the dataTransfer object
    const track = JSON.parse(event.dataTransfer.getData('text/plain'));
    let song = { "playlistId": this.$store.state.currentPlaylist,
    "songId": track.id,
    "title": track.name,
    "imageUrl": track.album.images[0].url,
    "artist": track.artists[0].name,
    "doNotPlay":true

    }
    DatabaseService.addSongToPlaylist(song);
    // Add the track to the doNotPlay array
    this.$store.state.doNotPlaySongs.push(song);
  },
  },
  
  created(){
      SpotifyService.getBearer()
      DatabaseService.getPlaylists(this.$store.state.user.id).then((response)=>{
        this.DatabasePlaylists = response.data;
        
         
      })
      if(localStorage.getItem('bearer')){
        this.$store.state.hasSpotify = true;
      }
      
    },
    comments:{
      PlayList,
    }
};
</script>
<style>

</style>
<style scoped>
body {
  display: block;
  background-image: url("../assets/Background1.jpg");
  background-size: 100vw 100vh;
}


.form-input-group {
  margin-bottom: 1rem;
  padding-right: 3%;
}

.label {
  width: 120px;
  display: inline-block;
  font-family: Georgia, 'Times New Roman', Times, serif;
  font-style: italic;
}
#picArrow{
  display: flex;
  align-items: center;
}
#normieSelect{
  display: flex;
  justify-content: center;
  
}
#normieSelect h2{
  font-size: 50px;
  letter-spacing: 6px;
  margin: 5px;
}

h1{
    letter-spacing: 1px;
    font-family: 'JosephSophia';
    font-size: 90px;
    color: #d1985e;
    text-shadow: 4px 4px rgb(10, 9, 9) ;
}
.box-outer {
    background-color: rgb(254, 250, 224, .75);
    display: flex;
    justify-content: center;
    position: relative;
    align-self: center;
    border-radius: 12px;
    margin-top: 10px;

} 
.box-outer h1{
  margin-top: 50px;
  margin-bottom: 50px;
}
.add{
  display: grid;
  grid-template-rows: 50px 1fr;
  grid-template-columns: 1fr;
    
   
    width: 600px;
    background-color:  #f0f0f0;
    border-radius: 12px;
    border: inset black;
    margin-left: 100px;
    max-height: 855px;
    
}

button {
  display: inline-block;
  width: 105px;
  height: 22px;
  margin-right: 0.5rem;
  font-family: Georgia, 'Times New Roman', Times, serif;
  font-style: italic;
}

.sgnbutton {
  display: flex;
  justify-content: center;
  align-content: center;
  margin: 3px;
}
.addUser{
  border: 0;
   width: 45px;
}
.addUser svg {
  margin-top: -5px;
}
.wholeDisplay::-webkit-scrollbar{
  display: none;
}
.userbox {
  background-color: #FEFAE0;
  height: 40px;
  width: 280px;
  border-radius: 12px;
  font-family: Georgia, 'Times New Roman', Times, serif;
  font-style: italic;
}

.search-results-container{
  display: flex;
  justify-content: center;
  width: 100%;
}



.home {
  display: flex;
  flex-direction: column;
  overflow: hidden;
  padding-top: 3%;
}

.main-content {
  display: flex;

  
  
}
form{
  display: flex;
  justify-content: center;
  }

.playlist-section {

  background-color: #f0f0f0 ;
  padding-top: 20px;
  padding-right: -100px;
  margin-left: 50px;
  border-radius: 10px; /* Rounded corners */
  width: 50%;
  justify-items: center;
  border-radius: 12px;
  border: inset black;
  max-height: 835px;
}
.playlist-container{
  display: flex;;
  padding: 20px;
  padding-top: 0px;
  justify-content: center;
  max-height: 93%;
  border-radius: 12px;
  flex-wrap: wrap;
}

.song-containers {
  
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: 'JosephSophia';
  width: 50%;
  
  
}

.search-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 20px;
  width: 50%;
}

.search-box {
  width: 300px;
  padding: 10px;
  border-radius: 5px;
  font-size: 16px;
  font-family: Georgia, 'Times New Roman', Times, serif;
  font-style: italic;
}


.song-container {
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 10px; /* Rounded corners */
  margin: 20px;
  width: 50%;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Add shadow for a card effect */
  

}

.playlist-songs-container{
  display: flex;
  justify-content: center;
}
.must-have-container, .do-not-play-container{
  text-align: center;
  display: grid;
  font-family: Georgia, 'Times New Roman', Times, serif;
  font-style: italic;
  border: black inset;
  border-radius: 12px;
  max-height: 300px;
  grid-template-rows: 50px 1fr;
  grid-template-columns: 1fr;
 
}
.must-have-container h2, .do-not-play-container h2{
  font-style: normal;
  text-decoration: underline;
}
#createForm{
  
  padding-bottom: 20px;
}
.must-have-container img,
.do-not-play-container img {
  padding-right: 5px;
  width: 60px;
  height: 60px; /* Remove padding */
}
.must-have-container ul,
.do-not-play-container ul {
  list-style-type: none; /* Remove bullets */
  padding: 0; /* Remove padding */
  overflow-y: auto;
}
.must-have-container ul::-webkit-scrollbar,
.do-not-play-container ul::-webkit-scrollbar {
  display: none;
}

.must-have-container li,
.do-not-play-container li {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 5px;
  border-bottom: 1px solid #ccc; /* Add a line between songs */
}
.must-have-container li button,
.do-not-play-container li button{
  height: 40px;
  width: 40px;
  border: hidden;
}

.must-have-container li:last-child,
.do-not-play-container li:last-child {
  border-bottom: none; /* Remove the line for the last song */
}

h2 {
  font-family: 'JosephSophia';
  font-size:xx-large;
  letter-spacing: 4px;
  color: #333;

}
</style>
