<template>
    <h1 id="title">{{ text }}</h1>
</template>

<script lang="ts" setup>

defineProps<{
    text: string
}>();
</script>

<style scoped>

#title{
    font-size: 1.9em;
    color:#1a2f4d;
    font-weight: bold;
}
@media (max-width: 1000px){
    #title{
        margin: 1em 0 0 0;
    }
}

</style>