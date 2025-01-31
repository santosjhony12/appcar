<template>
    <button type="submit" class="submit-button">

        <img src="../assets/loading-cinza.gif" alt="" v-if="isLoading" id="loading-img">
        <p v-else>{{ text }}</p>
    </button>
</template>
<script lang="ts" setup>

defineProps<{
    text: string,
    isLoading: boolean
}>();
</script>
<style scoped>
 button.submit-button {
   padding: 12px;
   background: black;
   color: white;
   border: none;
   border-radius: 5px;
   font-size: 1.1em;
   cursor: pointer;
   transition: background-color 0.3s ease;
   margin-top: 20px;
   transition: background-color 0.3s, transform 0.3s; /* Suaviza as transições */
 }
 
 button.submit-button:hover {
  transform: scale(1.1); /* Aumenta ligeiramente o botão */
  background-color: gray;
 }
 
 button.submit-button:active {
  transform: scale(0.95); /* Reduz ligeiramente ao clicar */
 }
 #loading-img{
    width: 3%
 }
 
 </style>