<template>
  <div class="container-nav">

    <div class="navbar">
      <font-awesome-icon :icon="['fas', 'user']" class="icon" />
      <h3>{{ usuarioLog.usuario?.nome }}</h3>
    </div>
    <div class="nav-lateral">
      <div class="box-nav">
        <div class="item-nav">
          <RouterLink class="links" to="/contato" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'users']" />
            <h5>Sobre</h5>
          </RouterLink>
        </div>
        <div class="item-nav" v-if="usuarioLog.usuario?.role === 'ADMIN'">
          <RouterLink class="links" to="/usuarios" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'users']" />
            <h5>Meus usuários</h5>
          </RouterLink>
        </div>
        <div class="item-nav" v-if="usuarioLog.usuario?.role !== 'ADMIN'">
          <RouterLink class="links" to="/videos-treinamentos" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'book']" />
            <h5>Estudos</h5>
          </RouterLink>
        </div>


        <div class="item-nav" v-if="usuarioLog.usuario?.role === 'MOTORISTA'">
          <RouterLink class="links" to="/alugar" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'car']" />
            <h5>Alugar</h5>
          </RouterLink>
        </div>

        <div class="item-nav" v-if="usuarioLog.usuario?.role === 'MOTORISTA'">
          <RouterLink class="links" to="/meusAlugueis" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'money-bill']" />
            <h5>Meus Aluguéis</h5>
          </RouterLink>
        </div>



        <div class="item-nav" v-if="usuarioLog.usuario?.role === 'INVESTIDOR'">
          <RouterLink class="links" to="/meusVeiculos" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'car']" />
            <h5>Meus Veículos</h5>
          </RouterLink>
        </div>

        <div class="item-nav" v-if="usuarioLog.usuario?.role === 'INVESTIDOR'">
          <RouterLink class="links" to="/projecaoAlugueis" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'money-bill']" />
            <h5>Alugados</h5>
          </RouterLink>
        </div>

        <div class="item-nav" v-if="usuarioLog.usuario?.role === 'ADMIN'">
          <RouterLink class="links" to="/solicitacoesVeiculos" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'car']" />
            <h5>Solicitações Veículos</h5>
          </RouterLink>
        </div>

        <div class="item-nav" v-if="usuarioLog.usuario?.role === 'ADMIN'">
          <RouterLink class="links" to="/aprovarAluguel" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'money-bill']" />
            <h5>Aprovar Aluguéis</h5>
          </RouterLink>
        </div>

        <div class="item-nav" v-if="usuarioLog.usuario?.role === 'ADMIN'">
          <RouterLink class="links" to="/alugueisAprovados" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'money-bill']" />
            <h5>Aluguéis Aprovados</h5>
          </RouterLink>
        </div>

        <div class="item-nav" v-if="usuarioLog.usuario?.role === 'ADMIN'">
          <RouterLink class="links" to="/treinamentosAdmin" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'book']" />
            <h5>Treinamentos</h5>
          </RouterLink>
        </div>



        <div class="item-nav">
          <RouterLink class="links" to="/login" active-class="active-link">
            <font-awesome-icon :icon="['fas', 'right-from-bracket']" />
            <h5>Sair</h5>
          </RouterLink>
        </div>
      </div>

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

watch(() => route.fullPath, () => {
  isDropdownOpen.value = false;
});

const checkMobile = () => {
  isMobile.value = window.innerWidth <= 768;
};

onMounted(() => {
  checkMobile();
  window.addEventListener('resize', checkMobile);
});

</script>
<style scoped>
.navbar {
  display: flex;
  position: fixed;
  width: 100vw;
  align-items: center;
  justify-content: end;
  height: 5vh;
  padding: 0 3vw;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  background-color: white;
  color: #007bff
}

.icon {
  font-size: 15px;
  margin-right: 1vw;
}

h5 {
  text-align: center;
  font-size: 12px;
}

.nav-lateral {
  box-shadow: 0 4px 6px rgb(161 161 161);
  height: 100vh;
  width: 3vw;
  position: fixed;
  z-index: 101;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: rgb(245, 245, 245);
  padding: 40px 0 0 0;
}

.login {
  background: white;
  color: #000 !important;
  padding: 0.3em 2em;
  border-radius: 1em;
}

.links {
  text-decoration: none;
  color: gray;
  cursor: pointer;
  display: inline-block;
  text-decoration: none;
  font-size: 15px;
  transition: transform 0.3s ease, color 0.3s ease;
  display: flex;
  flex-direction: column;

}

.logo {
  width: 8vw;
  margin-top: -2.5em;
}

.item-nav:hover {
  transform: scale(1.2);
}

.login:hover {
  color: black
}

.item-nav {
  padding:1.5vh 0;
  cursor: pointer
}

.active-link {
  color: #007bff
}

@media (max-width: 1000px) {
  .nav-lateral {
    width: 17vw;
  }

  .navbar {
    padding: 0 10vw;
  }

}
.box-nav{
  background-color: white;
  border-radius: 3px;
}
@media (max-width: 700px) {}
</style>