<template>
<div>
   <div class="box-outer"><h1>Creat{{e}}accoun{{t}}</h1></div>
  <div id="register" class="text-center">
    <form @submit.prevent="register">
      
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      
      <div class="form-input-group">
        <input class="userbox"  type="text" placeholder="username" id="username" v-model="user.username" required autofocus />
      </div>

      <div class="form-input-group">

       <input class="userbox" v-if="showPassword" type="text" placeholder="password" id="password" v-model="user.password" required />
        <input class="userbox" v-else type="password" placeholder="password" id="password" v-model="user.password" required />
        <div class="eye" @click="togglePasswordVisibility" >
          <i class="fa-solid fa-eye" id="eye"></i>
        </div>

      </div>

      <div class="form-input-group">

       <input class="userbox" v-if="showConfirmPassword" type="text" placeholder="Confirm Password" id="confirmPassword" v-model="user.confirmPassword" required />
        <input class="userbox" v-else type="password" placeholder="Confirm Password" id="confirmPassword" v-model="user.confirmPassword" required />
        <div class="eye" @click="toggleConfirmPasswordVisibility" >
          <i class="fa-solid fa-eye" id="eye2"></i>
        </div>

      </div>

      <div class="sgnbutton">
      <button type="submit">Create Account</button>
      </div>
      <p class="sgnbutton"><router-link :to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
      
    </form>
  </div>
</div>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      e: '\ue00e',
      t: '\ue01d',
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
      showPassword: false,
      showConfirmPassword: false,
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
     toggleConfirmPasswordVisibility(){
      this.showConfirmPassword = !this.showConfirmPassword;
     let eye = document.getElementById("eye2")
     console.log(eye.classList)
     if (eye.classList.contains("fa-eye-slash")){
       eye.classList.remove("fa-eye-slash")
       eye.classList.add("fa-eye")
     } else {
       eye.classList.remove("fa-eye")
       eye.classList.add("fa-eye-slash")
       
     }
    },
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
  body{
    display: flex;
  background-image: url("../assets/Background1.jpg");
    background-size: 100vw 100vh;  
  justify-content: center;
    align-self: center;
  }
  form{
    padding-top: 5%;
  }
  .text-center{
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
  }
  
   .form-input-group {
    display: flex;
    margin-bottom: 1rem;
    font-family: Georgia, 'Times New Roman', Times, serif;
    font-style: italic;
    justify-content: center;
    align-items: center;
    margin-bottom: 1rem;
    padding-right: 3%;
  }
  
.eye { 
    display: inline;
    margin-left: -30px;
    border-radius: 6px;
    line-height: 22px;
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
#username{
  margin-left: 12px;
}
[role=alert] {
  text-align: center;
  border-radius: 6px;
  background-color: rgb(254, 250, 224, .75); ;
}  
button{
  display: flex;
  justify-content: center;
  border-radius: 6px;
  line-height: 22px;
  background-color: #fcf7d9;
  font-family: Georgia, 'Times New Roman', Times, serif;
  font-style: italic;
}
.userbox{
  background-color: #FEFAE0;
  height: 40px;
  width: 280px;
  border-radius: 12px;
  font-family: Georgia, 'Times New Roman', Times, serif;
  font-style: italic;  
}

.sgnbutton{
  display: flex;
  justify-content: center;
  align-content: center;
  margin: 3px;
  font-family: Georgia, 'Times New Roman', Times, serif;
   font-style: italic;
  
  
}             
 


</style>

