<!-- src/components/login.vue -->
<template>
  <div class="logincontainer">
    <el-form class="loginForm" :model="loginModel" ref="loginForm" :inline="false" size="normal">
      <el-form-item>
        <span class="loginTitle">科研项目系统 - 登录</span>
      </el-form-item>
      <el-form-item prop="name">
        <el-input placeholder="请输入账户" v-model="loginModel.loginName"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="loginModel.password"></el-input>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="24" :offset="0">
            <el-button class="mybtn" type="primary" @click="onSubmit">登录</el-button>
          </el-col>
          <!-- <el-col :span="12" :offset="0">
            <el-button class="mybtn" @click="goRegister">注册</el-button>
          </el-col> -->
        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getUserId, getUserName, getUserType, setUserId, setUserName, setUserType } from "@/utils/auth";
import http from "@/utils/http";

export default {
  data() {
    return {
      loginModel: {
        loginName: "",
        password: ""
      }
    };
  },
  methods: {
    //登录提交
    async onSubmit() {
      let res = await http.post("/login", this.loginModel);
      console.log("res");
      console.log(res);
      if (res && res.code == 200) {
        //设置用户id
        setUserId(res.data.userId);
        setUserName(res.data.userName);
        setUserType(res.data.userType);
        console.log("userId: " + getUserId());
        console.log("userName: " + getUserName());
        console.log("userType: " + getUserType());
        // console.log(res.data.userId);
        this.$router.push('/');
      }
    },

    goRegister() {
      this.$router.push('/register') // 使用 Vue Router 的 push 方法跳转
    }
  }
}
</script>

<style lang="scss" scoped>
.logincontainer {
  height: 100%;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  background-size: 100% 100%;
  background-image: url('~@/assets/img/background.jpeg');

  .loginForm {
    height: 380px;
    width: 450px;
    background: #fff;
    opacity: 0.95;
    padding: 35px 20px;
    border-radius: 10px;

    .loginTitle {
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 30px;
      font-weight: 600;
      color: #409eff;
    }

    .mybtn {
      width: 100%;
    }
  }
}
</style>