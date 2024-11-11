<template>
  <page-main>
    <a-card :loading="loading" title="小区公共设施管理">
      <div class="head">
        <span>搜索类型</span>
        <a-select
          size="large"
          default-value="单位名称"
          v-model="facilities_query_type"
          style="width: 200px;"
        >
          <a-select-option value="name">单位名称</a-select-option>
          <a-select-option value="chargePerson">负责人</a-select-option>
          <a-select-option value="contactPerson">联系人</a-select-option>
          <a-select-option value="phone">电话</a-select-option>
        </a-select>
        <a-input-search
          v-model="facilities_query_text"
          placeholder="输入要搜索的文本"
          :enter-button="facilities_query_buttonTitle"
          size="large"
          @search="facilities_query_buttonTitle == '搜索' ? Query_facilitiesDataList() : Get_facilitiesDataList()"
        />
        <a-button
          type="primary"
          style="height: 38px;"
          @click="facilities_save_modalVisible = true"
        >添加设施</a-button>
        <a-button
          type="danger"
          v-if="table_selectedRowKeys.length > 0"
          style="height: 38px; margin-left: 10px;"
          @click="Del_batchData"
        >删除被选择的「公共设施」</a-button>
      </div>
      <a-table
        :data-source="facilities_data_list"
        :row-selection="{ selectedRowKeys: table_selectedRowKeys, onChange: Table_selectChange }"
      >
        <a-table-column key="name" title="设施名称" data-index="name" />
        <a-table-column key="type" title="设施类型" data-index="type" />
        <a-table-column key="chargePerson" title="设施负责人" data-index="chargePerson" />
        <a-table-column key="contactPerson" title="设施联系人" data-index="contactPerson" />
        <a-table-column key="phone" title="联系电话" data-index="phone" />
        <a-table-column key="action" title="操作">
          <template slot-scope="text, record">
            <a-button-group>
              <a-button type="primary" @click="Edit_facilitiesData(record)">编辑</a-button>
              <a-button type="danger" @click="Del_facilitiesData(record.id)">删除</a-button>
            </a-button-group>
          </template>
        </a-table-column>
      </a-table>
    </a-card>
    <!-- 新增或保存设施提示框 -->
    <a-modal
      v-model="facilities_save_modalVisible"
      :title="facilities_save_title"
      ok-text="确认"
      cancel-text="取消"
      :maskClosable="false"
      :destroyOnClose="false"
      @ok="Save_facilitiesData"
    >
      <a-form-model :model="facilities_form_data" :label-col="labelCol" :wrapper-col="wrapperCol">
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="单位名称">
              <a-input v-model="facilities_form_data.name" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="设备类型">
              <a-input v-model="facilities_form_data.type" />
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="负责人">
              <a-input v-model="facilities_form_data.chargePerson" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="联系人">
              <a-input v-model="facilities_form_data.contactPerson" />
            </a-form-model-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="联系电话">
              <a-input v-model="facilities_form_data.phone" />
            </a-form-model-item>
          </el-col>
        </el-row>
        <a-form-model-item label="设施描述" :labelCol=" { span: 4 }">
          <a-input v-model="facilities_form_data.descri" type="textarea" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </page-main>
</template>

<script>
import { getFacilities, saveFacilities, deleteFacilities } from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'
import { rTime } from '@/util/time.js'

export default {
  data () {
    return {
      loading: false,
      labelCol: { span: 8 },
      wrapperCol: { span: 14 },
      table_selectedRowKeys: [],
      facilities_query_type: 'name',
      facilities_query_buttonTitle: '搜索',
      facilities_query_text: '',
      facilities_save_title: '新增公共设施',
      facilities_save_modalVisible: false,
      facilities_form_data: {},
      facilities_data_list: [],
    }
  },
  created () {
    this.Get_facilitiesDataList()
  },
  watch: {
    facilities_save_modalVisible (val) {
      if (!val) {
        this.facilities_form_data = {}
      }
    }
  },
  methods: {
    Get_facilitiesDataList () {
      getFacilities().then(res => {
        this.facilities_query_buttonTitle = '搜索'
        this.facilities_data_list = res.data
        this.facilities_save_title = '新增公共设施'
      })
    },
    Query_facilitiesDataList () {
      let text = this.facilities_query_text
      let temp_list = []
      this.facilities_data_list.forEach(item => {
        if (item[this.facilities_query_type].indexOf(text) != -1) {
          temp_list.push(item)
        }
      })
      this.facilities_query_buttonTitle = '返回'
      this.facilities_data_list = temp_list
    },
    Edit_facilitiesData (form) {
      this.facilities_save_title = '编辑公共设施'
      this.facilities_form_data = JSON.parse(JSON.stringify(form))
      this.facilities_save_modalVisible = true
    },
    Del_facilitiesData (id) {
      deleteFacilities(id).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.Get_facilitiesDataList()
      })
    },
    Del_batchData () {
      this.table_selectedRowKeys.forEach(i => {
        this.Del_facilitiesData(this.facilities_data_list[i].id)
      })
      this.table_selectedRowKeys = []
    },
    Save_facilitiesData () {
      saveFacilities(this.facilities_form_data).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.facilities_save_modalVisible = false
        this.Get_facilitiesDataList()
      })


    },
    Table_selectChange (selectedRowKeys) {
      this.table_selectedRowKeys = selectedRowKeys;
    },
  },
}
</script>
    
<style lang="scss" scoped>
.head {
    display: flex;
    justify-content: flex-start;
    margin-bottom: 10px;
    span {
        line-height: 40px;
        margin-right: 10px;
    }
    .ant-input-search {
        width: 30%;
        margin-left: 10px;
    }
}
</style>