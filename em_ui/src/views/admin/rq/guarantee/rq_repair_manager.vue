<template>
  <page-main>
    <a-card :loading="loading" title="报修管理">
      <div class="head" v-if="!isUser">
        <span>搜索类型</span>
        <a-select
          size="large"
          default-value="申请原因"
          v-model="repair_query_type"
          style="width: 200px;"
        >
          <a-select-option value="title">申请原因</a-select-option>
          <a-select-option value="userName">用户名称</a-select-option>
          <a-select-option value="address">用户住址</a-select-option>
          <a-select-option value="phone">电话</a-select-option>
        </a-select>
        <a-input-search
          v-model="repair_query_text"
          placeholder="输入要搜索的文本"
          :enter-button="repair_query_buttonTitle"
          size="large"
          @search="repair_query_buttonTitle == '搜索' ? Query_repairDataList() : Get_repairDataList()"
        />
        <a-button
          type="danger"
          v-if="table_selectedRowKeys.length > 0"
          style="height: 38px; margin-left: 10px;"
          @click="Del_batchData"
        >删除被选择的「报修申请单」</a-button>
      </div>
      <a-table
        :data-source="repair_data_list"
        :row-selection="{ selectedRowKeys: table_selectedRowKeys, onChange: Table_selectChange }"
      >
        <a-table-column key="id" title="申请编号" data-index="id" />
        <a-table-column key="date" title="申请时间" data-index="date">
          <!-- rTime -->
          <template slot-scope="text, record">
            <span>{{String(record.date).substr(0,10)}}</span>
          </template>
        </a-table-column>
        <a-table-column key="userName" title="申请人" data-index="userName" />
        <a-table-column key="title" title="申请原因" data-index="title" />
        <a-table-column key="phone" title="联系方式" data-index="phone" />
        <a-table-column key="address" title="用户住址" data-index="address" />
        <a-table-column key="isExamine" title="是否处理" data-index="isExamine">
          <template slot-scope="text, record">
                 <a-tooltip placement="top">
        <template slot="title">
          <span>点我查看详情</span>
        </template>
        <div @click="See_repairDateModal(record)">
                          <a-tag :color="record.isExamine == 1 ? 'red' : 'blue'" >
              {{
              record.isExamine == 1 ? '已处理' : '未处理'
              }}
            </a-tag>
            </div>

      </a-tooltip>
            
          </template>
        </a-table-column>
        <a-table-column v-if="!isUser" key="action" title="操作">
          <template slot-scope="text, record">
            <a-button-group>
              <a-button
                :disabled="record.isExamine == 1"
                type="primary"
                @click="Edit_repairData(record)"
              >审核</a-button>
              <a-button type="danger" @click="Del_repairData(record.id)">删除</a-button>
            </a-button-group>
          </template>
        </a-table-column>
      </a-table>
    </a-card>
    <!-- 新增或保存设施提示框 -->
    <a-modal
      v-model="repair_save_modalVisible"
      :title="repair_save_title"
      ok-text="确认"
      cancel-text="取消"
      :maskClosable="false"
      :destroyOnClose="false"
      @ok="Save_repairData"
    >
      <a-form-model :model="repair_form_data" :label-col="labelCol" :wrapper-col="wrapperCol">
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="申请人">
              <span>{{repair_form_data.userName}}</span>
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="联系方式">
              <span>{{repair_form_data.phone}}</span>
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="保修单标题">
              <span>{{repair_form_data.title}}</span>
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="用户住址">
              <span>{{repair_form_data.address}}</span>
            </a-form-model-item>
          </el-col>
        </el-row>
        <div>
          <span style="color: rgba(0, 0, 0, 0.85);">报修详情:</span>
          <a-input
            v-model="repair_form_data.text"
            type="textarea"
            disabled
            :rows="5"
            style="margin-top: 10px;"
          />
        </div>
        <div>
          <span style="color: rgba(0, 0, 0, 0.85);">审核报修:</span>
          <a-input
            v-model="repair_form_data.examineData"
            type="textarea"
            :rows="5"
            style="margin-top: 10px;"
          />
        </div>
      </a-form-model>
    </a-modal>


    <a-modal
      v-model="repair_see_modalVisible"
      title="详细信息"
      ok-text="确认"
      @ok="repair_see_modalVisible = false"
      cancel-text="取消"
      :maskClosable="false"
      :destroyOnClose="false"
    >
        <div style="margin-top: 10px;">
          <span style="color: rgba(0, 0, 0, 0.85);">报修详情:</span>
          <a-input
            v-model="repair_form_data.text"
            type="textarea"
            disabled
            :rows="5"
            style="margin-top: 10px;"
          />
        </div>
        <div style="margin-top: 10px;">
          <span style="color: rgba(0, 0, 0, 0.85);">审核报修:</span>
          <a-input
            v-model="repair_form_data.examineData"
            type="textarea"
            disabled
            :rows="5"
            style="margin-top: 10px;"
          />
        </div>
      </a-form-model>
    </a-modal>
  </page-main>
</template>

<script>
import { getRepair, examineRepair ,deleteRepair,getRepairByUserId} from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'
import { rTime } from '@/util/time.js'

export default {
  data () {
    return {
      isUser:true,
      loading: false,
      labelCol: { span: 8 },
      wrapperCol: { span: 14 },
      table_selectedRowKeys: [],
      repair_query_type: 'title',
      repair_query_buttonTitle: '搜索',
      repair_query_text: '',
      repair_save_title: '报修申请单审核',
      repair_save_modalVisible: false,
      repair_see_modalVisible:false,
      repair_form_data: {},
      repair_data_list: [],
    }
  },
  created () {
    this.isUser = this.$route.path.indexOf('user') != -1
    this.Get_repairDataList()
  },
  watch: {
    repair_save_modalVisible (val) {
      if (!val) {
        this.repair_form_data = {}
      }
    }
  },
  methods: {
    Get_repairDataList () {
      let method = getRepairByUserId()
      if(localStorage.getItem('isAdmin') == 'true'){
        method = getRepair()
      }
      method.then(res => {
        this.repair_query_buttonTitle = '搜索'
        this.repair_data_list = res.data
      })
    },
    Query_repairDataList () {
      let text = this.repair_query_text
      let temp_list = []
      this.repair_data_list.forEach(item => {
        if (item[this.repair_query_type].indexOf(text) != -1) {
          temp_list.push(item)
        }
      })
      this.repair_query_buttonTitle = '返回'
      this.repair_data_list = temp_list
    },
    Edit_repairData (form) {
      this.repair_form_data = JSON.parse(JSON.stringify(form))
      this.repair_save_modalVisible = true
    },
    Del_repairData (id) {
            let data = {
        'id':id
      }
      deleteRepair(data).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.Get_repairDataList()
      })
    },
    Del_batchData () {
      this.table_selectedRowKeys.forEach(i => {
        this.Del_repairData(this.repair_data_list[i].id)
      })
      this.table_selectedRowKeys = []
    },
    Save_repairData () {
      examineRepair(this.repair_form_data).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.repair_save_modalVisible = false
        this.Get_repairDataList()
      })


    },
    Table_selectChange (selectedRowKeys) {
      this.table_selectedRowKeys = selectedRowKeys;
    },
    See_repairDateModal(form){
      this.repair_form_data = JSON.parse(JSON.stringify(form))
      this.repair_see_modalVisible = true
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
.ant-form-item-children span {
    margin-left: 10px;
}
</style>