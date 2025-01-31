<template>
  <div class="video-container">
    <div class="video-player">
      <Title :text="'Treinamento: '+tituloVideo" />

      <iframe
        :src="videoSrc"
        frameborder="0"
        allowfullscreen
      ></iframe>
    </div>

    <div class="comments-videos">
      <div  class="comments-section">
        <h3>Comentários</h3>
        <AddComent v-if="isVisibleAddComent" @fechar-comentario="fecharAddCommentCarregarComentarios" @cancelar="cancelar" :id-treinamento="idTreinamentoVisualizado"/>
        <Button :text="'Adicionar comentário'" :is-loading="false" @click="showAddComment" v-else />
      <div class="comments-grid" >
        <h4  v-if="treinamentos.length == 0" >Sem comentários</h4>
        <div v-for="comentario in selecionadoComentarios" :key="comentario.id" class="comment-item" v-if="treinamentos.length > 0">
          <p><strong><font-awesome-icon :icon="['fas', 'user']" class="icon username" /> {{ comentario.username }}</strong></p>
          <p><font-awesome-icon :icon="['fas', 'comment']" class="icon" /> {{ comentario.comentario }}</p>
          <p><font-awesome-icon :icon="['fas', 'calendar-days']" class="icon" />{{ comentario.dayDateTime }}</p>

        </div>
      </div>
    </div>

    <div class="video-list">
      <h3>Treinamentos disponíveis para você</h3>
      <div v-for="(treinamento, index) in treinamentos" :key="index" class="video-item">
        <img :src="getThumbnail(treinamento.link)" alt="Thumbnail" @click="playVideo(treinamento.link, treinamento.comentarios, treinamento.id, treinamento.titulo)" />
        <div class="video-info">
          <h4>{{ treinamento.titulo }}</h4>
          <p>{{ treinamento.comentarios.length }} comentários</p>
        </div>
      </div>
    </div>

    </div>
    
    <!-- Comentários abaixo do vídeo -->
    
  </div>
  <Alert :message="msgAlert" v-if="showAlert" />
</template>


<script setup lang="ts">
import Title from '@/components/Title.vue';
import { onMounted, ref, watch } from 'vue';
import Alert from '@/components/Alert.vue';
import TreinamentosService from '@/service/treinamento'
import AddComent from '@/components/AddComent.vue';
import Button from '@/components/Button.vue';

const idTreinamentoVisualizado = ref<number>(0);
const videoSrc = ref(''); // Inicializando como uma string vazia
const treinamentos = ref<Treinamento[]>([]);
const selecionadoComentarios = ref<Comentario[]>([]); // Para armazenar os comentários do vídeo selecionado
const isVisibleAddComent = ref<boolean>(false);
const tituloVideo = ref<string>("");
const playVideo = (videoLink: string, comentarios: Comentario[], id: number, titulo: string) => {
  idTreinamentoVisualizado.value = id;
  const videoId = getVideoIdFromUrl(videoLink);
  videoSrc.value = `https://www.youtube.com/embed/${videoId}`;
  selecionadoComentarios.value = comentarios; // Atualiza os comentários ao selecionar o vídeo
  tituloVideo.value = titulo;
};
const showAddComment = () => {
  isVisibleAddComent.value = !isVisibleAddComent.value;
}

const cancelar = () => {
  showAddComment();
}

const fecharAddCommentCarregarComentarios = async () => {
  showAddComment();
  try {
    
    selecionadoComentarios.value = await TreinamentosService.getComentariosByTreinamento(idTreinamentoVisualizado.value);
    
  } catch (error: any) {
    showAlertaFunction(error);
  }
};
const getThumbnail = (videoLink: string) => {
  const videoId = getVideoIdFromUrl(videoLink);
  return `https://img.youtube.com/vi/${videoId}/hqdefault.jpg`;
};

const getVideoIdFromUrl = (url: string) => {
  // Expressão regular para capturar tanto o formato 'youtube.com' quanto 'youtu.be'
  const match = url.match(/(?:https?:\/\/(?:www\.)?youtube\.com\/(?:v|e(?:mbed)?)\/([a-zA-Z0-9_-]+)|https?:\/\/(?:www\.)?youtu\.be\/([a-zA-Z0-9_-]+))/);
  return match ? match[1] || match[2] : ''; // Retorna o ID do vídeo, que pode estar na posição 1 ou 2
};



interface Comentario {
  id: number;
  comentario: string;
  username: string;
  dayDateTime: string
}

interface Treinamento {
  id: number;
  role: string;
  link: string;
  titulo: string;
  comentarios: Comentario[];
}

const msgAlert = ref<string>('');
const showAlert = ref<boolean>(false);

onMounted(async () => {
  await getTreinamentos();
});

const getTreinamentos = async () => {
  try {
    treinamentos.value = await TreinamentosService.getTreinamentosByRoleLogado();
    
    if (treinamentos.value.length > 0) {
      // Inicializar com o primeiro vídeo da lista
      playVideo(treinamentos.value[0].link, treinamentos.value[0].comentarios, treinamentos.value[0].id, treinamentos.value[0].titulo);
    }
  } catch (error: any) {
    showAlertaFunction(error);
  }
};

const showAlertaFunction = (msg: string) => {
  msgAlert.value = msg;
  showAlert.value = true;
  setTimeout(() => {
    showAlert.value = false;
    msgAlert.value = '';
  }, 3000);
};
</script>



<style>
.video-container {
  display: flex;
  gap: 20px;
  flex-direction: column;
  color: black !important;
  overflow-x: hidden;
}

.video-player {
  flex: 2;
}

.video-player iframe {
  width: 100%;
  height: 500px;
}

.video-list {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.video-item {
  display: flex;
  align-items: center;
  cursor: pointer;
  margin-bottom: 10px;
  
}

.video-item img {
  width: 150px;
  height: 90px;
  margin-right: 10px;
  border-radius: 3px;
}
.video-info > h4{
  font-weight: bold;
}
.video-item:hover{
  background-color: rgb(206, 206, 206);
}

.comments-section {
  color: black;

}

.comments-grid {
  display: flex;
  flex-direction: column;
  gap: 15px;
  padding: 20px;
}

.comment-item {
  padding: 10px;
  
}

.comment-item p {
  margin: 5px 0;
}

.comment-item strong {
  font-size: 1.1em;
  font-weight: bold;
}
.comments-videos{
  display: grid;
  grid-template-columns: 5fr 3fr; /* Ajuste a proporção conforme necessário */
  gap: 20px;
  padding: 2em;
  
  
}
.comments-section{
  background-color: #f5f5f5;
 border: 1px solid #e4e4e4;
padding: 20px;
border-radius: 1em;
max-height: 60vh;
overflow-y: auto;

}
.video-list{
  background-color: #f5f5f5;
 border: 1px solid #e4e4e4;
padding: 20px;
border-radius: 1em;
max-height: 60vh;
overflow-y: auto;
}
.icon {
  margin-right: 5px;
  color: gray
}
.username{
  color: black
}

.comment-item{
  border: 1px solid rgba(203, 203, 203, 0.786)
}

@media (max-width:1000px ){
  .video-player iframe{
    width: 100%;
    height: 100%;
  }
  .comments-videos{
    display: flex;
    flex-direction: column;
  }
}
</style>
