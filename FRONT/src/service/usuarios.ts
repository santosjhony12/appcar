import axios from "axios"

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

class UsuarioService {
    
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
}

export default new UsuarioService;