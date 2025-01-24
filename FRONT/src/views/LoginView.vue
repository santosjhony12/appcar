<template>
  <div class="login-container">
    <div class="login-form">
      <div class="imagem">
        <img src="../assets/logo_app.png" alt="">
      </div>

      <form @submit.prevent="handleLogin">
        <div class="input-group">
          <label for="username">Username</label>
          <input
            type="text"
            id="username"
            v-model="username"
            placeholder="Digite seu nome de usuário"
            required
          />
        </div>

        <div class="input-group">
          <label for="password">Senha</label>
          <input
            type="password"
            id="password"
            v-model="password"
            placeholder="Digite sua senha"
            required
          />
        </div>

        <button type="submit" class="login-button">Entrar</button>
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
const username = ref('');
const password = ref('');
const usuarioLog = usuarioLogado();
const handleLogin = async () => {
  const response = await UsuarioService.autenticar({username: username.value, password: password.value});
  if(response == true){

    if(usuarioLog.usuario?.primeiroAcesso){
      router.push("/primeiroAcesso");
    }else{
      if(usuarioLog.usuario?.role == 'ADMIN'){
        router.push("/usuarios");
      }else{
        router.push('/alugar');
      }
    }
  }else{
      showAlertaFunction(response);
  }
};
const msgAlert = ref<string>('');
  const showAlert = ref<boolean>(false);
const showAlertaFunction = (msg: string) =>{
 msgAlert.value = msg;
 showAlert.value = true;
 setTimeout(() => {
    showAlert.value = false;
    msgAlert.value = '';
 }, 3000);
}
</script>

<style scoped>
/* Contêiner geral */
/* Contêiner geral */
/* Contêiner geral */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url('../assets/carro-landing-page.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-color: rgba(0, 0, 0, 0.85); /* Fundo mais escuro */
  background-blend-mode: darken;
  font-family: 'Arial', sans-serif;
}

/* Formulário de login */
.login-form {
  background-color: #1a1a1aac; /* Fundo escuro para o formulário */
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3); /* Sombra mais suave */
  width: 100%;
  max-width: 400px;
  text-align: center;
  color: #fff; /* Texto claro */
}

/* Título do formulário */
.login-title {
  font-size: 28px;
  color: #fff; /* Texto claro */
  margin-bottom: 24px;
  font-weight: bold;
}

/* Estilo dos grupos de entrada */
.input-group {
  margin-bottom: 24px;
  text-align: left;
}

.input-group label {
  font-size: 14px;
  color: white; /* Laranja para o texto dos labels */
  margin-bottom: 8px;
  display: block;
}

.input-group input {
  width: 100%;
  padding: 14px;
  font-size: 16px;
  border-radius: 8px;
  border: 1px solid #444; /* Bordas escuras */
  background-color: #333; /* Fundo escuro para os inputs */
  color: #fff; /* Texto claro nos inputs */
}

.input-group input:focus {
  outline: 2px solid #ff9800; /* Laranja para o foco */
  background-color: #444; /* Escurece ao focar */
}

/* Botão de login */
.login-button {
  width: 100%;
  padding: 14px;
  font-size: 18px;
  background-color: orange; /* Laranja escuro para o botão */
  color: #fff;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.login-button:hover {
  background-color: #e64a19; /* Laranja mais escuro ao passar o mouse */
}

/* Links do rodapé */
.footer-links {
  margin-top: 20px;
  font-size: 14px;
}

.footer-links a {
  color: #ff9800; /* Laranja para os links */
  text-decoration: none;
  transition: color 0.3s ease;
}

.footer-links a:hover {
  color: #ff5722; /* Laranja escuro ao passar o mouse */
}

.footer-links p {
  color: #bbb; /* Cor do texto do rodapé */
}
.imagem{
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin: -2em 0 -1em 0;
  padding: 0
}
.imagem > img{
  width: 10vw;
 
}

@media (max-width: 1000px){
  .imagem > img{
    width: 20vw;
  }
}

</style>
