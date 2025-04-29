import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import axios from 'axios'
import store from './store/store.js'
import 'element-plus/dist/index.css'


const app = createApp(App)
// 将 axios 绑定到 Vue 原型上，方便全局使用
app.config.globalProperties.$axios = axios;

// 改为采用vuex管理全局状态
// const globalState = reactive({
//     isLoggedIn: false,
//   });
// app.provide('globalState', globalState);

app.use(store)
app.use(ElementPlus)
app.use(router)
app.mount('#app')
