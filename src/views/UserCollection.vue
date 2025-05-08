<template>
    <el-container class="user-container">
      <!-- 左侧栏 -->
      <el-aside class="sidebar" width="200px">
        <el-menu default-active="3" class="user-menu" @select="handleSelect">
          <el-menu-item index="1">基本信息</el-menu-item>
          <el-menu-item index="2">个人钱包</el-menu-item>
          <el-menu-item index="3">个人收藏</el-menu-item>
          <el-menu-item index="4">浏览历史</el-menu-item>
        </el-menu>
      </el-aside>
  
      <!-- 右侧内容 -->
      <el-main class="main-content">
      </el-main>

    </el-container>
</template>
  
  <script>
  //import axios from 'axios';
  export default {
    data() {
      return {
      };
    },
    methods:{
      handleSelect(index){
        if(index=="1"){
          this.$router.push({ name: 'userview' });
        }
        else if(index=="2"){
          this.$router.push({name:'userwallet'});
        }
        else if(index=="3"){
            this.$router.push({name:'usercollection'})
        }
      }
    }
}
  </script>
  
  <style scoped>
  .user-container {
    height: 100vh;  /* 确保容器填充满视口高度 */
  }
  
  .sidebar {
    background-color: #b9b5b5;
    /* 内边距 */
    padding: 20px;
  }
  
  /* 菜单项样式 */
  .user-menu .el-menu-item {
    font-size: 16px;  /* 设置菜单项的字体大小 */
  }
  
  /* 主体内容区域样式 */
.main-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100vh;
    gap:40px/*调整上下间距*/
}
</style>
  