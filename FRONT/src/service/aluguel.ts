interface AluguelCadastro{
    dataInicio: string, 
    dataFim: string,
    idCarro: number, 
    idUsuario: number
}
import axios from 'axios';

class AluguelService {
    async createAluguel (aluguel : AluguelCadastro){
        try{
            const response = await axios.post('http://localhost:8080/api/v1/alugueis', aluguel);

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
            const response = await axios.post('http://localhost:8080/api/v1/alugueis/disponibilidade', aluguel);

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
}

export default new AluguelService;