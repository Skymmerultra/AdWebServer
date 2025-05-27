<template>
    <div>
        <el-menu :default-active="active_index" class="user-menu" @select="handleSelect">
            <el-menu-item index="5">回到首页</el-menu-item>
            <el-menu-item index="1">基本信息</el-menu-item>
            <el-menu-item index="2">个人钱包</el-menu-item>
            <el-menu-item index="3">个人收藏</el-menu-item>
            <el-menu-item index="4">浏览历史</el-menu-item>
        </el-menu>
    </div>
</template>

<script>
export default {
    data() {
        return {
            active_index: null
        };
    },
    watch: {
        // 监听 $route 对象，当路由变化时更新 active_index
        '$route'(to) {
            this.setActiveIndex(to.name);
        }
    },
    mounted() {
        // 页面加载时初始化 active_index
        this.setActiveIndex(this.$route.name);
    },
    methods: {
        setActiveIndex(routeName) {
            switch(routeName) {
                case "userview":
                    this.active_index = "1";
                    break;
                case "userwallet":
                    this.active_index = "2";
                    break;
                case "usercollection":
                    this.active_index = "3";
                    break;
                case "userviewhistory":
                    this.active_index = "4";
                    break;
                case "home":
                    this.active_index = "5";
                    break;
                default:
                    this.active_index = null;
            }
        },
        handleSelect(index) {
            switch(index) {
                case "1":
                    this.$router.push({ name: 'userview' });
                    break;
                case "2":
                    this.$router.push({ name: 'userwallet' });
                    break;
                case "3":
                    this.$router.push({ name: 'usercollection' })
                    .then(() => {
                        this.$router.go(0);
                    });
                    break;
                case "4":
                    this.$router.push({ name: 'userviewhistory' })
                    .then(() => {
                        this.$router.go(0);
                    });
                    break;
                case "5":
                    this.$router.push({ name: 'home' });
                    break;
            }
        }
    }
};
</script>

<style scoped>
/* 菜单项样式 */
.user-menu .el-menu-item {
    font-size: 20px;  /* 设置菜单项的字体大小 */
    font-weight: bold;
    text-align: center;
}
</style>
