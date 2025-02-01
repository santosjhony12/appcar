import { useAuthStore } from '@/stores/token'
import { usuarioLogado } from "@/stores/usuario"
import api from './api'
interface User {
    id: number | null,
    username: string,
    password: string,
    cpf: string | null,
    nome: string | null, 
    dataNascimento: string | null, 
    primeiroAcesso: boolean | null,
    role: string,
    telefone: string
}
const StoreToken = useAuthStore();
const UsuarioStore = usuarioLogado();
class UsuarioService {
    

    async autenticar(credenciais : {username: string, password: string}){
        try{
            
            const response = await api.post("/auth", credenciais);
            console.log(response);
            if(response.status == 200){
                StoreToken.setToken(response.data.token.token)
                UsuarioStore.setUsuario(response.data.usuarioResponseDto)
                return true;
            }else{
                return "Credenciais inválidas.";
            }
        }catch(error : any){
            return "Credenciais inválidas.";
        }
    }
    
    async cadastrarUsuario(user: User){
        try{
            const response = await api.post("/usuarios", user);

            if(response.status == 201){
                return "Usuário cadastrado com sucesso.";
            }else{
                return "Houve algum problema no cadastro. Entre em contato com o administrador do sistema.";
            }
        }catch(error : any){
            if(error.status == 409){
                return "Já existe um usuário com esse e-mail.";
            }
            throw new Error;
        }
    }
    async getAll(){
        try{
            const response = await api.get("/usuarios");

            if(response.status == 200){
                return response.data;
            }else{
                return "Houve algum problema. Contate o administrador.";
            }
        }catch(error: any){
            return "Houve algum problema. Contate o administrador.";
        }
    }

    async updateRole(user : {id: number | null, role: string}){
        try{
            const response = await api.put("/usuarios/updateRole", user);

            if(response.status == 200){
                return "Usuário atualizado com sucesso.";
            }else{
                return "Houve algum problema. Contate o administrador.";
            }
        }catch(error: any){
            return "Houve algum problema. Contate o administrador.";
        }
    }
    async deleteUsuario(id: number | null){
        try{
            const response = await api.delete(`/usuarios/${id}`);

            if(response.status == 200){
                return "Usuário deletado com sucesso.";
            }else{
                return "Houve algum problema. Contate o administrador.";
            }
        }catch(error: any){
            return "Houve algum problema. Contate o administrador.";
        }
    }
    async confirmarUsuario(user: User){
        try{
            const response = await api.post(`/usuarios/updatePrimeiroAcesso`, user);

            if(response.status == 200){
                return true;
            }else{
                return "Houve algum problema. Contate o administrador.";
            }
        }catch(error: any){
            return "Houve algum problema. Contate o administrador.";
        }
    }

    async resetSenha(id: number, senha: string){
        try{
            const response = await api.put(`/usuarios/resetSenha?id=${id}&senha=${senha}`);

            if(response.status == 204){
                return "Senha atualizada com sucesso";
            }else{
                return "Houve algum problema. Contate o administrador.";
            }
        }catch(error: any){
            return "Houve algum problema. Contate o administrador.";
        }
    }

    async enviarEmaildeReset(email: string){
        try{
            const response = await api.put(`/usuarios/enviarEmailReset?email=${email}`);

            if(response.status == 204){
                return "Enviamos um e-mail para sua caixa.";
            }else{
                return "Houve algum problema. Contate o administrador.";
            }
        }catch(error: any){
            return error.message;
        }
    }
}


export default new UsuarioService;