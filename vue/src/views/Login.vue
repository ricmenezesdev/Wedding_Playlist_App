<template>
<div>
 <div class="box-outer"> <h1>Please Sign I{{n}} </h1> </div>
  <div id="login">

    
    
      <form @submit.prevent="login" v-show="!isHash">
        
     
     
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <input class="userbox" type="text" placeholder="username"  v-model="user.username" required autofocus />
      </div>
      
      <div class="form-input-group">

        <input class="userbox" v-if="showPassword" type="text" placeholder="password" id="password" v-model="user.password" required />
        <input class="userbox" v-else type="password" placeholder="password" id="password" v-model="user.password" required />
        <div class="eye" @click="togglePasswordVisibility" >
          <i class="fa-solid fa-eye" id="eye"></i>
          <!-- <button class="eye" type="password" id="password"> <i class="fa-solid fa-eye" @click="togglePasswordVisibility"></i></button> -->
        </div>

      </div>

      <div class="sgnbutton">
      <button class="sgnbutton" type="submit">Sign in</button>
      </div>
      <p class="sgnbutton" id="spotify">
      <router-link :to="{ name: 'register' }">Need an account? Sign up.</router-link></p>
      <div id="spotify" class="sgnbutton" @click.prevent="spotify" ><i class="fa-brands fa-spotify fa-2xl"></i></div>
   

    </form>
    
</div>
</div>
</template>

<script>

import authService from "../services/AuthService";

import spotifyService from "../services/SpotifyService"
export default {
  name: "login",
  components: {},
  data() {
    return  {
      n: '\ue017',
      user: {
        username:"" ,
        password: ""
      },
      invalidCredentials: false,
      client_id : '6d7a9526161e48ba94c6d297e82b6075',
      redirect_uri : 'http://localhost:8080/login',

     scope : 'playlist-modify-public user-read-email',

    url : 'https://accounts.spotify.com/authorize',
    isHash : false,
    showPassword:false
    };
  },
  methods: {

    togglePasswordVisibility(){
      this.showPassword = !this.showPassword;
     let eye = document.getElementById("eye")
     console.log(eye.classList)
     if (eye.classList.contains("fa-eye-slash")){
       eye.classList.remove("fa-eye-slash")
       eye.classList.add("fa-eye")
     } else {
       eye.classList.remove("fa-eye")
       eye.classList.add("fa-eye-slash")
       
     }
      
     
    },
  
    spotify(){
    let state = this.generateRandomString(16);
    
    this.url += '?response_type=token';
    this.url += '&client_id=' + encodeURIComponent(this.client_id);
    this.url += '&scope=' + encodeURIComponent(this.scope);
    this.url += '&redirect_uri=' + encodeURIComponent(this.redirect_uri);
    this.url += '&state=' + encodeURIComponent(state);
    window.location.href = this.url;
    
    }
    ,
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
    ,
    generateRandomString(length)  {
      let result = '';
      const characters ='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
      const charactersLength = characters.length;
      for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
       }
      return result;
    }

  }
  ,
  created(){
   if (window.location.hash){
     this.isHash = true;
    let hash  = window.location.hash;
    let after = hash.substring(1);
      let urlParams = after.split("&");
      let splitParams = urlParams.reduce((accum, val)=> {
      let [key,value] = val.split("=");
      accum[key] = value;
      
      return accum;
      },{});

      let token = splitParams.access_token;
      localStorage.setItem("bearer" ,token);
      this.$store.state.hasSpotify= true,
      
      spotifyService.getSpotifyUser(token).then((spotResponse) => {
        let dat = spotResponse.data;
        localStorage.setItem( "spotifyId", dat.id);
        localStorage.setItem("tempPassword", dat.email + dat.id );
        localStorage.setItem("tempUserName",dat.display_name); 
        
        this.user.username = localStorage.getItem("tempUserName");
        this.user.password = localStorage.getItem("tempPassword");

        let registerUser = {
        "username": localStorage.getItem("tempUserName") ,
        "password": localStorage.getItem("tempPassword"),
        "confirmPassword": localStorage.getItem("tempPassword"),
        "role": "user",
         }
         
          
        authService.register(registerUser).then((responser) =>{
          if (responser.status === 201 ){this.user.username = localStorage.getItem("tempUserName");
        this.user.password = localStorage.getItem("tempPassword"); this.login()}
          }
          ).catch((error) =>{
            if(error.response.status === 400){
         this.login()
         }
          })
       }) 
  }
  }
};
</script>

<style>
  body{
    display: flex;
    background-image: url("../assets/Background1.jpg");
    background-size: 100vw 100vh;  
    justify-content: center;
    align-self: center;
  }
  </style>

  <style scoped>
  #login{
    display: flex;
    justify-content: center;
    padding-top: 3% ;
  }
  .form-input-group {
    display: flex;
    margin-bottom: 1rem;
    font-family: Georgia, 'Times New Roman', Times, serif;
    font-style: italic;
    justify-content: center;
    align-items: center;
  }
  label {
    width:75px;
    display: inline-block;
    margin-right: 0.5rem;
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
   button{
    display: flex;
    justify-content: center;
    border-radius: 6px;
    line-height: 22px;
    background-color: #fcf7d9;
}
.sgnbutton{
    display: flex;
    justify-content: center;
    align-content: center;
    margin: 3px;
    font-family: Georgia, 'Times New Roman', Times, serif;
    font-style: italic;
}
[role=alert] {
  text-align: center;
  border-radius: 6px;
  background-color: rgb(254, 250, 224, .75); ;
}             
.userbox{
    background-color: #FEFAE0;
    height: 40px;
    width: 280px;
    border-radius: 12px;
    font-family: Georgia, 'Times New Roman', Times, serif;
    font-style: italic;
}
.eye { 
    display: inline;
    margin-left: -7%;
    border-radius: 6px;
    line-height: 22px;
}
input{
   height: 15px;
}
#spotify{
    padding-top: 15px;  
    color: rgb(1, 83, 1); 
  }

</style>