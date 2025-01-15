<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import Nav from './components/Nav.vue';
</script>

<template>
  <Nav />

  <RouterView />
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Asap:ital,wght@0,100..900;1,100..900&display=swap');

*{
  font-family: "Asap", serif;
  font-optical-sizing: auto;
  font-weight: 300;
  font-style: normal;
  font-variation-settings:
    "wdth" 100;
}
</style>
