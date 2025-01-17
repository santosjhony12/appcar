<template>
  <div class="navbar">
    <!-- Navbar com título e botão hamburguer -->
    <div class="nav-content">
      <div class="left-side">
        <button class="hamburger" @click="toggleSidebar">
          <span></span>
          <span></span>
          <span></span>
        </button>
        <h1 class="logo">AppCar</h1>
      </div>
      <div class="right-side">
        <RouterLink class="login-button" to="/login"  v-if="route.path !== '/login'" >Fazer login</RouterLink>
        <h2 class="button-cadastra">Contate-nos</h2>
      </div>
    </div>

    <!-- Sidebar -->
    <div :class="['sidebar', { open: isSidebarOpen }]">
      <ul>
        <li>
          <RouterLink to="/about">Sobre</RouterLink>
        </li>
        <li><a href="#services">Serviços</a></li>
        <li><a href="#contact">Contato</a></li>
      </ul>
    </div>
    <div class="overlay" v-if="isSidebarOpen" @click="toggleSidebar"></div>
  </div>
</template>

<script lang="ts" setup>
import router from '@/router';
import { ref, watch } from 'vue';
import { RouterView, RouterLink, useRoute } from 'vue-router';

const isSidebarOpen = ref(false);
const route = useRoute();

const toggleSidebar = () => {
  isSidebarOpen.value = !isSidebarOpen.value;
};

// Fecha a sidebar ao mudar de rota
watch(() => route.fullPath, () => {
  isSidebarOpen.value = false;
});
</script>

<style scoped>
/* Navbar */
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: black;
  color: white;
  position: fixed;
  width: 100%;
  z-index: 10;
  padding: 0.8em 1em;
}
.login-button{
  color: white;
  text-decoration: none;
  outline: none;
}
.login-button:hover{
  cursor: pointer;
  text-decoration: none;
  outline: none;
  background-color: black;
  color: rgb(125, 125, 125)
}

/* Conteúdo do navbar */
.nav-content {
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: space-between;
}

.left-side,
.right-side {
  display: flex;
  align-items: center;
}

.button-cadastra {
  color: black;
  background-color: white;
  border-radius: 2em;
  padding: 0.4em 0.8em
}

.right-side {
  justify-content: left;

}

.right-side>h2 {
  font-size: 1em;
  font-weight: 400;
  margin: 0 1em;
}

/* Botão hamburguer */
.hamburger {
  background: none;
  border: none;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.hamburger span {
  display: block;
  width: 25px;
  height: 3px;
  background-color: white;
  border-radius: 3px;
  transition: 0.3s;
}

/* Sidebar */
.sidebar {
  position: fixed;
  top: 0;
  left: -250px;
  /* Escondido inicialmente */
  width: 250px;
  height: 100%;
  background-color: black;
  color: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  transform: translateX(0);
  transition: left 0.3s ease-in-out;
}

.sidebar.open {
  left: 0;
  /* Mostra a sidebar na esquerda */
}


.sidebar ul {
  cursor: pointer;
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  cursor: pointer;
  margin: 10px 0;
}

.sidebar a {
  cursor: pointer;
  text-decoration: none;
  color: white;
  font-size: 1.2em;
  transition: color 0.3s;
}

.sidebar li:hover {
  cursor: pointer;
  color: #00bcd4;
}

/* Overlay para escurecer o fundo */
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 9;
}

/* Animações adicionais para o hamburguer */
.hamburger.open span:nth-child(1) {
  transform: rotate(45deg) translate(5px, 5px);
}

.hamburger.open span:nth-child(2) {
  opacity: 0;
}

.hamburger.open span:nth-child(3) {
  transform: rotate(-45deg) translate(5px, -5px);
}

.logo {
  margin: 0 0 0 1em;
  font-weight: 100;
  font-size: 1.2rem
}

.sidebar a {
  pointer-events: auto;
  /* Garantir que o link seja clicável */
  position: relative;
  z-index: 11;
  /* Certificar que está acima de outros elementos */
}

.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
  width: 100%;
  /* Preenche a largura para evitar conflitos */
}

.sidebar li {
  width: 100%;
  /* Certifique-se de que o item ocupa toda a largura */
  text-align: center;
  padding: 10px 0;
}

.overlay {
  z-index: 9;
  /* Certifique-se de que o overlay está abaixo da sidebar */
}

.sidebar {
  z-index: 10;
  /* Sidebar acima do overlay */
}
</style>