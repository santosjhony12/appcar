<template>
    <div class="login-container">
      <div class="login-form">
        <h2 class="login-title">Login</h2>
  
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

  const username = ref('');
  const password = ref('');
  
  const handleLogin = async () => {
    const response = await UsuarioService.autenticar({username: username.value, password: password.value});
    if(response == true){
        router.push("/usuarios");
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
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f3f4f6;
    font-family: 'Arial', sans-serif;
  }
  
  /* Formulário de login */
  .login-form {
    background-color: #fff;
    padding: 40px;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 400px;
    text-align: center;
  }
  
  /* Título do formulário */
  .login-title {
    font-size: 24px;
    color: #333;
    margin-bottom: 20px;
    font-weight: bold;
  }
  
  /* Estilo dos grupos de entrada */
  .input-group {
    margin-bottom: 20px;
    text-align: left;
  }
  
  .input-group label {
    font-size: 14px;
    color: #555;
    margin-bottom: 8px;
    display: block;
  }
  
  .input-group input {
    width: 100%;
    padding: 12px;
    font-size: 16px;
    border-radius: 5px;
    border: 1px solid #ddd;
    background-color: #f9f9f9;
  }
  
  .input-group input:focus {
    border-color: #4e9bdb;
    outline: none;
  }
  
  /* Botão de login */
  .login-button {
    width: 100%;
    padding: 12px;
    font-size: 16px;
    background-color: black;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }
  
  .login-button:hover {
    background-color: rgb(125, 125, 125);
  }
  
  /* Links do rodapé */
  .footer-links {
    margin-top: 20px;
    font-size: 14px;
  }
  
  .footer-links a {
    color: #4e9bdb;
    text-decoration: none;
    transition: color 0.3s ease;
  }
  
  .footer-links a:hover {
    color: #3482a6;
  }
  
  .footer-links p {
    color: #555;
  }
  </style>
  