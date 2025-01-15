<template>
    <div class="container">
        <h2>Vocabulos</h2>

        <h3>Cadastrar / Buscar vocabulo</h3>
        <h3 v-if="showMessage">{{ message }}</h3>
        <div class="forms-cadastro">

            <label for="termo">Termo</label>
            <input type="text" name="termo" id="termo" v-model="termo">

            <label for="significado">Significado</label>
            <input type="text" name="significado" id="significado" v-model="significado">

            <label for="versao">Versão</label>
            <input type="number" name="versao" id="versao" v-model="versao">

            <button @click="cadastrarVocabulo">Cadastrar</button>
            <button @click="getByTermoAndVersao">Buscar</button>
            <button @click="limpar">Limpar</button>

        </div>
        
        <div v-if="vocabulos.length > 0">
            <table>
                <tr>
                    <th>Id</th>
                    <th>Termo</th>
                    <th>Significado</th>
                    <th>Versão</th>
                    <th>Situação</th>
                </tr>
                <tr v-for="vocabulo of vocabulos" :key="vocabulo.id">
                    <td>{{ vocabulo.id }}</td>
                    <td>{{ vocabulo.termo }}</td>
                    <td>{{ vocabulo.significado }}</td>
                    <td>{{ vocabulo.versao }}</td>
                    <td v-if="vocabulo.dataHoraDesativacao == null">Ativo</td>
                    <td v-else>Desativado</td>
                </tr>
                
            </table>
        </div>
        <div v-else>
            <h3>Sem vocabulos cadastrados</h3>
        </div>
        
    </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { onMounted, ref } from 'vue';

const vocabulos = ref<Vocabulo[]>([]);
const showMessage = ref<boolean>(false);
const message = ref<string>('');
const termo = ref<string>('');
const significado = ref<string>('');
const versao = ref<number | null>(null);
const vocabuloBuscado = ref<Vocabulo>();

interface Vocabulo {
    id: number,
    termo: string,
    significado: string,
    versao: number,
    daraHoraCadastro: string,
    dataHoraDesativacao: string
}

const limpar = async () => {
    await getVocabulos();
}
const cadastrarVocabulo = async () => {
    const newVocabulo = {
        termo: termo.value,
        significado: significado.value,
        versao: versao.value
    }

    if (newVocabulo.termo == '' || newVocabulo.significado == '' || newVocabulo.versao == null){
        message.value = 'Os campos termo, significado e versão não podem ser nulos';
        showMessage.value = true;
        setTimeout(()=>{
            showMessage.value = false;
            message.value = '';
        }, 3000);

    }else{
        await postVocabulo(newVocabulo);
    }
}

const getByTermoAndVersao = async () => {
    try{
        if (termo.value == '' || versao.value == null){
        message.value = 'Os campos termo,  e versão não podem ser nulos';
        showMessage.value = true;
        setTimeout(()=>{
            showMessage.value = false;
            message.value = '';
        }, 3000);

        }else{
            const response = await axios.get(`http://localhost:8080/vocabulo/${termo.value}/${versao.value}`);
            if(response.status == 200){
                significado.value = '';
                termo.value = '';
                versao.value = null;
                vocabulos.value = response.data;

            }else{
                throw new Error("Erro");
            }
        }
        
    }catch(error){
        throw new Error("Houve algum erro.");
    }
}

const getVocabulos = async () =>{
    try{
        const response = await axios.get('http://localhost:8080/vocabulo');
        if(response.status == 200){
            vocabulos.value = response.data;
        }else{
            throw new Error("Erro");
        }
    }catch(error){
        throw new Error("Houve algum erro.");
    }
    
}

const postVocabulo = async (body : any) =>{
    try{
        const response = await axios.post('http://localhost:8080/vocabulo', body);

        if(response.status == 200){
            significado.value = '';
            termo.value = '';
            versao.value = null;
            getVocabulos();
            message.value = 'Vocabulo Cadastrado com sucesso!';
            showMessage.value = true;
            setTimeout(()=>{
                showMessage.value = false;
                message.value = '';
            }, 3000);
        }else{
            message.value = 'Alguma coisa ocorreu de errado!';
            showMessage.value = true;
            setTimeout(()=>{
                showMessage.value = false;
                message.value = '';
            }, 3000);
        }
    }catch(error){
        console.log(error)
    }
}
onMounted( async () =>{
    await getVocabulos();
});

</script>

<style scoped>
.forms-cadastro{
    display: flex;
    justify-content: center;
    flex-direction: column;
}
button{
    margin: 2% 0;
    cursor: pointer;
}
button:hover{
    background-color: antiquewhite;
}
</style>