import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import './assets/main.css'

import axios from 'axios';
import { faArrowRight, faBullseye } from '@fortawesome/free-solid-svg-icons';

axios.defaults.baseURL = 'https://8080-mineda-springboot3app-g9ny0bwvgyx.ws-us106.gitpod.io/';
library.add(faBullseye, faArrowRight);
const app = createApp(App)
app.component('font-awesome-icon', FontAwesomeIcon);
app.use(createPinia())
app.use(router)
app.mount('#app')
