<template>
  <div class="home-view">
    <el-container class="home-container">
        <el-header height="100px" class="header"><AppHeader/></el-header>
        <el-container class="header-under">
            <el-aside class="aside"><CategoryMenu/></el-aside>
            <el-main class="main">
                <div class="card-grid" >
                    <AdCard   v-for="(adInfo, index) in adInfos" 
                              :key="index"
                              :adInfo="adInfo"></AdCard>
                </div>
            </el-main>
        </el-container>
    </el-container>
  </div>
</template>

<script>
    import AppHeader from '../components/AppHeader.vue';
    import CategoryMenu from '../components/CategoryMenu.vue';
    import AdCard from '../components/AdCard.vue'
    import axios from 'axios'

    export default{
        components:{
            AppHeader,
            CategoryMenu,
            AdCard,
        },
        data(){
          return{
            adInfos:[],
          }
        },
        mounted() {
          axios.get("https://m1.apifoxmock.com/m1/6267385-5961501-default/homeview")
          .then(response => {
          this.adInfos = response.data.data
          })
          .catch(error => {
          console.error("Error fetching ad data:", error);
          });
        }
    }
</script>

<style scoped>
  .home-view{
    width:100%;
    height: 100vh;
  }
  .home-container{
    width:100%;
    height: 100%;
    display: flex;
    flex-direction: column;
  }
  .head-under{
    display: flex;
    flex-direction: row;
  }
  .header{
    width:100%;
    padding: 0;
  }
  .aside{
    width:150px;
  }
  .main{
    overflow-y: auto;  /* 开启垂直滚动 */
    padding:20px;
  }
  .card-grid {
    max-height: 100%;
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 50px 24px;
    /* padding: 20px; */
  }
  /* 屏幕宽度较小时改为竖直排列 */
  @media (max-width: 768px) {
    .card-grid {
      grid-template-columns: 1fr;
      gap: 16px;
    }
  }
</style>