<template>
    <el-container class="user-container">
      <!-- 左侧栏 -->
      <el-aside class="sidebar" width="200px">
        <el-menu default-active="2" class="user-menu" @Select="handleSelect">
          <el-menu-item index="1">基本信息</el-menu-item>
          <el-menu-item index="2">个人钱包</el-menu-item>
          <el-menu-item index="3">个人收藏</el-menu-item>
          <el-menu-item index="4">浏览历史</el-menu-item>
        </el-menu>
      </el-aside>
  
      <!-- 右侧内容 -->
      <el-main class="main-content">
        <div class="balance">
            <div class="balance-border">
            <p class="content-text">我的余额</p>
            <text class="balance-text">￥1314</text>
            </div>
        </div>
        <div class="order">
          <el-button>订单记录</el-button>
        </div>
        <div class="invoice">
          <el-button>发票管理</el-button>
        </div>
        <div class="recharge">
          <el-button>钱包充值</el-button>
        </div>
      </el-main>
    </el-container>
</template>
  
  <script>
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
      }
    }
  };
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
.balance-border {
  width: 1000px; /* 设置矩形的宽度 */
  height: 150px; /* 设置矩形的高度 */
  border: 3px solid #775da7; /* 边框颜色和厚度 */
  background-color: #4578b3;
  border-radius: 10px; /*圆角设置*/
  padding: 20px; /* 内边距 */
  display: flex;
  flex-direction: column;
  font-size: 30px
}
.balance-text{
  font-weight: 700;
  color:black;
}
</style>
  