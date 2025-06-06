<template>
  <div class="sidebar">
    <ul>
      <li><router-link to="/userManage">用户管理</router-link></li>
      <li><router-link to="/adPoManage">广告位管理</router-link></li>
      <li><router-link to="/orderManage">订单管理</router-link></li>
    </ul>
  </div>
</template>

<script>
export default {
    
}
</script>

<style scoped>
.sidebar {
  width: 200px;
  background-color: #f4f4f4;
  height: 100vh;
  padding: 20px;
}

.sidebar ul {
  list-style-type: none;
  padding: 0;
}

.sidebar ul li {
  margin-bottom: 10px;
}

.sidebar ul li a {
  text-decoration: none;
  color: #333;
}

.sidebar ul li a:hover {
  color: #007BFF;
}
</style>