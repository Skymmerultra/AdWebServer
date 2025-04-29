<template>
    <div class="block">
          <el-avatar :size="50" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                    @click="toUserView"/>
    </div>
</template>

<script>
    export default{
    name: 'AvatarIcon',
    methods:{
        toUserView(){
            this.$router.push({ name: 'userview' });
        }
    }
}
</script>