<template>
  <div class="home-view">
    <el-container class="home-container">
        <el-header height="100px" class="header"><AppHeader
                                                  @search="search"
                                                  @reload="reload"/></el-header>
        <el-container class="header-under">
            <el-aside class="aside">
                <el-menu
                class="adcase-menu"
                :default-active="active_index"
                @select="handleSelect"
                defau>
                <el-menu-item index="5">全部广告</el-menu-item>
                <el-menu-item index="4">投递结束</el-menu-item>
                <el-menu-item index="3">正在投递</el-menu-item>
                <el-menu-item index="2">审核失败</el-menu-item>
                <el-menu-item index="1">正在审核</el-menu-item>
                </el-menu>
            </el-aside>
            <el-main class="main">
                <div class="ad-display">
                  
                        <adShowCard v-for="(adInfo, index) in adInfos" 
                              :key="index"
                              :adInfo="adInfo"
                              @updateOrderData="onUpdateOrderData"
                              @updateOrderInvoice="onupdateOrderInvoice"></adShowCard>
          
                </div>
            </el-main>
        </el-container>
    </el-container>
  </div>
</template>


<script>
import axios from 'axios'
import AppHeader from "../components/AppHeader.vue"
import adShowCard from "@/components/adShowCard.vue"
import { throttle } from 'lodash';
export default{
    components:{
    AppHeader,
    adShowCard,
    },
    data(){
        return {
            id:this.$store.getters.getUserId,
            adInfos:[],
            active_index:"5",

            fetchAllData: throttle(function() {
                axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/adhouse/${this.id}`)
                .then(response => {
                this.adInfos = response.data.data;})  
            }, 1000),
      
            fetchBackData: throttle(function() {
               axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/adhouse/cycle/${this.id}?cycle=end`)
               .then(response => {
                this.adInfos = response.data.data;
               })
            }, 1000),
      
            fetchMidData: throttle(function() {
                axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/adhouse/cycle/${this.id}?cycle=delivery`)
                .then(response => {
                  this.adInfos = response.data.data;
                })
            }, 1000),
      
            fetchFrontData: throttle(function() {
              axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/adhouse/cycle/${this.id}?cycle=inspection`)
              .then(response => {
              this.adInfos = response.data.data;})
            }, 1000),

            fetchFailData: throttle(function(){
              axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/adhouse/cycle/${this.id}?cycle=fail`)
              .then(response => {
                this.adInfos = response.data.data
              })
            },1000),
        }
    },
    methods:{
        // fetchAllData(){
        //   axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/adhouse/${this.id}`)
        //   .then(response => {
        //     this.adInfos = response.data.data;
        //   })

        // },
        // //查找正在审核的广告
        // fetchFrontData(){
          
        // },
        // fetchMidData(){

        // },
        // fetchBackData(){

        // },
        handleSelect(index){
            if (this.active_index === index) {
                return;  // 点击无反应
            }
            if(index == "5"){
                this.fetchAllData()
                this.active_index = "5"
            }
            else if(index == "4"){
                this.fetchBackData()
                this.active_index = "4"
            }
            else if(index == '3'){
                this.fetchMidData()
                this.active_index = "3"
            }
            else if(index == '2'){
                this.fetchFailData()
                this.active_index = "2"
            }
            else if(index == "1"){
                this.fetchFrontData()
                this.active_index = "1"
            }
        },
        deleteOrder(order_id){
          axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/order/delete/${order_id}`)
          .then(response => {
            if(response.data.code === 200){
              this.$message.success("订单删除成功")
            }
            else{
              this.$message.error("出错啦，请稍后试试")
            }
          })
        },
        onUpdateOrderData(order_id){
          this.deleteOrder(order_id)
          if(this.active_index == '1'){
            this.fetchFrontData()
          }
          else if(this.active_index =='2'){
            this.fetchFrontData()
          }
          else if(this.active_index =='3'){
            this.fetchBackData()
          }
          else if(this.active_index == '4'){
            this.fetchAllData()
          }
        },
        onupdateOrderInvoice(order_id){
          axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/order/invoice/${order_id}`)
          .then(response => {
            if(response.data.code === 200){
              this.$message.success("提交成功，等待管理员审核")
            }
            else{
              this.$message.error("出错啦,请稍后试试")
            }
          }
            
          )
        }
    },
    mounted(){
        this.fetchAllData();
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
  .adcase-menu{
    width:150px;
    position: fixed;
    border: 1px solid #cf4040;
    background-color: #ffffff;
    position: fixed;
    --el-menu-active-color:#e51111;
  }
  .el-menu-item {
    transition: transform 0.3s ease, color 0.3s ease;
    text-align: center;
  }

  .el-menu-item:hover {
    color: #e51111; 
    transform: translateY(-3px);
  }
 .ad-display {
    display:grid;
    grid-template-columns: repeat(1, 1fr);
 }
</style>
