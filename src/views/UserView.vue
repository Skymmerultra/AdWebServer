<template>
  <div class="user-view">
    <el-container class="user-container">
      <!-- 左侧栏 -->
      <el-aside class="sidebar" width="200px">
        <UserSidebar/>
      </el-aside>
  
      <!-- 右侧内容 -->
      <el-main class="main-content">
        <div class="avatar-container"  @click="uploadAvatar">
            <el-avatar class="avatar-image"
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            />
             <div class="upload-icon">
             <i class="fa-solid fa-arrow-up-from-bracket"></i>
             </div>
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
            <el-button class="button" @click="open_changeInformation">
                修改信息
            </el-button>
        </div>
        <div>
            <el-button class="button" @click="open_changePassword">
                修改密码
            </el-button>
        </div>
        <div>
            <el-button class="button" @click="logout">
                退出登录
            </el-button>
        </div>
      </el-main>
    </el-container>
  </div>

    <el-dialog v-model="information_dialogVisible" @close="close_informationDialog" center>
        <el-form :model="changeduserInfo" label-width="70px">
          <el-form-item label="用户名:">
            <el-input v-model="changeduserInfo.username" clearable/>
          </el-form-item>
          <el-form-item label="性别:">
            <el-select v-model="changeduserInfo.sex">
              <el-option value="男"></el-option>
              <el-option value="女"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="手机号:">
            <el-input  v-model="changeduserInfo.phone" clearable/>
          </el-form-item>
          <el-form-item label="住址:">
            <el-input v-model="changeduserInfo.address" clearable/>
          </el-form-item>
          <el-form-item label="邮箱:">
            <el-input  v-model="changeduserInfo.email" clearable/>
          </el-form-item>
          <el-form-item>
            <el-button @click="changeInformation_confirm">确认修改</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

    <el-dialog v-model="password_dialogVisible" @close="resetPassword" center>
        <el-form :model="passwordForm" :rules="rules" ref="changePassword">
        <el-form-item label="请输入原密码" prop="oldPassword" label-position="top">
            <el-input v-model="passwordForm.oldPassword"></el-input>
        </el-form-item>
        <el-form-item label="请输入新密码" prop="newPassword" label-position="top">
            <el-input v-model="passwordForm.newPassword"></el-input>
        </el-form-item>
        <el-form-item label="请再次输入新密码" prop="checkPassword" label-position="top">
            <el-input v-model="passwordForm.checkPassword"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="submitpasswordForm">确认修改</el-button>
        </el-form-item>
      </el-form>
      </el-dialog>

      <el-dialog v-model="avatar_dialogVisible" center width="300px">
        <div class="avatar-upload-container">
          <p>请上传您的头像</p>
          <el-upload 
            class="avatar-uploader"
            action="/back/user/changeavatar"
            method="put"
            :show-file-list="false"
            :data="{userId: this.$store.getters.getUserId}"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <!-- 关键修改：添加全屏覆盖的透明按钮 -->
            <div class="full-area-trigger"></div>
            
            <img v-if="avatarUrl" :src="avatarUrl" class="avatar" />
            <div v-else class="upload-placeholder">
              <el-icon class="avatar-uploader-icon"><Plus /></el-icon>
              <span>点击上传头像</span>
            </div>
          </el-upload>
          
          <div class="dialog-buttons">
            <el-button @click="avatar_dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="confirmAvatar">确定</el-button>
          </div>
        </div>
      </el-dialog>
</template>
  
  <script>
  import { ElMessage } from 'element-plus';
  import { Plus } from '@element-plus/icons-vue';
  import axios from 'axios';
  import UserSidebar from '@/components/UserSidebar.vue';
  export default {
    components:{
      UserSidebar,
      Plus
    },
    data() {
      // const validOldpassword = (rule,value,callback) => {
      //   const validOldform = {
      //     password:value,
      //     id:this.$store.getters.getUserId
      //   };
      //   axios.get("https://m1.apifoxmock.com/m1/6267385-5961501-default/validoldpassword",validOldform)
      //   .then(response => {
      //     if(response.data.code === "200"){
      //       callback();
      //     }
      //     else if(response.data.code === "404"){
      //       callback(new Error("密码输入错误"));
      //     }
      //   })
      // }
      // const validPasswordfomat = (rule,value,callback) => {
        
      // }
      return {
        information_dialogVisible:false,
        password_dialogVisible:false,
        avatar_dialogVisible:false,
        passwordForm:{
          oldPassword:"",
          newPassword:"",
          checkPassword:"",
        },
        changeduserInfo:{
          id:this.$store.getters.getUserId,
          username:"",
          sex:"",
          phone:"",
          address:"",
          email:"",
        },
        userInfo:{},
        avatarUrl:"",

        rules:{
          oldPassword:[
            {required:true,message:"请输入旧密码",trigger:'blur'},
          ],
          newPassword:[
            {required:true, message:"请输入新密码", trigger:'blur'},
            {min:6, max:12, message:"密码长度在6-12位"}
          ],
          checkPassword:[
            {required:true,message:"请再次输入密码",trigger:'blur'},
            {validator:this.validcheckPassword,trigger:['blur','change']}
          ]

        }
      };
    },
    methods:{
      logout(){
        this.$store.dispatch('logout');
        this.$router.push({name:'home'});
      },
      // handleClick(event) {
      //  // 触发按钮失去焦点，清除高亮状态
      // event.target.blur();
      // },
      open_changeInformation(){
          this.information_dialogVisible=true;
          this.changeduserInfo.username=this.userInfo.username;
          this.changeduserInfo.sex=this.userInfo.sex;
          this.changeduserInfo.phone=this.userInfo.phone;
          this.changeduserInfo.address=this.userInfo.address;
          this.changeduserInfo.email=this.userInfo.email;
      },
      async changeInformation_confirm(){
        await axios.put(`/back/user/changeInformation`,this.changeduserInfo)
        .then( response => {
          if(response.data.code==200){
            this.$message.success("修改成功")
            this.information_dialogVisable = false
          }
        })
        this.fetchUserinfo()
      },
      open_changePassword(){
        this.password_dialogVisible=true;
      },
      validcheckPassword(rule,value,callback) {
        if(value !== this.passwordForm.newPassword){
          callback(new Error("两次密码输入不一致"));
        }
        else{
          callback();
        }
      },
      resetPassword(){
        this.$refs.changePassword.resetFields();
      },
      async submitpasswordForm() {
      try {
        // this.$refs.changePassword.validate((valid) => {
        // if (valid) {
        // console.log('验证通过');
        // } else {
        // console.log('验证失败');
        // }
        // })
        //await this.$refs.changePassword.clearValidate();
        // console.log("开始校验表单");
        await this.$refs.changePassword.validate();
        // console.log("表单校验成功");
        // console.log("提交数据:", this.passwordForm);
        const submitForm={
          id:this.$store.getters.getUserId,
          oldpassword: this.passwordForm.oldPassword,
          newpassword: this.passwordForm.newPassword
        }
        // for (const [key, value] of Object.entries(submitForm)) {
        // console.log(`${key}:`, value);
        // }
        const response = await this.$axios.put("/back/user/changepassword",submitForm,{
          headers: {'content-Type':'application/x-www-form-urlencoded'}
        })
        if(response.data.code == "200"){
          this.$message.success('密码修改成功')
          this.password_dialogVisible = false
        }
        else if(response.data.code == "404"){
          this.$message.warning('密码修改失败,请检查原密码是否正确')
        }
        }catch (error) {
          // console.log(error)
          this.$message.error("表单输入有误")
       }
    },
    uploadAvatar(){
      this.avatar_dialogVisible = true
    },
    handleAvatarSuccess(response) {
      this.avatarUrl = response.data;
    },
    beforeAvatarUpload(rawFile) {
      if (rawFile.type !== 'image/jpeg') {
        ElMessage.error('Avatar picture must be JPG format!');
        return false;
      } else if (rawFile.size / 1024 / 1024 > 4) {
        ElMessage.error('Avatar picture size can not exceed 4MB!');
        return false;
      }
      return true;
    },
    fetchUserinfo(){
      axios.get(`/back/user/${this.$store.getters.getUserId}`)
      .then(response => {
        this.userInfo=response.data.data;
        // alert("用户id为"+this.$store.getters.getUserId)
      })
    }
    },
    created(){
      this.fetchUserinfo()
    }
  }
  </script>
  
  <style scoped>

  .user-view{
    width:100%;
    height:95vh;
  }
  .user-container {
    width:100%;
    height: 95vh;  
    display: flex;
    flex-direction: row;
  }
  .sidebar {
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
    text-align: center; 
    gap:20px/*调整上下间距*/
}

.avatar-container {
  position: relative;
  display: inline-block; /* 确保容器根据内容自适应 */
  overflow: hidden; /* 防止图标溢出 */
  border-radius: 50%; /* 保持和头像相同的圆形 */
}

/* 头像基础样式 */
.el-avatar {
  width:150px;
  height: 150px;
  display: block;
  transition: all 0.3s ease; /* 可选：添加头像的悬停效果 */
}

/* 上传图标容器 */
.upload-icon {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  text-align: center;
  background: rgba(0, 0, 0, 0.5); /* 半透明背景 */
  color: white;
  padding: 8px 0;
  transform: translateY(100%); /* 初始隐藏在下方 */
  opacity: 0;
  transition: all 0.3s ease;
  border-radius: 0 0 50% 50%; /* 底部圆角匹配头像 */
}

/* 悬停时显示上传图标 */
.avatar-container:hover .upload-icon {
  transform: translateY(0); /* 上浮显示 */
  opacity: 1;
}

 /*上传头像弹窗*/
.avatar-upload-container {
  position: relative;
  text-align: center;
  padding: 20px;
}

.avatar-uploader {
  position: relative;
  width: 150px;
  height: 150px;
  margin: 0 auto 20px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}

.avatar-uploader:hover{
  border-color: aqua;
}

.full-area-trigger {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
}


.avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.upload-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #8c939d;
}

.avatar-uploader-icon {
  font-size: 28px;
}

.dialog-buttons {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  gap: 10px;
}




.descriptions{
    width:800px;
}
.label {
  font-size: 16px;
  padding: 10px 10px;
}
.button {
    color: rgb(22, 99, 72);  /* 按钮文字颜色 */
    width: 500px;  /* 按钮宽度 */
    font-size: 16px;  /* 字体大小 */
    background-color: #ffffff;  /* 默认背景色 */
    border: 2px solid rgb(22, 99, 72);  /* 默认边框颜色 */
    padding: 10px 20px;  /* 增加内边距 */
    border-radius: 4px;  /* 圆角边框 */
    transition: background-color 0.3s, color 0.3s, border-color 0.3s;  /* 平滑过渡 */
}

.button:hover {
    color: rgb(255, 255, 255);  /* 悬停时文字颜色变为白色 */
    background-color: rgb(121, 191, 193);  /* 悬停时背景色变为紫色 */
    border-color: rgb(73, 42, 168);  /* 悬停时边框颜色变为紫色 */
}


  </style>
  