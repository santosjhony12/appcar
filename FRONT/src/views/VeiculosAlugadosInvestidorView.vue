<template>
    <div class="solicitacoes" id="solicitacoes">
        <Title :text="'Projeção do Investidor'" />

        <div class="cards-dash">
            <CardDash :title="'Quantidade de Aluguéis Efetivados'" :value="dadoDash.quantAlugueis" />
            <CardDash :title="'Valor Adquirido'" :value="formatarParaReal(dadoDash.valorAdquirido)" />
            <CardDash :title="'Valor Investido'" :value="formatarParaReal(dadoDash.valorInvestido)" />
        </div>

        <!-- <div class="filters">
            <div class="filtro-div">
                <label for="">Busque pela placa do veículo</label>
                <input v-model="filtroPlaca" placeholder="Digite a placa do veículo" type="string" class="input" />
            </div>

        </div> -->

        <div class="tabela-div">
            <div class="tabela">
                <table>
                    <thead>
                        <tr>
                            <th>Imagem</th>
                            <th>Modelo Carro</th>
                            <th>Placa</th>
                            <th>Data Inicio</th>
                            <th>Data Fim</th>
                            <th>Valor a receber</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(aluguel) in alugueisFiltrados">
                            <td><img :src="'data:image/png;base64,' + aluguel.imagem" alt="" class="imagem-veiculo">
                            </td>
                            <td>{{ aluguel.modeloCarro }}</td>
                            <td>{{ aluguel.placa }}</td>
                            <td> {{ aluguel.dataInicio }}</td>
                            <td>{{ aluguel.dataFim }}</td>
                            <td>{{ formatarParaReal(aluguel.valorAluguel) }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <Alert :message="msgAlert" v-if="showAlert" />

</template>

<script lang="ts" setup>
import Title from '@/components/Title.vue';
import { onMounted, ref, watch } from 'vue';
import AluguelService from '@/service/aluguel';
import Button from '@/components/Button.vue';
import Alert from '@/components/Alert.vue';
import CardDash from '@/components/CardDash.vue';

interface Aluguel {
    id: number,
    username: string,
    nomeUsuario: string,
    modeloCarro: string,
    placa: string,
    aprovado: boolean,
    dataInicio: number,
    dataFim: string,
    valorAluguel: number,
    imagem: string,

}

interface DadosDash {
    quantAlugueis: number,
    valorAdquirido: number,
    valorInvestido: number
}
const filtroNome = ref<string>('');
const filtroPlaca = ref<string>('');
const alugueisFiltrados = ref<Aluguel[]>([]);
const alugueis = ref<Aluguel[]>([]);
const msgAlert = ref<string>('');
const showAlert = ref<boolean>(false);
const isLoading = ref<boolean>(false);
const idAluguelAutorizar = ref<number>(0);
const dadoDash = ref<DadosDash>({
    quantAlugueis: 0,
    valorAdquirido: 0,
    valorInvestido: 0
});
function formatarParaReal(valor: number): string {
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL',
    }).format(valor);
}

onMounted(async () => {
    await getAlugueis();
    await getDadosDash();
})

const getAlugueis = async () => {
    try {
        alugueis.value = await AluguelService.getAlugueisPorLocador();
        alugueisFiltrados.value = alugueis.value;
    } catch (error: any) {
        showAlertaFunction("Alguma coisa não saiu bem! Entre em contato com o administrador.")
    }
}

const showAlertaFunction = (msg: string) => {
    msgAlert.value = msg;
    showAlert.value = true;
    setTimeout(() => {
        showAlert.value = false;
        msgAlert.value = '';
    }, 3000);
}

const getDadosDash = async () => {
    try {
        dadoDash.value = await AluguelService.getDadosParaDash();
    } catch (error: any) {
        showAlertaFunction("Alguma coisa não saiu bem! Entre em contato com o administrador.")
    }
}

watch([filtroPlaca], () => {
    let filtrados = alugueis.value;
    if (filtroPlaca.value !== '') {
        filtrados = filtrados.filter(aluguel =>
            aluguel.placa.toLowerCase().includes(filtroPlaca.value.toLowerCase())
        );
    }
    alugueisFiltrados.value = filtrados;
}, { immediate: true });

</script>

<style scoped>
.img-carro {
    width: 5vw;
}

.tabela-div {
    display: flex;
    justify-content: center;
    margin-top: 5vh;
}

.tabela {
    min-width: 95vw;
    overflow-x: auto;
}

.form-description {
    text-align: center;
    font-size: 1em;
    color: #666;
    margin-bottom: 20px;
}

table {
    width: 100%;
    border-collapse: collapse;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    background-color: #fff;
}

th,
td {
    padding: 12px;
    text-align: left;
    font-size: 14px;
}

th {
    background-color: black;
    color: white;
    font-weight: 600;
}


td {
    background-color: #f9f9f9;
    color: #333;
    border-bottom: 1px solid #ddd;
}

tr:nth-child(even) td {
    background-color: #f1f1f1;
}

tr:hover td {
    background-color: #e0f7fa;
    transition: background-color 0.3s ease;
}

td {
    word-wrap: break-word;
    max-width: 200px;
}

td::after {
    content: "";
    display: block;
    height: 0;
}


/* Fundo do modal */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 999;
}

/* Modal */
.modal {
    background: white;
    padding: 30px;
    border-radius: 8px;
    color: black;
    width: 35vw;
    max-width: 90%;
    height: auto;
    max-height: 80vh;
    overflow-y: auto;
    /* Habilita rolagem se o conteúdo for maior que o modal */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* Botões dentro do modal */
.modal button {
    margin: 10px 10px;
    padding: 10px 20px;
    background-color: #000000;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1em;
}

.modal button:hover {
    background-color: #323232;
}

/* Título do modal */
.titulo-modal {
    font-size: 1.2em;
    font-weight: 500;
    text-align: center;
    margin-bottom: 20px;
}

/* Botões organizados em coluna */
.buttons {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

/* Ajustes para telas menores */
@media (max-width: 1000px) {
    .modal {
        width: 90%;
        padding: 20px;
    }

    .titulo-modal {
        font-size: 1em;
    }

    .buttons {
        flex-direction: column;
        gap: 8px;
    }

    .tabela {
        max-width: 900px;
        overflow-x: auto;
        padding: 0 10px
    }
}

@media (max-width: 480px) {
    .modal {
        padding: 15px;
        border-radius: 6px;
    }

    .modal button {
        font-size: 0.9em;
        padding: 8px 16px;
    }

    .titulo-modal {
        font-size: 0.9em;
    }
}

button {
    padding: 6px 12px;
    background-color: #000000;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
}

button:hover {
    background-color: #0056b3;
}

.filters {
    display: flex;
    gap: 1rem;
    margin: 1rem 0;
    justify-content: center;
}

.filters label {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    font-size: 1rem;
    cursor: pointer;
    color: #333;
}

.filters input[type="radio"] {
    accent-color: #007bff;
    /* Estiliza a cor do radio button */
    cursor: pointer;
}

.input {
    width: 20vw;
    font-size: 1em;
    padding: 10px 12px;
    border: 1px solid #ccc;
    /* Borda padrão */
    border-radius: 5px;
    /* Cantos arredondados */
    outline: none;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.input:focus {
    border-color: #007bff;
    /* Cor de destaque */
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
    /* Efeito de foco */
}

.input::placeholder {
    color: #aaa;
    font-style: italic;
}

.filtro-div {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.imagem-veiculo {
    width: 5vw
}
.cards-dash{
    display: flex;
    justify-content: space-evenly;
}
</style>