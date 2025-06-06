<template>
  <div class="home-container">
    <Sidebar />
    <div class="main-content">
      <table>
        <thead>
          <tr>
            <th>用户 ID</th>
            <th>用户名</th>
            <th>用户具体信息</th>
            <th>用户在线状态</th>
            <th>操作</th>

          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.userId }}</td>
            <td>{{ user.username }}</td>
            <td>
              <button @click="showUserInfoModal = true; selectedUser = user">查看详情</button>
            </td>
            <td>{{ user.isOnline ? '在线' : '离线' }}</td>
            <td>
              <button 
                @click="forceOffline(user.userId)" 
                :disabled="!user.isOnline"
              >
                强制下线
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button @click="prevPage" :disabled="currentPage === 1">上一页</button>
        <span>{{ currentPage }}</span>
        <button @click="nextPage">下一页</button>
      </div>

      <!-- 弹窗 -->
      <div v-if="showUserInfoModal" class="modal">
        <div class="modal-content">
          <span class="close" @click="showUserInfoModal = false">&times;</span>
          <h3>用户详情</h3>
          <p><strong>性别:</strong> {{ selectedUser.sex }}</p>
          <p><strong>地址:</strong> {{ selectedUser.address }}</p>
          <p><strong>电话:</strong> {{ selectedUser.phone }}</p>
          <p><strong>邮箱:</strong> {{ selectedUser.email }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Sidebar from "@/components/AppSidebar.vue"

export default {
  components: {
    Sidebar
  },
  data() {
    return {
      users: [],
      currentPage: 1,
      showUserInfoModal: false,
      selectedUser: {}
    };
  },
  async created() {
    await this.fetchUsers();
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/alluser?page=${this.currentPage}`);
        this.users = response.data.data;
      } catch (error) {
        console.error('获取用户信息失败:', error);
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.fetchUsers();
      }
    },
    nextPage() {
      this.currentPage++;
      this.fetchUsers();
    },
    async forceOffline(userId) {
      try {
        const response = await axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/admin/outUser/${userId}`);
        // 检查 response.data 是否存在
        if (response && response.data) {
          // console.log(response.data);
          if (response.data.code !== 200) {
            if (this.$message) {
              this.$message.error(`用户${userId}强制下线失败`);
            } else {
              console.error(`用户${userId}强制下线失败`);
            }
            return;
          } else {
            if (this.$message) {
              this.$message.success(`用户${userId}强制下线成功`);
            } else {
              console.log(`用户${userId}强制下线成功`);
            }
          }
        } else {
          if (this.$message) {
            this.$message.error(`用户${userId}强制下线请求返回异常`);
          } else {
            console.error(`用户${userId}强制下线请求返回异常`);
          }
        }
        // 重新获取用户列表以更新状态
        await this.fetchUsers();
      } catch (error) {
        console.error('强制下线失败:', error);
        if (this.$message) {
          this.$message.error(`用户${userId}强制下线请求出错`);
        }
      }
    }
  }
}
</script>

<style scoped>
.home-container {
  display: flex;
}

.main-content {
  flex: 1;
  padding: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
}

.pagination {
  margin-top: 20px;
}

.pagination button {
  margin: 0 5px;
}

/* 弹窗样式 */
.modal {
  display: block;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0,0,0,0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 30%;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>
