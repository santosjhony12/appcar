<template>
    <div class="container-contato">
        <div class="container-titulo-principal">
            <Title :text="'Contato'" />
            <p class="form-description">Precisa de suporte ou quer falar com nossa equipe? Se você já é investidor ou motorista, entre em contato para esclarecer dúvidas, gerenciar sua frota ou resolver qualquer questão. Estamos disponíveis por WhatsApp, e-mail e telefone para te atender com rapidez e eficiência!</p>
        </div>

        <div class="container-titulo-principal segundo-container">
            <p class="form-description">
                <div class="contatos">
                    <strong>E-mail: </strong>
                </div>
                <div class="contatos">
                    <strong>WhatsApp: </strong>
                </div>
                <div class="contatos">
                    <strong>Localização: </strong>
                </div>
            </p>
        </div>
    </div>
</template>

<script lang="ts" setup>
import Title from '@/components/Title.vue';
</script>

<style scoped>
.segundo-container{
    margin-top: 2vh;
}
strong{
    font-weight: bold;
}
.contatos{
    color: #1a2f4d
}
</style>