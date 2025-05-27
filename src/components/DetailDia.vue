<template>
  <div>
    <el-dialog v-model="localDialogVisible" width="700px">
        <div v-if="this.id==0">审核中,请耐心等待</div>
        <div v-if="this.id!=0"> 
           <el-table
           :data="deliveryCase"
            show-summary>
            <el-table-column prop="time" label="投递时间"  width="250px"/>
            <el-table-column prop="viewNum" label="观看流量" width="150px"/>
            <el-table-column prop="clickNum" label="点击次数" width="150px"/>
          </el-table>
        </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      required: true
    },
    id:{
      type:Number,
      required:true
    }
  },
  data(){
    return{
      deliveryCase:[]
    }
  },
  methods:{
    fetchDeliveryData(){
       axios.get(`https://m1.apifoxmock.com/m1/6267385-5961501-default/deliveryCase/${this.id}`)
       .then(response => {
          this.deliveryCase=response.data.data;
       })
    }
  },
  mounted(){
    this.fetchDeliveryData()
  },
  emits: ['update:dialogVisible'], // 声明要触发的事件
  computed: {
    localDialogVisible: {
      get() {
        return this.dialogVisible;
      },
      set(value) {
        // 当对话框关闭时，触发事件通知父组件更新
        this.$emit('update:dialogVisible', value);
      }
    }
  }
}
</script>