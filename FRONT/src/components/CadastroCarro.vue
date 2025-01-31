<template>
  <div class="containerCadastro" >
    <div v-if="isVisibleForms" class="modal-overlay">
    <div class="modal">
      <p class="form-description information">
      Preencha todas informações abaixo para solicitar o cadastro de um novo veículo ao sistema. Todos os campos são obrigatórios.
    </p>

    <form @submit.prevent="handleSubmit" class="car-form">
      <div class="form-group">
        <Input :label="'Montadora'" :placeholder="'Digite o nome da montadora'" :type="'text'" v-model="car.montadora" />
      </div>
      <div class="form-group">
        <Input :label="'Modelo'" :placeholder="'Digite o modelo'" :type="'text'" v-model="car.modelo" />
      </div>
      <div class="form-group">
        <Input :label="'Cor'" :placeholder="'Digite a cor'" :type="'text'" v-model="car.cor" />
      </div>
      <div class="form-group">
        <Input :label="'Ano'" :placeholder="'Digite o ano'" :type="'number'" v-model="car.ano" />
      </div>
      <div class="form-group">
        <Input :label="'Placa'" :placeholder="'Digite a placa'" :type="'text'" v-model="car.placa" />
      </div>
      <div class="form-group">
        <Input :label="'Valor'" :placeholder="'Digite o valor pago'" :type="'number'" v-model="car.valor" />
      </div>
      <div class="form-group">
        <InputFile v-model="car.imagemBase64" />
      </div>
      <div class="form-group">
        <Button :is-loading="isLoading" :text="'Cadastrar'" />
      </div>
      <Button @click="toogleAddUser" :text="'Cancelar'" :is-loading="false"/>

     
    </form>
    </div>
    
  </div>
    <div class="centralizar-button">
      <Button @click="toogleAddUser" :text="isVisibleForms ? 'Cancelar' : 'Adicionar Veículo'" :is-loading="false" class="button"/>
    </div>

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
const isVisibleForms = ref<boolean>(false);
const toogleAddUser = () => {
    isVisibleForms.value = !isVisibleForms.value;
}
const emit = defineEmits(['fecharModal']);
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
         isVisibleForms.value = false;
         emit('fecharModal');

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
   padding: 20px;

 }

 
 .form-description {
   text-align: center;
   font-size: 1em;
   color: #000000;
   margin-bottom: 20px;
 }
 .button{
  width: 40%;
 }

 .car-form {
   display: flex;
  flex-direction: column;
  justify-content: center;
 }
 
 .form-group {
   display: flex;
   flex-direction: column;
 }
 


 .modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

/* Modal */
.modal {
  background: white;
  padding: 30px;
  border-radius: 8px;
  color: black;
  width: 35vw;
  max-width: 90%;
  height: auto;
  max-height: 80vh;
  overflow-y: auto;
  /* Habilita rolagem se o conteúdo for maior que o modal */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* Botões dentro do modal */
.modal button {
  margin: 10px 10px;
  padding: 10px 20px;
  background-color: #000000;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1em;
}

.modal button:hover {
  background-color: #323232;
}

/* Título do modal */
.titulo-modal {
  font-size: 1.2em;
  font-weight: 500;
  text-align: center;
  margin-bottom: 20px;
}

/* Botões organizados em coluna */
.buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

@media (max-width: 1000px) {
  .modal {
    width: 100%;
    padding: 20px;
  }

  .titulo-modal {
    font-size: 1em;
  }

  .buttons {
    flex-direction: column;
    gap: 8px;
  }

  .tabela {
    max-width: 900px;
    overflow-x: auto;
    padding: 0 10px
  }
}
.button > button {
  background-color: #297fe0;
}
@media (max-width: 480px) {
  .modal {
    padding: 15px;
    border-radius: 6px;
  }

  .modal button {
    font-size: 0.9em;
    padding: 8px 16px;
  }

  .titulo-modal {
    font-size: 0.9em;
  }
}

 </style>
 