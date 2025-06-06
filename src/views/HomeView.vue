<template>
  <div class="home-view">
    <el-container class="home-container">
        <el-header height="100px" class="header"><AppHeader
                                                  @search="search"
                                                  @reload="reload"/></el-header>
        <el-container class="header-under">
            <el-aside class="aside"><CategoryMenu
                                      :activeIndex="cateActiveIndex"
                                      @categoryNavigation="handleCategorySelect"/></el-aside>
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
    import AdCard from '../components/AdPoCard.vue'
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
            cateActiveIndex:''
          }
        },
        mounted() {
          this.fetchClickNumMaxData()
        },
        methods:{
          fetchClickNumMaxData(){
            axios.get("/back/adpo/getSomeAdPo")
            .then(response => {
            this.adInfos = response.data.data
            })
            .catch(error => {
            console.error("Error fetching ad data:", error);
            });
          },
          search(query){
            axios.get(`/back/adpo/getAdPoListByKeyWord?keyWord=${query}`)
            .then(response => {
              this.adInfos = response.data.data
            })
          },
          reload(){
             this.cateActiveIndex = '';
             this.fetchClickNumMaxData()
          },
          fetchCategoryData(index){
            axios.get(`/back/adpo/catenavi?category=${index}`)
            .then(response => {
              this.adInfos = response.data.data
            })
          },
          handleCategorySelect(index){
              this.cateActiveIndex = index
              this.fetchCategoryData(index)
          }
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
  .card-grid {
    max-height: 100%;
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px 10px;
  }
  /* 屏幕宽度较小时改为竖直排列 */
  @media (max-width: 768px) {
    .card-grid {
      grid-template-columns: 1fr;
      gap: 16px;
    }
  }
</style>