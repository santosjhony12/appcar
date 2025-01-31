<template>
    <div class="tabela-usuarios">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>Data de Nascimento</th>
                    <th>Primeiro Acesso</th>
                    <th>Role</th>
                    <th>Editar</th>
                    <th>Excluir</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(usuario, index) in paginatedUsuarios">
                    <td>
                        <span>{{ usuario.id ?? 'Não informado' }}</span>
                    </td>
                    <td>

                        <span>{{ usuario.username ?? 'Não informado' }}</span>
                    </td>
                    <td>
                        <span>{{ usuario.nome ?? 'Não informado' }}</span>
                    </td>
                    <td>
                        <span>{{ usuario.cpf ?? 'Não informado' }}</span>
                    </td>
                    <td>
                        <span>{{ usuario.dataNascimento ?? 'Não informado' }}</span>
                    </td>
                    <td>
                        <span>{{ usuario.primeiroAcesso !== null ? (usuario.primeiroAcesso ? 'Sim' : 'Não') : 'Não informado' }}</span>
                    </td>
                    <td>
                        <select v-if="isEditing[index]" v-model="usuario.role">
                            <option value="INVESTIDOR">INVESTIDOR</option>
                            <option value="ADMINISTRADOR">ADMINISTRADOR</option>
                            <option value="MOTORISTA">MOTORISTA</option>
                        </select>
                        <span v-else>{{ usuario.role ?? 'Não informado' }}</span>
                    </td>
                    <td>
                        <button @click="toggleEdit(index, usuario.id, usuario.role)" class="button-editar">
                            {{ isEditing[index] ? 'Salvar' : 'Editar' }}
                        </button>
                    </td>
                    <td>
                        <button @click="confirmDelete(index, usuario.id)" class="button-excluir">Excluir</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="pagination">
        <button @click="previousPage" :disabled="currentPage === 1">Anterior</button>
        <span>Página {{ currentPage }} de {{ totalPages }}</span>
        <button @click="nextPage" :disabled="currentPage === totalPages">Próxima</button>
    </div>
    <Alert :message="msgAlert" v-if="showAlert" />


    <div v-if="showModal" class="modal-overlay">
        <div class="modal">
            <p>Tem certeza que deseja excluir este usuário?</p>
            <button @click="deleteUser(selectedUserId)">Sim, excluir</button>
            <button @click="closeModal">Cancelar</button>
        </div>
    </div>

</template>

<script lang="ts" setup>
import { ref, computed } from 'vue';
import UsuarioService from '@/service/usuarios';
import Alert from '@/components/Alert.vue';


interface User {
    id: number | null;
    username: string;
    password: string;
    cpf: string | null;
    nome: string | null;
    dataNascimento: string | null;
    primeiroAcesso: boolean | null;
    role: string;
}


const props = defineProps<{
    usuarios: User[];
}>();
const emit = defineEmits(['delete']);

const currentPage = ref(1);
const itemsPerPage = 10;

const deleteUser = async (id: number | null) => {
    const response = await UsuarioService.deleteUsuario(id);
    showAlertaFunction(response);
    closeModal();
    emit('delete');

};
const confirmDelete = (index: number, id: number | null) => {
    selectedUserIndex.value = index;
    selectedUserId.value = id;
    showModal.value = true;
};

const closeModal = () => {
    showModal.value = false;
};
const selectedUserIndex = ref<number | null>(null);
const selectedUserId = ref<number | null>(null);
const showModal = ref(false);
const totalPages = computed(() => Math.ceil(props.usuarios.length / itemsPerPage));

const paginatedUsuarios = computed(() => {
    const startIndex = (currentPage.value - 1) * itemsPerPage;
    return props.usuarios.slice(startIndex, startIndex + itemsPerPage);
});


const previousPage = () => {
    if (currentPage.value > 1) currentPage.value--;
};

const nextPage = () => {
    if (currentPage.value < totalPages.value) currentPage.value++;
};
const isEditing = ref<boolean[]>(props.usuarios.map(() => false));


const toggleEdit = async (index: number, id: number | null, role: string) => {
    isEditing.value[index] = !isEditing.value[index];
    if (!isEditing.value[index]) {
        switch (role) {
            case "INVESTIDOR":
                role = "ROLE_INVESTIDOR";
                break;
            case "MOTORISTA":
                role = "ROLE_MOTORISTA";
                break;
            case "ADMINISTRADOR":
                role = "ROLE_ADMIN";
                break;
        }
        const response = await UsuarioService.updateRole({ id: id, role: role })
        showAlertaFunction(response);
    }
};
const msgAlert = ref<string>('');
const showAlert = ref<boolean>(false);

const showAlertaFunction = (msg: string) => {
    msgAlert.value = msg;
    showAlert.value = true;
    setTimeout(() => {
        showAlert.value = false;
        msgAlert.value = '';
    }, 3000);
}
</script>

<style scoped>
.title {
    text-align: left;
    margin: 0 3vw;
}

.tabela-usuarios {
    margin: 0 auto;
    padding: 20px;
    font-family: 'Arial', sans-serif;
    overflow-x: auto;
}


@media (max-width: 1000px) {
    .tabela-usuarios {
        overflow-x: auto;
        -webkit-overflow-scrolling: touch;
        margin-bottom: 15px;
        width: 100%;
    }

    table {
        min-width: 800px;
    }

    th,
    td {
        font-size: 14px;
        padding: 12px 15px;
    }


}

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

.modal {
    background: white;
    padding: 60px;
    border-radius: 8px;
    text-align: center;
    color: black;
}

.modal button {
    margin: 10px 10px;
    padding: 6px 12px;
    background-color: #000000;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.modal button:hover {
    background-color: #323232;
}


.button-editar{
    background-color: #007bff;
}
.button-excluir{
    background-color: red;
}
h2 {
    text-align: center;
    margin-bottom: 20px;
    color: #333;
    font-size: 24px;
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

span {
    color: black;
}

.pagination {
    display: flex;
    justify-content: center;
    margin-top: 20px;
    gap: 15px;
}

.pagination button {
    padding: 8px 16px;
    background-color: black;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.nao-informado {
    color: red;

}

.pagination button:disabled {
    background-color: #ccc;
}

.pagination span {
    align-self: center;
    font-size: 16px;
}

button {
    padding: 6px 12px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
}

button:hover {
    background-color: #0056b3;
}

button:disabled {
    background-color: #ccc;
}

input,
select {
    padding: 6px;
    border-radius: 4px;
    border: 1px solid #ddd;
    font-size: 14px;
    width: 100%;
}

input:disabled {
    background-color: #f2f2f2;
}

td input,
td select {
    width: 100%;
}

td {
    text-align: center;
}
</style>