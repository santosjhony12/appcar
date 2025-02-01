interface AluguelCadastro{
    dataInicio: string, 
    dataFim: string,
    idCarro: number, 
    idUsuario: number,
    valor: number
}
import api from './api';
import { useAuthStore } from '@/stores/token';

class AluguelService {
    async createAluguel (aluguel : AluguelCadastro){
        try{
            const response = await api.post('/alugueis', aluguel);

            if(response.status == 201){
                return "Solicitação de aluguel enviada com sucesso. Aguarde a aprovação no máximo de 1 dia."
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
            }
        }catch(error : any){
            return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
        }
    }

    async verificarDisponibilidade (aluguel : AluguelCadastro){
        try{
            const response = await api.post('/alugueis/disponibilidade', aluguel);

            if(response.status == 200){
                return "Veículo indisponível na data selecionada."
            }else if(response.status == 404){
                return "Veículo disponível na data selecionada.";
            }
        }catch(error : any){

            if(error.status == 404){
                return "Veículo disponível na data selecionada.";
            }
            return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
        }
    }

    async getAlugueisNaoAprovados(){
        try{
            const response = await api.get('/alugueis/aprovar?autorizado=false');

            if(response.status == 200){
                return response.data
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
            }
        }catch(error : any){
            return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
        }
    }

    async getAlugueisAprovados(){
        try{
            const response = await api.get('/alugueis/aprovar?autorizado=true');

            if(response.status == 200){
                return response.data
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
            }
        }catch(error : any){
            return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
        }
    }

    async autorizarAluguel(id: number){
        try{
            const response = await api.patch(`/alugueis/aprovar?id=${id}`);
            console.log(response.status)
            if(response.status == 200){
                return "Autorizado com sucesso!";
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
            }
        }catch(error : any){
            return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
        }
    }

    async getAlugueisPorUsuarioLogado(){
        try{
            const token = useAuthStore();
            const response = await api.get(`/alugueis/aprovacao`);
        
            if(response.status == 200){
                return response.data;
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
            }
        }catch(error : any){
            return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
        }
    }

    async deleteAluguel(id: number){
        try{
            const response = await api.delete(`/alugueis/${id}`);
        
            if(response.status == 200){
                return "Aluguel cancelado com sucesso!";
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
            }
        }catch(error : any){
            return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
        }
    }

    async getAlugueisPorLocador(){
        try{
            const token = useAuthStore();
            const response = await api.get(`/alugueis/veiculos-alugados/locador`);
        
            if(response.status == 200){
                return response.data;
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
            }
        }catch(error : any){
            return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
        }
    }

    async getDadosParaDash(){
        try{
            const token = useAuthStore();
            const response = await api.get(`/alugueis/dados-dash-investidor`);
        
            if(response.status == 200){
                return response.data;
            }else{
                return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
            }
        }catch(error : any){
            return "Alguma coisa não ocorreu bem. Entre em contato com o administrador.";
        }
    }
}

export default new AluguelService;