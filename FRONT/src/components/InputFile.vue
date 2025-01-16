<template>
    <div class="input-file">
      <label for="image-input" class="custom-file-label">
        <span v-if="!fileName">Clique para selecionar uma imagem</span>
        <span v-else>{{ fileName }}</span>
      </label>
      <input
        id="image-input"
        type="file"
        accept="image/*"
        @change="handleFileChange"
      />
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, watch } from 'vue';
  
  // Definir o nome do arquivo e a referência para o arquivo selecionado
  const fileName = ref<string | null>(null);
  const inputValue = ref<string | null>(null);  // Valor será do tipo File ou null
  
  const emit = defineEmits<{
    (event: 'update:modelValue', value: string | null): void; // Emitindo a mudança para o pai
  }>();
  
  // Quando o arquivo mudar, atualizamos o nome do arquivo e emitimos o valor
  const handleFileChange = (event: Event) => {
  const input = event.target as HTMLInputElement;
  if (input.files && input.files[0]) {
    const file = input.files[0];
    const reader = new FileReader();

    // Quando o arquivo for lido, convertê-lo para Base64
    reader.onload = () => {
      const base64String = reader.result as string;
      fileName.value = file.name;
      inputValue.value = base64String;  // Armazenando a string Base64
      emit('update:modelValue', base64String);  // Emitir para o componente pai
    };

    reader.readAsDataURL(file);  // Lê o arquivo como Base64
  } else {
    fileName.value = null;
    inputValue.value = null;
    emit('update:modelValue', null);  // Emitir null caso não haja arquivo
  }
};

  </script>
  
  <style scoped>
  .input-file {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    position: relative;
    margin-bottom: 1.5em;
  }
  
  .custom-file-label {
    display: inline-block;
    padding: 10px 15px;
    background-color: #000000;
    color: white;
    border-radius: 5px;
    cursor: pointer;
    text-align: center;
    font-size: 1em;
    transition: background-color 0.3s ease, transform 0.2s ease;
  }
  
  .custom-file-label:hover {
    background-color: #0056b3;
    transform: scale(1.03);
  }
  
  .custom-file-label:active {
    transform: scale(1);
  }
  
  #image-input {
    display: none; /* Esconde o input real */
  }
  </style>
  