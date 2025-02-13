<template>
  <div class="solicitacoes" id="solicitacoes">
    <div class="container-solicitacoes-veiculos">
      <Title :text="'Solicitações de aprovação de veículos'" />

      <p class="form-description">Abaixo, visualize as solicitações de veículos cadastrados. Além disso, defina os
        percentuais de cada perfil.</p>
      <div class="filters">
        <label>
          <input type="radio" name="statusFilter" value="all" v-model="selectedFilter" @change="filtrar" />
          Todos
        </label>
        <label>
          <input type="radio" name="statusFilter" value="unauthorized" v-model="selectedFilter" @change="filtrar" />
          Não autorizados
        </label>
        <label>
          <input type="radio" name="statusFilter" value="authorized" v-model="selectedFilter" @change="filtrar" />
          Autorizados
        </label>
      </div>
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
              <th>Investidor</th>
              <th>% Investidor</th>
              <th>% Motorista</th>
              <th>% Sistema</th>
              <th>Ação</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(carro) in paginatedCarros" :key="carro.id">
              <td><img :src="'data:image/png;base64,' + carro.imagem" alt="" class="img-carro"></td>
              <td>{{ carro.modelo }}</td>
              <td>{{ carro.montadora }}</td>
              <td>{{ carro.ano }}</td>
              <td>{{ carro.cor }}</td>
              <td>{{ carro.placa }}</td>
              <td>{{ formatarParaReal(carro.valor) }}</td>
              <td>{{ carro.username }}</td>
              <td>{{ carro.percentualInvestidor }}%</td>
              <td>{{ formatarParaReal(carro.valorAluguel) }}</td>
              <td>{{ carro.percentualSistema }}%</td>
              <td>
                <button class="icones"
                  @click="showModalAutorizar(carro.id, carro.percentualInvestidor, carro.valorAluguel, carro.percentualSistema)"
                  v-if="carro.autorizado"> <font-awesome-icon :icon="['fas', 'pen-to-square']"/></button>
                <button
                  @click="showModalAutorizar(carro.id, carro.percentualInvestidor, carro.valorAluguel, carro.percentualSistema)"
                  v-else>Autorizar</button>
              </td>
            </tr>
          </tbody>

        </table>
      </div>

    
    </div>

    

    <h3 class="form-description" v-if="carros.length == 0">Sem solicitações no momento.</h3>
    <div class="pagination">
  <button @click="prevPage" :disabled="currentPage === 1">Anterior</button>
  <span>Página {{ currentPage }} de {{ totalPages }}</span>
  <button @click="nextPage" :disabled="currentPage === totalPages">Próxima</button>
</div>

  </div>

  <div v-if="showModal" class="modal-overlay">
    <div class="modal">
      <h2 class="titulo-modal">Insira os valores abaixo:</h2>

      <InputSelect :label="'Autorizar'" :options="['SIM', 'NÃO']" :placeholder="'Selecione uma opção'"
        v-model="autorizado" />
      <Input :label="'Percentual Investidor'" :placeholder="'Insira o percentual destinado ao Investidor'"
        :type="'number'" v-model="autorizar.percentualInvestidor" />
      <Input :label="'Valor aluguel'" :placeholder="'Insira o valor do aluguel do veículo por dia'" :type="'number'"
        v-model="autorizar.valorAluguel" />
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
import { computed, onMounted, ref } from 'vue';
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
  autorizado: boolean,
  valorAluguel: number,
  percentualSistema: number,
  percentualInvestidor: number
}
function formatarParaReal(valor: number): string {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
  }).format(valor);
}
const selectedFilter = ref("all");
const isLoading = ref<boolean>(false);
const autorizado = ref<string>('');
const carrosAuxiliar = ref<Carro[]>([]);
const autorizar = {
  autorizado: false,
  percentualInvestidor: '',
  valorAluguel: '',
  percentualSistema: '',
  id: 0
}

const filtrar = () => {
  if (selectedFilter.value == 'all') {
    carros.value = carrosAuxiliar.value;
  } else if (selectedFilter.value == 'unauthorized') {
    carros.value = carrosAuxiliar.value.filter(carro => {
      return !carro.autorizado;
    });
  } else if (selectedFilter.value == 'authorized') {
    carros.value = carrosAuxiliar.value.filter(carro => {
      return carro.autorizado;
    });
  }
};

const carros = ref<Carro[]>([]);
const getAllVeiculos = async () => {
  carros.value = await CarroService.getAllVeiculos();
  carrosAuxiliar.value = carros.value;
  selectedFilter.value = 'all';
  filtrar();
}
const showModal = ref<boolean>(false);
onMounted(() => {
  getAllVeiculos();
})

const showModalAutorizar = (id: number, percentualInvestidor: number, percentualMotorista: number, percentualSistema: number) => {
  autorizar.id = id;
  autorizar.percentualInvestidor = String(percentualInvestidor);
  autorizar.valorAluguel = String(percentualMotorista);
  autorizar.percentualSistema = String(percentualSistema);
  autorizar.autorizado = true;
  showModal.value = true;

}
const autorizarVeiculo = async () => {
  try {
    if (

      autorizar.percentualInvestidor == ''
      || autorizar.valorAluguel == ''
      || autorizar.percentualSistema == '' ||
      autorizar.percentualInvestidor == 'null'
      || autorizar.valorAluguel == 'null'
      || autorizar.percentualSistema == 'null') {
      showAlertaFunction("Todas as informações são necessárias para prosseguir.")
    } else {
      isLoading.value = true;
      autorizar.autorizado = true ? autorizado.value == 'SIM' : false;

      const response = await CarroService.autorizarVeiculo(autorizar);
      cancelarModal();
      showAlertaFunction(response);
      getAllVeiculos();
      autorizar.autorizado = false;
      autorizar.percentualInvestidor = '';
      autorizar.valorAluguel = '';
      autorizar.percentualSistema = '';

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
const currentPage = ref(1);
const itemsPerPage = ref(5);

const paginatedCarros = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage.value;
  const end = start + itemsPerPage.value;
  return carros.value.slice(start, end);
});

const totalPages = computed(() => Math.ceil(carros.value.length / itemsPerPage.value));

const nextPage = () => {
  if (currentPage.value < totalPages.value) currentPage.value++;
};

const prevPage = () => {
  if (currentPage.value > 1) currentPage.value--;
};


</script>

<style scoped>
.icones{
  color: #1a2f4d;
  background-color: white;
}
.container-solicitacoes-veiculos {
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
  margin: 0 auto;
  padding: 20px 0;
  display: flex;
  justify-content: center;
}

.tabela {
 
  min-width: 91vw;

  margin: 0 2vw;
}

.form-description {
  font-size: 17px;
  color: #1a2f4d;
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

.cabecalho {
  background-color: #f5f5f5;
  border: 1px solid #e4e4e4;
}

th,
td {
  padding: 12px;
  text-align: center;
  font-size: 14px;
}

th {
  color: black;
  font-weight: 600;
}


td {
  background-color: white;
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

.filters {
  display: flex;
  gap: 1rem;
  margin: 1rem 0;
}

.filters label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 1rem;
  cursor: pointer;
  color: #333;
}

.filters input[type="radio"] {
  accent-color: #007bff;
  /* Estiliza a cor do radio button */
  cursor: pointer;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
}

.pagination button {
  margin: 0 10px;
  padding: 8px 12px;
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}

.pagination button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

</style>