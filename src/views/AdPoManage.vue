<template>
  <div class="home-container">
    <Sidebar />
    <div class="main-content">
      <table>
        <thead>
          <tr>
            <th>广告位 ID</th>
            <th>图片</th>
            <th>广告位点击量</th>
            <th>查看详情</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="adPo in adPos" :key="adPo.id">
            <td>{{ adPo.information.id}}</td>
            <td><img :src="adPo.image_src[0]" alt="广告位图片" width="100"></td>
            <td>{{ adPo.information.clickNum }}</td>
            <td>
              <button @click="showAdPoInfoModal = true; selectedAdPo = adPo">查看详情</button>
            </td>
            <td>
              <!-- 这里可以添加具体的操作按钮 -->
              <button @click="deleteAdPo(adPo.information.id)">删除</button>
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
      <div v-if="showAdPoInfoModal" class="modal">
        <div class="modal-content">
          <span class="close" @click="showAdPoInfoModal = false">&times;</span>
          <h3>广告位详情</h3>
          <p><strong>广告位 ID:</strong> {{ selectedAdPo.information.id }}</p>
          <p><strong>广告位名称:</strong> {{ selectedAdPo.information.name }}</p>
          <p><strong>广告位内容:</strong> {{ selectedAdPo.information.content }}</p>
          <p><strong>广告位价格:</strong> {{ selectedAdPo.information.price }}</p>
          <p><strong>广告位点击量:</strong> {{ selectedAdPo.information.clickNum }}</p>
          <p><strong>投放平台:</strong> {{ selectedAdPo.information.platform }}</p>
          <p><strong>广告位类型:</strong> {{ selectedAdPo.information.type }}</p>
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
      adPos: [],
      currentPage: 1,
      showAdPoInfoModal: false,
      selectedAdPo: {}
    };
  },
  async created() {
    await this.fetchAdPos();
  },
  methods: {
    async fetchAdPos() {
      try {
        // 请替换为实际的 API 地址
        const response = await axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/admin/listadPo?page=${this.currentPage}`);
        this.adPos = response.data.data;
      } catch (error) {
        console.error('获取广告位信息失败:', error);
      }
    },
    deleteAdPo(orderId) {
      axios.delete(`https://m1.apifoxmock.com/m1/6267385-5961501-default/admin/deleteadPo/${orderId}`)
        .then(response => {
        if (response.data.code === 200) {
          this.fetchAdPos(); // 刷新广告位列表
        }
      })
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.fetchAdPos();
      }
    },
    nextPage() {
      this.currentPage++;
      this.fetchAdPos();
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