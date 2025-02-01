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
            <CarroInformation :ano="carro.ano" :cor="carro.cor" :id="carro.id" :imagem="'data:image/png;base64,'+carro.imagem" :modelo="carro.modelo" :montadora="carro.modelo" :valorAluguel="carro.valorAluguel" 
            @alugar="alugar"/>
          </div>
        </div>
      </div>
  
      <!-- Botões de navegação -->
      <button @click="nextSlide" class="nav-button next">Próximo</button>
    </div>
  </template>
  
  <script lang="ts" setup>
interface Carro {
    id: number,
    modelo: string,
    montadora: string, 
    ano: number, 
    cor: string,
    valorAluguel: number,
    imagem: string
}
const emit = defineEmits(['alugar']);

const alugar = (id : number, imagem: string, valorAluguel: number) => {
  emit('alugar', id, imagem, valorAluguel);
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
    min-height: 100vh;
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
    background-color: #007bff;
    border: none;
    padding: 5px;
    cursor: pointer;
    font-size: 15px;
    border-radius: 30px;
    width: 10%;
  }
  
  .prev {
    left: 10px;
  }
  
  .next {
    top: 55vh;
    color: white
  }
  @media (max-width: 1000px){
    .next{
      top: 90vh;
      width: 20%;
    }
  }
  .next:hover{
    background-color: gray;
  }
  </style>
  