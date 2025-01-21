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
  
  
  const fileName = ref<string | null>(null);
  const inputValue = ref<string | null>(null);  
  
  const emit = defineEmits<{
    (event: 'update:modelValue', value: string | null): void; 
  }>();
  
  
  const handleFileChange = (event: Event) => {
  const input = event.target as HTMLInputElement;
  if (input.files && input.files[0]) {
    const file = input.files[0];
    const reader = new FileReader();

    
    reader.onload = () => {
      const base64String = reader.result as string;
      fileName.value = file.name;
      inputValue.value = base64String;  
      emit('update:modelValue', base64String);  
    };

    reader.readAsDataURL(file);  
  } else {
    fileName.value = null;
    inputValue.value = null;
    emit('update:modelValue', null);  
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
    margin-top: 1.2em
  }
  
  .custom-file-label {
    display: inline-block;
    padding: 10px 15px;
    background-color: black;
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
    display: none;
  }
  </style>
  