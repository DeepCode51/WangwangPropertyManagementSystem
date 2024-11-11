<template>
  <page-main>
    <a-card :loading="loading" title="小区简介">
      <a-form-model
        :model="info_form"
        style="width: 80vh;"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="小区名称">
              <a-input v-model="info_form.name" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="小区负责人">
              <a-input v-model="info_form.person" />
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="建成日期">
              <a-input v-model="info_form.date" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="楼宇数量">
              <a-input v-model="info_form.buildingsNumber" />
            </a-form-model-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="小区地址">
              <a-input v-model="info_form.address" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="联系电话">
              <a-input v-model="info_form.phone" />
            </a-form-model-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="建筑面积(亩)">
              <a-input v-model="info_form.builtUpArea" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="绿化面积(亩)">
              <a-input v-model="info_form.afforestedArea" />
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="道路面积(亩)">
              <a-input v-model="info_form.roadArea" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="停车场面积(亩)">
              <a-input v-model="info_form.parkingArea" />
            </a-form-model-item>
          </el-col>
        </el-row>

        <a-form-model-item label="小区简介" :labelCol=" { span: 4 }">
          <a-input v-model="info_form.desc" type="textarea" />
        </a-form-model-item>

        <a-form-model-item style="margin-left: 16.6%;">
          <a-button type="primary" @click="Save_RqInfo">保存</a-button>
          <a-button style="margin-left: 10px;" @click="Get_RqInfo">重置</a-button>
        </a-form-model-item>
      </a-form-model>
    </a-card>
  </page-main>
</template>
<script>
import { getRqInfo, saveRqInfo } from '@/api/requests/rq-manage.js'
export default {
  data () {
    return {
      loading: false,
      info_form: {},
      labelCol: { span: 8 },
      wrapperCol: { span: 14 },
    }
  },
  created () {
    this.Get_RqInfo()
  },
  methods: {
    Get_RqInfo () {
      getRqInfo().then(res => {
        console.log(res);
        this.info_form = JSON.parse(res.data.text)
      })
    },
    Save_RqInfo () {
      saveRqInfo(this.info_form).then(res => {
        console.log(res)
      })
    }
  },
}
</script>

<style lang="scss" scoped>
.ant-form-item {
    margin-bottom: 15px;
}
label {
    width: 200px;
}
</style>