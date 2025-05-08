<template>
  <div class="ad-detail-view">
  <div class="ad-detail-header"><AppHeader/></div>
  <div class="ad-detail-container">
    <!-- 走马灯展示图片 -->
    <div class="left">
      <el-carousel trigger="click"  class="image-container">
        <el-carousel-item v-for="(image, index) in adInfo?.image_src || []" :key="index">
          <img :src="image" alt="Advertisement Image"  class="ad-image"/>
        </el-carousel-item> 
      </el-carousel>
    </div>

    <div class="right">
      <h1 >{{ adInfo?.information?.name }}</h1>

      <u class="adInfo-content">{{ adInfo?.information?.content }}</u>

      <el-descriptions 
        class="descriptions"
        column="1"
        label-width="150px"
        >
        <el-descriptions-item>
          <template #label>
            <div class="descriptions-label">投递平台:</div>
          </template>
          {{ adInfo?.information?.platform }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="label">类型:</div>
          </template>
          {{ adInfo?.information?.type }}
        </el-descriptions-item>
      </el-descriptions> 

      <h2>定位</h2>
      <div style="display: flex; flex-wrap: wrap;">
        <span v-for="(category, index) in adInfo?.categories" :key="index">
          /{{ category }}
          </span>
      </div>


      <p class="ad-price"><strong>售价: </strong><u>￥{{ adInfo?.information?.price}}</u></p>
      <el-button type="warning" size="large" @click="purchase">立即购买</el-button>
    </div>

    <el-dialog 
    v-model="dialogVisible" 
    :close-on-click-modal="false"
    class="purchase-dialog" 
    center
    @close="ReSetForm" 
    >
    
    <el-form>
      <el-form-item>
        <!--<el-upload
              ref="upload"
              :file-list="fileList"
              action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
              :before-upload="beforeUpload"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :limit="1"
              :on-exceed="handleExceed"
              :auto-upload="false"
              accept="image/*,video/*"
            >
              <el-button type="primary">Click to upload</el-button>
              <template #tip>
                <div class="el-upload__tip">
                  请上传图片或视频
                </div>
              </template>
        </el-upload> -->

        <input type="file" ref="fileInput" accept="image/*,video/*,pdf" @change="handleFileChange"/>
      </el-form-item>
      <el-form-item label="投递次数">
        <el-input-number v-model="diliveryNum" :min="1" :max="100"></el-input-number>
      </el-form-item>
      <el-form-item label="投递时间段">
        <el-date-picker
        v-model="dateRange"
        type="daterange"
        unlink-panels
        range-separator="To"
        start-placeholder="Start date"
        end-placeholder="End date"
        :picker-options="pickerOptions"
      />
      </el-form-item>
      <el-form-item label="是否开具发票">
        <el-switch v-model="isInvoice"></el-switch>
      </el-form-item>
      <el-form-item label="请选择支付方式">
        <el-radio-group v-model="payType">
          <el-radio value="1">支付宝</el-radio>
          <el-radio value="2">微信</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="最终售价：">
          {{ payment }}
      </el-form-item>
      <el-form-item>
          <el-button @click="purchase_confirm">确认购买</el-button>
      </el-form-item>
    </el-form>
    </el-dialog>
    
  </div>
</div>
</template>

<script>
import axios from 'axios';
import AppHeader from '../components/AppHeader.vue';
import {ElMessage} from 'element-plus';
import { ElMessageBox } from 'element-plus';
export default {
  components:{
    AppHeader,
  },
  computed:{
    isLoggedIn(){
      return this.$store.getters.isLoggedIn;
    },
    payment(){
      return this.adInfo?.information?.price*this.diliveryNum;
    },
    userId(){
      return this.$store.getters.userId;
    }
  },
  data() {
    return {
      adInfo: {},
      dialogVisible:false,
      adId:null,
      payType:null,
      diliveryNum: 1,
      isInvoice: false,
      dateRange: [new Date(), new Date()],
      pickerOptions: {
        disabledDate(time) {
          // 禁用当前日期之前的日期
          return time < new Date(); // 返回 true 禁用当前日期之前的日期
        },
      },
      //fileList:[],
      file:null
    };
  },
  created() {
    this.adId = this.$route.params.adId;
    // console.log("adcard传递的广告id为",this.adId);
  },
  methods:{
    purchase() {
        if (!this.isLoggedIn) {
        ElMessageBox.alert(
        '登录后方可购买',
        {
          center: true,
        }
        )
        .then(() => {
        })
        .catch(() => {
        })
        } else {
           //处理购买逻辑
           this.dialogVisible=true;
        }
    },
    handleFileChange(){
      const inputElement = this.$refs.fileInput; 
      const files = inputElement.files; 
      if(files.length > 0){
        // console.log("有上传文件");
        this.file=files[0];
        const allowedTypes = ['image/jpeg', 'image/png', 'video/mp4', 'application/pdf'];
        if (!allowedTypes.includes(this.file.type)) {
          alert('只能上传图片、视频或PDF文件');
          this.$refs.fileInput.value = '';  // 清空文件输入框
          return;
        }
        // console.log("文件名:",this.file.name);
      }
      else{
        // console.log("无上传文件");
      }
    },
    ReSetForm(){
      this.$refs.fileInput.value = '';
      this.diliveryNum = 1;
      this.dateRange = [new Date(), new Date()];
      this.isInvoice = false;
      this.payType = null;
    },
    purchase_confirm(){
      // if (!this.fileList.length) {
      // ElMessage.error('请上传素材文件');
      // return;
      // }
      if(!this.file){
        ElMessage.error('请上传广告素材');
        return;
      }
      if (!this.payType) {
      ElMessage.error('请选择支付方式');
      return;
      }
      const formData = new FormData();
      formData.append('file', this.file);
      formData.append('adPoId', this.adId);
      formData.append('userId', this.$store.getters.getUserId)
      formData.append('deliveryNum', this.diliveryNum);
      formData.append('startTime', this.dateRange[0].toISOString());
      formData.append('endTime', this.dateRange[1].toISOString());
      formData.append('isInvoice', this.isInvoice);
      formData.append('payment', this.payment);
      formData.append('payType', this.payType);
      // for (let [key, value] of formData.entries()) {
      // console.log(`${key}:`, value);
      // }
      axios.post('https://m1.apifoxmock.com/m1/6267385-5961501-default/order',formData)
      .then(response => {
        if(response.data.code === "200") { 
          alert("购买成功");
        }
        else if(response.data.code === "404") {
          alert("购买失败");
        }
        else if(response.code === "404") {
          console.log("发送失败");
        }
        }
      )
    },
    // handleRemove(file) {
    // const index = this.fileList.indexOf(file);
    // this.fileList.splice(index, 1);
    // },
    // handleExceed(files) {
    // this.fileList = [files[0]];
    // },
    // beforeUpload(file) {
    // this.fileList = [file];
    // return false; // 手动上传
    // }
  },
  mounted() {
  axios.get("https://m1.apifoxmock.com/m1/6267385-5961501-default/adpo/15")
    .then(response => {
      this.adInfo = response.data.data;
      // alert("广告id为:"+this.adId);
    })
    .catch(error => {
      console.error("There was an error fetching data:", error);
    });
  }
}
</script>

<style scoped>

.ad-detail-view{
  display: flex;
  flex-direction: column;
}
.ad-detail-container {
  background-color: rgb(255, 255, 255);
  display: flex;
  flex-direction: row;
  gap: 40px;
  align-items: flex-start;
}

.left {
  background-color: rgb(154, 143, 143);
  width: 40%;
  height: 75vh; 
  margin-top: 10%;
}

.image-container{
  width:100%;
  height:100%;
}

::v-deep .el-carousel__container,
::v-deep .el-carousel__item {
  height: 100% !important; /* 强制继承 */
}

.ad-image {
  width:100%;
  height:100%;
  object-fit: cover; /* 保持图片比例，填充整个容器 */
} 

.right {
  margin-top:10%;
  display: flex;
  flex-direction: column;
  width: 45%; /* 设置右侧区域宽度 */
  height: 75vh;

}

.adInfo-content {
  font-size: 20px;
  color: #956262;
}

.descriptions{
  margin-top:20px;
}
:deep(.descriptions .el-descriptions) {
  background-color: inherit; /* 继承父容器背景色 */
}

/* 修改标签和内容单元格样式 */
:deep(.descriptions .el-descriptions__label){
  font-size: 20px; 
}
:deep(.descriptions .el-descriptions__content) {
  font-size:20px;
  color:aqua
}

.ad-price{
  font-size: 40px;
  color: rgb(193, 91, 84);
}

</style>
