import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'home',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/HomeView.vue')
  },
  {
    path:'/user',
    name:'userview',
    component: () => import('../views/UserView.vue')
  },
  {
    path:'/user/wallet',
    name:'userwallet',
    component: () => import('../views/UserWallet.vue')
  },
  {
    path:'/user/collection',
    name:'usercollection',
    component:() => import('../views/UserCollection.vue')
  },
  {
    path:'/addetail/:adId',
    name:'addetail',
    component:() => import('../views/AdDetail.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
