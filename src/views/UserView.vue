<template>
    <el-container class="user-container">
      <!-- 左侧栏 -->
      <el-aside class="sidebar" width="200px">
        <el-menu default-active="1" class="user-menu" @select="handleSelect">
          <el-menu-item index="1">基本信息</el-menu-item>
          <el-menu-item index="2">个人钱包</el-menu-item>
          <el-menu-item index="3">个人收藏</el-menu-item>
          <el-menu-item index="4">浏览历史</el-menu-item>
        </el-menu>
      </el-aside>
  
      <!-- 右侧内容 -->
      <el-main class="main-content">
        <div class="avatar-container" >
            <el-avatar class="avatar-image"
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            />
        </div>
        <div class="information-container">
            <el-descriptions
            class=descriptions
            column="1"
            label-width="50px"
            border
            >
                <el-descriptions-item>
                <template #label>
                    <div class="label">Username</div>
                </template>
                {{ userInfo.username }}
                </el-descriptions-item>

                <el-descriptions-item>
                    <template #label>
                    <div class="label">Sex</div>
                </template>
                {{ userInfo.sex }}
                </el-descriptions-item>

                <el-descriptions-item>
                    <template #label>
                    <div class="label">Phone</div>
                </template>
                {{ userInfo.phone }}
                </el-descriptions-item>

                <el-descriptions-item>
                    <template #label>
                    <div class="label">Address</div>
                </template>
                {{ userInfo.address }}
                </el-descriptions-item>

                <el-descriptions-item>
                    <template #label>
                    <div class="label">Email</div>
                </template>
                {{ userInfo.email }}
                </el-descriptions-item>
            </el-descriptions>
        </div>
        <div>
            <el-button class="button" @click="change">
                修改信息
            </el-button>
        </div>
        <div>
            <el-button class="button" @click="logout">
                退出登录
            </el-button>
        </div>
      </el-main>

      <el-dialog v-model="dialogVisable">
        <el-form :model="changeduserInfo">
          <el-form-item label="用户名">
            <el-input :model="changeduserInfo.username"/>
          </el-form-item>
          <el-form-item label="性别">
            <el-input  :model="changeduserInfo.sex"/>
          </el-form-item>
          <el-form-item label="手机号">
            <el-input  :model="changeduserInfo.phone"/>
          </el-form-item>
          <el-form-item label="住址">
            <el-input :model="changeduserInfo.address"/>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input  :model="changeduserInfo.email"/>
          </el-form-item>
          <el-form-item>
            <el-button @click="change_confirm"></el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-container>
</template>
  
  <script>
  import axios from 'axios';
  export default {
    data() {
      return {
        dialogVisable:false,
        changeduserInfo:{
          id:this.$store.getters.getUserId,
          username:"",
          sex:"",
          phone:"",
          address:"",
          email:"",
        },
        userInfo: {
        username: "Skymmer",
        sex: "男",
        phone: "18100000000",
        address: "Suzhou",
        email: "3358048998@QQ.com"
      }
      };
    },
    methods:{
      handleSelect(index){
        if(index=="1"){
          this.$router.push({ name: 'userview' });
        }
        else if(index=="2"){
          this.$router.push({name:'userwallet'});
        }
      },
      logout(){
        this.$store.dispatch('logout');
        this.$router.push({name:'home'});
      },
      change(){
          this.dialogVisable=true;
      },
      change_confirm(){
        axios.put(`https://m1.apifoxmock.com/m1/6267385-5961501-default/changeinformation`,this.changeduserInfo)
        .then( response => {
          if(response.data.code==200){
            console.log("修改成功");
          }
        })
      },

    },
    mounted(){

    }
  };
  </script>
  
  <style scoped>
  .user-container {
    height: 100vh;  
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
    /* 主轴为垂直方向 */
    flex-direction: column;
    /* 集中在主轴中央方向显示 */
    justify-content: center;
    /* 集中在交叉轴中央方向显示 */
    align-items: center;
    height: 100vh;
    text-align: center; 
    gap:40px/*调整上下间距*/
}

.avatar-image {
  width: 150px; /* 调整头像的尺寸 */
  height: 150px;
  border-radius: 50%; /* 保证头像圆形 */
}

.descriptions{
    width:800px;
}
.label {
  font-size: 16px;
  padding: 10px 10px;
}
.button{
    color: rgb(22, 99, 72);
    width:500px;
    font-size: 16px;
}
  </style>
  