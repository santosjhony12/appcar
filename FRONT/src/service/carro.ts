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

class CarroService{
    async cadastrarCarro(carro: Carro){
        try{
            const response = await axios.post("http://localhost:8080/api/v1/cars/create", carro);

            if(response.status == 201){
                console.info("Cadastrado com sucesso")
            }else{
                console.warn("Houve algum erro.");
            }
        }catch(error){
            throw new Error;
        }
    }
}
export default new CarroService;