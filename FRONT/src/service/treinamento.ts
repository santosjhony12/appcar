import { useAuthStore } from "@/stores/token";
import { usuarioLogado } from "@/stores/usuario";
import api from "./api";

class Treinamento {
    async cadastrar(treinamento: {titulo: string, link: string, role: string}){
        try{
            const response = await api.post("/treinamentos", treinamento);

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
            const response = await api.get("/treinamentos");

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
            const response = await api.delete(`/treinamentos/${id}`);

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
            const response = await api.put("/treinamentos", treinamento);

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
            const response = await api.get(`/treinamentos/role?role=${usuario.usuario?.role}`);

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
            
            const response = await api.get(`/comentarios/treinamento?id=${id}`);

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
            
            const response = await api.post(`/comentarios`, comentario);

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