<template>
  <div class="user-view">
    <el-container class="user-container">
      <!-- 左侧栏 -->
      <el-aside class="sidebar" width="200px">
        <UserSidebar/>
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

    <el-dialog v-model="information_dialogVisable" @close="close_informationDialog" center>
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

    <el-dialog v-model="password_dialogVisable" @close="resetPassword" center>
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
</template>
  
  <script>
  import axios from 'axios';
  import UserSidebar from '@/components/UserSidebar.vue';
  export default {
    components:{
      UserSidebar
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
        information_dialogVisable:false,
        password_dialogVisable:false,
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
          this.information_dialogVisable=true;
          this.changeduserInfo.username=this.userInfo.username;
          this.changeduserInfo.sex=this.userInfo.sex;
          this.changeduserInfo.phone=this.userInfo.phone;
          this.changeduserInfo.address=this.userInfo.address;
          this.changeduserInfo.email=this.userInfo.email;
      },
      changeInformation_confirm(){
        axios.put(`https://m1.apifoxmock.com/m1/6267385-5961501-default/changeinformation`,this.changeduserInfo)
        .then( response => {
          if(response.data.code==200){
            console.log("修改成功");
          }
        })
      },
      open_changePassword(){
        this.password_dialogVisable=true;
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
        const response = await this.$axios.put("https://m1.apifoxmock.com/m1/6267385-5961501-default/user/changepassword",submitForm)
        if(response.data.code === "200"){
          this.$message.success('密码修改成功')
          this.password_dialogVisible = false
        }
        else if(response.data.code === "404"){
          this.$message.warning('密码修改失败,请检查原密码是否正确')
        }
        }catch (error) {
          // console.log(error)
          this.$message.error("表单输入有误")
       }
    },
  },
    created(){
      axios.get("https://m1.apifoxmock.com/m1/6267385-5961501-default/user/${this.$store.getters.getUserId}")
      .then(response => {
        this.userInfo=response.data.data;
        // alert("用户id为"+this.$store.getters.getUserId)
      })
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
  