<template>
  <div class="login">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      label-position="left"
      label-width="0px"
      class="login-form"
    >
      <h3 class="title">Rapid 4 You 后台管理系统</h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="验证码"
          style="width: 63%"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" />
        </div>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0 0 25px 0;">记住我</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!--  footer  -->
    <div v-if="$store.state.settings.showFooter" id="el-login-footer">
      <span v-html="$store.state.settings.footerTxt" />
      <span>⋅</span>
      <a href="http://www.beian.miit.gov.cn" target="_blank">{{ $store.state.settings.caseNumber }}</a>
    </div>
  </div>
</template>

<script>
import { encrypt } from "../utils/rsaEncrypt";
import Config from "../settings";
import { login, getCodeImg } from "../api/login";
import Cookies from "js-cookie";
import { setToken } from "../utils/auth";

export default {
  name: "Login",
  data() {
    return {
      codeUrl: "", // validate code
      loading: false,
      cookiePass: "", // password in cookie
      // redirect : undefined,
      loginForm: {
        // login form entity
        username: "admin",
        password: "123456",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        // validate rules
        username: [
          { required: true, trigger: "blur", message: "用户名不能为空" }
        ],
        password: [
          { required: true, trigger: "blur", message: "密码不能为空" }
        ],
        code: [{ required: true, trigger: "change", message: "验证码不能为空" }]
      }
    };
  },
  // watch: {
  //   $route: {
  //     handler: function(route) {
  //       this.redirect = route.query && route.query.redirect
  //     },
  //     immediate: true
  //   }
  // },
  created() {
    // load validate code after page created
    this.getCode();
  },
  methods: {
    // fetch the validate code img
    getCode() {
      getCodeImg().then(res => {
        this.codeUrl = res.img;
        this.loginForm.uuid = res.uuid;
      });
    },
    // hanle login
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        const rememberMe = this.loginForm.rememberMe;
        // submit user info
        const user = {
          username: this.loginForm.username,
          password: this.loginForm.password,
          // rememberMe: this.loginForm.rememberMe,
          code: this.loginForm.code,
          uuid: this.loginForm.uuid
        };
        // rewrite password using cookie's
        if (user.password !== this.cookiePass) {
          user.password = encrypt(user.password);
        }

        if (valid) {
          this.loading = true;
          if (rememberMe) {
            Cookies.set("username", user.username, {
              expires: Config.passCookieExpires
            });
            Cookies.set("password", user.password, {
              expires: Config.passCookieExpires
            });
            Cookies.set("rememberMe", rememberMe, {
              expires: Config.passCookieExpires
            });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove("rememberMe");
          }
          login(user)
            .then(res => {
              // store the user info after invoke login api
              setToken(res.token, rememberMe);
              return this.$store.dispatch("Login", {
                token: res.token,
                user: res.user
              });
            })
            .then(res => {
              this.loading = false;
              this.$router.push({ path: this.redirect || "/" });
            })
            .catch(() => {
              // refresh validate code for login fail
              this.loading = false;
              this.getCode();
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    }
  }
};
</script>

<style lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/background.jpg");
  background-size: cover;
}
.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 385px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
</style>
