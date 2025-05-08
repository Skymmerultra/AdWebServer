<template>
  <button class="login-register-Button" @click="openDialog">登录/注册</button>

  <!-- 登录弹窗 -->
  <el-dialog v-model="dialogVisible" title="AdWebServer" width="400px" @close="resetLoginForm" align-center center="true">
      <el-form :model="loginForm" label-width="80px">
          <el-form-item label="用户名">
              <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码">
              <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item class="login-confirmButton">
              <el-button type="primary" @click="login">登录</el-button>
          </el-form-item>
      </el-form>
      <div class="form-footer">
      <div class="dialog-footer-left">
        <el-button @click="switchToRegister" text="true">注册账户</el-button>
      </div>
      <div class="dialog-footer-right">
        <el-button @click="forgotPassword" text="true">忘记密码</el-button>
      </div>
      </div>
  </el-dialog>

  <!-- 注册弹窗 -->
  <el-dialog v-model="registerVisible" title="注册AdWebServer" width="400px" @close="resetRegisterForm" align-center center="true">
      <el-form :model="registerForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="registerForm.password"  placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="registerForm.checkPassWord"  placeholder="再次请输入密码"></el-input>
        </el-form-item>
        <el-form-item class="register-confirmButton">
          <el-button type="primary" @click="Register">注册</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
</template>

<script>
import axios from 'axios';
export default {
  name: 'LoginButton',
  data() {
    return {
      // 弹窗显示状态
      dialogVisible: false,
      registerVisible: false,

      // 登录表单数据
      loginForm: {
        username: '',
        password: ''
      },
      //注册表单数据
      registerForm:{
        username:'',
        password:'',
        checkPassWord:''
      }
    };
  },
  methods: {
    openDialog() {
      this.dialogVisible = true;
    },
    switchToRegister() {
      this.dialogVisible = false;  
      this.registerVisible = true; 
    },
    switchToLogin(){
      this.registerVisible = false;
      this.dialogVisible = true;
    },
    resetLoginForm() {
      this.loginForm.username = '';
      this.loginForm.password = '';
    },
    resetRegisterForm() {
      this.registerForm.username = '';
      this.registerForm.password = '';
      this.registerForm.checkPassWord='';
    },
    login(){
      axios.get("https://m1.apifoxmock.com/m1/6267385-5961501-default/user/login",this.loginForm)
      .then(response => {
        if(response.data.code === "200"){
          //传入用户id
          this.$store.dispatch('login',response.data.data);
          this.$message.success("登录成功");
          this.dialogVisible = false;
        }
        else if(response.data.code === "404"){
          this.$message.error("登录失败,请稍后重试")
        }
      })
    },
    Register(){
      // console.log(this.registerForm);
      if(this.registerForm.password !== this.registerForm.checkPassWord){
        this.$message.error("两次密码输入不一致，请重新输入");
      }
      else{
      axios.get("https://m1.apifoxmock.com/m1/6267385-5961501-default/user/register",
        {
          username:this.username,
          password:this.password
        }
      )
      .then(response => {
        if(response.data.code === "200"){
          this.$message.success("账号注册成功")
          this.switchToLogin();
        }
        else if(response.data.code === "404"){
          this.$message.error("账号注册失败,请稍后重试")
        }
      })
      }
    }
  }
};
</script>

<style scoped>
.login-register-Button {
  height: 40px;
  padding: 0 24px;
  background-color: #3B82F6;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  float:right;
  }

  .login-register-Button:hover {
  background-color: #2563EB;
  transform: translateY(-1px);
  }

  .login-register-Button:active {
  background-color: #1D4ED8;
  transform: translateY(1px);
  }

  .login-confirmButton{
    position: relative;
    left:75px;
  }
  .form-footer {
  display: flex;
  justify-content: space-between;
  align-items: center; /* 垂直居中按钮 */
  padding: 10px 0; 
  }

  .dialog-footer-left,
  .dialog-footer-right {
    display: flex;
    align-items: center; 
  }

  .register-confirmButton{
    position: relative;
    left:75px;
  }
</style>
