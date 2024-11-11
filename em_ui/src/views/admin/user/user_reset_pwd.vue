<template>
  <page-main>
    <a-card title="密码修改">
      <a-form-model
        ref="current_form"
        :rules="rules"
        :model="current_form"
        style="width: 80vh;"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-form-model-item label="现行密码" prop="password">
          <a-input-password v-model="current_form.email" placeholder="您的现行密码" />
        </a-form-model-item>
        <a-form-model-item label="新的密码" prop="newPassword">
          <a-input-password v-model="current_form.workUnit" placeholder="新的密码" />
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
  name: 'user_reset_pwd',
  data () {
    return {
      current_form: {}
    }
  },

}
</script>
