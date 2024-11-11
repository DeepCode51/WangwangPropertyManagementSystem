<template>
  <a-layout class="content">
    <!-- <a-layout-header class="header">
      <div class="logo" />
      <a-menu
        theme="dark"
        mode="horizontal"
        :default-selected-keys="['1']"
        :style="{ lineHeight: '64px' }"
      >
        <a-menu-item key="1">首页</a-menu-item>
      </a-menu>
    </a-layout-header>-->
    <a-layout>
      <a-layout-sider width="400" style="background: #eff2f5;">
        <a-card title="用户登录">
          <div class="login">
            <a-input v-model="login_form_data.userName" placeholder="请输入你的用户名">
              <a-icon slot="prefix" type="user" />
              <a-tooltip slot="suffix" title="输入用户名或手机号">
                <a-icon type="info-circle" style="color: rgba(0, 0, 0, 0.45);" />
              </a-tooltip>
            </a-input>
            <br />
            <br />
            <a-input-password v-model="login_form_data.passWord" placeholder="请输入你的密码" />
            <br />
            <br />
            <el-row :gutter="20">
              <el-col :span="12" :offset="0">
                <a-input v-model="login_form_data.code" placeholder="请输入验证码" />
              </el-col>
              <el-col :span="12" :offset="0">
                <img
                  @click="() => {
                  login_img_src += '?time=' + new Date().getTime()

                }"
                  :src="login_img_src"
                  style="height: 32px; width: 100px; border: 1px solid;"
                />
              </el-col>
            </el-row>
            <br />
            <el-row :gutter="20">
              <el-col :span="5" :offset="0">
                <a-button type="primary" @click="home_login()">登录</a-button>
              </el-col>
              <el-col :span="12" :offset="0">
                <a-button>找回密码</a-button>
              </el-col>
            </el-row>
          </div>
        </a-card>
        <a-card title="小区信息">
          <a-descriptions title layout="vertical" bordered>
            <a-descriptions-item label="小区名称" :span="3">城市花园小区</a-descriptions-item>
            <a-descriptions-item label="道路面积">222亩</a-descriptions-item>
            <a-descriptions-item label="绿化面积">222亩</a-descriptions-item>
            <a-descriptions-item label="建筑面积">3000</a-descriptions-item>
            <a-descriptions-item label="楼宇数量">100</a-descriptions-item>
            <a-descriptions-item label="联系我们" :span="3">
              <a-badge status="processing" text="+8618676767676" />
            </a-descriptions-item>
          </a-descriptions>
        </a-card>
      </a-layout-sider>
      <a-layout-content style="overflow: hidden;">
        <div class="carousel">
          <a-card title="图片展示">
            <el-carousel indicator-position="outside" style="height: 300px; overflow: hidden;">
              <el-carousel-item
                v-for="item in carousel_img_list"
                :key="item"
                style="height: 300px;"
              >
                <img :src="item" style="width: 100%; height: 300px;" />
              </el-carousel-item>
            </el-carousel>
          </a-card>
        </div>
        <div class="notice">
          <a-card
            style="width: 100%; max-height: 360px;"
            :tab-list="tabListNoTitle"
            :active-tab-key="noTitleKey"
            @tabChange="key => onTabChange(key, 'noTitleKey')"
          >
            <p v-if="noTitleKey === 'em_notice'">
              <notice-list />
            </p>
            <p v-else-if="noTitleKey === 'rq_facilities'">
              <rq-facilities-list />
            </p>
            <p v-else="noTitleKey === 'estate_user'">
              <estate-user-list />
            </p>
            <a slot="tabBarExtraContent" href="#">More</a>
          </a-card>
        </div>
      </a-layout-content>
    </a-layout>
    <a-layout-footer>Copyright © 2021 旺旺物业管理系统</a-layout-footer>
  </a-layout>
</template>

<script>
import { login, isAdmin } from '@/api/requests/rq-manage.js'
import notice_list from '@/views/home/notice_list.vue'
import rq_facilities_list from '@/views/home/rq_facilities_list.vue'
import estate_user_list from '@/views/home/estate_user_list.vue'

export default {
  name: 'home',
  components: {
    'notice-list': notice_list,
    'rq-facilities-list': rq_facilities_list,
    'estate-user-list': estate_user_list,
  },
  data () {
    return {
      login_img_src: 'http://localhost:8082/login/code',
      login_form_data: {},
      carousel_img_list: ['https://s3.ax1x.com/2021/03/13/6dz6HS.png', 'http://img4.imgtn.bdimg.com/it/u=290215386,829228996&fm=15&gp=0.jpg'],
      tabListNoTitle: [
        {
          key: 'em_notice',
          tab: '物业公告',
        },
        {
          key: 'rq_facilities',
          tab: '小区设施',
        },
        {
          key: 'estate_user',
          tab: '物业人员',
        },
      ],
      key: 'em_notice',
      noTitleKey: 'rq_facilities',
    }
  },
  created () {
    if (localStorage.getItem("isLogin")) {

      this.$store.dispatch('user/login', this.form).then(res => {
        this.$router.push('/')
      })

    }
  },
  methods: {
    home_login () {
      login(this.login_form_data).then(res => {
        if (res.code == 200) {
          this.$success({
            title: '登录成功',
            content: (
              <div>
                <p>接下来即将跳转到后台界面...</p>
              </div>
            ),
          });
          localStorage.setItem("username", res.data.userName)
          this.$store.dispatch('user/login', this.form).then(res => {
              setTimeout(() => {
            window.location.href = '/'
          }, 1000)

          })
        
    

        } else {
          this.$error({
            title: '登录失败',
            content: res.msg,
          });

        }
      })
    },
    onTabChange (key, type) {
      console.log(key, type);
      this[type] = key;
    },
  },
}
</script>

<style lang="scss" scoped>
.content {
    padding: 0 30px;
}
.ant-card {
    margin-top: 20px;
    .login {
        padding: 20px;
    }
}
.ant-layout-content {
    margin-left: 10px;
}
</style>