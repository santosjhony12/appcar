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
    font-size: 1.7em;
    color:#35424A;
    text-align: center;
    margin: 1em 0 0 0 ;
    font-weight: 500;
}
@media (max-width: 1000px){
    #title{
        margin: 1em 0 0 0;
    }
}

</style>