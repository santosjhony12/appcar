<template>
    <div class="inputs">
      <label for="input" id="label">{{ label }}</label>
      <input 
        v-model="inputValue"
        @input="applyPhoneMask" 
        :type="type" 
        :placeholder="placeholder" 
        name="input" 
        id="input" 
        @focus="onFocus" 
        @blur="onBlur"
        inputmode="numeric"
      />
      <div class="underline"></div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, watch } from 'vue';
  
  const props = defineProps<{
    label: string,
    placeholder: string,
    type: string,
    modelValue: string
  }>();
  
  const onFocus = (event: FocusEvent) => {
    const input = event.target as HTMLInputElement;
    input.nextElementSibling?.classList.add('active');
  };
  
  const onBlur = (event: FocusEvent) => {
    const input = event.target as HTMLInputElement;
    input.nextElementSibling?.classList.remove('active');
  };
  
  const emit = defineEmits<{
    (event: 'update:modelValue', value: string): void;
  }>();
  
  const inputValue = ref('');
  
  // Atualiza o valor do campo com a máscara de telefone
  const applyPhoneMask = () => {
    let value = inputValue.value.replace(/\D/g, ''); // Remove caracteres não numéricos
  
    // Limita o valor a 11 dígitos (DDD + Telefone)
    value = value.slice(0, 11);
  
    // Aplica a máscara de telefone
    if (value.length > 10) {
      value = value.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    } else if (value.length > 6) {
      value = value.replace(/(\d{2})(\d{4})(\d{0,4})/, '($1) $2-$3');
    } else if (value.length > 2) {
      value = value.replace(/(\d{2})(\d{0,4})/, '($1) $2');
    }
  
    inputValue.value = value;
    emit('update:modelValue', value); // Emit o valor formatado
  };
  
  // Atualiza inputValue quando o valor do pai muda
  watch(() => props.modelValue, (newValue) => {
    inputValue.value = newValue;
  });
  </script>
  
  <style scoped>
  .inputs {
    display: flex;
    flex-direction: column;
    position: relative;
    margin-bottom: 1.5em;
  }
  
  #label {
    color: #555;
    font-size: 1.1em;
    margin-bottom: 5px;
    transition: color 0.3s ease;
  }
  
  #input {
    font-size: 1em;
    padding: 10px 12px;
    border: 1px solid #ccc;
    border-radius: 5px;
    outline: none;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
  }
  
  #input:focus {
    border-color: #007bff;
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
  }
  
  #input::placeholder {
    color: #aaa;
    font-style: italic;
  }
  </style>
  