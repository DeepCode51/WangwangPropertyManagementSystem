<template>
  <page-main>
    <a-card title="申请投诉">
      <a-form-model
        ref="current_form"
        :rules="rules"
        :model="current_form"
        style="width: 80vh;"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-form-model-item label="姓名" prop="userName">
          <a-input v-model="current_form.userName" placeholder="您的姓名" />
        </a-form-model-item>
        <a-form-model-item label="联系电话" prop="phone">
          <a-input v-model="current_form.phone" placeholder="您的联系方式" />
        </a-form-model-item>
        <a-form-model-item label="投诉原因" prop="title">
          <a-input v-model="current_form.title" placeholder="投诉事物名称" />
        </a-form-model-item>
        <a-form-model-item label="住址" prop="address">
          <a-input v-model="current_form.address" placeholder="楼宇名-单元号-房间号" />
        </a-form-model-item>
        <a-form-model-item label="详细说明" prop="text">
          <a-input v-model="current_form.text" type="textarea" :rows="4" placeholder="详细说明投诉的具体原因" />
        </a-form-model-item>

        <a-form-model-item style="margin-left: 33.3%;">
          <a-button type="primary" @click="Save_Complaint">保存</a-button>
          <a-button style="margin-left: 10px;">取消</a-button>
        </a-form-model-item>
      </a-form-model>
    </a-card>
  </page-main>
</template>

<script>
import { addComplaint } from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'

export default {
  data () {
    return {
      current_form: {},
      labelCol: { span: 8 },
      wrapperCol: { span: 14 },
      rules: {
        userName: [{ required: true, message: '此项为必填项', trigger: 'blur' }],
        phone: [{ required: true, message: '此项为必填项', trigger: 'blur' }],
        title: [{ required: true, message: '此项为必填项', trigger: 'blur' }],
        address: [{ required: true, message: '此项为必填项', trigger: 'blur' }],
        text: [{ required: true, message: '此项为必填项', trigger: 'blur' }],
      },
    }
  },
  created () {
  },
  methods: {
    Save_Complaint () {
      this.$refs.current_form.validate(v => {
        if (v) {
          addComplaint(this.current_form).then(res => {
            if (res.code == 200) {
              Success(this, '操作成功！')
            }
          })
        }
      })
    }
  },
}
</script>