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
   background: #000000;
   color: white;
   border: none;
   border-radius: 5px;
   font-size: 1.1em;
   cursor: pointer;
   transition: background-color 0.3s ease;
   margin-top: 20px;
 }
 
 button.submit-button:hover {
   background-color: #0056b3;
 }
 
 button.submit-button:active {
   background-color: #004080;
 }
 #loading-img{
    width: 3%
 }
 
 </style>