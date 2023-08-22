<template>
  <div id="app">
    <div id="nav">   
      <router-link v-bind:to="{ name: 'home' }"><i   class="fa-solid fa-house fa-2xl"   ></i></router-link>
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''"><i class="fa-solid fa-person-walking-dashed-line-arrow-right fa-2xl" ></i></router-link>
      

    </div>
    <router-view />
  </div>

</template>


   <style>
   @font-face {
    font-family: 'JosephSophia';
    src: url('./assets/font/josephsophia.otf') format('opentype');
    font-weight: normal;
    font-style: normal;
}
#nav{
  display: flex;
  justify-content: space-between;
  
}
 .fa-house{
   color: #FEFAE0;
 }
 .fa-person-walking-dashed-line-arrow-right{
   display: flex;
   color: #FEFAE0;
   justify-content: end;
      
 }
  
   </style>