<template>
    <div class="containerAlugar">

        <div class="landing-page-main">
            <div class="navbar">
                <div class="image">
                    <img src="../assets/logo_app.png" alt="" class="logo">
                </div>
                <div class="options">
                    <RouterLink class="links" to="/contato">Sobre nós</RouterLink>
                    <RouterLink class="links" to="/contato">Contate-nos</RouterLink>
                    <RouterLink class="links login" to="/login">Entrar</RouterLink>

                </div>
            </div>

            <div class="main">
                <div class="frases">
                    <h2 class="title-main">Seu Parceiro Completo Para o Sucesso no Volante</h2>
                    <h3 class="subtitle-main">Carro, suporte e treinamento para você dirigir e lucrar mais.</h3>
                    <button class="button-contato">Entrar em contato</button>
                </div>
            </div>
        </div>

        <div class="landing-page-first">
            <h1 class="title-beneficios">Benefícios</h1>

            <div class="divisao-usuarios">
                <div class="motoristas">
                    <h2>Motoristas</h2>
                    <ul>
                        <li>Carros prontos para você dirigir e lucrar! Sem burocracia, só acelerar!</li>
                        <li>Ganhe mais com um carro que trabalha tão duro quanto você!</li>
                        <li>Precisa de um carro confiável? Nós cuidamos disso por você.</li>
                        <li>Alugue com quem entende de motoristas: suporte, flexibilidade e confiança.</li>
                        <li>Dirija com conforto e tranquilidade. O resto, nós resolvemos!</li>
                    </ul>
                </div>

                <div class="investidores">
                    <h2>Investidores</h2>
                    <ul>
                        <li>Investimento inteligente: seu carro rende mais aqui!</li>
                        <li>Maximize seus ganhos com nosso modelo de negócios lucrativo e seguro!</li>
                        <li>Seu investimento, nossa gestão: mais ganhos, menos preocupações.</li>
                        <li>Ganhe mais com quem entende do mercado: rentabilidade garantida!</li>
                        <li>Transforme seu carro em renda passiva mensal!</li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="landing-page-second">
            <h1 class="carros-disponiveis">NOSSOS CARROS DISPONÍVEIS</h1>
            <div class="aluguel-carro">
               <Swipper :carros="veiculos"/>

            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import CarroService from '@/service/carro';
import { RouterLink } from 'vue-router';
import Swipper from '../components/Swipper.vue'
interface Carro {
    id: number,
    modelo: string,
    montadora: string,
    ano: number,
    cor: string,
    placa: string,
    valor: number,
    username: string,
    imagem: string,
    autorizado: boolean,
    percentualMotorista: number,
    percentualSistema: number,
    percentualInvestidor: number
}
const veiculos = ref<Carro[]>([]);
const getAllVeiculos = async () => {
    veiculos.value = await CarroService.getCarrosAutorizados();
}

onMounted(() => {
    getAllVeiculos();
})
</script>

<style scoped>
.containerAlugar {
    color: black;
}
.swiper {
  width: 100%;
  height: 100%;
}

.swiper-slide {
  display: flex;
  justify-content: center;
  align-items: center;
}

.title-main {
    font-weight: 500;
    font-style: normal;
    color: white;
    font-size: 3em;
}
.subtitle-main{
    font-weight: 300;
    font-style: normal;
    color: yellow;
    font-size: 1.5em;
}
.main{
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0 5em;
    height: 50vh;
    text-align: center;
}

.landing-page-main {
    background-image: url('../assets/fundo.jpg');
    height: 100vh;
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    background-color: rgba(0, 0, 0, 0.725);
    background-blend-mode: darken;

}

.navbar {
    display: flex;
    padding: 2em 4em;
    justify-content: space-between;
    
}

.links {
    text-decoration: none;
    color: white;
    cursor: pointer;
    margin: 0 1em;
    display: inline-block;
    /* Garante que transform funcione corretamente */
    text-decoration: none;
    /* Remove o sublinhado, opcional */
    font-size: 16px;
    /* Tamanho inicial do texto */
    transition: transform 0.3s ease, color 0.3s ease;
}

.login {
    background: white;
    color: black;
    padding: 0.3em 2em;
    border-radius: 1em;
}

.logo {
    width: 8vw;
    margin-top: -2em;
}

.links:hover {
    color: yellow;
    /* Muda a cor do link para amarelo ao passar o mouse */
    transform: scale(1.2);
    /* Aumenta o tamanho em 20% */
}

.login:hover {
    color: black
}

@media (max-width: 1000px) {
    .logo {
        width: 20vw;
        margin-top: -3.2em
    }

    .navbar {
        padding: 2em 2em;
    }

    .links {
        font-size: 14px
    }
    .divisao-usuarios{
        flex-direction: column;
    }
    .landing-page-first{
        height: 100vh;
        padding: 10vh 1vw;
        background-color: white;
        
    }
    .motoristas{
        margin: 2em;
    }
}
@media (max-width: 700px){
    .logo{
        margin-top: -1.8em
    }
    .landing-page-first{
        height: 120vh !important;
        padding-left: 1em !important;
        padding-right: 1em !important;
    }
    .carros-disponiveis{
        margin-top: 6em
    }
    .links{
        font-size: 11px;
        margin: 0 0.3em!important;
    }
    .main{
        padding: 10vh 2vw !important;
    }
}
.button-contato {
    background-color: yellow; /* Cor de fundo */
    color: black; /* Cor do texto */
    padding: 0.8em 3em; /* Espaçamento interno */
    border-radius: 2em; /* Borda arredondada */
    font-size: 1.2em; /* Tamanho da fonte */
    border: none; /* Remove a borda */
    cursor: pointer; /* Muda o cursor para indicar que é clicável */
    transition: background-color 0.3s, transform 0.3s; /* Suaviza as transições */
}

.button-contato:hover {
    transform: scale(1.1); /* Aumenta ligeiramente o botão */
}

.button-contato:active {
    transform: scale(0.95); /* Reduz ligeiramente ao clicar */
}
.landing-page-first{
    height: 70vh;
    background-color: rgb(206, 206, 206);
    display: flex;
    padding: 11vh 15vw;
    color: rgb(73, 73, 73);
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
}


.divisao-usuarios{
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 20px
}
.title-beneficios{
    font-size: 2em;
    font-weight: 400;
}
.motoristas > h2 , .investidores > h2{
    text-align: center;
}

.icon{
    margin: 0 1em 0 0;
}
li{
    list-style: none;
    margin: 0
}
ul{
    margin: 0;
    padding: 0
}
.landing-page-second{
    height: 80vh;
    display: flex;
    flex-direction: column;
    justify-content: center;

}
.carros-disponiveis{
    font-weight: bold;
    margin-bottom: 10vh;
    text-align: center;
}
.aluguel-carro{
    display: flex;
    justify-content: center;

}
</style>