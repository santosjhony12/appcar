<template>
    <Title :text="'Meus veículos cadastrados'" />
    <p class="form-description">
      Confira os veículos cadastrados no sistema. Nesta seção, você pode verificar se um veículo foi aprovado ou se ainda está pendente.</p>
    <CadastroCarro @fechar-modal="fecharModal"/>

    <div class="tabela-div">
      <div class="tabela">
        <table>
          <thead>
            <tr>
              <th>Imagem</th>
              <th>Modelo</th>
              <th>Montadora</th>
              <th>Ano</th>
              <th>Cor</th>
              <th>Placa</th>
              <th>Valor</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(carro) in carros">
              <td><img :src="'data:image/png;base64,' + carro.imagem" alt="" class="img-carro"></td>
              <td>{{ carro.modelo }}</td>
              <td>{{ carro.montadora }}</td>
              <td>{{ carro.ano }}</td>
              <td>{{ carro.cor }}</td>
              <td>{{ carro.placa }}</td>
              <td>R$ {{ carro.valor }}</td>
              <td :style="{ color: carro.autorizado === false ? 'red' : 'green' }">{{ carro.autorizado == false ?  "Pendente"   : "Aprovado" }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
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

  const fecharModal = () => {
    getCarros();
  }

  //data:image/png;base64,
  const carros = ref<{
    id: number,
      modelo: string,
      montadora: string, 
      ano: string, 
      cor: string,
      valor: number,
      imagem: string,
      placa: string,
      autorizado: boolean

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
  .img-carro {
  width: 5vw;
}

.tabela-div {
  display: flex;
  justify-content: center;
}

.tabela {
  min-width: 95vw;
  overflow-x: auto;
}

.form-description {
  text-align: center;
  font-size: 1em;
  color: #666;
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  background-color: #fff;
}

th,
td {
  padding: 12px;
  text-align: left;
  font-size: 14px;
}

th {
  background-color: black;
  color: white;
  font-weight: 600;
}


td {
  background-color: #f9f9f9;
  color: #333;
  border-bottom: 1px solid #ddd;
}

tr:nth-child(even) td {
  background-color: #f1f1f1;
}

tr:hover td {
  background-color: #e0f7fa;
  transition: background-color 0.3s ease;
}

td {
  word-wrap: break-word;
  max-width: 200px;
}

td::after {
  content: "";
  display: block;
  height: 0;
}
  </style>
  