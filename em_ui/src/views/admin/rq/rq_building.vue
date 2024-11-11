<template>
  <page-main>
    <a-card :loading="loading" title="小区楼宇管理">
      <div class="head">
        <span>搜索类型</span>
        <a-select
          size="large"
          default-value="单位名称"
          v-model="building_query_type"
          style="width: 200px;"
        >
          <a-select-option value="name">楼宇名称</a-select-option>
          <a-select-option value="layers">楼宇层数</a-select-option>
          <a-select-option value="height">楼宇高度</a-select-option>
          <a-select-option value="area">楼宇面积</a-select-option>
        </a-select>
        <a-input-search
          v-model="building_query_text"
          placeholder="输入要搜索的文本"
          :enter-button="building_query_buttonTitle"
          size="large"
          @search="building_query_buttonTitle == '搜索' ? Query_buildingDataList() : Get_buildingDataList()"
        />
        <a-button
          type="primary"
          style="height: 38px;"
          @click="building_save_modalVisible = true"
        >添加楼宇</a-button>
        <a-button
          type="danger"
          v-if="table_selectedRowKeys.length > 0"
          style="height: 38px; margin-left: 10px;"
          @click="Del_batchData"
        >删除被选择的「楼宇」</a-button>
      </div>
      <a-table
        :data-source="building_data_list"
        :row-selection="{ selectedRowKeys: table_selectedRowKeys, onChange: Table_selectChange }"
      >
        <a-table-column key="name" title="楼宇名称" data-index="name" />
        <a-table-column key="layers" title="楼宇层数" data-index="layers" />
        <a-table-column key="height" title="楼宇高度" data-index="height" />
        <a-table-column key="area" title="楼宇面积" data-index="area" />
        <a-table-column key="date" title="楼宇建成时间" data-index="date">
          <!-- rTime -->
          <template slot-scope="text, record">
            <span>{{rTime(record.date)}}</span>
          </template>
        </a-table-column>
        <a-table-column key="action" title="操作">
          <template slot-scope="text, record">
            <a-button-group>
              <a-button type="primary" @click="Edit_buildingData(record)">编辑</a-button>
              <a-button type="danger" @click="Del_buildingData(record.id)">删除</a-button>
            </a-button-group>
          </template>
        </a-table-column>
      </a-table>
    </a-card>
    <!-- 新增或保存设施提示框 -->
    <a-modal
      v-model="building_save_modalVisible"
      :title="building_save_title"
      ok-text="确认"
      cancel-text="取消"
      :maskClosable="false"
      :destroyOnClose="false"
      @ok="Save_buildingData"
    >
      <a-form-model :model="building_form_data" :label-col="labelCol" :wrapper-col="wrapperCol">
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="楼宇名称">
              <a-input v-model="building_form_data.name" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="楼宇层数">
              <a-input v-model="building_form_data.layers" />
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="楼宇高度">
              <a-input v-model="building_form_data.height" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="楼宇面积">
              <a-input v-model="building_form_data.area" />
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="发布时间">
              <a-date-picker
                :default-value="building_form_data.date"
                @change="Form_date_changeHandler"
                placeholder="选择时间"
              />
            </a-form-model-item>
          </el-col>
        </el-row>
      </a-form-model>
    </a-modal>
  </page-main>
</template>

<script>
import { getBuilding, saveBuilding, deleteBuilding } from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'
import { rTime } from '@/util/time.js'
export default {
  data () {
    return {
      rTime,
      loading: false,
      labelCol: { span: 8 },
      wrapperCol: { span: 14 },
      table_selectedRowKeys: [],
      building_query_type: 'name',
      building_query_buttonTitle: '搜索',
      building_query_text: '',
      building_save_title: '新增楼宇',
      building_save_modalVisible: false,
      building_form_data: {},
      building_data_list: [],
    }
  },
  created () {
    this.Get_buildingDataList()
  },
  watch: {
    building_save_modalVisible (val) {
      if (!val) {
        this.building_form_data = {}
      }
    }
  },
  methods: {
    Get_buildingDataList () {
      getBuilding().then(res => {
        this.building_query_buttonTitle = '搜索'
        this.building_data_list = res.data
        this.building_save_title = '新增楼宇'
      })
    },
    Query_buildingDataList () {
      let text = this.building_query_text
      let temp_list = []
      this.building_data_list.forEach(item => {
        let flag = false
        if (typeof (item[this.building_query_type]) == 'number') {
          if (item[this.building_query_type] <= parseInt(text)) {
            flag = true
          }
        } else {
          if (item[this.building_query_type].indexOf(text) != -1) {
            flag = true
          }
        }
        if (flag) {
          temp_list.push(item)
        }

      })
      this.building_query_buttonTitle = '返回'
      this.building_data_list = temp_list
    },
    Edit_buildingData (form) {
      this.building_save_title = '编辑楼宇'
      this.building_form_data = JSON.parse(JSON.stringify(form))
      this.building_save_modalVisible = true
    },
    Del_buildingData (id) {
      deleteBuilding(id).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.Get_buildingDataList()
      })
    },
    Del_batchData () {
      this.table_selectedRowKeys.forEach(i => {
        this.Del_buildingData(this.building_data_list[i].id)
      })
      this.table_selectedRowKeys = []
    },
    Save_buildingData () {
      saveBuilding(this.building_form_data).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.building_save_modalVisible = false
        this.Get_buildingDataList()
      })


    },
    Table_selectChange (selectedRowKeys) {
      this.table_selectedRowKeys = selectedRowKeys;
    },
    Form_date_changeHandler (date) {
      this.building_form_data.date = date._d
    }
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