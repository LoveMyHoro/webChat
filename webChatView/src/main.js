import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
//1.导入ElementPlus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//导入element-plus中文包
import locale from'element-plus/dist/locale/zh-cn.js'
//2.导入router路由
import router from './router'
//3.导入pinia
//5.导入pinia创建函数保存token
import { createPinia } from 'pinia'
//6.导入pinia的持久化插件长久保存token
import { createPersistedState } from 'pinia-persistedstate-plugin'


const app=createApp(App)
const pinia=createPinia();
const persist=createPersistedState()

pinia.use(persist)
app.use(pinia)
app.use(ElementPlus,{locale})
app.use(router)
app.mount('#app')
