<template>
    <div class="container-esqueceu-senha">
        <div class="container-titulo-principal diminuir-container">
            <Title :text="'Esqueci minha senha!'" />
            <p class="form-description">Entre com seu e-mail. Logo após, enviaremos uma senha provisória para seu acesso.</p>
            <Input :label="'Digite seu email'" :placeholder="'Seu e-mail'" :type="'text'"
                v-model="email" />
               <div class="buttons">
                <Button :text="'Enviar'" :is-loading="isLoading" class="button" @click="enviarEmail" />
            </div>
        </div>

    </div>
    <Alert :message="msgAlert" v-if="showAlert" />

</template>

<script lang="ts" setup>
import { computed, ref } from 'vue';
import Input from '@/components/Input.vue';
import Button from '@/components/Button.vue';
import Alert from '@/components/Alert.vue';
import UsuarioService from '@/service/usuarios';
import { usuarioLogado } from '@/stores/usuario';
import Title from '@/components/Title.vue';
import router from '@/router';
const email = ref<string>('');
const isLoading = ref<boolean>(false);


const enviarEmail = async () => {
    if (isValidEmail(email.value)) {
        isLoading.value = true;
        try {
            const response = await UsuarioService.enviarEmaildeReset(email.value);
        
            showAlertaFunction(response);
        } catch (error: any) {
            showAlertaFunction(error)
        }
    } else{
        showAlertaFunction("E-mail inválido!")
    }
    isLoading.value = false;
}


const isValidEmail = (email: string) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
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
.exigencias-senha {
    color: black;
    margin-bottom: 10px;
}

.exigencias-senha ul {
    list-style: none;
    padding: 0;
}

.exigencias-senha li {
    margin: 5px 0;
    color: #666;
}

.exigencias-senha li.valid {
    color: green;
    font-weight: bold;
}

.error {
    color: red;
    font-size: 0.9em;
}

.button {
    width: 100%;
}

.buttons {
    display: flex;
    justify-content: center;
}
</style>