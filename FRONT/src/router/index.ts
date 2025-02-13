import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    }, 
    {
      path: "/usuarios",
      name: "usuarios",
      component: () => import("../views/UsuariosView.vue")
    },
    {
      path: "/",
      name: "login",
      component: () => import("../views/LoginView.vue")
    },
    {
      path: "/primeiroAcesso",
      name: "primeiroAcesso",
      component: () => import("@/views/PrimeiroAcessoView.vue")
    },
    {
      path: "/meusVeiculos",
      name: "meusVeiculos",
      component: () => import("@/views/MeusVeiculosView.vue")
    },
    {
      path: "/solicitacoesVeiculos",
      name: "solicitacoesVeiculos",
      component: () => import("@/views/SolicitacoesVeiculos.vue")
    },
    {
      path: "/alugar",
      name: "alugar",
      component: () => import("@/views/AlugarVeiculoView.vue")
    },
    {
      path: "/aprovarAluguel",
      name: "aprovarAluguel",
      component: () => import("@/views/AprovarAluguel.vue")
    },
    {
      path: "/alugueisAprovados",
      name: "alugueisAprovados",
      component: () => import("@/views/AlugueisAprovados.vue")
    },
    {
      path: "/meusAlugueis",
      name: "meusAlugueis",
      component: () => import("@/views/AlugueisMotorista.vue")
    },
    {
      path: "/projecaoAlugueis",
      name: "projecaoAlugueis",
      component: () => import("@/views/VeiculosAlugadosInvestidorView.vue")
    },
    {
      path: "/treinamentosAdmin",
      name: "treinamentosAdmin",
      component: () => import("@/views/TreinamentosAdminView.vue")
    },
    {
      path: "/videos-treinamentos",
      name: "videos-treinamentos",
      component: () => import("@/views/TreinamentosVideosView.vue")
    },
    {
      path: "/contato",
      name: "contato",
      component: () => import("@/views/ContatoView.vue")
    },
    {
      path: "/definir-nova-senha",
      name: "definirSenha",
      component: () => import("@/views/EsqueceuSenhaView.vue")
    },
    {
      path: "/redefinir-senha",
      name: "esqueciSenha",
      component: () => import("@/views/EnviarEmailNovaSenhaView.vue")
    }
  ]
})

export default router
