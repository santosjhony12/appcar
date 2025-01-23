<template>
    <div class="carrossel-container">
      <!-- Botões de navegação -->
  
      <!-- Área do carrossel -->
      <div class="carrossel">
        <div
          class="carrossel-slides"
          :style="{
            transform: `translateX(-${currentSlide * 100}%)`,
          }"
        >
          <div class="carrossel-slide" v-for="(carro, index) in carros" :key="index">
            <CarroInformation :ano="carro.ano" :cor="carro.ano" :id="carro.id" :imagem="'data:image/png;base64,'+carro.imagem" :modelo="carro.modelo" :montadora="carro.modelo" :valor="carro.valor" />
          </div>
        </div>
      </div>
  
      <!-- Botões de navegação -->
      <button @click="nextSlide" class="nav-button next"><font-awesome-icon :icon="['fas', 'arrow-right']" /></button>
    </div>
  </template>
  
  <script lang="ts" setup>
interface Carrro {
    id: number,
    modelo: string,
    montadora: string, 
    ano: number, 
    cor: string,
    valor: number,
    imagem: string
}
const props = defineProps<{
    carros: Carro[]
}>();

  import { ref } from 'vue';
  import CarroInformation from '../components/CarroInformation.vue';
  
  const currentSlide = ref(0); // Indica o slide atual
  
  // Função para ir para o slide anterior
  const prevSlide = () => {
    // Se o índice for maior que 0, decrementa. Caso contrário, vai para o último slide.
    currentSlide.value = (currentSlide.value > 0) ? currentSlide.value - 1 : props.carros.length - 1;
  };
  
  // Função para ir para o próximo slide
  const nextSlide = () => {
    // Se o índice for menor que o número total de slides, incrementa. Caso contrário, volta para o primeiro slide.
    currentSlide.value = (currentSlide.value < props.carros.length - 1) ? currentSlide.value + 1 : 0;
  };
  </script>
  
  
  <style scoped>
  /* Estilos para o carrossel */
  .carrossel-container {
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
  }
  
  .carrossel {
    width: 100%;
    max-width: 90vw;
    height: auto;
    overflow: hidden;
    min-height: 55vh;
    border-radius: 8px;
  }
  
  .carrossel-slides {
    display: flex;
    transition: transform 0.5s ease-in-out;
  }
  
  .carrossel-slide {
    flex-shrink: 0;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  
  /* Estilos para os botões */
  .nav-button {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    color: orange;
    border: none;
    padding: 10px;
    cursor: pointer;
    background-color: white;
    font-size: 20px;
  }
  
  .prev {
    left: 10px;
  }
  
  .next {
    right: 10px;
  }
  </style>
  