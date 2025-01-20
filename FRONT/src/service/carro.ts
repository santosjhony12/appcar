import { useAuthStore } from "@/stores/token";
import { usuarioLogado } from "@/stores/usuario";
import axios from "axios";
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
            const response = await axios.post("http://localhost:8080/api/v1/cars/create", carro,
                {
                    headers: {
                      Authorization: `Bearer ${token.token}` 
                    }
                  }
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
            const response = await axios.get(`http://localhost:8080/api/v1/cars/username?username=${userLogado.usuario?.username}`);

            if(response.status == 200){
                return response.data;
            }else{
                console.warn("Houve algum erro.");
            }
        }catch(error){
            throw new Error;
        }
    }
}
export default new CarroService;