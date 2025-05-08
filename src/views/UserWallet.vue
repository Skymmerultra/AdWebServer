<template>
    <el-container class="user-container">
      <!-- 左侧栏 -->
      <el-aside class="sidebar" width="200px">
        <el-menu default-active="2" class="user-menu" @select="handleSelect">
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
            <text class="balance-text">￥{{ balance }}</text>
            </div>
        </div>
        <div class="order">
          <el-button @click="open_orderRecord_dialog">订单记录</el-button>
        </div>
        <div class="recharge">
          <el-button>钱包充值</el-button>
        </div>
      </el-main>

      <el-dialog v-model="orderRecord_dialog" center @close="close_orderRecord_dialog">
        <el-table :data="paginatedOrders" class="orderRecord">
          <el-table-column label="订单编号" width="100px">
            <template #default="scope">
            <span @click="goToAdPage(scope.row.adPoId)" class="orderId">
              {{ scope.row.id }}
            </span>
            </template>
          </el-table-column>
          <el-table-column prop="time" label="下单时间" width="110px"/>
          <el-table-column prop="diliveryNum" label="投递次数" width="100px"/>
          <el-table-column prop="startTime" label="投递开始时间" width="110px"/>
          <el-table-column prop="endTime" label="投递结束时间" width="110px"/>
          <el-table-column prop="isInvoice" label="是否开具发票" width="110px"/>
        </el-table>
        <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="orderArray.length"
        @current-change="handlePageChange"
        layout="prev, pager, next" />
      </el-dialog>
    </el-container>
</template>
  
  <script>
  import axios from 'axios';
  export default {
    data() {
      return {
        balance:null,
        userId:this.$store.getters.getUserId,
        orderRecord_dialog:false,
        orderArray:[],
        currentPage:1,
        pageSize:5,
      };
    },
    computed:{
      // 计算分页后的订单数据
      paginatedOrders() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.orderArray.slice(start, end);
    }
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
          this.$router.push({name:'usercollection'});
        }
      },
      open_orderRecord_dialog(event){
        this.orderRecord_dialog = true;
        event.target.blur();
      },
      close_orderRecord_dialog(){
        this.orderRecord_dialog = false;
      },
      fetchBalance(){
        axios.get("https://m1.apifoxmock.com/m1/6267385-5961501-default/user/balance/${this.userId}")
        .then(response => {
        this.balance=response.data.data;
      })
      },
      fetchOrder(){
        axios.get("https://m1.apifoxmock.com/m1/6267385-5961501-default/user/order/1")
        .then(response => {
          this.orderArray=response.data.data;
        })
      },
      handlePageChange(page){
        this.currentPage=page;
      },
      goToAdPage(adPoId){
        this.$router.push({ name: 'addetail', params: { adId: adPoId } });
      }
    },
    created(){
      this.fetchBalance();
      this.fetchOrder();

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

.orderId{
  cursor:pointer;
  color:#4578b3
}
.orderId:hover{
  text-decoration: underline;
}
</style>
  