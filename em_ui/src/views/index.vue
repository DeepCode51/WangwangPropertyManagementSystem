<template>
  <div>
    <page-header title="欢迎使用「旺旺物业管理系统」">
      <template #content>
        <div>
          <!-- <div>注：在作者就职的公司，本框架已在电商、直播、OA、ERP等多个不同领域的中后台系统中应用并稳定运行</div> -->
        </div>
      </template>
      <el-button-group style="margin-right: 10px;">
        <el-button
          type="success"
          plain
          @click="open('https://gitee.com/kumuuuu/estate_management')"
        >代码仓库</el-button>
        <el-button type="primary" plain @click="open('https://ku-m.cn/')">个人博客</el-button>
      </el-button-group>
    </page-header>
    <page-main>
      <el-row :gutter="24" v-show="isAdmin">
        <el-col :span="6" :offset="0">
          <Card
            title="/rq-manager/info"
            num="小区管理"
            tip="主要管理小区基本内容"
            type="orange"
            icon="el-icon-office-building"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/building-manager/building"
            num="楼宇管理"
            tip="小区内所有楼宇详细记录"
            type="orange"
            icon="el-icon-school"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/building-manager/room"
            num="房间管理"
            tip="楼盘内房间管理"
            type="pink"
            icon="el-icon-house"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/rq-manager/facilities"
            num="公共设施"
            tip="小区内所有公共设施"
            type="pink"
            icon="el-icon-chat-square"
          />
        </el-col>
      </el-row>
      <el-row :gutter="24" v-show="isAdmin">
        <el-col :span="6" :offset="0">
          <Card
            title="/guarantee-manager/repair"
            num="报修管理"
            tip="及时处理业主的报修"
            type="violet"
            icon="el-icon-attract"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/guarantee-manager/complaint"
            num="投诉管理"
            tip="查看业主反映的问题/建议"
            type="violet"
            icon="el-icon-message"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card title num="业主管理" tip="小区内业主账号的管理" type="blue" icon="el-icon-user" />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card title num="费用管理" tip="小区内业主的费用管理" type="blue" icon="el-icon-coin" />
        </el-col>
      </el-row>
      <el-row :gutter="24" v-show="isAdmin">
        <el-col :span="6" :offset="0">
          <Card
            title="/system-manager/automatic-task"
            num="自动任务"
            tip="系统自动执行的任务管理"
            type="aaa"
            icon="el-icon-date"
          />
        </el-col>
      </el-row>
      <el-row :gutter="24" v-show="!isAdmin">
        <el-col :span="6" :offset="0">
          <Card title="/user/base/info" num="个人信息" tip="修改自己的信息" type="blue" icon="el-icon-user" />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/user/base/reset_pwd"
            num="密码修改"
            tip="修改自己的密码"
            type="orange"
            icon="el-icon-lock"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/user/pay_record"
            num="物业收费"
            tip="缴纳本月费用"
            type="pink"
            icon="el-icon-collection-tag
"
          />
        </el-col>
        <el-col :span="6" :offset="0">
          <Card
            title="/user/repair/add"
            num="申请报修"
            tip="设施损坏在此报修"
            type="aaa"
            icon="el-icon-tickets"
          />
        </el-col>
      </el-row>
      <el-row :gutter="24" v-show="!isAdmin">
        <el-col :span="6" :offset="0">
          <Card
            title="/user/complaint/add"
            num="发起投诉"
            tip="对物业服务进行投诉"
            type="violet"
            icon="el-icon-phone-outline"
          />
        </el-col>
      </el-row>
    </page-main>
  </div>
</template>

<script>
import card from '@/views/component_extend_example/card.vue'
export default {
  data () {
    return {
      isAdmin: false
    }
  },
  components: {
    'Card': card,
  },
  created () {
    let isAdmin = localStorage.getItem('isAdmin')
    this.isAdmin = isAdmin == 'true' ? true : false

  },
  methods: {
    open (url) {
      window.open(url, 'top')
    },
    onIconClick (val) {
      this.$message({
        message: `你点击了：${val}`,
        type: 'info'
      })
    },

  }
}
</script>

<style lang="scss" scoped>
.el-row {
    margin: 10px;
}
</style>
