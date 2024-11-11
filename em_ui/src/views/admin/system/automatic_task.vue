<template>
  <page-main>
    <a-card :loading="loading" title="定时任务管理">
      <div class="head">
        <span>搜索类型</span>
        <a-select size="large" default-value="公告编号" v-model="task_query_type" style="width: 200px;">
          <a-select-option value="name">任务名称</a-select-option>
          <a-select-option value="id">任务编号</a-select-option>
        </a-select>
        <a-input-search
          v-model="task_query_text"
          placeholder="输入要搜索的文本"
          :enter-button="task_query_buttonTitle"
          size="large"
          @search="task_query_buttonTitle == '搜索' ? Query_taskDataList() : Get_taskDataList()"
        />
        <a-button type="primary" style="height: 38px;" @click="task_save_modalVisible = true">添加定时任务</a-button>
        <a-button
          type="danger"
          v-if="table_selectedRowKeys.length > 0"
          style="height: 40px;"
          @click="Del_batchData"
        >删除被选择的「定时任务」</a-button>
      </div>
      <a-table :data-source="task_data_list">
        <a-table-column key="id" title="任务编号" data-index="id" />
        <a-table-column key="name" title="任务名称" data-index="name" />
        <a-table-column key="groupName" title="任务组名" data-index="groupName" />
        <a-table-column key="classPath" title="调用目标类" data-index="classPath" />
        <a-table-column key="cron" title="cron表达式" data-index="cron" />
        <a-table-column key="status" title="当前状态" data-index="status">
          <template slot-scope="text, record">
            <a-switch v-model="record.status == 0" @change="Change_taskStatus(record)" />
          </template>
        </a-table-column>

        <a-table-column key="action" title="操作">
          <template slot-scope="text, record">
            <a-button-group>
              <a-button type="primary" @click="Edit_taskData(record)">编辑</a-button>
              <a-button type="danger" @click="Del_taskData(record)">删除</a-button>
            </a-button-group>
          </template>
        </a-table-column>
      </a-table>
    </a-card>
    <!-- 新增或保存设施提示框 -->
    <a-modal
      v-model="task_save_modalVisible"
      :title="task_save_title"
      ok-text="确认"
      cancel-text="取消"
      @ok="Save_taskData"
      :maskClosable="false"
      :destroyOnClose="false"
      layout="horizontal"
      style="width: 54vw;"
    >
      <a-form-model
        :model="task_form_data"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        :rules="rules"
      >
        <el-row :gutter="20" style="margin-top: 10px;">
          <el-col :span="10">
            <a-form-model-item label="任务名称" prop="name">
              <a-input v-model="task_form_data.name" style="width: 200px;" placeholder="请输入任务名称" />
            </a-form-model-item>
          </el-col>
          <el-col :span="8">
            <a-form-model-item label="任务组别" prop="groupName">
              <a-select
                default-value="系统任务"
                v-model="task_form_data.groupName"
                style="width: 200px;"
                placeholder="请选择"
              >
                <a-select-option value="系统任务">系统任务</a-select-option>
                <a-select-option value="自定任务">自定任务</a-select-option>
              </a-select>
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <a-form-model-item label="调用类" prop="classPath">
              <a-input
                v-model="task_form_data.classPath"
                style="width: 481px;"
                placeholder="请输入任务调用的目标类"
              >
                <a-tooltip slot="suffix" title="Extra information">
                  <a-icon type="info-circle" style="color: rgba(0, 0, 0, 0.45);" />
                </a-tooltip>
              </a-input>
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <a-form-model-item label="cron表达式" prop="cron">
              <a-input
                v-model="task_form_data.cron"
                style="width: 200px;"
                placeholder="请输入cron表达式"
              />
            </a-form-model-item>
          </el-col>
          <el-col :span="8">
            <a-form-model-item label="当前策略" placeholder="请选择" prop="status">
              <a-select default-value="0" v-model="task_form_data.status" style="width: 200px;">
                <a-select-option value="0">立即执行</a-select-option>
                <a-select-option value="1">暂停任务</a-select-option>
              </a-select>
            </a-form-model-item>
          </el-col>
        </el-row>
      </a-form-model>
    </a-modal>
  </page-main>
</template>

<script>
import { getTask, updateTask, deleteTask, addTask } from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'
import { rTime } from '@/util/time.js'

export default {
  data () {
    return {
      rTime,
      loading: false,
      labelCol: { span: 8 },
      wrapperCol: { span: 5 },
      table_selectedRowKeys: [],
      task_query_type: 'name',
      task_query_buttonTitle: '搜索',
      task_query_text: '',
      task_save_title: '新增定时任务',
      task_save_modalVisible: false,
      task_save_current_text: '',
      task_form_data: {},
      task_data_list: [],
      rules: {
        id: [{ required: true, message: '', trigger: 'blur' }],
        name: [{ required: true, message: '', trigger: 'blur' }],
        status: [{ required: true, message: '', trigger: 'blur' }],
        groupName: [{ required: true, message: '', trigger: 'blur' }],
        cron: [{ required: true, message: '', trigger: 'blur' }],
        classPath: [{ required: true, message: '', trigger: 'blur' }],
      },
    }
  },
  created () {
    this.Get_taskDataList()
  },
  watch: {
    task_save_modalVisible (val) {
      if (!val) {
        this.task_form_data = {}
      } else {
        //saveModal被显示
        this.task_save_current_text = this.task_form_data.text
      }
    },
  },
  methods: {
    Get_taskDataList () {
      getTask().then(res => {
        this.task_query_buttonTitle = '搜索'
        this.task_data_list = res.data
        this.task_save_title = '新建定时任务'
      })
    },
    Query_taskDataList () {
      let text = this.task_query_text
      let temp_list = []
      this.task_data_list.forEach(item => {
        if (item[this.task_query_type].indexOf(text) != -1) {
          temp_list.push(item)
        }
      })
      this.task_query_buttonTitle = '返回'
      this.task_data_list = temp_list
    },
    Edit_taskData (form) {
      this.task_save_title = '编辑定时任务'
      this.task_form_data = JSON.parse(JSON.stringify(form))
      this.task_save_modalVisible = true
    },
    Del_taskData (data) {
      deleteTask(data).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.Get_taskDataList()
      })
    },
    Del_batchData () {
      this.table_selectedRowKeys.forEach(i => {
        this.Del_taskData({
          'name': this.task_data_list[i].name,
          'groupName': this.task_data_list[i].groupName
        })
      })
    },
    Save_taskData () {
      let form = this.task_form_data
      let req = this.task_save_title == '编辑定时任务' ? updateTask(form) : addTask(form)
      req.then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.task_save_modalVisible = false
        this.Get_taskDataList()
      })


    },
    Table_selectChange (selectedRowKeys) {
      this.table_selectedRowKeys = selectedRowKeys;
    },
    Form_date_changeHandler (date) {
      this.task_form_data.date = date._d
    },
    Change_taskStatus (r) {
      r.status = r.status == 0 ? 1 : 0
      this.task_form_data = r
      this.task_save_title = '编辑定时任务'
      this.Save_taskData()

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

<style lang="scss" >
.ant-modal-content {
    width: 60vw;
}
</style>