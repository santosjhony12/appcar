<template>
    <Title :text="'Meus veículos cadastrados'" />
    <CadastroCarro />
    <Carrossel :carros="carros" />
  </template>
  
  <script setup lang="ts">
  import { onMounted, ref } from "vue";
  import Carrossel from "@/components/Carrossel.vue";
  import CarroService from "@/service/carro";
  import CadastroCarro from "@/components/CadastroCarro.vue";
  import Title from "@/components/Title.vue";
  onMounted(()=>{
    getCarros();
  })

  //data:image/png;base64,
  const carros = ref<{
    id: number,
      modelo: string,
      montadora: string, 
      ano: string, 
      cor: string,
      valor: number,
      imagem: string
  }[]>([]);

  const getCarros = async () =>{
    try{
        const response = await CarroService.getCarrosByUsername();
        carros.value = response;
    }catch(error : any){
        console.log(error)

    }
  }
  </script>
  
  <style scoped>
  
  </style>
  