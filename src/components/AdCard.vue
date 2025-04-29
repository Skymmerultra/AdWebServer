<template>
  <el-card class="ad-card" @click="handleCardClick">
    <template #header>
      <div class="card-title">{{ adInfo.information.name }}</div>
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

  props: {
    adInfo: { 
      type: Object,
      required: true
    }
  },
  methods: {
    handleCardClick() {
      //post传递用户id和广告id
      this.$router.push({ name:'addetail', params: { adId: this.adInfo.information.id } }) ;
    },
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
