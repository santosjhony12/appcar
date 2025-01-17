// stores/authStore.ts
import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null, // Obtém o token do localStorage, se existir
  }),
  actions: {
    setToken(token: string) {
      this.token = token;
      localStorage.setItem('token', token); // Armazena o token no localStorage
    },
    removeToken() {
      this.token = null;
      localStorage.removeItem('token'); // Remove o token do localStorage
    },
  },
  getters: {
    isAuthenticated: (state) => !!state.token, // Verifica se há token (autenticado ou não)
  },
});
