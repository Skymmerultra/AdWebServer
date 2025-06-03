<template>
    <i class="far fa-bookmark" :style="{ color: isFavorite ? 'red' : ''}" @click="toggleFavorite"></i>
     
</template>

<script>
import axios from 'axios';
    export default{
        data(){
           return{
            isFavorite:false
           }
        },
        props:{
            adPoId:{
                type: Number,
                required: true
            }
        },
        methods:{
            toggleFavorite(){
                if(!this.$store.getters.isLoggedIn){
                    this.$message.warning("登录后方可收藏")
                }
                else{
                    this.isFavorite=!this.isFavorite
                    axios.post("https://m1.apifoxmock.com/m1/6267385-5961501-default/user/favorite",
                        {
                            userId:this.$store.getters.getUserId,
                            adPoId:this.adPoId
                        },
                        {
                            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
                        }
                    )
                }
                
            }
        },
        created(){
            if(this.$store.getters.isLoggedIn){
                axios.post("https://m1.apifoxmock.com/m1/6267385-5961501-default/user/isFavorite",
                    {
                        userId:this.$store.getters.getUserId,
                        adPoId:this.adPoId
                    },
                     {
                        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
                    }
                ).then(response => {
                    this.isFavorite=response.data.data;
                })
            }
        }
    }
</script>