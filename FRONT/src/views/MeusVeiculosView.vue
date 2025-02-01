<template>

    <div class="container-meus-veiculos">
      <Title :text="'Meus veículos cadastrados'" />
    <p class="form-description">
      Confira os veículos cadastrados no sistema. Nesta seção, você pode verificar se um veículo foi aprovado ou se ainda está pendente.</p>
    <CadastroCarro @fechar-modal="fecharModal"/>
    </div>
    

    <div class="tabela-div">
      <div class="tabela">
        <table>
          <thead>
            <tr class="cabecalho">
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
              <td>{{ formatarParaReal(carro.valor)}}</td>
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


  function formatarParaReal(valor: number): string {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(valor);
}
  </script>
  
  <style scoped>

  .container-meus-veiculos{

    padding: 20px;
    border: 1px solid #e4e4e4;
    border-radius: 24px;
    margin: 0 1em 0 1em;
    box-shadow: -1px 2px 8px 3px #e4e4e4;
  }
  .img-carro {
  width: 5vw;
}

.tabela-div {
  margin: 0 2vw;
  display: flex;
  justify-content: center;
}

.tabela {
  min-width: 92vw;
}

.form-description {
  font-size: 17px;
   color: #1a2f4d;
   margin-bottom: 20px;
}

table {
  margin: 2vh 0;
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
  text-align: center;
  font-size: 14px;
}
.cabecalho{
    background-color: #f5f5f5;
    border: 1px solid #e4e4e4;
}
th {

  color: black;
  font-weight: 600;
}


td {
  background-color: #fff;
  color: #333;
  border-bottom: 1px solid #ddd;
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
  