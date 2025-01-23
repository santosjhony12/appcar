<template>
    <div class="container-carro">
        <div class="imagem-carro">
            <img src="https://www.localiza.com/brasil-site/geral/Frota/HAVA.png" alt="">

            <h2 class="valor">R$ {{ valor }}</h2>
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

                <button>ALUGAR</button>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>

const props = defineProps<{
    id: number,
    modelo: string,
    montadora: string, 
    ano: number, 
    cor: string,
    valor: number,
    imagem: string
}>();

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
        height: 30vh !important;
        width: 80vw !important;
    }
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
    height: 50vh;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); 
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
}
button{
    border: none;
    background-color: orange;
    padding: 0.8em 2em;
    border-radius: 2em;
    margin-top: 3em;
    cursor: pointer;
    transition: background-color 0.3s, transform 0.3s; 
}
button:hover{
    transform: scale(1.1);
}
</style>