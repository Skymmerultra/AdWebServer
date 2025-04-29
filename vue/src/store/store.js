// src/store/index.js
import { createStore } from 'vuex';

const store = createStore({
  state() {
    return {
      userId: null,
      isLoggedIn:false,
    };
  },
  mutations: {
    setUserId(state, userId) {
      state.userId = userId; 
    },
    setIsLoggedIn(state,isLoggedIn){
        state.isLoggedIn = isLoggedIn;
    }
  },
  actions: {
    login({ commit }, userId) {
        commit('setUserId', userId);         // 更新用户ID
        commit('setIsLoggedIn', true);       // 设置为已登录
      },
      logout({ commit }) {
        commit('setUserId', null);           // 清空用户ID
        commit('setIsLoggedIn', false);      // 设置为未登录
      }
  },
  getters: {
    getUserId: (state) => state.userId,  
    isLoggedIn: (state) => state.isLoggedIn, 
  },
});

export default store;
