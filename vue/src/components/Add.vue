<template>
    <div>
        <div class="title">
            <h1>Select user{{s}}</h1>
        </div>
        
        <div class="list">
        <ul>
            <li v-for="user in otherUsers" v-bind:key="user.id" v-bind:id="'add'+user.id">
                <div>
                    {{user.username}}
                </div>
                
                <div >
                    <button @click="add(user)"><i class="fa-solid fa-user-plus fa-2xl"></i></button>
                </div>
                
                
                
            </li>
        </ul>
        </div>
        
    </div>
</template>

<script>
import DatabaseService from '../services/DatabaseService'

export default {
    name: "Add",
    data(){
        return{
            otherUsers:[],
            s:'\ue01c'
        }
    }
    ,
    methods: {
        add(user){
           DatabaseService.addUserToPlaylist(user)
           document.getElementById(`add${user.id}`).style.display = "none";
        },
       
    },
    created(){
        DatabaseService.getOtherUsers(this.$store.state.user.id).then((response)=>{
            this.otherUsers = response.data
        })
    }
};
</script>

<style scoped>
button{
    border: none;
}
.title{
    display: flex;
    justify-content: center;
    font-family: 'JosephSophia';
    letter-spacing: 3px;
}
.list{

    display: flex;
    justify-content: left;
    font-family: Georgia, 'Times New Roman', Times, serif;
  font-style: italic;
  font-size: 20px;
  height: 800px;
  overflow-y: auto;

  
}
.list::-webkit-scrollbar{
    display: none;
}
ul{
    width: 85%;
}
li{
    
    display: flex;
    justify-content: space-between;
    padding: 10px;
    border-bottom: 1px solid #ccc;
    
}

</style>
