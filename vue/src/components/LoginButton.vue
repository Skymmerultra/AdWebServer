<template>
  <button class="login-Button" @click="openDialog">登录/注册</button>

  <!-- 登录弹窗 -->
  <el-dialog v-model="dialogVisible" title="AdWebServer" width="400px" @close="resetLoginForm" align-center center="true">
      <el-form :model="loginForm" label-width="80px">
          <el-form-item label="用户名">
              <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码">
              <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item class="login">
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
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="registerForm.password" type="password" placeholder="再次请输入密码"></el-input>
        </el-form-item>
        <el-form-item class="register">
          <el-button type="primary" @click="onRegisterSubmit">注册</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
</template>

<script>
export default {
  name: 'LoginButton',
  data() {
    return {
      userId:1,
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
        password:''
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
    resetLoginForm() {
      this.loginForm.username = '';
      this.loginForm.password = '';
    },
    resetRegisterForm() {
      this.registerForm.username = '';
      this.registerForm.password = '';
    },
    login(){
      //登录成功后，返回一个用户id
      this.$store.dispatch('login', this.userId)  // 登录，传入用户ID
      console.log(this.userId,this.$store.getters.isLoggedIn)
    }
  }
};
</script>

<style scoped>
  .login-Button {
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

  .login-Button:hover {
  background-color: #2563EB;
  transform: translateY(-1px);
  }

  .login-Button:active {
  background-color: #1D4ED8;
  transform: translateY(1px);
  }

  .login{
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

  .register{
    position: relative;
    left:75px;
  }
</style>
