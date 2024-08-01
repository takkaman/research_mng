<template>
    <el-menu :default-active="activeIndex" class="el-menu-vertical-demo" background-color="#334157" text-color="#fff"
        active-text-color="#ffd04b" :collapse="collapsed" @select="handleSelect">
        <!-- <el-menu default-active="2" :collapse="collapsed" collapse-transition router :default-active="$route.path" unique-opened class="el-menu-vertical-demo" background-color="#334157" text-color="#fff" active-text-color="#ffd04b"> -->
        <div class="logobox">
            <img class="logoimg" src="../assets/img/logo.png" alt="">
        </div>
        <el-menu-item index="/home">
            <i class="el-icon-menu"></i>
            <span slot="title">首页</span>
        </el-menu-item>
        <el-menu-item index="/users">
            <i class="el-icon-user"></i>
            <span slot="title">用户管理</span>
        </el-menu-item>
        <el-submenu index="3">
            <template slot="title">
                <i class="el-icon-location"></i>
                <span>科研项目</span>
            </template>
            <el-menu-item index="/vprojects">
                <i class="el-icon-document"></i>
                <span slot="title">纵向课题</span>
            </el-menu-item>
            <el-menu-item index="/hprojects">
                <i class="el-icon-document"></i>
                <span slot="title">横向课题</span>
            </el-menu-item>
        </el-submenu>
        <el-menu-item index="/papers">
            <i class="el-icon-notebook-2"></i>
            <span slot="title">学术论文</span>
        </el-menu-item>
        <el-menu-item index="/works">
            <i class="el-icon-reading"></i>
            <span slot="title">学术著作</span>
        </el-menu-item>
        <el-menu-item index="/awards">
            <i class="el-icon-medal"></i>
            <span slot="title">成果获奖</span>
        </el-menu-item>
        <el-menu-item index="/researchers">
            <i class="el-icon-user"></i>
            <span slot="title">科研队伍</span>
        </el-menu-item>
    </el-menu>
</template>
<script>
export default {
    data() {
        return {
            // activeIndex: '/home',
            collapsed: false
        }
    },
    created() {
        // this.$root.Bus.$on('aaa', v => {
        //     this.collapsed = v;
        // });
    },
    props: {
        activeIndex: {
            type: String,
            default: "/home" // 或者你想要的任何默认值  
        }
    },
    methods: {
        handleSelect(key) {
            // console.log('select', key, keyPath);
            // 这里可以执行一些操作，比如导航到对应的路由
            // this.activeIndex = keyPath;
            this.$router.push(key);
        }
    }
}
</script>
<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 240px;
    min-height: 400px;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
    border: none;
    text-align: left;
}

.el-menu-item-group__title {
    padding: 0px;
}

.el-menu-bg {
    background-color: #1f2d3d !important;
}

.el-menu {
    border: none;
}

.logobox {
    height: 40px;
    line-height: 40px;
    color: #9d9d9d;
    font-size: 20px;
    text-align: center;
    padding: 20px 0px;
}

.logoimg {
    height: 40px;
}
</style>