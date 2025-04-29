<template>
    <div class="category-menu">
      <el-menu
        class="adcategory-menu"
        width="150px"
        @select="handleSelect"
      >
        <el-menu-item index="network">
          网络营销
        </el-menu-item>
        <el-menu-item index="wecaht">
          微信营销
        </el-menu-item>
        <el-menu-item index="weibo">
          微博营销
        </el-menu-item>
        <el-menu-item index="media">
          自媒体营销
        </el-menu-item>
        <el-menu-item index="zhibo">
          直播营销
        </el-menu-item>
        <el-menu-item index="television">
          电视营销
        </el-menu-item>
        <el-menu-item index="radio">
          电台营销
        </el-menu-item>
      </el-menu>
    </div>
    
</template>
  
<script>
  export default {
    name: 'CategoryMenu',
    methods: {
      handleSelect(index) {
        console.log('当前选中的菜单项 index: ', index);
      }
    }
  }
</script>
  
<style scoped>
  .adcategory-menu {
    border: 1px solid #cf4040;
    background-color: #ffffff;
    position: fixed;
    --el-menu-active-color:#e51111;
  }
  .el-menu-item {
    transition: transform 0.3s ease, color 0.3s ease;
  }

  .el-menu-item:hover {
    color: #e51111; 
    transform: translateY(-3px);
  }
</style>
  