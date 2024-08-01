<!-- src/components/login.vue -->
<template>
  <div class="logincontainer">
    <el-form
      class="loginForm"
      :model="registerModel"
      ref="loginForm"
      :inline="false"
      size="normal"
    >
      <el-form-item>
        <span class="loginTitle">科研项目系统 - 注册</span>
      </el-form-item>
      <el-form-item prop="name">
        <el-input
          placeholder="请输入用户名"
          v-model="registerModel.name"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          placeholder="请输入密码"
          v-model="registerModel.password"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <el-button class="mybtn" type="primary" @click="onSubmit"
              >注册</el-button
            >
          </el-col>
          <el-col :span="12" :offset="0">
            <el-button class="mybtn">取消</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// import { setUserId } from "@/utils/auth";
import http from "@/utils/http";

export default {
  data() {
    return {
      registerModel: {
        name: "",
        password: ""
      }
    };
  },
  methods: {
    //登录提交
    async onSubmit() {
      // console.log("registerModel");
      // console.log(this.registerModel);
      let res = await http.post("/register", this.registerModel);
      // console.log(res)
      if (res && res.code == 200) {
        this.$router.push('/login');
      }
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