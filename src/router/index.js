import { createRouter, createWebHashHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import UserManage from '../views/UserMange.vue'
import OrderManage from '@/views/OrderManage.vue'
import AdPoManage from '@/views/AdPoManage.vue'

const routes = [
  {
    path: '/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/userManage',
    name: 'userManage',
    component: UserManage
  },
  {
    path: '/orderManage',
    name: 'orderManage',
    component: OrderManage
  },
  {
    path: '/adPoManage',
    name: 'adPoManage',
    component: AdPoManage
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
