<template>
    <el-container class="user-container">
      <!-- 左侧栏 -->
      <el-aside class="sidebar" width="200px" position="fixed">
        <UserSidebar/>
      </el-aside>
  
      <!-- 右侧内容 -->
      <el-container>
        <el-header> <div class="main-header"><SearchBox
                                              @search="search"/></div></el-header>
        <el-main>
          <el-scrollbar>
            <div class="card-grid" >
                        <AdCard   v-for="(adInfo, index) in adInfos" 
                                  :key="index"
                                  :adInfo="adInfo"></AdCard>
            </div>
          </el-scrollbar>
      </el-main>
    </el-container>
    </el-container>
</template>
  
  <script>
  import axios from 'axios';
  import SearchBox from '@/components/SearchBox.vue';
  import AdCard from '@/components/AdPoCard.vue';
  import UserSidebar from '@/components/UserSidebar.vue';
  export default {
    components:{
      SearchBox,
      AdCard,
      UserSidebar
    },
    data() {
      return {
        adInfos:[]
      };
    },
    methods:{
      search(query){
        axios.get(`/back/user/findFavorites?keyWord=${query}&userId=${this.$store.getters.getUserId}`)
        .then(response => {
          this.adInfos=response.data.data
        })
      }
    },
    created(){
      axios.get(`/back/user/favorites/${this.$store.getters.getUserId}`)
      .then(response => {
        this.adInfos=response.data.data;
        // console.log(this.adInfos);
      })
      .catch(error => {
      console.error('API 请求失败', error);
      });

    }
}
  </script>
  
  <style scoped>
  .user-container {
    height: 95vh;
  }
  
  .sidebar {
  }
  
 .main-header{
    top: 0;
    width:100%;
    z-index: 10;
    display: flex;
    justify-content: flex-end;
    border-bottom: 2px solid #000000;
    padding:5px;
  }
  .card-grid {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      /* 上下距离，左右距离 */
      gap: 24px 20px; 
  }
</style>
  