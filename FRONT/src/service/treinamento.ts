import { useAuthStore } from "@/stores/token";
import { usuarioLogado } from "@/stores/usuario";
import axios from "axios";

class Treinamento {
    async cadastrar(treinamento: {titulo: string, link: string, role: string}){
        try{
            const response = await axios.post("http://localhost:8080/api/v1/treinamentos", treinamento);

            if(response.status === 201){
                return "Treinamento registrado com sucesso!";
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
            }
        }catch(error: any){
            return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
        }
    }

    async getAll(){
        try{
            const response = await axios.get("http://localhost:8080/api/v1/treinamentos");

            if(response.status === 200){
                return response.data;
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
            }
        }catch(error: any){
            return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
        }
    }

    async deleteById(id: number){
        try{
            const response = await axios.delete(`http://localhost:8080/api/v1/treinamentos/${id}`);

            if(response.status === 204){
                return "Treinamento excluído com sucesso!";
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
            }
        }catch(error: any){
            return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
        }
    }

    async editar(treinamento: {id: number, titulo: string, link: string, role: string}){
        try{
            const response = await axios.put("http://localhost:8080/api/v1/treinamentos", treinamento);

            if(response.status === 200){
                return "Informações do treinamento atualizado com sucesso!";
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
            }
        }catch(error: any){
            return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
        }
    }
    
    async getTreinamentosByRoleLogado(){
        try{
            const usuario = usuarioLogado()
            const response = await axios.get(`http://localhost:8080/api/v1/treinamentos/role?role=ROLE_${usuario.usuario?.role}`);

            if(response.status === 200){
                return response.data;
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
            }
        }catch(error: any){
            return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
        }
    }

    async getComentariosByTreinamento(id: number){
        try{
            
            const response = await axios.get(`http://localhost:8080/api/v1/comentarios/treinamento?id=${id}`);

            if(response.status === 200){
                return response.data;
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
            }
        }catch(error: any){
            return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
        }
    }

    async createComment(comentario : {username: string, idTreinamento: number, comentario: string}){
        try{
            
            const response = await axios.post(`http://localhost:8080/api/v1/comentarios`, comentario);

            if(response.status === 201){
                return "Comentário adicionado!";
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
            }
        }catch(error: any){
            return "Alguma coisa não ocorreu bem. Entre em contato com seu administrador.";
        }
    }
}

export default new Treinamento;