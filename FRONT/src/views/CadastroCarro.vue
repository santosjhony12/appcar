<template>
   <div class="containerCadastro">
     <Title :text="'Cadastrar Veículo'" />
     <p class="form-description">
       Preencha as informações abaixo para adicionar um novo veículo ao sistema. Todos os campos são obrigatórios.
     </p>
 
     <form @submit.prevent="handleSubmit">
       <Input :label="'Montadora'" :placeholder="'Digite o nome da montadora'" :type="'text'" v-model="car.montadora" />
       <Input :label="'Modelo'" :placeholder="'Digite o modelo'" :type="'text'" v-model="car.modelo" />
       <Input :label="'Cor'" :placeholder="'Digite a cor'" :type="'text'" v-model="car.cor" />
       <Input :label="'Ano'" :placeholder="'Digite o ano'" :type="'number'" v-model="car.ano" />
       <Input :label="'Placa'" :placeholder="'Digite a placa'" :type="'text'" v-model="car.placa" />
       <Input :label="'Valor'" :placeholder="'Digite o valor pago'" :type="'number'" v-model="car.valor" />
 
       <InputFile v-model="car.imagemBase64" />
 
       <Button :is-loading="isLoading" :text="'Cadastrar'" />
     </form>
     <Alert :message="msgAlert" v-if="showAlert" />

   </div>
 </template>
 
 <script lang="ts" setup>
 import Title from '@/components/Title.vue';
 import Input from '@/components/Input.vue';
 import InputFile from '@/components/InputFile.vue';
 import Button from '@/components/Button.vue';
import { ref } from 'vue';
import CarroService from '../service/carro';
import Alert from '@/components/Alert.vue';

interface Carro{
  montadora: string;
  modelo: string;
  cor: string;
  ano: string;
  placa: string;
  valor: string;
  imagemBase64: string | null;
}
const car = ref<Carro>({
  montadora: '',
  modelo: '',
  cor: '',
  ano: '',
  placa: '',
  valor: '',
  imagemBase64: null,
});
const isLoading = ref<boolean>(false);
const msgAlert = ref<string>('');
const showAlert = ref<boolean>(false);
 
 const handleSubmit = async () => {
      if(car.value.montadora.trim() == '' || 
         car.value.modelo.trim() == '' || 
         car.value.cor.trim() == '' || 
         car.value.ano == '' || 
         car.value.placa.trim() == '' || 
         car.value.valor == '' || 
         car.value.imagemBase64 == null || 
         car.value.imagemBase64.trim() == ''
   ){
      showAlertaFunction('Todos os campos são obrigatórios.');
   }else{
      isLoading.value = true;
      try{
         await CarroService.cadastrarCarro(car.value);
         showAlertaFunction('Veículo registrado com sucesso.');

      }catch{
         showAlertaFunction('Não foi possível cadastrar o veículo.');
      }
      limparCampos();
      isLoading.value = false;
   }
      
 };

 const limparCampos = () => {
   car.value.montadora = '' 
   car.value.modelo = ''
   car.value.cor= ''
   car.value.ano= ''
   car.value.placa = ''
   car.value.valor = '' 
   car.value.imagemBase64 = null
 }
 
 const showAlertaFunction = (msg: string) =>{
   msgAlert.value = msg;
   showAlert.value = true;
   setTimeout(() => {
      showAlert.value = false;
      msgAlert.value = '';
   }, 3000);
 }
 </script>
 
 <style scoped>
 .containerCadastro {
   max-width: 600px;
   margin: 0 auto;
   padding: 20px;
   background: #ffffff;
  
 }
 
 .form-description {
   text-align: center;
   font-size: 1em;
   color: #666;
   margin-bottom: 20px;
 }
 
 form {
   display: flex;
   flex-direction: column;
 }
 

 @media (max-width: 600px) {
   .containerCadastro {
     padding: 15px;
   }
 
   button.submit-button {
     font-size: 1em;
   }
 }
 </style>
 