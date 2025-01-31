<template>
    <div class="add-comment-container">
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <textarea
            v-model="comentario"
            id="comentario"
            placeholder="Digite seu comentário"
            rows="1"
            class="comment-input"
            required
          ></textarea>
        </div>
        <div class="button">
          <Button :text="'Cancelar'" :is-loading="false" @click="cancelarComentario"/>

            <Button :text="'Adicionar Comentário'" :is-loading="false" id="button-add"/>

        </div>
      </form>
      <Alert v-if="showAlert" :message="msgAlert" />
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue';
  import Alert from '@/components/Alert.vue'; // Certifique-se de que o componente Alert existe ou crie-o
  import TreinamentosService from '@/service/treinamento'; // Certifique-se de ter um serviço para salvar o comentário
  import Button from './Button.vue';
import { usuarioLogado } from '@/stores/usuario';

  const props = defineProps<{
    idTreinamento: number
  }>();
  // Dados do comentário
  const username = ref('');
  const comentario = ref('');
  const msgAlert = ref<string>('');
  const showAlert = ref<boolean>(false);
  const usuario = usuarioLogado();
  const emit = defineEmits(['fecharComentario', 'cancelar']);
  // Função para enviar o comentário
  const handleSubmit = async () => {
    if(comentario.value.trim() === ''){
        showAlertaFunction("Campo vazio");
    }else{
        if(usuario.usuario){
            username.value = usuario.usuario.username;
        }
        try{
            const body = {username: username.value, idTreinamento: props.idTreinamento, comentario: comentario.value }
            const response = await TreinamentosService.createComment(body);
            emit('fecharComentario');
            showAlertaFunction(response);
        }catch(error : any){
            showAlertaFunction(error);
        }
    }
  };
  const cancelarComentario = () => {
    emit('cancelar')
  }
  const showAlertaFunction = (msg: string) => {
  msgAlert.value = msg;
  showAlert.value = true;
  setTimeout(() => {
    showAlert.value = false;
    msgAlert.value = '';
  }, 3000);
};
  </script>
  
  <style scoped>
  .add-comment-container {
    padding: 20px;
    border-radius: 8px;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  label {
    display: block;
    font-weight: bold;
  }
  
  input,
  textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    margin-top: 5px;
  }
.button{
    display: flex;
    justify-content: end;
}
#button-add{
  margin-left:2vw
}
  </style>
  