<template>
    <div
      class="alert"
    :class="{ fading: isFading }"
    >
      <span>{{ message }}</span>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue';
  
  // Propriedade para passar a mensagem do alerta
  defineProps<{
    message: string;  // Mensagem do alerta
    
  }>()

  onMounted(()=>{
    setTimeout(() => {
    isFading.value = true;
  }, 2000);
  })
  
  const isFading = ref(false);
  </script>
  
  <style scoped>
  .alert {
    position: fixed;
    bottom: 20px;
    left: 20px;
    background-color: #000;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    font-size: 1em;
    box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.5);
    transition: opacity 1s ease-out; /* Transição suave para o desaparecimento */
    opacity: 1;
    z-index: 1000;
  }
  
  .fading {
    opacity: 0;  /* Torna o alerta transparente ao longo do tempo */
  }
  
  .alert span {
    display: block;
    transition: opacity 1s ease-out;
  }
  </style>