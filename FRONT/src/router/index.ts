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
    },{
      path: "/cadastrarCarro",
      name: "cadastroCarro",
      component: () => import("../views/CadastroCarro.vue")
    }, 
    {
      path: "/usuarios",
      name: "usuarios",
      component: () => import("../views/UsuariosView.vue")
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../views/LoginView.vue")
    }
  ]
})

export default router
