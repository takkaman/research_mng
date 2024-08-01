<template>
	<el-menu class="el-menu-demo" mode="horizontal" background-color="#334157" text-color="#fff"
		active-text-color="#fff">
		<el-button class="buttonimg">
			<img class="showimg" :src="collapsed ? imgshow : imgsq" @click="doToggle()">
		</el-button>
		<el-submenu v-if="userName" index="2" class="submenu">
			<template slot="title">{{ userName }}</template>
			<!-- <el-menu-item index="2-1">设置</el-menu-item>
			<el-menu-item index="2-2">个人中心</el-menu-item> -->
			<el-menu-item @click="exit()" index="2-3">退出</el-menu-item>

		</el-submenu>
		<el-menu-item v-else index="4" class="submenu" @click="login">登录</el-menu-item>
	</el-menu>
</template>

<script>
import { getUserName, getUserType, clearSession } from "@/utils/auth";

export default {
	data() {
		return {
			userName: getUserName(),
			userType: getUserType(),
			collapsed: 'false',
			imgshow: require('@/assets/img/show.svg'),
			imgsq: require('@/assets/img/collapse.svg'),
		}
	},
	created() {
		this.userName = getUserName();
		// console.log(this.userName);
	},
	methods: {
		doToggle() {
			// this.collapsed = ! this.collapsed;
			// this.$root.Bus.$emit('aaa',this.collapsed);
		},
		login() {
			this.$router.push("/login");
		},
		exit() {
			clearSession();
			this.$router.push("/login");
		}
	}
}
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
	border: none;
}

.submenu {
	float: right;
}

.login {
	float: right;
	color: white;
}

.buttonimg {
	height: 60px;
	background-color: transparent;
	border: none;
}

.showimg {
	width: 26px;
	height: 26px;
	position: absolute;
	top: 17px;
	left: 17px;
}

.showimg:active {
	border: none;
}
</style>