<template>
  <div class="login-container">
    <h2>管理员登录</h2>
    <form @submit.prevent="handleLogin">
      <div>
        <label for="username">用户名:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div>
        <label for="password">密码:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit">登录</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      username: '',
      password: ''
    };
  },
  methods: {
    async handleLogin() {
      try {
        const response = await axios.post('/api/admin/login', {
          username: this.username,
          password: this.password
        });
        if(response.data.code === 200){
          this.$message.success('登录成功');
          this.$router.push({name:'UserManage'});
        }
        } catch (error) {
          // 处理登录失败逻辑，例如显示错误信息
          this.$router.push({name:'userManage'});
          console.error('登录失败:', error);
        }
    }
  }
};
</script>

<style scoped>
.login-container {
  width: 80%; /* 使用百分比宽度，增加灵活性 */
  max-width: 400px; /* 限制最大宽度 */
  margin: 20vh auto; /* 使用视口高度单位，垂直居中 */
  padding: 30px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.login-container h2 {
  text-align: center;
  margin-bottom: 20px;
}

.login-container div {
  margin-bottom: 15px;
}

.login-container label {
  display: block;
  margin-bottom: 5px;
  font-weight: 600;
}

.login-container input {
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
  box-sizing: border-box;
}

.login-container button {
  width: 100%;
  padding: 12px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.login-container button:hover {
  background-color: #0056b3;
}

/* 适配小屏幕 */
@media (max-width: 480px) {
  .login-container {
    width: 90%;
    padding: 20px;
  }
}
</style>
