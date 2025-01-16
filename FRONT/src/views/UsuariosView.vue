<template>
    <div class="container-usuarios">
        
        <Title :text="'Cadastro de Usuarios'" />

        <div v-if="isVisibleFormsUser" class="forms-cadastrar-usuario">
            <Input :label="'E-mail'" :placeholder="'Digite o e-mail do usuário'" :type="'email'" v-model="usuario.username" />
            <Input :label="'Senha provisória'" :placeholder="'Digite uma senha provisória de primeiro acesso'" :type="'password'" v-model="usuario.password" />
            <InputSelect :label="'Tipo de Usuário'" :options="['INVESTIDOR', 'MOTORISTA', 'ADMINISTRADOR']" v-model="usuario.role" :placeholder="'Selecione o tipo de usuário'"/>
        </div>

        <div class="div-button">
            <Button :text="'Salvar'" :is-loading="isLoading" v-if="isVisibleFormsUser" @click="cadastrarUsuario"/>
       
            <button class="button" @click="toogleAddUser">{{ isVisibleFormsUser ? 'Cancelar' : 'Adicionar Usuário' }}</button>

        </div>
        <Alert :message="msgAlert" v-if="showAlert" />
    </div>

</template>

<script lang="ts" setup>
import Title from '@/components/Title.vue';
import Input from '@/components/Input.vue';
import InputSelect from '@/components/InputSelect.vue';
import Button from '@/components/Button.vue';
import { ref } from 'vue';
import Alert from '@/components/Alert.vue';
import UsuarioService from '@/service/usuarios';

const isVisibleFormsUser = ref<boolean>(false);
interface User {
    id: number | null,
    username: string,
    password: string,
    cpf: string | null,
    nome: string | null, 
    dataNascimento: string | null, 
    primeiroAcesso: boolean | null,
    role: string
}
const usuario = ref<User>({
    id: null,
    username: '',
    password: '',
    cpf: null, 
    nome: null, 
    dataNascimento: null, 
    primeiroAcesso: true, 
    role: ''
});

const isLoading = ref<boolean>(false);
const msgAlert = ref<string>('');
const showAlert = ref<boolean>(false);
const toogleAddUser = () => {
    isVisibleFormsUser.value = !isVisibleFormsUser.value;
}

const cadastrarUsuario = async () => {
    console.log(usuario.value)
    if(usuario.value.username.trim() == "" ||
        usuario.value.password.trim() == "" 
    || usuario.value.role == ""){
        showAlertaFunction("Todos os campos precisam ser preenchidos.");
        }else if(!isValidEmail(usuario.value.username)){
            showAlertaFunction("O e-mail informado não é válido.");

        }else if (usuario.value.password.length < 6){
            showAlertaFunction("A senha não pode ter menos de 6 digitos.")   
        }else{
            isLoading.value = true;
            try{
                switch(usuario.value.role){
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
            }catch(error : any){
                showAlertaFunction(error);
            }
            
            isLoading.value = false;
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
        role: ''
    }
}
const showAlertaFunction = (msg: string) =>{
   msgAlert.value = msg;
   showAlert.value = true;
   setTimeout(() => {
      showAlert.value = false;
      msgAlert.value = '';
   }, 3000);
 }
 function isValidEmail(email: string) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}
</script>

<style scoped>
.container-usuarios{
    max-width: 600px;
   margin: 0 auto;
   padding: 20px;
}
.button{
    width: 15vw;
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
@media (max-width: 1000px){
    .button{
        width: 40vw;
    }
}
.div-button{
    display: flex;
    justify-content: end;
}
</style>