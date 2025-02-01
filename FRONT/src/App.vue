<script setup lang="ts">
import { RouterLink, RouterView, useRoute } from 'vue-router'
import Nav from './components/Nav.vue';
const route = useRoute();

</script>

<template>
  <Nav class="nav" v-if="route.path != '/' && route.path != '/redefinir-senha'" />

  <div :class="{ conteudo: route.path != '/' && route.path != '/redefinir-senha' }">
    <RouterView />
  </div>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Asap:ital,wght@0,100..900;1,100..900&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Asap:ital,wght@0,100..900;1,100..900&family=DM+Sans:ital,opsz,wght@0,9..40,100..1000;1,9..40,100..1000&display=swap');

* {
  font-family: "DM Sans", serif;
  font-optical-sizing: auto;
  font-style: normal;
  font-weight: 300;
  font-style: normal;
  font-variation-settings:
    "wdth" 100;
}
body{
  max-width: 100vw;
  overflow-x: hidden;
}
.conteudo {
  padding: 70px 0 100px  80px;
  
}
@media (max-width: 1000px){
  .conteudo {
  padding: 60px 0 100px 0;
  z-index: 100;
}
}


.container-titulo-principal{
  padding: 20px;
  border: 1px solid #e4e4e4;
  border-radius: 24px;
  margin: 0 1em 0 1em;
  box-shadow: -1px 2px 8px 3px #e4e4e4;
  background-color: white;

}

.form-description {
   font-size: 17px;
   color: #1a2f4d;
   margin-bottom: 20px;
 }

 .cabecalho{
    background-color: #f5f5f5;
    border: 1px solid #e4e4e4;
}
</style>
