<template>
  <div  class="search-results">
    <ul v-if="results.length > 0">
      <li v-for="track in results" :key="track.id" draggable="true" @dragstart="dragStart($event, track)" @dragend="dragEnd">
        <div class="card-content">
          <span>{{ track.name }} - {{ track.artists[0].name }}</span>
          <img :src="track.album.images[0].url" alt="Album Cover" />
        </div>
        <div class="card-buttons">
          <button class="must-have-button" @click="$emit('add-must-have', track)"><img src="@/assets/img/heart-svgrepo-com.svg" alt="Add to Must Have"></button>
          <button class="do-not-play-button" @click="$emit('add-do-not-play',track)"><img src="@/assets/img/no-svgrepo-com (1).svg" alt="Add to Do Not Play"></button>
          <button v-show="$store.state.currentPlaylistOwner == $store.state.user.id" class="add-to-playlist-button" @click="$emit('add-to-playlist', track.id)"><img src="@/assets/img/add-to-playlist-svgrepo-com.svg" alt="Add to Playlist"></button>
        </div>
        <!-- Add more details as needed -->
      </li>
    </ul>
    <div v-else>
      <p v-if="searchPerformed && results.length === 0">No results found. Please try a different search query.</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    results: {
      type: Array,
      required: true,
    },
    searchPerformed: {
    type: Boolean,
    required: true,
  },
  },
  methods: {
    dragStart(event, track) {
      event.target.classList.add('dragging');
      // Store the track data in the dataTransfer object
      event.dataTransfer.setData('text/plain', JSON.stringify(track));
    },
    dragEnd(event) {
      event.target.classList.remove('dragging');
  },
  },
};
</script>

<style scoped>
ul{
 
  flex-grow: 1;
 padding-left: 0px;
  
}

.search-results {
  list-style: none;
  padding: 0;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  overflow-y: auto;
  max-height: 400px;
  scrollbar-width: none;
  -ms-overflow-style: none;
  width: 50%;
  font-family: Georgia, 'Times New Roman', Times, serif;
  font-style: italic;
}

.search-results p {
  font-size: 18px;
  color: #666;
  text-align: center;
  margin: 20px;
}

.search-results::-webkit-scrollbar {
  display: none;
}

.search-results li {
  display: flex;
  flex-direction: row;
  margin : 20px;
  margin-left: 25px;
  border: 1px solid black;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  width: 80%;
  background-color: #f9f9f9;
  padding-left: 10%;
}

.card-content {
  display: flex;
  align-items: center;
  width: 70%; /* Adjust as needed */
  padding: 10px;
}

.card-buttons {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 30%; /* Adjust as needed */
  padding: 5px;
}

button {
  border: none;
  background: transparent;
  cursor: pointer;
  padding: 5px;
  margin: 5px 0;
}

.search-results img {
  width: 60px;
  height: 60px;
  padding-left: 10px;
}

.search-results span {
  font-size: 24px;
  color: #333;
}

.search-results .must-have-button img,
.search-results .do-not-play-button img,
.search-results .add-to-playlist-button img {
  width: 25px;
  height: 25px;
}

.search-results li:active {
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
  transform: scale(1.05) rotate(5deg);
}

.search-results li.dragging {
  opacity: 0.7;
  transform: scale(1.1) rotate(5deg);
}

.search-results li.dragging::after {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.1);
  z-index: -1;
}
</style>