<script setup lang="ts">
import { RouterLink, RouterView, useRoute } from 'vue-router'
import Nav from './components/Nav.vue';
const route = useRoute();

</script>

<template>
  <Nav class="nav" v-if="route.path != '/login'" />

  <div :class="{ conteudo: route.path != '/login' }">
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
  padding: 60px 0 0 80px;
  z-index: 100;
}
@media (max-width: 1000px){
  .conteudo{
    padding: 30px 0 0 80px;
  }
}


</style>
