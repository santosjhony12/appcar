// stores/authStore.ts
import { defineStore } from 'pinia';

export const usuarioLogado = defineStore('usuarioLogado', {
  state: () => ({
    usuario: localStorage.getItem('usuario') || null,
  }),
  actions: {
    setUsuario(usuario: string) {
      this.usuario = usuario;
      localStorage.setItem('usuario', usuario);
    },
    removeToken() {
      this.usuario = null;
      localStorage.removeItem('usuario');
    },
  }
});
