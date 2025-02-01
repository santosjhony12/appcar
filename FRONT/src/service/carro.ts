import { useAuthStore } from "@/stores/token";
import { usuarioLogado } from "@/stores/usuario";
import api from "./api";
interface Carro{
    montadora: string;
    modelo: string;
    cor: string;
    ano: string;
    placa: string;
    valor: string;
    imagemBase64: string | null;
  }
const token = useAuthStore();
const userLogado = usuarioLogado()
class CarroService{
    async cadastrarCarro(carro: Carro){
        try{
            const response = await api.post("/cars/create", carro
            );

            if(response.status == 201){
                console.info("Cadastrado com sucesso")
            }else{
                console.warn("Houve algum erro.");
            }
        }catch(error){
            throw new Error;
        }
    }

    async getCarrosByUsername(){
        try{
            const response = await api.get(`/cars/username?username=${userLogado.usuario?.username}`);

            if(response.status == 200){
                return response.data;
            }else{
                console.warn("Houve algum erro.");
            }
        }catch(error){
            throw new Error;
        }
    }
    async getCarrosNaoAutorizados(){
        try{
            const response = await api.get(`/cars/nao-autorizados`);

            if(response.status == 200){
                return response.data;
            }else{
                console.warn("Houve algum erro.");
            }
        }catch(error){
            throw new Error;
        }
    }

    async getCarrosAutorizados(){
        try{
            const response = await api.get(`/cars/veiculos-autorizados`);

            if(response.status == 200){
                return response.data;
            }else{
                console.warn("Houve algum erro.");
            }
        }catch(error){
            throw new Error;
        }
    }


    async autorizarVeiculo(autorizar: {id: number, valorAluguel: string, percentualInvestidor: string, percentualSistema: string, autorizado: boolean}){
        try{
            const response = await api.put(`/cars/autorizar`, autorizar);

            if(response.status == 200){
                return "Dados gravados com sucesso!";
            }else{
                return "Houve algum problema. Contate o administrador.";
            }
        }catch(error){
            return "Houve algum problema. Contate o administrador.";
        }
    }
    async getAllVeiculos(){
        try{
            const response = await api.get(`/cars`);

            if(response.status == 200){
                return response.data;
            }else{
                return "Houve algum problema. Contate o administrador.";
            }
        }catch(error){
            return "Houve algum problema. Contate o administrador.";
        }
    }


}
export default new CarroService;