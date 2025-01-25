import { createRouter, createWebHistory} from "vue-router"
import Login from "@/views/Login.vue"
const routes=[
    {
        path: '/',
        component: Login
    },
    {
        path: '/login',
        component: Login
    }
]

// createRouter() 来创建路由
const router = createRouter({
    routes, // 路由路径哪些
    history: createWebHistory(),
})

// 导出这个变量
export default router