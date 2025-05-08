<template>
  <el-card class="ad-card" @click="handleCardClick">
    <template #header>
      <div class="card-title">
       <span>{{ adInfo.information.name }}</span>
       <span>  <i class="far fa-hand-point-up" />{{ adInfo.information.clickNum }}</span>
      </div>    
    </template>
      <img class="ad-image"
          :src="adInfo.image_src[0]"
          alt="image"
      />
    <template #footer>
      <div class="card-content">{{ adInfo.information.content }}</div>
    </template>
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
      adId:null
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
      //根据广告id增加广告点击量
      axios.get("https://m1.apifoxmock.com/m1/6267385-5961501-default/adpo/ClickIncrement/${this.adId")
      this.$router.push({ name:'addetail', params: { adId: this.adId } }) ;
    },
  },
  created(){
    this.adId=this.adInfo?.information?.id;
    // console.log("广告位id为:",this.adId);
  }
}
</script>

<style scoped>
.ad-card {
  background-color: #ffffff;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  width: 500px;
  max-height: 650px;
}

.ad-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.card-title{
  display:flex;
  justify-content: space-between;
  align-items: center;
}

.ad-image{
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
  color: #1f2937;
  padding: 10px;
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
