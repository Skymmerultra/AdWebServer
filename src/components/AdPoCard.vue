<template>
  <el-card class="ad-card" @click="handleCardClick">
    <template #header>
      <div class="card-title">
       <span>{{ adInfo?.information?.name }}</span>
       <span>  <i class="far fa-hand-point-up" />{{ adInfo?.information?.clickNum }}</span>
      </div>    
    </template>
    <div class="card-content">{{ adInfo?.information?.content }}</div>

    <div class="line"></div>

      <img class="ad-image"
          :src="adInfo?.image_src[0]"
          alt="image"
      />
      
  </el-card>
</template>

<script>
import axios from 'axios';
export default {
  name: 'AdCard',
  computed:{
    isLoggedIn(){
      return this.$store.getters.isLoggedIn;
    },
    UserId(){
      return this.$store.getters.UserId;
    }
  },
  data(){
    return{
      adPoId:null
    }
  },
  props: {
    adInfo: { 
      type: Object,
      required: true
    }
  },
  methods: {
    handleCardClick() {
      //根据广告id增加广告点击量,同时记录用户浏览行为
      axios.get(`/back/adpo/ClickIncrement/${this.adPoId}`)
      axios.post("/back/user/viewIncrement",
        {
          userId:this.$store.getters.getUserId,
          adPoId:this.adPoId
        },
        {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        }
      )
      this.$router.push({ name:'addetail', params: { adPoId: this.adPoId } }) ;
    },
  },
  created(){
    this.adPoId=this.adInfo?.information?.id;
    // console.log("广告位id为:",this.adPoId);
  }
}
</script>

<style scoped>
.ad-card {
  background-color: #ffffff;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  width: 350px;
  height: 400px;
}

/* 使用深度选择器穿透组件作用域 */
.ad-card :deep(.el-card__header) {
  height: 50px; /* 强制 header 高度 */
}

.ad-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.card-title{
  display:flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
  font-weight: 600;
  color: #1f2937;
}

.ad-image{
  max-width:100%;
  object-fit: cover;
}
.line{
  width:100%;
  height:0;
  border: 1px solid #888282;
  margin-top:3px;
  margin-bottom:3px;
}

.card-content {
  padding: 10px;
  font-size: 14px;
  color: #4a4a4a;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
