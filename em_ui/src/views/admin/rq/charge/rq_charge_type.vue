<template>
  <page-main>
    <a-card :loading="loading" title="小区收费类型管理">
      <div class="head">
        <span>搜索类型</span>
        <a-select
          size="large"
          default-value="收费类型名称"
          v-model="chargeType_query_type"
          style="width: 200px;"
        >
          <a-select-option value="chargeName">收费类型名称</a-select-option>
        </a-select>
        <a-input-search
          v-model="chargeType_query_text"
          placeholder="输入要搜索的文本"
          :enter-button="chargeType_query_buttonTitle"
          size="large"
          @search="chargeType_query_buttonTitle == '搜索' ? Query_chargeTypeDataList() : Get_chargeTypeDataList()"
        />
        <a-button
          type="primary"
          style="height: 40px;"
          @click="chargeType_save_modalVisible = true"
        >添加收费类型</a-button>
        <a-button
          type="danger"
          v-if="table_selectedRowKeys.length > 0"
          style="height: 38px; margin-left: 10px;"
          @click="Del_batchData"
        >删除被选择的「收费类型」</a-button>
      </div>
      <a-table
        :data-source="chargeType_data_list"
        :row-selection="{ selectedRowKeys: table_selectedRowKeys, onChange: Table_selectChange }"
      >
        <a-table-column key="id" title="编号" data-index="id" />
        <a-table-column key="chargeName" title="收费类型名称" data-index="chargeName" />
        <a-table-column key="chargeMoney" title="收费金额(元)" data-index="chargeMoney" />
        <a-table-column key="createTime" title="创建时间" data-index="createTime">
          <!-- rTime -->
          <template slot-scope="text, record">
            <span>{{rTime(record.createTime)}}</span>
          </template>
        </a-table-column>
        <a-table-column key="updateTime" title="修改时间" data-index="updateTime">
          <!-- rTime -->
          <template slot-scope="text, record">
            <span>{{rTime(record.updateTime)}}</span>
          </template>
        </a-table-column>
        <a-table-column key="action" title="操作">
          <template slot-scope="text, record">
            <a-button-group>
              <a-button type="primary" @click="Edit_chargeTypeData(record)">编辑</a-button>
              <a-button type="danger" @click="Del_chargeTypeData(record.id)">删除</a-button>
            </a-button-group>
          </template>
        </a-table-column>
      </a-table>
    </a-card>
    <!-- 新增或保存设施提示框 -->
    <a-modal
      v-model="chargeType_save_modalVisible"
      :title="chargeType_save_title"
      ok-text="确认"
      cancel-text="取消"
      :maskClosable="false"
      :destroyOnClose="false"
      @ok="Save_chargeTypeData"
    >
      <a-form-model
        :model="chargeType_form_data"
        :rules="rules"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-form-model-item label="收费名称" prop="chargeName">
          <a-input v-model="chargeType_form_data.chargeName" />
        </a-form-model-item>
        <a-form-model-item label="收费金额(月)" prop="chargeName">
          <a-input v-model="chargeType_form_data.chargeMoney" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </page-main>
</template>

<script>
import { getChargeType, saveChargeType, deleteChargeType } from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'
import { rTime } from '@/util/time.js'

export default {
  data () {
    return {
      rTime,
      loading: false,
      labelCol: { span: 7 },
      wrapperCol: { span: 7 },
      table_selectedRowKeys: [],
      chargeType_query_type: 'chargeName',
      chargeType_query_buttonTitle: '搜索',
      chargeType_query_text: '',
      chargeType_save_title: '新增收费类型',
      chargeType_save_modalVisible: false,
      chargeType_form_data: {},
      chargeType_data_list: [],
      rules: {
        chargeName: [{ required: true, message: '此项为必填项', trigger: 'blur' }],
        chargeMoney: [{ required: true, message: '此项为必填项', trigger: 'blur' }],
      },
    }
  },
  created () {
    this.Get_chargeTypeDataList()
  },
  watch: {
    chargeType_save_modalVisible (val) {
      if (!val) {
        this.chargeType_form_data = {}
      }
    }
  },
  methods: {
    Get_chargeTypeDataList () {
      getChargeType().then(res => {
        this.chargeType_query_buttonTitle = '搜索'
        this.chargeType_data_list = res.data
        this.chargeType_save_title = '新增收费类型'
      })
    },
    Query_chargeTypeDataList () {
      let text = this.chargeType_query_text
      let temp_list = []
      this.chargeType_data_list.forEach(item => {
        if (item[this.chargeType_query_type].indexOf(text) != -1) {
          temp_list.push(item)
        }
      })
      this.chargeType_query_buttonTitle = '返回'
      this.chargeType_data_list = temp_list
    },
    Edit_chargeTypeData (form) {
      this.chargeType_save_title = '编辑收费类型'
      this.chargeType_form_data = JSON.parse(JSON.stringify(form))
      this.chargeType_save_modalVisible = true
    },
    Del_chargeTypeData (id) {
      deleteChargeType(id).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.Get_chargeTypeDataList()
      })
    },
    Del_batchData () {
      this.table_selectedRowKeys.forEach(i => {
        this.Del_chargeTypeData(this.chargeType_data_list[i].id)
      })
      this.table_selectedRowKeys = []
    },
    Save_chargeTypeData () {
      saveChargeType(this.chargeType_form_data).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.chargeType_save_modalVisible = false
        this.Get_chargeTypeDataList()
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
.ant-modal-content {
    width: 24vw;
}
</style>