<template>
  <page-main>
    <a-card :loading="loading" title="小区住户信息管理">
      <div class="head">
        <span>搜索类型</span>
        <a-select
          size="large"
          default-value="住户信息名称"
          v-model="household_query_type"
          style="width: 200px;"
        >
          <a-select-option value="userName">用户名</a-select-option>
          <a-select-option value="fullName">真实姓名</a-select-option>
          <a-select-option value="buildingName">所在楼宇</a-select-option>
          <a-select-option value="unitName">所在单元</a-select-option>
          <a-select-option value="roomId">房间编号</a-select-option>
        </a-select>
        <a-input-search
          v-model="household_query_text"
          placeholder="输入要搜索的文本"
          :enter-button="household_query_buttonTitle"
          size="large"
          @search="household_query_buttonTitle == '搜索' ? Query_householdDataList() : Get_householdDataList()"
        />
        <a-button
          type="primary"
          style="height: 40px;"
          @click="household_save_modalVisible = true"
        >添加住户信息</a-button>
        <a-button
          type="primary"
          style="height: 40px; margin-left: 10px;"
          @click="Download_householdsExcel()"
        >导出Excel</a-button>
        <a-button
          type="danger"
          v-if="table_selectedRowKeys.length > 0"
          style="height: 38px; margin-left: 10px;"
          @click="Del_batchData"
        >删除被选择的「住户信息」</a-button>
      </div>
      <a-table
        :data-source="household_data_list"
        :row-selection="{ selectedRowKeys: table_selectedRowKeys, onChange: Table_selectChange }"
      >
        <a-table-column key="userName" title="用户名" data-index="userName" />
        <a-table-column key="fullName" title="真实姓名" data-index="fullName" />
        <a-table-column key="phone" title="联系电话" data-index="phone" />
        <a-table-column key="buildingName" title="所在楼宇" data-index="buildingName" />
        <a-table-column key="unitName" title="所在单元" data-index="unitName" />
        <a-table-column key="roomId" title="房间编号" data-index="roomId" />

        <a-table-column key="action" title="操作">
          <template slot-scope="text, record">
            <a-button-group>
              <a-button type="primary" @click="Edit_householdData(record)">编辑</a-button>
              <a-button type="danger" @click="Del_householdData(record.id)">删除</a-button>
            </a-button-group>
          </template>
        </a-table-column>
      </a-table>
    </a-card>
    <!-- 新增或保存设施提示框 -->
    <a-modal
      v-model="household_save_modalVisible"
      :title="household_save_title"
      ok-text="确认"
      cancel-text="取消"
      :maskClosable="false"
      :destroyOnClose="false"
      @ok="Save_householdData"
    >
      <a-form-model
        :model="household_form_data"
        :rules="rules"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
      >
        <a-form-model-item label="选择用户" prop="userName">
          <div class="global-search-wrapper">
            <a-auto-complete
              class="global-search"
              size="large"
              style="width: 100%;"
              placeholder="选择目标用户"
              option-label-prop="title"
              @select="Users_choice_onSelect"
              @search="Users_choice_handleSearch"
            >
              <template slot="dataSource">
                <a-select-option
                  v-for="item in household_form_search_userNames"
                  :key="item"
                  :title="item"
                >
                  <span class="global-search-item-count">{{ item }}</span>
                </a-select-option>
              </template>
              <a-input v-model="household_form_data.userName"></a-input>
            </a-auto-complete>
          </div>
        </a-form-model-item>
        <a-form-model-item label="选择房间" prop="roomId">
          <a-input v-model="household_form_data.roomId" />
        </a-form-model-item>
        <a-form-model-item label="真实姓名" prop="fullName">
          <a-input v-model="household_form_data.fullName" />
        </a-form-model-item>
        <a-form-model-item label="住户电话" prop="phone">
          <a-input v-model="household_form_data.phone" />
        </a-form-model-item>
      </a-form-model>
    </a-modal>
  </page-main>
</template>

<script>
import { getHousehold, saveHousehold, deleteHousehold, downloadHouseholds, getUsers, registerUser } from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'
import { download } from '@/util/download.js'
import { rTime } from '@/util/time.js'

export default {
  data () {
    return {
      rTime,
      loading: false,
      labelCol: { span: 7 },
      wrapperCol: { span: 7 },
      table_selectedRowKeys: [],
      household_query_type: 'userName',
      household_query_buttonTitle: '搜索',
      household_query_text: '',
      household_save_title: '新增住户信息',
      household_save_modalVisible: false,
      household_form_data: {},
      household_data_list: [],
      rules: {
        userName: [{ required: true, message: '此项为必填项', trigger: 'blur' }],
        fullName: [{ required: true, message: '此项为必填项', trigger: 'blur' }],
        phone: [{ required: true, message: '此项为必填项', trigger: 'blur' }],
        roomId: [{ required: true, message: '此项为必填项', trigger: 'blur' }],
      },
      user_data_list: [],
      household_form_search_userNames: [],
      household_form_userName_isChoice: false,
      household_user_register_from: {},
    }
  },
  created () {
    this.Get_householdDataList()
    this.Get_users()
  },
  watch: {
    household_save_modalVisible (val) {
      if (!val) {
        this.household_form_data = {}
      }
    }
  },
  methods: {
    Get_householdDataList () {
      getHousehold().then(res => {
        this.household_query_buttonTitle = '搜索'
        this.household_data_list = res.data
        this.household_save_title = '新增住户信息'
      })
    },
    Get_users () {
      getUsers().then(res => {
        this.user_data_list = res.data
      })
    },
    Query_householdDataList () {
      let text = this.household_query_text
      let temp_list = []
      this.household_data_list.forEach(item => {
        if (item[this.household_query_type].indexOf(text) != -1) {
          temp_list.push(item)
        }
      })
      this.household_query_buttonTitle = '返回'
      this.household_data_list = temp_list
    },
    Edit_householdData (form) {
      this.household_save_title = '编辑住户信息'
      this.household_form_data = JSON.parse(JSON.stringify(form))
      this.household_save_modalVisible = true
    },
    Del_householdData (id) {
      deleteHousehold(id).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.Get_householdDataList()
      })
    },
    Del_batchData () {
      this.table_selectedRowKeys.forEach(i => {
        this.Del_householdData(this.household_data_list[i].id)
      })
      this.table_selectedRowKeys = []
    },
    async Save_householdData () {
      let userId = null
      if (!this.household_form_userName_isChoice) {
        this.household_user_register_from.fullName = this.household_form_data.fullName
        this.household_user_register_from.phone = this.household_form_data.phone
        this.household_user_register_from.password = '123456'
        let b = await this.Register_User(this.household_user_register_from)
        console.log(b)

        if (b != false) {
          userId = b.userId
        }
      }
      if (userId != null) {
        this.household_form_data.userId = userId
      }
      saveHousehold(this.household_form_data).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.household_save_modalVisible = false
        this.Get_householdDataList()
      })


    },
    Download_householdsExcel () {
      downloadHouseholds().then(res => {
        download('小区住户信息.xlsx', res.data)
      })
    },
    Table_selectChange (selectedRowKeys) {
      this.table_selectedRowKeys = selectedRowKeys;
    },
    async Register_User (from) {
      this.$message.info('系统自动为此账户注册，默认密码为「123456」')
      return await registerUser(from).then(res => {
        return res.data
      })

    },
    Users_choice_onSelect (value) {
      let flag = false
      this.user_data_list.forEach(item => {
        if (item.userName.indexOf(value) != -1) {
          flag = true
          this.household_form_data.userId = item.id
        }
      })
    },

    Users_choice_handleSearch (value) {
      let flag = false
      this.household_form_search_userNames = []
      this.user_data_list.forEach(item => {
        if (item.userName.indexOf(value) != -1) {
          flag = true
          this.household_form_userName_isChoice = true
          this.household_form_search_userNames.push(item.userName)
        }
      })
      if (!flag) {
        this.household_user_register_from.userName = value
        this.household_form_userName_isChoice = false
      }
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