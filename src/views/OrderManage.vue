<template>
  <div class="container">
    <AppSidebar />
    <div class="main-content">
      <div class="tab-container">
        <button class="tab-button" :class="{ active: activeTab === 'all' }" @click="changeTab('all')">全部订单</button>
        <button class="tab-button" :class="{ active: activeTab === 'unreviewed' }" @click="changeTab('unreviewed')">未审核订单</button>
        <button class="tab-button" :class="{ active: activeTab === 'reviewed' }" @click="changeTab('reviewed')">已审核订单</button>
      </div>
      <div v-if="activeTab === 'all'">
        <table class="order-table">
          <thead>
            <tr>
              <th>提交时间</th>
              <th>订单 ID</th>
              <th>提交用户 ID</th>
              <th>详细情况</th>
              <th>状态</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="order in allOrders" :key="order.id">
              <td>{{ order.time }}</td>
              <td>{{ order.id }}</td>
              <td>{{ order.userId }}</td>
              <td>
                <button @click="showDetail(order.id)">查看详情</button>
              </td>
              <td>
                <span v-if="order.cycle == 'inspection' ">
                  <button @click="handleReview(order.id, 'success')">审核通过</button>
                  <button @click="handleReview(order.id, 'fail')">审核失败</button>
                </span>
                <span v-else-if="order.cycle == 'fail'" class="fail-mark">×</span>
                <span v-else class="checkmark">✓</span>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="pagination">
        <button @click="prevPage('all')" :disabled="currentPageAll === 1">上一页</button>
        <span>第 {{ currentPageAll }} 页，共 {{ this.totalAll }} 页</span>
        <button @click="nextPage('all')" :disabled="currentPageAll === this.totalAll">下一页</button>
      </div>
    </div>
    <div v-if="activeTab === 'unreviewed'">
      <table class="order-table">
        <thead>
            <tr>
              <th>提交时间</th>
              <th>订单 ID</th>
              <th>提交用户 ID</th>
              <th>详细情况</th>
              <th>状态</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="order in unreviewedOrders" :key="order.id">
              <td>{{ order.time }}</td>
              <td>{{ order.id }}</td>
              <td>{{ order.userId }}</td>
              <td>
                <button @click="showDetail(order.id)">查看详情</button>
              </td>
              <td>
                <span v-if="order.cycle == 'inspection' ">
                  <button @click="handleReview(order.id, 'success')">审核通过</button>
                  <button @click="handleReview(order.id, 'fail')">审核失败</button>
                </span>
                <span v-else-if="order.cycle == 'fail'" class="fail-mark">×</span>
                <span v-else class="checkmark">✓</span>
              </td>
            </tr>
          </tbody>
      </table>
      <div class="pagination">
        <button @click="prevPage('unreviewed')" :disabled="currentPageUnreviewed === 1">上一页</button>
        <span>第 {{ currentPageUnreviewed }} 页，共 {{ this.totalUnreviewed }} 页</span>
        <button @click="nextPage('unreviewed')" :disabled="currentPageUnreviewed === this.totalUnreviewed ">下一页</button>
      </div>
    </div>
    <div v-if="activeTab === 'reviewed'">
      <table class="order-table">
        <thead>
          <tr>
            <th>提交时间</th>
            <th>订单 ID</th>
            <th>提交用户 ID</th>
            <th>投递平台</th>
            <th>详细情况</th>
            <th>状态</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in reviewedOrders" :key="order.id">
            <td>{{ order.time }}</td>
            <td>{{ order.id }}</td>
            <td>{{ order.userId }}</td>
            <td>{{ order.platform }}</td>
            <td>
              <button @click="showDetail(order.id)">查看详情</button>
            </td>
            <td>
              <span v-if="order.cycle == 'fail'" class="fail-mark">×</span>
              <span v-else class="checkmark">✓</span>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button @click="prevPage('reviewed')" :disabled="currentPageReviewed === 1">上一页</button>
        <span>第 {{ currentPageReviewed }} 页，共 {{ this.totalReviewed }} 页</span>
        <button @click="nextPage('reviewed')" :disabled="currentPageReviewed === this.totalReviewed">下一页</button>
      </div>
    </div>
    <div v-if="dialogVisible" class="dialog-overlay">
      <div class="dialog">
        <div id="order-detail-modal">
          <h2>订单详情</h2>
          <p>提交时间: {{ orderDetail.time }}</p>
          <p>订单 ID: {{ orderDetail.id }}</p>
          <p>提交用户 ID: {{ orderDetail.userId }}</p>
          <p>投递平台: {{ orderDetail.platform }}</p>
          <p>投递案例 ID: {{ orderDetail.deliveryCaseId }}</p>
          <p>广告位 ID: {{ orderDetail.adPoId }}</p>
          <p>图片地址: {{ orderDetail.image_src }}</p>
          <p>订单周期: {{ orderDetail.cycle }}</p>
          <p>是否开发票: {{ orderDetail.isInvoice }}</p>
          <p>开始时间: {{ orderDetail.startTime }}</p>
          <p>结束时间: {{ orderDetail.endTime }}</p>
          <p>计划投递数量: {{ orderDetail.deliveryNum }}</p>
          <p>实际投递数量: {{ orderDetail.realDeliveryNum }}</p>
        </div>
        <button @click="dialogVisible = false">关闭</button>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios';
import AppSidebar from "@/components/AppSidebar.vue"

export default {
  components: {
    AppSidebar
  },
  data() {
    return {
      activeTab: 'all',
      allOrders: [],
      unreviewedOrders: [],
      reviewedOrders: [],
      dialogVisible: false,
      orderDetail: {},
      currentPageAll: 1,
      currentPageUnreviewed: 1,
      currentPageReviewed: 1,
      totalAll: 0,
      totalUnreviewed: 0,
      totalReviewed: 0,
    };
  },
  mounted() {
    this.fetchOrders(this.activeTab);
  },
  methods: {
    changeTab(tab) {
      this.activeTab = tab;
      switch (tab) {
        case 'all':
          this.currentPageAll = 1;
          break;
        case 'unreviewed':
          this.currentPageUnreviewed = 1;
          break;
        case 'reviewed':
          this.currentPageReviewed = 1;
          break;
      }
      this.fetchOrders(tab);
    },
    fetchOrders(tab) {
      let currentPage = 0;
      switch (tab) {
        case 'all':
          currentPage = this.currentPageAll;
          break;
        case 'unreviewed':
          currentPage = this.currentPageUnreviewed;
          break;
        case 'reviewed':
          currentPage = this.currentPageReviewed;
          break;
      }
      const url = 'https://m1.apifoxmock.com/m1/6267385-5961501-default/admin/listOrders';
      axios.get(url, {
        params: {
          case: tab,
          parm: currentPage,
        }
      })
     .then(response => {
        switch (tab) {
          case 'all':
            this.allOrders = response.data.data.data;
            this.totalAll = response.data.data.total;
            break;
          case 'unreviewed':
            this.unreviewedOrders = response.data.data.data;
            this.totalUnreviewed = response.data.data.total;
            break;
          case 'reviewed':
            this.reviewedOrders = response.data.data.data;
            this.totalReviewed = response.data.data.total;
            break;
        }
      })
     .catch(error => {
        console.error('获取订单失败:', error);
      });
    },
    prevPage(tab) {
      switch (tab) {
        case 'all':
          if (this.currentPageAll > 1) {
            this.currentPageAll--;
            this.fetchOrders(tab);
          }
          break;
        case 'unreviewed':
          if (this.currentPageUnreviewed > 1) {
            this.currentPageUnreviewed--;
            this.fetchOrders(tab);
          }
          break;
        case 'reviewed':
          if (this.currentPageReviewed > 1) {
            this.currentPageReviewed--;
            this.fetchOrders(tab);
          }
          break;
      }
    },
    nextPage(tab) {
      let totalPages = 0;
      let currentPage = 0;
      switch (tab) {
        case 'all':
          totalPages = Math.ceil(this.totalAll / 10);
          currentPage = this.currentPageAll;
          if (currentPage < totalPages) {
            this.currentPageAll++;
            this.fetchOrders(tab);
          }
          break;
        case 'unreviewed':
          totalPages = Math.ceil(this.totalUnreviewed / 10);
          currentPage = this.currentPageUnreviewed;
          if (currentPage < totalPages) {
            this.currentPageUnreviewed++;
            this.fetchOrders(tab);
          }
          break;
        case 'reviewed':
          totalPages = Math.ceil(this.totalReviewed / 10);
          currentPage = this.currentPageReviewed;
          if (currentPage < totalPages) {
            this.currentPageReviewed++;
            this.fetchOrders(tab);
          }
          break;
      }
    },
    handleReview(orderId, status) {
      const orders = this.activeTab === 'all' ? this.allOrders : this.unreviewedOrders;
      const order = orders.find(o => o.id === orderId);
      if (order) {
        axios.post(`https://m1.apifoxmock.com/m1/6267385-5961501-default/admin/checkOrder?orderId=${orderId}&case=${status}`)
          .then(response => {
            if (response.data.code === 200) {
              this.fetchOrders(this.activeTab);
            } else {
              console.error(`审核${status === 'success' ? '成功' : '失败'}请求出错:`, response.data.message);
            }
          })
          .catch(error => {
            console.error(`审核${status === 'success' ? '成功' : '失败'}请求出错:`, error);
          });
      }
    },
    showDetail(orderId) {
      this.dialogVisible = true;
      switch (this.activeTab) {
        case 'all':
          this.orderDetail = this.allOrders.find(order => order.id === orderId);
          break;
        case 'unreviewed':
          this.orderDetail = this.unreviewedOrders.find(order => order.id === orderId);
          break;
        case'reviewed':
          this.orderDetail = this.reviewedOrders.find(order => order.id === orderId);
          break;  
      }
    },
  },
};
</script>

<style>
.container {
  display: flex;
}

.sidebar {
  width: 200px;
}

.main-content {
  flex: 1;
  padding: 20px;
}

.tab-container {
  margin-bottom: 20px;
}

.tab-button {
  padding: 10px 20px;
  margin-right: 10px;
  background-color: #f0f0f0;
  border: none;
  cursor: pointer;
}

.tab-button.active {
  background-color: #007bff;
  color: white;
}

.order-table {
  width: 100%;
  border-collapse: collapse;
}

.order-table th, .order-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.order-table th {
  background-color: #f2f2f2;
}

.pagination {
  margin-top: 20px;
}

.pagination button {
  margin-right: 10px;
}

.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.dialog {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
}
.fail-mark {
  color: red;
  font-size: 20px;
}
.checkmark {
  color: green;
  font-size: 20px;
}
</style>