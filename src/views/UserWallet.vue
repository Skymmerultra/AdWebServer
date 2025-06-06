<template>
    <el-container class="user-container">
      <!-- 左侧栏 -->
      <el-aside class="sidebar" width="200px">
        <UserSidebar/>
      </el-aside>
  
      <!-- 右侧内容 -->
      <el-main class="main-content">
        <div class="balance">
            <div class="balance-border">
            <p class="content-text">我的余额</p>
            <text class="balance-text">￥{{ balance }}</text>
            </div>
        </div>
        <div class="buttonGrid">
        <div class="order">
          <el-button class="buttonDiv" @click="open_orderRecord_dialog"
                    :icon="Tickets">订单记录</el-button>
        </div>
        <div class="recharge">
          <el-button class="buttonDiv" @click="open_recharge_dialog"
                    :icon="Wallet">钱包充值</el-button>
        </div>
        <div class="shoppingCart">
          <el-button class="buttonDiv" :icon="ShoppingCart">购物车</el-button>
        </div>
        <div class="stream">
          <el-button class="buttonDiv" :icon="Money">流水查询</el-button>
        </div>
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

      <el-dialog v-model="recharge_dialog" center>
      <el-form>
        <el-input type="number" v-model="rechargeNum" placeholder="请输入金额" style="width: 150px;"></el-input>
        <el-button @click="Recharge">确认</el-button>
      </el-form>
      </el-dialog>
    </el-container>
</template>
  
  <script>
  import { Money,Wallet,ShoppingCart,Tickets} from '@element-plus/icons-vue'
  import axios from 'axios';
  import UserSidebar from '@/components/UserSidebar.vue';
  export default {
    components:{
      UserSidebar,
     
    },
    data() {
      return {
        Tickets:Tickets,
        Money:Money,
        Wallet:Wallet,
        ShoppingCart:ShoppingCart,
        balance:null,
        userId:this.$store.getters.getUserId,
        orderRecord_dialog:false,
        recharge_dialog:false,
        orderArray:[],
        currentPage:1,
        pageSize:5,
        rechargeNum:{
          type:Number,
          default:0
        }
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
      async Recharge(){
        const params = new URLSearchParams();
        params.append("id",this.$store.getters.getUserId)
        params.append("rechargeNum",this.rechargeNum)
        await axios.post("/back/user/recharge",params)
        .then(
          response => {
            if(response.data.code == 200){
              this.$message.success("充值成功")
            }
          }
        )
        this.recharge_dialog=false

        this.fetchBalance()
      },
      open_orderRecord_dialog(event){
        this.orderRecord_dialog = true;
        event.target.blur();
      },
      open_recharge_dialog(){
        this.recharge_dialog = true;
      },
      close_orderRecord_dialog(){
        this.orderRecord_dialog = false;
      },
      fetchBalance(){
        axios.get(`/back/user/balance/${this.userId}`)
        .then(response => {
        this.balance=response.data.data;
      })
      },
      fetchOrder(){
        axios.get(`/back/user/order/${this.userId}`)
        .then(response => {
          this.orderArray=response.data.data;
        })
      },
      handlePageChange(page){
        this.currentPage=page;
      },
      goToAdPage(adPoId){
        this.$router.push({ name: 'addetail', params: { adPoId: adPoId } });
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
    height: 95vh;  /* 确保容器填充满视口高度 */
  }
  
  .sidebar {
    background-color: #ffffff;
  }
  
  /* 主体内容区域样式 */
.main-content {
    border-left:2px solid #000;
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 95vh;
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

#icon{
  font-size: 30px;
}

/*鼠标点击后移开，恢复本身样式*/
.buttonDiv, .buttonDiv:focus:not(.buttonDiv:hover){ 
    width:150px;
    padding: 10px 20px;
    border: 1px solid #2794f8;
    border-radius: 2px;
    box-shadow: 0 2px 4px 0 #f4f4f4;
    color: #2794f8;
    background: white;
}
/*鼠标悬浮，没有按下；鼠标按下后抬起，没有移开*/
.buttonDiv:focus, .buttonDiv:hover{
    background: #eaf5ff;
    border: 1px solid #2794f8 !important;
    color: #2794f8;
}
/*鼠标按下，没有抬起*/
.buttonDiv:active {
    background: #2794f8;
    color: white;
}

.buttonGrid{
   display: grid;
   grid-template-columns: repeat(2, 1fr);
   gap: 50px 200px;
}


</style>
  