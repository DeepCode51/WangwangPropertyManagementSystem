<template>
  <page-main>
    <a-card title="个人信息">
      <a-form-model
        ref="current_form"
        :rules="rules"
        :model="current_form"
        style="width: 80vh;"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-form-model-item label="真实姓名" prop="fullName">
          <a-input v-model="current_form.userName" placeholder="您的姓名" />
        </a-form-model-item>
        <a-form-model-item label="联系电话" prop="phone">
          <a-input v-model="current_form.phone" placeholder="您的联系方式" />
        </a-form-model-item>
        <a-form-model-item label="电子邮箱" prop="email">
          <a-input v-model="current_form.email" placeholder="您的电子邮箱" />
        </a-form-model-item>
        <a-form-model-item label="工作单位" prop="workUnit">
          <a-input v-model="current_form.workUnit" placeholder="您的工作单位" />
        </a-form-model-item>

        <a-form-model-item style="margin-left: 33.3%;">
          <a-button type="primary" @click="save_userInfo">保存</a-button>
          <a-button style="margin-left: 10px;">取消</a-button>
        </a-form-model-item>
      </a-form-model>
    </a-card>
  </page-main>
</template>

<script>
export default {
  name: 'user_info',
  data () {
    return {
      current_form: {},
    }
  },
  methods: {
    save_userInfo () {

    }
  },

}
</script>