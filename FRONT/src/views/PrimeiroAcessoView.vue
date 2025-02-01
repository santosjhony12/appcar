<template>
    <div class="container-usuarios">
        <Title :text="'Confirmar cadastro'" />
        <p class="form-description">Por gentileza, finalize seu cadastro para continuar.</p>

        <div class="forms-cadastrar-usuario">
            <Input :label="'Nome Completo'" :placeholder="'Digite o seu nome completo'" :type="'text'"
                v-model="usuario.nome" />
            <InputCPF :label="'CPF'" :placeholder="'___.___.___-__'" :type="'text'" v-model="usuario.cpf" />
            <Input :label="'Data de Nascimento'" :placeholder="'dd/mm/aaaa'" :type="'date'"
                v-model="usuario.dataNascimento" />
            <InputTelefone :label="'Celular'" :placeholder="'(XX) XXXXX-XXXX'" :type="'text'"
                v-model="usuario.telefone" />
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
            <Button :text="'Continuar'" :is-loading="isLoading" :disabled="!isFormValid" id="button-continuar"
                @click="salvarInformacoes" />
            <Alert :message="msgAlert" v-if="showAlert" />

        </div>
    </div>
</template>

<script lang="ts" setup>
import { computed, ref } from 'vue';
import Title from '@/components/Title.vue';
import Input from '@/components/Input.vue';
import InputCPF from '@/components/InputCPF.vue';
import InputTelefone from '@/components/InputTelefone.vue';
import Button from '@/components/Button.vue';
import UsuarioService from '@/service/usuarios';
import Alert from '@/components/Alert.vue';
import router from '@/router';
import { usuarioLogado } from '@/stores/usuario';

const usuarioLog = usuarioLogado();
interface User {
    id: number | null;
    username: string;
    password: string;
    cpf: string;
    nome: string;
    dataNascimento: string;
    primeiroAcesso: boolean | null;
    role: string;
    telefone: string;
}
const isLoading = ref<boolean>(false);
const usuario = ref<User>({
    id: null,
    username: '',
    password: '',
    cpf: '',
    nome: '',
    dataNascimento: '',
    primeiroAcesso: true,
    role: '',
    telefone: '',
});

const password = ref('');
const confirmPassword = ref('');

const passwordValidations = ref({
    minLength: false,
    uppercase: false,
    lowercase: false,
    specialChar: false,
});

const validatePassword = () => {
    const value = password.value;
    passwordValidations.value.minLength = value.length >= 6;
    passwordValidations.value.uppercase = /[A-Z]/.test(value);
    passwordValidations.value.lowercase = /[a-z]/.test(value);
    passwordValidations.value.specialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);
};

const areAllFieldsFilled = computed(() => {
    return (
        usuario.value.nome.trim() !== '' &&
        usuario.value.cpf.trim() !== '' &&
        usuario.value.dataNascimento.trim() !== '' &&
        usuario.value.telefone.trim() !== '' &&
        password.value.trim() !== '' &&
        confirmPassword.value.trim() !== ''
    );
});

const isFormValid = computed(() => {
    const allPasswordValid = Object.values(passwordValidations.value).every((valid: any) => valid);
    return areAllFieldsFilled.value && allPasswordValid && password.value === confirmPassword.value;
});
const removeMask = (value: string) => {
    return value.replace(/[^\d]/g, '');
};

const salvarInformacoes = async () => {
    if (isFormValid.value) {
        if (usuarioLog.usuario) {
            isLoading.value = true;
            usuario.value.password = confirmPassword.value;
            usuario.value.id = usuarioLog.usuario.id;
            usuario.value.cpf = removeMask(usuario.value.cpf);
            usuario.value.telefone = removeMask(usuario.value.telefone);
            const response = await UsuarioService.confirmarUsuario(usuario.value);
            if (response == true) {
                showAlertaFunction("Informações salvas com sucesso.");
                const usuarioLog = usuarioLogado();
                if(usuarioLog.usuario?.role === 'ROLE_ADMIN'){
                    router.push("/usuarios");
                }else{
                    router.push("/videos-treinamentos")
                }

            } else {
                showAlertaFunction(response);
            }
        }



    } else {
        console.error('Formulário inválido.');
    }
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
.form-description {
    text-align: center;
    font-size: 1em;
    color: #666;
    margin-bottom: 20px;
}

.container-usuarios {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
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

.button:disabled {
    background: #ccc;
    cursor: not-allowed;
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

#button-continuar:disabled {
    background-color: #666;
}
</style>