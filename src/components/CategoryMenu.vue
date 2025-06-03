<template>
    <div class="category-menu">
      <el-menu
        class="adcategory-menu"
        width="150px"
        :default-active="active_index"
        @select="handleSelect"
      >
        <el-menu-item index="network">
          网络营销
        </el-menu-item>
        <el-menu-item index="wechat">
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
    props: ['activeIndex'], // 接收父组件传递的当前激活项
    watch: {
      activeIndex: {
        immediate: true, 
        handler(newVal) {
          this.active_index = newVal;
        }
      }
    },
    data(){
      return{
        active_index:''
      }
    },
    methods: {
      handleSelect(index) {
        if(this.active_index == index)
          return;
        this.$emit("categoryNavigation",index)
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
    color:black;
    transition: transform 0.3s ease, color 0.3s ease;
  }

  .el-menu-item:hover {
    color: #e51111; 
    transform: translateY(-3px);
  }

  ::v-deep .el-menu-item.is-active {
  color: #e51111 !important;
  }
</style>
  