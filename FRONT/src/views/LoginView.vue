<template>
  <div class="login-container">
    <div class="imagem">
      <img src="../assets/fundo.jpg" alt="" id="imagem-carro">
    </div>

    <div class="login-form">
      <div class="title-login">
        <Title :text="'Faça seu login'" />
      </div>

      <form @submit.prevent="handleLogin">
        <div class="input-group">

          <Input :label="'Entre com seu e-mail'" :placeholder="'E-mail'" :type="'string'" v-model="username" />
        </div>

        <div class="input-group">
          <Input :label="'Digite sua senha'" :placeholder="'Senha'" :type="'password'" v-model="password" />

        </div>
        <Button :is-loading="isLoading" :text="'Entrar'" class="button" />

      </form>

      <div class="footer-links">
        <a href="#">Esqueceu sua senha?</a>
        <p>Quer se juntar a nós? <a href="#">Nos contate</a></p>
      </div>
    </div>
    <Alert :message="msgAlert" v-if="showAlert" />

  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import UsuarioService from '@/service/usuarios';
import Alert from '@/components/Alert.vue';
import router from '@/router';
import { usuarioLogado } from '@/stores/usuario';
import Input from '@/components/Input.vue';
import Button from '@/components/Button.vue';
import Title from '@/components/Title.vue';
const username = ref('');
const password = ref('');
const usuarioLog = usuarioLogado();
const isLoading = ref<boolean>(false);
const handleLogin = async () => {
  isLoading.value = true;
  const response = await UsuarioService.autenticar({ username: username.value, password: password.value });
  if (response == true) {

    if (usuarioLog.usuario?.primeiroAcesso) {
      router.push("/primeiroAcesso");
    } else {
      if (usuarioLog.usuario?.role == 'ADMIN') {
        router.push("/usuarios");
      } else {
        router.push('/videos-treinamentos');
      }
    }
  } else {
    isLoading.value = false;
    showAlertaFunction(response);
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
.login-container {
  display: flex;
  align-items: center;
  height: 100vh;
  font-family: 'Arial', sans-serif;
  overflow-y: hidden;
}

.title-login {
  margin: 2vh 0;
}

.login-form {
  background-color: #ffffff;
  padding: 40px;
  border-radius: 12px;
  width: 100%;
  text-align: center;
  color: black;
  display: flex;
  flex-direction: column;
  justify-content: center;

}

.input-group {
  margin-bottom: 24px;
  text-align: left;
}

.footer-links {
  margin-top: 20px;
  font-size: 14px;
}

.footer-links a {
  color: #007bff;
  text-decoration: none;
  transition: color 0.3s ease;
}

.footer-links a:hover {
  color: #007bff;
}

.footer-links p {
  color: #bbb;
}

.button {
  width: 100%;
}

#imagem-carro {
  width: 70vw;
  height: 105vh;

}

@media (max-width: 1000px) {
  .login-container {
    flex-direction: column;
  }

  .imagem {
    position: fixed;
  }

  #imagem-carro {
    width: 100%;
  }

  .login-container {
    display: flex;
    align-items: center !important;
    justify-content: center;
    padding: 0 5vw
  }
}
</style>
