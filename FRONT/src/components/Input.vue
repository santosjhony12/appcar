<template>
    <div class="inputs">
      <label for="input" id="label">{{ label }}</label>
      <input 
        v-model="inputValue"
        @input="$emit('update:modelValue', $event.target.value)"
        :type="type" 
        :placeholder="placeholder" 
        name="input" 
        id="input" 
        @focus="onFocus" 
        @blur="onBlur"
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
    modelValue: string | number
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
  (event: 'update:modelValue', value: string): void; // Emitindo a mudança do valor
}>();

// Definindo o valor do input
const inputValue = ref();

// Quando o valor mudar, emitimos o novo valor
watch(inputValue, (newValue) => {
  emit('update:modelValue', newValue);  // Emitindo a mudança para o pai
});
watch(() => props.modelValue, (newValue) => {
  inputValue.value = newValue; // Atualize inputValue quando o valor do pai mudar
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
  border: 1px solid #ccc; /* Borda padrão */
  border-radius: 5px; /* Cantos arredondados */
  outline: none;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

#input:focus {
  border-color: #007bff; /* Cor de destaque */
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5); /* Efeito de foco */
}

#input::placeholder {
  color: #aaa;
  font-style: italic;
}
  </style>
  