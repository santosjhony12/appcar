<template>
    <Title :text="'Treinamentos'" />
    <p class="form-description">
      Confira os os treinamentos cadastrados no sistema.</p>
    <CadastroTreinamento @fechar-modal="fecharModal"/>

    <div class="tabela-div">
      <div class="tabela">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Titulo</th>
              <th>Link</th>
              <th>Acesso</th>
              <th>Ação</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(treinamento) in treinamentos">
              <td>{{ treinamento.id }}</td>
              <td>{{ treinamento.titulo }}</td>
              <td><a :href="treinamento.link">{{ treinamento.link }}</a></td>
              <td>{{ treinamento.role }}</td>
              <td><button @click="abrirModalDelete(treinamento.id)">Excluir</button></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div v-if="showModal" class="modal-overlay">
        <div class="modal">
            <p>Tem certeza que deseja excluir este usuário?</p>
            <div class="buttons">
              <button @click="deleteTreinamento">Sim, excluir</button>
              <button @click="closeModal">Cancelar</button>
            </div>
           
        </div>
    </div>
    <Alert :message="msgAlert" v-if="showAlert" />

  </template>
  
  <script setup lang="ts">
  import { onMounted, ref } from "vue";
  import CadastroTreinamento from "@/components/CadastroTreinamento.vue";
  import TreinamentoService from '@/service/treinamento';
  import Title from "@/components/Title.vue";
  import Alert from "@/components/Alert.vue";

  onMounted(()=>{
    getTreinamentos();
  })


  interface Treinamento {
    id: number, 
    titulo: string, 
    link: string, 
    role: string
  }
  const treinamentos = ref<Treinamento[]>([]);

  const fecharModal = () => {
    getTreinamentos();
  }
  const showModal = ref<boolean>(false);

  const getTreinamentos = async () =>{
    try{
        const response = await TreinamentoService.getAll();
        treinamentos.value = response;
    }catch(error : any){
        showAlertaFunction(error);
    }
  }
  const closeModal = () => {
    showModal.value = false;
  }
  const idDelete = ref<number>(0);
  const abrirModalDelete = (id: number) => {
    idDelete.value = id;
    showModal.value = true;
  }
  const deleteTreinamento = async () => {
    try{
        const response = await TreinamentoService.deleteById(idDelete.value);
        showAlertaFunction(response);
    }catch(error : any){
        showAlertaFunction(error);
    }
    showModal.value = false;
    getTreinamentos();
  }
  const msgAlert = ref<string>('');
  const showAlert = ref<boolean>(false);
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
  