import axios from "axios"
import { useAuthStore } from '@/stores/token'
import { usuarioLogado } from "@/stores/usuario"
interface User {
    id: number | null,
    username: string,
    password: string,
    cpf: string | null,
    nome: string | null, 
    dataNascimento: string | null, 
    primeiroAcesso: boolean | null,
    role: string
}
const StoreToken = useAuthStore();
const UsuarioStore = usuarioLogado();
class UsuarioService {
    

    async autenticar(credenciais : {username: string, password: string}){
        try{
            const response = await axios.post("http://localhost:8080/api/v1/auth", credenciais);
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
            const response = await axios.post("http://localhost:8080/api/v1/usuarios", user);

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
            const response = await axios.get("http://localhost:8080/api/v1/usuarios");

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
            const response = await axios.put("http://localhost:8080/api/v1/usuarios/updateRole", user);

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
            const response = await axios.delete(`http://localhost:8080/api/v1/usuarios/${id}`);

            if(response.status == 200){
                return "Usuário deletado com sucesso.";
            }else{
                return "Houve algum problema. Contate o administrador.";
            }
        }catch(error: any){
            return "Houve algum problema. Contate o administrador.";
        }
    }
}


export default new UsuarioService;