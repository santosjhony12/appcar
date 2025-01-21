<template>
  <div class="solicitacoes" id="solicitacoes">
    <Title :text="'Solicitações de aprovação de veículos'" />

    <p class="form-description">Abaixo, visualize as solicitações de veículos cadastrados. Além disso, defina os
      percentuais de cada perfil.</p>
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
              <th>Investidor</th>
              <th>Autorizar</th>
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
              <td>{{ carro.username }}</td>
              <td><button @click="showModalAutorizar(carro.id)">Autorizar</button></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <h3 class="form-description" v-if="carros.length == 0">Sem solicitações no momento.</h3>

  </div>

  <div v-if="showModal" class="modal-overlay">
    <div class="modal">
      <h2 class="titulo-modal">Insira os valores abaixo:</h2>

      <InputSelect :label="'Autorizar'" :options="['SIM', 'NÃO']" :placeholder="'Selecione uma opção'"
        v-model="autorizado" />

      <Input :label="'Percentual Investidor'" :placeholder="'Insira o percentual destinado ao Investidor'"
        :type="'number'" v-model="autorizar.percentualInvestidor" />
      <Input :label="'Percentual Motorista'" :placeholder="'Insira o percentual destinado ao Motorista'"
        :type="'number'" v-model="autorizar.percentualMotorista" />
      <Input :label="'Percentual Sistema'" :placeholder="'Insira o percentual destinado ao Sistema'" :type="'number'"
        v-model="autorizar.percentualSistema" />
      <div class="buttons">
        <Button :text="'Salvar'" :is-loading="isLoading" @click="autorizarVeiculo" />
        <Button :text="'Cancelar'" :is-loading="isLoading" @click="cancelarModal" />
      </div>
    </div>
  </div>
  <Alert :message="msgAlert" v-if="showAlert" />

</template>

<script lang="ts" setup>
import Title from '@/components/Title.vue';
import { onMounted, ref } from 'vue';
import CarroService from '@/service/carro';
import Input from '@/components/Input.vue';
import InputSelect from '@/components/InputSelect.vue';
import Button from '@/components/Button.vue';
import Alert from '@/components/Alert.vue';
interface Carro {
  id: number,
  modelo: string,
  montadora: string,
  ano: number,
  cor: string,
  placa: string,
  valor: number,
  username: string,
  imagem: string,
  autorizado: boolean
}
const isLoading = ref<boolean>(false);
const autorizado = ref<string>('');

const autorizar = {
  autorizado: false,
  percentualInvestidor: '',
  percentualMotorista: '',
  percentualSistema: '',
  id: 0
}
const carros = ref<Carro[]>([]);
const getCarrosNaoAutorizados = async () => {
  carros.value = await CarroService.getCarrosNaoAutorizados();
}
const showModal = ref<boolean>(false);
onMounted(() => {
  getCarrosNaoAutorizados();
})

const showModalAutorizar = (id: number) => {
  showModal.value = true;
  autorizar.id = id;
}
const autorizarVeiculo = async () => {
  try {
    if (autorizar.percentualInvestidor == ''
      || autorizar.percentualMotorista == ''
      || autorizar.percentualSistema == '') {
      showAlertaFunction("Todas as informações são necessárias para prosseguir.")
    } else {
      isLoading.value = true;
      autorizar.autorizado = true ? autorizado.value == 'SIM' : false;

      const response = await CarroService.autorizarVeiculo(autorizar);
      cancelarModal();
      showAlertaFunction(response);
      getCarrosNaoAutorizados();

    }

  } catch (error: any) {
    showAlertaFunction(error);
  }
  isLoading.value = false;

}

const msgAlert = ref<string>('');
const showAlert = ref<boolean>(false);

const showAlertaFunction = (msg: string) => {
  msgAlert.value = msg;
  showAlert.value = true;
  setTimeout(() => {
    showAlert.value = false;
    msgAlert.value = '';
  }, 3000);
}
const cancelarModal = () => {
  showModal.value = false;
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


/* Fundo do modal */
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

/* Ajustes para telas menores */
@media (max-width: 1000px) {
  .modal {
    width: 90%;
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

button {
    padding: 6px 12px;
    background-color: #000000;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
}

button:hover {
    background-color: #0056b3;
}
</style>