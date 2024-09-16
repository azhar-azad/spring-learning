import { createWebHistory, createRouter } from "vue-router"

const routes = [
    {
        path: '/',
        alias: '/demos',
        name: 'demos',
        component: () => import('./components/DemoList.vue')
    },
    {
        path: '/demos/:id',
        name: '/demo-details',
        component: () => import('./components/DemoDetails.vue')
    },
    {
        path: '/add',
        name: '/add',
        component: () => import('./components/AddDemo.vue')
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router