<template>
    <div>
        <h1>Guests</h1>
        
    </div>
</template>

<script>
import PlayList from '@/components/PlayList.vue';



export default {
    name: 'guest-home',
    components: PlayList,
}
</script>
<style>
body{
  display: block;
background-image: url("../assets/Background1.jpg");
  background-size: 100vw 100vh;  



}
</style>
<style scoped>
.form-input-group {
  margin-bottom: 1rem;
  padding-right: 3%;
  font-family: Georgia, 'Times New Roman', Times, serif;
   font-style: italic;
}
label {
  width:75px;
  display: inline-block;
  margin-right: 0.5rem;
  
}
h1{
  display: flex;
  justify-content: center;
  letter-spacing: 3px;
  font-family: 'JosephSophia';
  font-size: 90px;
  color: #D4a373;
  text-shadow: 4px 4px black ;
  
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
.sgnbutton{
  display: flex;
  justify-content: center;
  align-content: center;
  margin: 3px;
  
  
}
.userbox{
  background-color: #FEFAE0;
  height: 40px;
  width: 280px;
  border-radius: 12px;
  
}
input{
  height: 15px;
}
#login{
  display: flex;
  justify-content: center;
  align-items: center;
  size: 100%;
  padding-top: 13%;
  
  }

</style>