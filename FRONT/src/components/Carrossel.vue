<template>
<div class="carousel-container" v-if="carros.length != 0">
    <button class="nav prev" @click="prevSlide">‹</button>
    <div class="carousel">
      <div
        class="carousel-track"
        :style="{ transform: `translateX(-${currentIndex * 100}%)` }"
      >
        <div class="carousel-item" v-for="(carro, index) in carros" :key="index">
          <CardCarro :ano="carro.ano" :cor="carro.cor" :id="carro.id" :modelo="carro.modelo" :montadora="carro.montadora" :valor="carro.valor"
            :imagem="'data:image/png;base64,'+carro.imagem"
          
          />
        </div>
      </div>
    </div>
    <button class="nav next" @click="nextSlide">›</button>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import CardCarro from "@/components/CardCarro.vue";
import Title from "@/components/Title.vue";

const props = defineProps<{
  carros: {
      id: number,
      modelo: string,
      montadora: string, 
      ano: string | number, 
      cor: string,
      valor: number,
      imagem: string
  }[]
}>();




const currentIndex = ref(0);

const prevSlide = () => {
  if (currentIndex.value > 0) {
    currentIndex.value--;
  }
};

const nextSlide = () => {
  if (currentIndex.value < props.carros.length - 1) {
    currentIndex.value++;
  }
};
</script>

<style scoped>
.carousel-container {
  position: relative;
  width: 80%;
  margin: 0 auto;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center; /* Alinha o carrossel verticalmente */
}



.carousel {
  width: 110%;
  overflow: hidden;
  margin: 0 0 2em 0;
}

.carousel-track {
  display: flex;
  transition: transform 0.5s ease-in-out;
}

.carousel-item {
  min-width: 100%;
  flex-shrink: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}
.form-description {
 text-align: center;
 font-size: 1em;
 color: #666;
 margin-bottom: 20px;
}

.nav {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  color: black;
  border: solid 1px rgb(95, 95, 95);
  border-radius: 50%;
  width: 30px; /* Ajuste o tamanho */
  height: 30px; /* Ajuste o tamanho */
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 18px; /* Ajuste o tamanho da fonte */
  cursor: pointer;
  z-index: 100;
  transition: background-color 0.3s;
}

.nav:hover {
  background-color: rgba(0, 0, 0, 0.9);
  color: white
}

.nav.prev {
  left: 30%; /* Ajuste a distância das setas */
}

.nav.next {
  right: 30%; /* Ajuste a distância das setas */
}
@media (max-width: 1000px){
  .nav.prev {
  left: 10%; /* Ajuste a distância das setas */
}

.nav.next {
  right: 10%; /* Ajuste a distância das setas */
}
}
</style>
