<template>
  <div class="navbar">
    <div class="nav-content">
      <h1 class="logo">AppCar</h1>

      <!-- Navbar com links visíveis no desktop -->
      <div class="nav-links" v-if="!isMobile">
        <RouterLink to="/about">Sobre nós</RouterLink>
        <RouterLink to="#services" >Meus dados</RouterLink>
        <RouterLink to="/meusVeiculos" v-if="usuarioLog.usuario?.role === 'INVESTIDOR'">Meus veículos</RouterLink>
        <RouterLink to="/usuarios" v-if="usuarioLog.usuario?.role === 'ADMIN'" >Meus usuários</RouterLink>
        <RouterLink to="#services" v-if="usuarioLog.usuario?.role !== 'ADMIN' && usuarioLog.usuario?.role != ''"  >Meus aluguéis</RouterLink>
        <RouterLink to="#services" v-if="usuarioLog.usuario?.role === 'MOTORISTA'" >Alugar carro</RouterLink>
        <RouterLink to="#services" v-if="usuarioLog.usuario?.role !== 'ADMIN' && (usuarioLog.usuario?.role != '' || usuarioLog.usuario != null) " >Treinamentos</RouterLink>
        <RouterLink @click="logout" to="">Sair</RouterLink>
      </div>

      <!-- Botão hamburguer no mobile -->
      <button class="hamburger" @click="toggleDropdown" v-if="isMobile">
        <span></span>
        <span></span>
        <span></span>
      </button>
    </div>

    <!-- Dropdown para mobile -->
    <div :class="['dropdown', { open: isDropdownOpen }]">
      <ul>
        <li>
          <RouterLink to="/about">Sobre nós</RouterLink>
        </li>
        <li><a href="#services">Meus dados</a></li>
        <li><a href="#services">Meus veículos</a></li>
        <li><a href="#services">Meus aluguéis</a></li>
        <li><a href="#services">Sair</a></li>
      </ul>
    </div>
  </div>
</template>


<script lang="ts" setup>
import { useAuthStore } from '@/stores/token';
import { usuarioLogado } from '@/stores/usuario';
import { ref, watch, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import router from '@/router';
const isDropdownOpen = ref(false);
const isMobile = ref(false);
const route = useRoute();
const usuarioLog = usuarioLogado();
const token = useAuthStore();
const logout = () => {
  usuarioLog.setUsuario(null);
  token.setToken('');
  router.push("/login")
}

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};

// Fecha o dropdown ao mudar de rota
watch(() => route.fullPath, () => {
  isDropdownOpen.value = false;
});

// Detecta se a tela é mobile
const checkMobile = () => {
  isMobile.value = window.innerWidth <= 768;
};

onMounted(() => {
  checkMobile();
  window.addEventListener('resize', checkMobile); // Atualiza ao redimensionar
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
 /* position: fixed;*/
  width: 100%;
  z-index: 10;
  padding: 0.8em 1em;
}

.nav-content {
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: space-between;
}

.logo {
  font-weight: 100;
  font-size: 1.2rem;
}

/* Navbar links no desktop */
.nav-links {
  display: flex;
  gap: 1.5em;
}

.nav-links a {
  text-decoration: none;
  color: white;
  font-size: 1em;
}

.nav-links a:hover {
  color: #b1b1b1;
}

/* Botão hamburguer */
.hamburger {
  background: none;
  border: none;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  gap: 5px;
  margin-left: auto;
}

.hamburger span {
  display: block;
  width: 25px;
  height: 3px;
  background-color: white;
  border-radius: 3px;
  transition: 0.3s;
}

/* Dropdown */
.dropdown {
  position: absolute;
  top: 100%;
  right: 1em;
  background-color: rgb(255, 255, 255);
  color: black !important;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  display: none;
  flex-direction: column;
  width: 200px;
  padding: 1em 0;
}

.dropdown.open {
  display: flex;
}

.dropdown ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.dropdown li {
  text-align: center;
  padding: 10px 0;
}

.dropdown a {
  text-decoration: none;
  color: black;
  font-size: 1em;
  transition: color 0.3s;
}

.dropdown li:hover {
  background-color: #b1b1b1;
  color: black;
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

/* Para telas grandes (desktop) */
@media (min-width: 768px) {
  .hamburger {
    display: none;
  }

  .dropdown {
    display: none;
  }
}

/* Para telas pequenas (mobile) */
@media (max-width: 768px) {
  .nav-links {
    display: none;
  }
}
</style>