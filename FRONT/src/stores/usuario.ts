// stores/authStore.ts
import { defineStore } from 'pinia';

// Definição da interface User
interface User {
  id: number | null;
  username: string;
  password: string;
  cpf: string | null;
  nome: string | null;
  dataNascimento: string | null;
  primeiroAcesso: boolean | null;
  role: string;
}

// Definição do store usando Pinia
export const usuarioLogado = defineStore('usuarioLogado', {
  state: () => ({
    usuario: JSON.parse(localStorage.getItem('usuario') || 'null') as User | null,
  }),
  actions: {
    setUsuario(usuario: User) {
      this.usuario = usuario;
      // Armazena o objeto serializado como string no localStorage
      localStorage.setItem('usuario', JSON.stringify(usuario));
    },
    removeToken() {
      this.usuario = null;
      localStorage.removeItem('usuario');
    },
  },
});
