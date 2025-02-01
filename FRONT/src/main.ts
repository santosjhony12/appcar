import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { library } from "@fortawesome/fontawesome-svg-core";
import './assets/main.css'

import axios from 'axios';
import { faArrowRight, faBook, faBullseye, faCalendarDays, faCar, faCheck, faComment, faMoneyBill, faPenSquare, faPenToSquare, faPhone, faQuestion, faRightFromBracket, faTrash, faUser, faUsers } from '@fortawesome/free-solid-svg-icons';
library.add(faBullseye, faArrowRight, faUsers, faQuestion, faCar, faBook, faMoneyBill, 
    faRightFromBracket, faUser, faComment, faCalendarDays, 
    faPenToSquare, faTrash, faCheck, faPhone);
const app = createApp(App)



app.component('font-awesome-icon', FontAwesomeIcon);
app.use(createPinia())
app.use(router)
app.mount('#app')
