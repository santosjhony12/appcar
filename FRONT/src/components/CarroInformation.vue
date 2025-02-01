<template>
    <div class="container-carro container-titulo-principal" >
        <div class="imagem-carro">
            <img :src="props.imagem" alt="" class="imagem">
            
            <h2 class="valor"> {{ formatarParaReal(valorAluguel) }}</h2>
        </div>

        <div class="tabela-informacoes">
            <div class="information">
                <div class="info">
                    <span>Modelo:</span>
                    <p>{{ modelo }}</p>
                </div>
                <div class="info">
                    <span>Montadora:</span>
                    <p>{{ montadora }}</p>
                </div>
                <div class="info">
                    <span>Ano:</span>
                    <p>{{ ano }}</p>
                </div>
                <div class="info">
                    <span>Cor:</span>
                    <p>{{ cor }}</p>
                </div>

                <Button @click="alugar" :text="'Alugar'" :is-loading="false" class="button" />

            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import router from '@/router';
import {usuarioLogado} from '@/stores/usuario';
import Button
 from './Button.vue';
const props = defineProps<{
    id: number,
    modelo: string,
    montadora: string, 
    ano: number, 
    cor: string,
    valorAluguel: number,
    imagem: string
}>();
function formatarParaReal(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL',
    }).format(valor);
}
const UsuarioLog = usuarioLogado();
const emit = defineEmits(['alugar']);
const alugar = () => {
    if(UsuarioLog.usuario == null || !UsuarioLog.usuario){
        router.push('/login');
    }else{
        emit("alugar", props.id, props.imagem, props.valorAluguel);
    }
}


</script>

<style scoped>
.valor{
    color: green;
    font-size: 2em;
    font-weight: bold;
    text-align: center;
}
@media (max-width: 1000px){
    .container-carro{
        flex-direction: column;
        
    }
    .information{
        width: 80vw !important;
    }
}
.imagem{
    max-width: 30vw;
    max-height: 30vh;
}
.button{
    margin-top: 2vh;
}
.container-carro{
    display: flex;
    gap: 20px;
    justify-content: center;
    align-items: center;
}
.information{
    display: flex;
    flex-direction: column;
    justify-content: center;

    padding: 3em;
    font-size: 1.2em;
    width: 60vh;

  
}
.info{
    display: flex;
    justify-content: space-between;
}
span{
    font-weight: bold;
}
.imagem-carro{
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 30vh;
    width: 30vw;
    justify-content: center;
}

button:hover{
    transform: scale(1.1);
}
</style>