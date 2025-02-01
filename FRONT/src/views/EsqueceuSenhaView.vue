<template>
    <div class="container-esqueceu-senha">
        <div class="container-titulo-principal diminuir-container">
            <Input :label="'Nova Senha'" :placeholder="'Digite uma nova senha de acesso'" :type="'password'"
                v-model="password" @input="validatePassword" />
            <p class="exigencias-senha">
                Sua senha deve conter:
            <ul>
                <li :class="{ valid: passwordValidations.minLength }">No mínimo 6 caracteres.</li>
                <li :class="{ valid: passwordValidations.uppercase }">Pelo menos uma letra maiúscula.</li>
                <li :class="{ valid: passwordValidations.lowercase }">Pelo menos uma letra minúscula.</li>
                <li :class="{ valid: passwordValidations.specialChar }">Pelo menos um caracter especial.</li>
            </ul>
            </p>
            <Input :label="'Confirmar Senha'" :placeholder="'Digite novamente sua senha'" :type="'password'"
                v-model="confirmPassword" />
            <p v-if="password !== confirmPassword && confirmPassword" class="error">As senhas não coincidem.</p>
            <div class="buttons">
                <Button :text="'Salvar'" :is-loading="isLoading" class="button" @click="resetar" />
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
import router from '@/router';
const password = ref('');
const confirmPassword = ref('');
const isLoading = ref<boolean>(false);
const passwordValidations = ref({
    minLength: false,
    uppercase: false,
    lowercase: false,
    specialChar: false,
});

const resetar = async () => {
    if (isFormValid.value) {
        try {
            const usuarioLog = usuarioLogado();
            const response = await UsuarioService.resetSenha(usuarioLog.usuario?.id, password.value);
            password.value = '';
            confirmPassword.value = '';
            showAlertaFunction(response);
        } catch (error: any) {
            showAlertaFunction(error)
        }

    } else {
        showAlertaFunction("Requisitos não atendidos");
    }
}

const isFormValid = computed(() => {
    const allPasswordValid = Object.values(passwordValidations.value).every((valid: any) => valid);
    return allPasswordValid && password.value === confirmPassword.value;
});

const validatePassword = () => {
    const value = password.value;
    passwordValidations.value.minLength = value.length >= 6;
    passwordValidations.value.uppercase = /[A-Z]/.test(value);
    passwordValidations.value.lowercase = /[a-z]/.test(value);
    passwordValidations.value.specialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);
};
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