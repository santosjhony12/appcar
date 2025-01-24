<template>
  <!-- <div class="navbar">
    <div class="nav-content"> -->
      <div class="navbar">
                <div class="image">
                    <img src="../assets/logo_app.png" alt="" class="logo">
                </div>
                <div class="options">
                    <RouterLink class="links" to="/contato">Sobre nós</RouterLink>
                    <RouterLink class="links" to="/contato" v-if="usuarioLog.usuario == null" >Contate-nos</RouterLink>
                    <!-- <RouterLink to="#services" >Meus dados</RouterLink> -->
                    <RouterLink class="links"  to="/meusVeiculos" v-if="usuarioLog.usuario.role === 'INVESTIDOR'">Meus veículos</RouterLink>
                    <RouterLink class="links" to="/usuarios" v-if="usuarioLog.usuario.role === 'ADMIN'" >Meus usuários</RouterLink>
                    <RouterLink class="links" to="/solicitacoesVeiculos" v-if="usuarioLog.usuario.role === 'ADMIN'">Solicitações de Veículos</RouterLink>
                    <!-- <RouterLink to="#services" v-if="usuarioLog.usuario?.role !== 'ADMIN' && usuarioLog.usuario?.role != ''"  >Meus aluguéis</RouterLink> -->
                    <RouterLink class="links" to="/alugar" v-if="usuarioLog.usuario.role === 'MOTORISTA'" >Alugar carro</RouterLink>
                    <!-- <RouterLink to="#services" v-if="usuarioLog.usuario?.role !== 'ADMIN' && (usuarioLog.usuario?.role != '' || usuarioLog.usuario != null) " >Treinamentos</RouterLink> -->
                    
                    <RouterLink class="links login" to="/login" v-if="usuarioLog.usuario == null">Entrar</RouterLink>
                    <RouterLink class="links login" to="/login" v-if="usuarioLog.usuario != null">Sair</RouterLink>


                </div>
            </div>
      <!-- <h1 class="logo">AppCar</h1> -->

      <!-- Navbar com links visíveis no desktop -->
      <!-- <div class="nav-links" v-if="!isMobile">

        
        <RouterLink @click="logout" to="">Sair</RouterLink>
      </div>

        Botão hamburguer no mobile
      <button class="hamburger" @click="toggleDropdown" v-if="isMobile">
        <span></span>
        <span></span>
        <span></span>
      </button> -->
    <!-- </div>

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
  </div> -->
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


.navbar {
    display: flex;
    padding: 2em 4em;
    justify-content: space-between;
    background-image: url('../assets/fundo.jpg');
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    background-color: rgba(0, 0, 0, 0.85); /* Fundo mais escuro */
    background-blend-mode: darken;
    height: 12vh
}
.login {
    background: white;
    color: #000 !important;
    padding: 0.3em 2em;
    border-radius: 1em;
}
.links {
    text-decoration: none;
    color: white;
    cursor: pointer;
    margin: 0 1em;
    display: inline-block;
    /* Garante que transform funcione corretamente */
    text-decoration: none;
    /* Remove o sublinhado, opcional */
    font-size: 16px;
    /* Tamanho inicial do texto */
    transition: transform 0.3s ease, color 0.3s ease;
}
.logo {
    width: 8vw;
    margin-top: -2.5em;
}

.links:hover {
    color: yellow;
    /* Muda a cor do link para amarelo ao passar o mouse */
    transform: scale(1.2);
    /* Aumenta o tamanho em 20% */
}

.login:hover {
    color: black
}
@media (max-width: 1000px) {
    .logo {
        width: 20vw;
        margin-top: -3.2em
    }

    .navbar {
        padding: 2em 2em;
    }

    .links {
        font-size: 14px
    }

}
@media (max-width: 700px){
    .logo{
        margin-top: -1.8em
    }
    .links{
        font-size: 11px;
        margin: 0 0.3em!important;
    }
   
}
/* Navbar */
/* .navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: black;
  color: white;

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

.hamburger.open span:nth-child(1) {
  transform: rotate(45deg) translate(5px, 5px);
}

.hamburger.open span:nth-child(2) {
  opacity: 0;
}

.hamburger.open span:nth-child(3) {
  transform: rotate(-45deg) translate(5px, -5px);
}


@media (min-width: 768px) {
  .hamburger {
    display: none;
  }

  .dropdown {
    display: none;
  }
}


@media (max-width: 768px) {
  .nav-links {
    display: none;
  }
} */
</style>