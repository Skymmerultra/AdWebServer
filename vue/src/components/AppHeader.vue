<template>
  <div class="app-header">
    <nav class="nav-section">
      <a href="#" class="nav-link" :class="{ active: currentPage === 'HomeView' }">首页</a>
    </nav>
    <div class="right-section">
      <SearchBox />
      <LoginButton v-if="!isReplaced" @login="replaceIcon"/>
      <AvatarIcon v-if="isReplaced"/>
    </div>
  </div>
</template>

<script>
import LoginButton from './LoginButton.vue';
import SearchBox from './SearchBox.vue';
import AvatarIcon from './AvatarIcon.vue';

export default {
  name: 'AppHeader',
  components:{
    LoginButton,
    SearchBox,
    AvatarIcon
  },
  computed: {
    // 使用计算属性,确保实时响应
    isReplaced() {
      return this.$store.getters.isLoggedIn;  
    }
  },
  data() {
    return {
      
    };
  },
};
</script>

<style scoped>
.app-header {
  height: 100px;
  width: 100%;
  /* 背景设置为白色 */
  background-color: #ffffff;
  /* 设置头部底部边框 */
  border-bottom: 1px solid #5c5c5c;
  position:fixed;
  z-index: 100;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nav-section {
  display: flex;
  gap: 24px;
}

.nav-link {
  /* 文本颜色设置 */
  color: #4b5563;
  /* 去下划线 */
  text-decoration: none;
  font-size: 30px;
  font-weight: 500;
  padding: 8px 16px;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.nav-link:hover {
  color: #3B82F6;
}

.nav-link.active {
  background-color: #e5e7eb;
  color: #1f2937;
  font-weight: 600;
}

.right-section {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-right:16px;
}

@media (max-width: 768px) {
  .app-header {
    padding: 0 16px;
  }
  
  .nav-section {
    gap: 16px;
  }
  
  .right-section {
    gap: 8px;
  }
}
</style>