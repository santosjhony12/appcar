<template>

    <div class="container-usuarios ">
        
        <Title :text="'Meus Usuários'" />
        <p class="form-description">Veja todos os usuários registrados, edite suas informações ou exclua usuários quando necessário.</p>
        <div class="div-button">
            <button class="button" @click="toogleAddUser">{{ isVisibleFormsUser ? 'Cancelar' : 'Adicionar Usuário'
                }}</button>
        </div>
        

        <div v-if="isVisibleFormsUser" class="forms-cadastrar-usuario modal-overlay">
            <div class="modal">
            <p class="form-description information-cadastro">Para o cadastro, preencha somente um endereço de e-mail válido e seu tipo de acesso, logo após, será enviado a senha para o e-mail inserido para o usuário obter acesso a plataforma.</p>

            <Input :label="'E-mail'" :placeholder="'Digite o e-mail do usuário'" :type="'email'"
                v-model="usuario.username" />
            <InputSelect :label="'Tipo de Usuário'" :options="['INVESTIDOR', 'MOTORISTA', 'ADMINISTRADOR']"
                v-model="usuario.role" :placeholder="'Selecione o tipo de usuário'" />
        

        <div class="div-button">
            <Button :text="'Salvar'" :is-loading="isLoading" v-if="isVisibleFormsUser" @click="cadastrarUsuario" />

            <button class="button" @click="toogleAddUser">{{ isVisibleFormsUser ? 'Cancelar' : 'Adicionar Usuário'
                }}</button>

            </div>
        </div>
        
        </div>
    </div>
    <TabelaUsuarios :usuarios="usuariosLista" @delete="getAll"/>
    <Alert :message="msgAlert" v-if="showAlert" />

</template>

<script lang="ts" setup>
import Title from '@/components/Title.vue';
import Input from '@/components/Input.vue';
import InputSelect from '@/components/InputSelect.vue';
import Button from '@/components/Button.vue';
import { onMounted, ref } from 'vue';
import Alert from '@/components/Alert.vue';
import UsuarioService from '@/service/usuarios';
import TabelaUsuarios from '@/components/TabelaUsuarios.vue';

const isVisibleFormsUser = ref<boolean>(false);
interface User {
    id: number | null,
    username: string,
    password: string,
    cpf: string | null,
    nome: string | null,
    dataNascimento: string | null,
    primeiroAcesso: boolean | null,
    role: string,
    telefone: string
}
const usuario = ref<User>({
    id: null,
    username: '',
    password: '',
    cpf: null,
    nome: null,
    dataNascimento: null,
    primeiroAcesso: true,
    role: '',
    telefone: ''
});

const isLoading = ref<boolean>(false);
const msgAlert = ref<string>('');
const showAlert = ref<boolean>(false);
const usuariosLista = ref<User []>([]);
const toogleAddUser = () => {
    isVisibleFormsUser.value = !isVisibleFormsUser.value;
}

const cadastrarUsuario = async () => {
    console.log(usuario.value)
    if (usuario.value.username.trim() == "" ||
        usuario.value.password.trim() == ""
        || usuario.value.role == "") {
        showAlertaFunction("Todos os campos precisam ser preenchidos.");
    } else if (!isValidEmail(usuario.value.username)) {
        showAlertaFunction("O e-mail informado não é válido.");

    } else if (usuario.value.password.length < 6) {
        showAlertaFunction("A senha não pode ter menos de 6 digitos.")
    } else {
        isLoading.value = true;
        try {
            switch (usuario.value.role) {
                case "INVESTIDOR":
                    usuario.value.role = "ROLE_INVESTIDOR";
                    break;
                case "MOTORISTA":
                    usuario.value.role = "ROLE_MOTORISTA";
                    break;
                case "ADMINISTRADOR":
                    usuario.value.role = "ROLE_ADMIN";
                    break;
            }
            const response = await UsuarioService.cadastrarUsuario(usuario.value);
            showAlertaFunction(response);
            limparCampos();
        } catch (error: any) {
            showAlertaFunction(error);
        }

        isLoading.value = false;
        getAll()
    }

}

const limparCampos = () => {
    usuario.value = {
        id: null,
        username: '',
        password: '',
        cpf: null,
        nome: null,
        dataNascimento: null,
        primeiroAcesso: true,
        role: '',
        telefone: ''
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

const getAll = async () =>{
    usuariosLista.value = await UsuarioService.getAll();
}
const isValidEmail = (email: string) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}
onMounted(()=>{
    getAll();
})
</script>

<style scoped>
.form-description {
   text-align: center;
   font-size: 1em;
   color: #666;
   margin-bottom: 20px;
 }

.information-cadastro{
    color: blue
}
.button {
    margin: 0 0 0 2em;
    padding: 12px;
    background: #000000;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 1.1em;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin-top: 20px;

}

@media (max-width: 1000px) {
    .button {
        width: 40vw;
    }
}

.div-button {
    display: flex;
    justify-content: center;
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
</style>