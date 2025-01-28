import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import './assets/main.css'
import { createScrollRevealDirective } from 'vue-scroll-reveal';

import axios from 'axios';
import { faArrowRight, faBullseye } from '@fortawesome/free-solid-svg-icons';

library.add(faBullseye, faArrowRight);
const app = createApp(App)

const vScrollReveal = createScrollRevealDirective({
    delay: 100,
    duration: 1000,
    scale: 0.9,
    reset: true,
  });
  
app.directive('scroll-reveal', vScrollReveal);


app.component('font-awesome-icon', FontAwesomeIcon);
app.use(createPinia())
app.use(router)
app.mount('#app')
