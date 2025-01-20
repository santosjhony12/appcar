<template>
    <div class="container-card-carro">
        <img :src="imagem" alt="" class="imagem-carro">

        <div class="dados">
            <h3 class="nome-carro">{{ modelo }}</h3>
        
            <ul>
                <li>Montadora: {{ montadora }}</li>
                <li>Ano: {{ ano }}</li>
                <li>Cor: {{cor}}</li>
            </ul>

            <h3 class="valor-investimento">Valor do investimento</h3>
            <h2 class="valor-do-veiculo">R$ {{ valor }}</h2>
        </div>
        
    </div>
</template>
<script setup lang="ts">

const props = defineProps<{
    id: number,
    modelo: string,
    montadora: string, 
    ano: string, 
    cor: string,
    valor: number,
    imagem: string
}>();

</script>

<style scoped>
.container-card-carro{
    padding: 2em;
    border-radius: 1em;
    height: 70vh;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); 
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    background-color: rgb(255, 255, 255);
    width: 25vw;
}
.imagem-carro{
    width: 80%
}
@media (max-width: 1000px){
    .container-card-carro{
        width: 80vw;
    }
}
.dados{
    text-align: center;
    color: #000;
}
ul{
    list-style: none;
    padding: 0;
  margin: 0;
  color: gray;
  
}
.nome-carro{
    font-weight: 600;
    font-size: 1.3em
}
.valor-investimento{
    color: gray;
    margin: 1em 0 0 0 ;
    font-size: 1.1em;
}
.valor-do-veiculo{
    font-weight: bold;
    font-size: 2em
}


</style>