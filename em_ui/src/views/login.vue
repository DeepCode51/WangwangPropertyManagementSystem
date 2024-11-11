<template>
  <div>
    <div class="bg-banner" />
    <div id="login-box">
      <div class="login-banner" />
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        class="flex-container login-form"
        auto-complete="on"
        label-position="left"
      >
        <div class="title-container">
          <h3 class="title">{{ title }}管理后台</h3>
        </div>
        <div>
          <el-form-item prop="username">
            <el-input
              ref="name"
              v-model="form.username"
              placeholder="用户名"
              type="text"
              tabindex="1"
              auto-complete="on"
            >
              <svg-icon slot="prefix" name="user" />
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              ref="password"
              v-model="form.password"
              :type="passwordType"
              placeholder="密码"
              tabindex="2"
              auto-complete="on"
              @keyup.enter.native="handleLogin"
            >
              <svg-icon slot="prefix" name="password" />
              <svg-icon
                slot="suffix"
                :name="passwordType === 'password' ? 'eye' : 'eye-open'"
                @click="showPassword"
              />
            </el-input>
          </el-form-item>
          <!-- <el-form-item v-if="open_verificationCode">
            <el-row :gutter="20">
              <el-col :span="12" :offset="0">
                <el-input
                  ref="code"
                  v-model="form.VerificationCode"
                  placeholder="验证码"
                  type="text"
                  tabindex="1"
                  auto-complete="on"
                ></el-input>
              </el-col>
              <el-col :span="12" :offset="0">
                <img
                  :src="'http://localhost:8080/user/verification/code/' + form.token"
                  style="height: 51px;"
                  @click="refreshCode()"
                />
              </el-col>
            </el-row>
          </el-form-item>-->
        </div>
        <el-button
          :loading="loading"
          type="primary"
          style="width: 100%;"
          @click.native.prevent="handleLogin"
        >登录</el-button>
        <div style="margin-top: 20px; color: #999; font-size: 14px; text-align: center;">
          演示帐号一键登录：
          <span
            style="padding: 0 5px; cursor: pointer; color: #666;"
            @click="testusername('admin')"
          >admin</span>或
          <span
            style="padding: 0 5px; cursor: pointer; color: #666;"
            @click="testusername('test')"
          >test</span>
        </div>
      </el-form>
    </div>
    <Copyright v-if="$store.state.settings.showCopyright" />
  </div>
</template>

<script>
// import fileUtils from '@/util/file'
// import { getRoutineConfig } from '../api/requests/admin'
export default {
  name: 'Login',
  data () {
    return {
      // fileUtils,
      title: process.env.VUE_APP_TITLE,
      open_verificationCode: false,
      form: {
        username: localStorage.login_username || '',
        password: '',
        VerificationCode: '',
        // token: fileUtils.UUID(),
      },
      rules: {
        username: [
          { required: true, trigger: 'blur', message: '请输入用户名' }
        ],
        password: [
          { required: true, trigger: 'blur', message: '请输入密码' },
          { min: 6, max: 18, trigger: 'blur', message: '密码长度为6到18位' }
        ],
        code: [
          { required: true, trigger: 'blur', message: '请输入验证码' },

        ]
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created () {
  },
  methods: {
    showPassword () {
      this.passwordType = this.passwordType === 'password' ? '' : 'password'
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.form).then(res => {
            this.loading = false
            if (res.isSuccess == false) {
              this.$notify({
                title: '警告',
                message: res.msg,
                type: 'warning'
              });
              return
            }
            this.$notify({
              title: '成功',
              message: '登录成功',
              type: 'success'
            });

            setTimeout(() => {
              location.reload(true)
            }, 1000);



          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    testusername (username) {
      this.form.username = username
      this.form.password = '123456'
      this.handleLogin()
    },
    // getRoutineSettings () {
    //   getRoutineConfig().then(res => {
    //     let temp = JSON.parse(res.data.value)
    //     localStorage.setItem('routine', res.data.value)
    //     this.open_verificationCode = temp.loginCheck
    //   })
    // },
    // refreshCode () {
    //   console.log(1)
    //   this.form.token = fileUtils.UUID()
    // }
  }
}
</script>

<style lang="scss" scoped>
[data-mode="mobile"] {
    #login-box {
        max-width: 80%;
        .login-banner {
            display: none;
        }
    }
}
::v-deep input[type="password"]::-ms-reveal {
    display: none;
}
.bg-banner {
    position: absolute;
    z-index: 0;
    width: 100%;
    height: 100%;
    background-size: cover;
    background-image: url(../assets/images/login-bg.jpg);
}
#login-box {
    display: flex;
    justify-content: space-between;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translateX(-50%) translateY(-50%);
    background: rgba(255, 255, 255, 0.8);
    height: 400px;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 0 5px #999;
    .login-banner {
        width: 300px;
        height: 400px;
        background-image: url(../assets/images/login-banner.jpg);
        background-size: cover;
    }
    .flex-container {
        width: 400px;
    }
    .login-form {
        width: 450px;
        padding: 50px 35px 0;
        overflow: hidden;
        .svg-icon {
            vertical-align: -0.35em;
        }
        .title-container {
            position: relative;
            .title {
                font-size: 22px;
                color: #666;
                margin: 0 auto 30px;
                text-align: center;
                font-weight: bold;
                @include text-overflow;
            }
        }
    }
    ::v-deep .el-input {
        display: inline-block;
        height: 48px;
        width: 100%;
        input {
            height: 48px;
        }
        .el-input__prefix {
            left: 10px;
        }
        .el-input__suffix {
            right: 10px;
        }
    }
    .el-checkbox {
        margin: 20px 0;
    }
}
.copyright {
    position: absolute;
    bottom: 30px;
    width: 100%;
    margin: 0;
    mix-blend-mode: difference;
}
</style>
