<template>
    <div class="adShowCard">
        <div>
            <div class=image_container>
            <el-image 
            :src="adInfo?.image_src"
            :fit="cover"
           >
            </el-image>
        </div>
        </div>
        <div class="card_main">
            <el-descriptions
            column="2">
                <el-descriptions-item label="投递平台">
                    {{adInfo?.platform }}
                </el-descriptions-item>
                <el-descriptions-item label="状态">
                    {{ adInfo?.cycle }}
                </el-descriptions-item>
                <el-descriptions-item label="投递时间段">
                   {{ adInfo?.startTime }} - {{ adInfo?.endTime }}
                </el-descriptions-item>
                 <el-descriptions-item label=" 是否开具发票">
                    {{ adInfo?.isInvoice }} <el-button v-if="couldReInvoice" @click="openRemedyInvoice" class="button2">补开发票</el-button>
                </el-descriptions-item>
                <el-descriptions-item label="总投递次数">
                   {{ adInfo?.deliveryNum }}
                </el-descriptions-item>
                <el-descriptions-item label="已投递次数">
                   {{ adInfo?.realDeliveryNum }}
                </el-descriptions-item>
            </el-descriptions>
            <div class = operation>
            <el-button class="button" @click="openDetailDia">详情查看</el-button>
            <el-button class="button" @click="openCancelOrderDia">取消订单</el-button>
            <el-button class="button" @click="openReBuyVue">再次购买</el-button>
            <el-button class="button" @click="openRemarkDia">立即评价</el-button>
            </div>
        </div>
    </div>
     <DetailDia v-model:dialogVisible="DetailDiaVis" :id="this.deliveryCaseId"></DetailDia>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus'
import DetailDia from "@/components/DetailDia"
export default{
        components:{
            DetailDia
        },
        props:{
            adInfo:{
                type:Object
            }
        },
        data(){
            return {
                 DetailDiaVis:false,
                 order_id:this.adInfo?.id,
                 adPoId:this.adInfo?.adPoId,
                 deliveryCaseId:this.adInfo?.deliveryCaseId
            }
        },
        computed:{
            couldReInvoice(){
                if(this.adInfo?.isInvoice == "否")
                return true;
                else
                return false;
            }
        },
        methods:{
            openDetailDia(){
            this.DetailDiaVis = true;
            },
            openCancelOrderDia(){
                if(this.adInfo?.cycle!="inspection"){
                   return 
                }
                else{
                     ElMessageBox.confirm(
                        '该订单正在审核,您确定取消订单吗',
                        'Warning',
                        {
                        confirmButtonText: 'OK',
                        cancelButtonText: 'Cancel',
                        type: 'warning',
                        }
                        )
                        .then(() => {
                        ElMessage({
                            type: 'success',
                            message: '订单取消成功',
                        })
                        this.$emit('updateOrderData',this.order_id)
                        })
                        .catch(() => {
                        })
                }
            },
            openReBuyVue(){

                this.$router.push({name:'addetail',params:{adId:this.adPoId}})
            },
            openRemedyInvoice(){
                if(this.adInfo?.cycle == "inspection"){
                      ElMessageBox.confirm(
                        '您确定要为该订单补开发票吗',
                        {
                        confirmButtonText: 'OK',
                        cancelButtonText: 'Cancel',
                        type: 'info',
                        }
                        )
                        .then(() => {
                            this.$emit('updateOrderInvoice',this.order_id)
                        })
                        .catch(() => {
                        })
                }
                else{
                    ElMessage({
                        type:'warning',
                        message:'已投递的广告不支持补开发票'
                    })
                }
            }
        },
        mounted(){
            console.log(this.deliveryCaseId)
        }
    }
</script>

<style scoped>
.adShowCard{
    width: 100%; /* 设置矩形的宽度 */
    height: 210px; /* 设置矩形的高度 */
    border: 1px solid #000000; /* 边框颜色和厚度 */
    display: flex;
    flex-direction: row;
    padding-left: 10px;
    padding-top:10px;
}
.card_main{
    display: flex;
    flex-direction: column;
    padding-left: 10px;
}
.image_container{
    width:200px;
    height:200px;
}
.image_container el-image {
  max-height: 100%;
  max-width: 100%;
}

.operation{
    display: flex;
    flex-direction: row;
}
.button{
    color: rgb(0, 0, 0);  /* 按钮文字颜色 */
    font-size: 16px;  /* 字体大小 */
    background-color: #ffffff;  /* 默认背景色 */
    border: 2px solid rgb(79, 105, 174);  /* 默认边框颜色 */
    padding: 10px 20px;  /* 增加内边距 */
    border-radius: 4px;  /* 圆角边框 */
    transition: background-color 0.3s, color 0.3s, border-color 0.3s;  /* 平滑过渡 */
}

.button:hover{
    color: rgb(255, 255, 255);  /* 悬停时文字颜色变为白色 */
    background-color: rgb(121, 191, 193);  /* 悬停时背景色*/
    border-color: rgb(79, 105, 174);  /* 悬停时边框颜色*/
}

.button2, .button2:focus:not(.button2:hover){ 
    margin-right: 12px;
    border: 1px solid #2794f8;
    border-radius: 2px;
    box-shadow: 0 2px 4px 0 #f4f4f4;
    color: #2794f8;
    background: white;
}
.button2:focus, .button2:hover{
    background: #eaf5ff;
    border: 1px solid #2794f8 !important;
    color: #2794f8;
}
.button2:active {
    background: #2794f8;
    color: white;
}
</style>

