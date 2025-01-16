<template>
    <div class="inputs">
      <label for="select" id="label">{{ label }}</label>
      <select 
        v-model="selectedValue" 
        name="select" 
        id="select" 
        @focus="onFocus" 
        @blur="onBlur"
           @input="$emit('update:modelValue', $event.target.value)"
      >
        <option value="" disabled>{{ placeholder }}</option>
        <option v-for="(value, index) in options" :key="index" :value="value">
          {{ value }}
        </option>
      </select>
      <div class="underline"></div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, watch } from 'vue';
  
  // Propriedades do componente
  const props = defineProps<{
    label: string,
    placeholder: string,
    options: string[], // Lista de valores do select
    modelValue: string
  }>();
  
  const emit = defineEmits<{
    (event: 'update:modelValue', value: string): void;
  }>();
  
  // Valor selecionado
  const selectedValue = ref('');
  
  // Observa o valor selecionado e emite para o componente pai
  watch(selectedValue, (newValue) => {
    emit('update:modelValue', newValue);
  });
  
  // Estilização do select quando focado
  const onFocus = (event: FocusEvent) => {
    const select = event.target as HTMLSelectElement;
    select.nextElementSibling?.classList.add('active');
  };
  
  const onBlur = (event: FocusEvent) => {
    const select = event.target as HTMLSelectElement;
    select.nextElementSibling?.classList.remove('active');
  };
  watch(() => props.modelValue, (newValue) => {
  selectedValue.value = newValue; // Atualize inputValue quando o valor do pai mudar
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
  
  #select {
    font-size: 1em;
    padding: 10px 12px;
    border: 1px solid #ccc; /* Borda padrão */
    border-radius: 5px; /* Cantos arredondados */
    outline: none;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
    background-color: #fff;
    color: #333;
    appearance: none; /* Remove o estilo padrão do select */
  }
  
  #select:focus {
    border-color: #007bff; /* Cor de destaque */
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5); /* Efeito de foco */
  }
  
  #select option {
    color: #333;
  }
  
  #select::placeholder {
    color: #aaa;
    font-style: italic;
  }
  
  .underline {
    position: absolute;
    bottom: -3px;
    left: 0;
    width: 100%;
    height: 2px;
    background-color: transparent;
    transition: background-color 0.3s ease;
  }
  
  .underline.active {
    background-color: #007bff; /* Cor de destaque ao focar */
  }
  </style>
  