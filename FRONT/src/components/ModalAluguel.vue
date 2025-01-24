<template>

    <div class="modal-overlay">
        <div class="modal">
            <div class="title">
              <Title :text="'Informe as datas de aluguel'" />
            </div>

            <div class="conteudo">
              <div class="informacoes-carro">
                <img :src="props.imagem" alt="" class="imagem">

                <h3 class="valor-aluguel">R$ {{ valorCalculado }}</h3>
              </div>

              <div class="input-data-inicio">
              <label for="" id="label">Data de início</label>
              <input type="date" name="" id="input" v-model="aluguel.dataInicio">
            </div>

            <div class="input-data-fim">
              <label for="" id="label">Data de fim</label>
              <input type="date" name="" id="input" v-model="aluguel.dataFim">
            </div>
            </div>
            <div class="disponibilidade"  >
              <p>
              {{ disponibilidadeAluguel }}
              </p>

              <Button :text="'Salvar'" :is-loading="isLoading" @click="salvar" v-if="disponivel"/>

            </div>
            <div class="buttons">
                <Button :text="'Consultar disponibilidade'" :is-loading="isLoading" @click="consultarDisponibilidade" />
                <Button :text="'Cancelar'" :is-loading="false" @click="close"/>

            </div>
        </div>
    </div>
    <Alert :message="msgAlert" v-if="showAlert" />

</template>

<script lang="ts" setup>
import Title from './Title.vue';
import Input from './Input.vue';
import { onMounted, ref, watch } from 'vue';
import Button from './Button.vue';
import AluguelService from '@/service/aluguel';
import { usuarioLogado } from '@/stores/usuario';

const usuarioLog = usuarioLogado();
interface AluguelCadastro{
    dataInicio: string, 
    dataFim: string,
    idCarro: number, 
    idUsuario: number
}


import Alert from './Alert.vue';
const dataInicioAluguel = ref<string>("");
const disponibilidadeAluguel = ref<string>("");
const aluguel = ref<AluguelCadastro>({
  idCarro: 0, 
  idUsuario: 0,
  dataFim: '',
  dataInicio: ''
});
const valorCalculado = ref<number>(0.00);
onMounted(() => {
  valorCalculado.value = props.valor;
})
watch(aluguel.value, (newValue) => {
  const { idCarro, idUsuario, dataFim, dataInicio } = newValue;
  const { idCarro: idCarroPrev, idUsuario: idUsuarioPrev, dataFim: dataFimPrev, dataInicio: dataInicioPrev } = aluguelDisponivel.value;
  console.log(dataFim, aluguelDisponivel.value.dataFim)
  if (
    dataFim !== dataFimPrev ||
    dataInicio !== dataInicioPrev
  ) {
    disponivel.value = false;
  }

  if (dataFim > dataInicio) {
  // Converte as datas para objetos Date, removendo o tempo (setando para 00:00)
  const inicio = new Date(dataInicio);
  const fim = new Date(dataFim);

  // Zera o horário das duas datas para considerar apenas o dia
  inicio.setHours(0, 0, 0, 0);
  fim.setHours(0, 0, 0, 0);

  // Calcula a diferença em milissegundos e converte para dias
  const diferencaEmMilissegundos = fim.getTime() - inicio.getTime();
  const diferencaEmDias = diferencaEmMilissegundos / (1000 * 3600 * 24);

  // Calcula o valor com base na diferença de dias
  valorCalculado.value = diferencaEmDias * props.valor;
} else if (dataFim === dataInicio) {
  valorCalculado.value = props.valor;
}

});
const aluguelDisponivel = ref<AluguelCadastro>({
  idCarro: 0, 
  idUsuario: 0,
  dataFim: '',
  dataInicio: ''
});

const props = defineProps<{
  id: number, 
  imagem: string,
  valor: number
}>();

const disponivel = ref<boolean>(false);

const emit = defineEmits(['close']);
const close = () => {
    emit('close');
}
const isLoading = ref<boolean>(false);
const consultarDisponibilidade = async () => {
    isLoading.value = true;
    if(aluguel.value.dataFim === '' || aluguel.value.dataInicio == ''){
      showAlertaFunction("Todos os campos são obrigatórios.");
    }else if(aluguel.value.dataInicio > aluguel.value.dataFim){
      showAlertaFunction("A data de início não pode ser maior que a data fim.")
    }else{
      aluguel.value.idCarro = props.id;
      const response = await AluguelService.verificarDisponibilidade(aluguel.value);
      disponibilidadeAluguel.value = String(response);
      if(String(response) == 'Veículo disponível na data selecionada.'){
        disponivel.value = true;
        aluguelDisponivel.value.dataFim = aluguel.value.dataFim;
        aluguelDisponivel.value.dataInicio = aluguel.value.dataInicio;
        aluguelDisponivel.value.idCarro = aluguel.value.idCarro;
      }
    }
    isLoading.value = false;
}

const salvar = async () => {
  aluguelDisponivel.value.idUsuario = usuarioLog.usuario?.id;
  const response = await AluguelService.createAluguel(aluguelDisponivel.value);
  showAlertaFunction(String(response));
  if(String(response) === "Solicitação de aluguel enviada com sucesso. Aguarde a aprovação no máximo de 1 dia."){
    emit('close');
  }
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

</script>

<style scoped>
.informacoes-carro{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.disponibilidade{
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  margin-top: 2em;
}
.imagem{
  max-width: 20vw;
  max-height: 20vw;
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
.input-data-inicio, .input-data-fim{
  display: flex;
  flex-direction: column;
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

#label {
  color: #555;
  font-size: 1.1em;
  margin-bottom: 5px;
  transition: color 0.3s ease;
}

#input {
  font-size: 1em;
  padding: 10px 12px;
  border: 1px solid #ccc; /* Borda padrão */
  border-radius: 5px; /* Cantos arredondados */
  outline: none;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

#input:focus {
  border-color: #007bff; /* Cor de destaque */
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5); /* Efeito de foco */
}

#input::placeholder {
  color: #aaa;
  font-style: italic;
}
</style>