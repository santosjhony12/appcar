<template>
    <div class="containerCadastro">
        <div v-if="isVisibleForms" class="modal-overlay">
            <div class="modal">
                <p class="form-description information">
                    Preencha todas informações abaixo para cadastrar um novo treinamento. Todos os campos são
                    obrigatórios.
                </p>

                <form @submit.prevent="cadastrarTreinamento" class="car-form">
                    <div class="form-group">
                        <Input :label="'Titulo do treinamento'" :placeholder="'Escreva o titulo do treinamento'"
                            :type="'text'" v-model="treinamento.titulo" />
                    </div>
                    <div class="form-group">
                        <Input :label="'Link treinamento'" :placeholder="'Cole aqui o link do treinamento'"
                            :type="'text'" v-model="treinamento.link" />
                    </div>
                    <div class="form-group">
                        <InputSelect :label="'Tipo de Usuário para treinamento'"
                            :options="['INVESTIDOR', 'MOTORISTA', 'AMBOS']" v-model="treinamento.role"
                            :placeholder="'Selecione o tipo de usuário'" />
                    </div>

                    <div class="form-group">
                        <Button :is-loading="isLoading" :text="'Salvar'" />
                    </div>
                    <Button @click="closeModal" :text="'Cancelar'" :is-loading="false" />


                </form>
            </div>

        </div>
        <div class="centralizar-button">
      <Button @click="toogleAddUser" :text="isVisibleForms ? 'Cancelar' : 'Adicionar Treinamento'" :is-loading="false" class="button"/>
    </div>
        <Alert :message="msgAlert" v-if="showAlert" />
    </div>
</template>

<script lang="ts" setup>
import Alert from '@/components/Alert.vue';
import Button from '@/components/Button.vue';
import Input from '@/components/Input.vue';
import InputSelect from '@/components/InputSelect.vue';
import { ref } from 'vue';
import TreinamentoService from '../service/treinamento';

interface Treinamento {
    titulo: string,
    link: string,
    role: string
}

const treinamento = ref<Treinamento>({
    titulo: '',
    link: '',
    role: ''
});
const isLoading = ref<boolean>(false);
const msgAlert = ref<string>('');
const showAlert = ref<boolean>(false);
const isVisibleForms = ref<boolean>(false);
const toogleAddUser = () => {
    isVisibleForms.value = !isVisibleForms.value;
}
const emit = defineEmits(['fecharModal']);

const closeModal = () => {
    isVisibleForms.value = !isVisibleForms.value;
    emit('fecharModal');
}

const cadastrarTreinamento = async () => {
    if (treinamento.value.link.trim() === '' || treinamento.value.role === '') {
        showAlertaFunction("Todos os dados são obrigatórios");
    } else {
        isLoading.value = true;
        try {
            treinamento.value.role = "ROLE_"+treinamento.value.role;
            
            const response = await TreinamentoService.cadastrar(treinamento.value);
            closeModal();
            showAlertaFunction(response)
            
        } catch (error: any) {
            showAlertaFunction(error);
        }
        isLoading.value = false;

    }

}

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
.containerCadastro {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;

}


.form-description {
    text-align: center;
    font-size: 1em;
    color: #000000;
    margin-bottom: 20px;
}

.button {
    width: 80%;
}

.centralizar-button {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
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