<template>
  <page-main>
    <a-card :loading="loading" title="小区物业公告管理">
      <div class="head">
        <span>搜索类型</span>
        <a-select
          size="large"
          default-value="公告编号"
          v-model="notice_query_type"
          style="width: 200px;"
        >
          <a-select-option value="id">公告编号</a-select-option>
          <a-select-option value="title">公告标题</a-select-option>
          <a-select-option value="author">发布人</a-select-option>
        </a-select>
        <a-input-search
          v-model="notice_query_text"
          placeholder="输入要搜索的文本"
          :enter-button="notice_query_buttonTitle"
          size="large"
          @search="notice_query_buttonTitle == '搜索' ? Query_noticeDataList() : Get_noticeDataList()"
        />
        <a-button
          type="primary"
          style="height: 38px;"
          @click="notice_save_modalVisible = true"
        >添加物业公告</a-button>
        <a-button
          type="danger"
          v-if="table_selectedRowKeys.length > 0"
          style="height: 40px;"
          @click="Del_batchData"
        >删除被选择的「物业公告」</a-button>
      </div>
      <a-table :data-source="notice_data_list">
        <a-table-column key="id" title="公告编号" data-index="id" />
        <a-table-column key="title" title="公告标题" data-index="title" />
        <a-table-column key="date" title="发布时间" data-index="date">
          <!-- rTime -->
          <template slot-scope="text, record">
            <span>{{rTime(record.date)}}</span>
          </template>
        </a-table-column>
        <a-table-column key="author" title="发布人" data-index="author" />
        <a-table-column key="action" title="操作">
          <template slot-scope="text, record">
            <a-button-group>
              <a-button type="primary" @click="Edit_noticeData(record)">编辑</a-button>
              <a-button type="danger" @click="Del_noticeData(record.id)">删除</a-button>
            </a-button-group>
          </template>
        </a-table-column>
      </a-table>
    </a-card>
    <!-- 新增或保存设施提示框 -->
    <a-modal
      v-model="notice_save_modalVisible"
      :title="notice_save_title"
      ok-text="确认"
      cancel-text="取消"
      @ok="Save_noticeData"
      :maskClosable="false"
      :destroyOnClose="false"
      layout="horizontal"
      style="width: 60vw;"
    >
      <a-form-model :model="notice_form_data" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-model-item label="公告标题">
          <a-input v-model="notice_form_data.title" />
        </a-form-model-item>
        <mavon-editor
          v-model="notice_save_current_text"
          :toolbars="markdownOption"
          style="z-index: 9;"
        />
        <el-row :gutter="20" style="margin-top: 10px;">
          <el-col :span="5" :offset="0">
            <a-input v-model="notice_form_data.author" placeholder="发布人" />
          </el-col>
          <el-col :span="12" :offset="0">
            <a-date-picker
              :default-value="notice_form_data.date"
              @change="Form_date_changeHandler"
              placeholder="发布时间"
            />
          </el-col>
        </el-row>
      </a-form-model>
    </a-modal>
  </page-main>
</template>

<script>
import { getNotice, saveNotice, deleteNotice } from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'
import { rTime } from '@/util/time.js'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
  components: {
    mavonEditor
  },
  data () {
    return {
      rTime,
      loading: false,
      labelCol: { span: 2 },
      wrapperCol: { span: 16 },
      table_selectedRowKeys: [],
      notice_query_type: 'title',
      notice_query_buttonTitle: '搜索',
      notice_query_text: '',
      notice_save_title: '新增小区物业公告',
      notice_save_modalVisible: false,
      notice_save_current_text: '',
      notice_form_data: {},
      notice_data_list: [],
      markdownOption: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        htmlcode: true, // 展示html源码
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        navigation: true, // 导航目录
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        preview: true, // 预览

      }
    }
  },
  created () {
    this.Get_noticeDataList()
  },
  watch: {
    notice_save_modalVisible (val) {
      if (!val) {
        this.notice_form_data = {}
      } else {
        //saveModal被显示
        this.notice_save_current_text = this.notice_form_data.text
      }
    },
    notice_save_current_text (val) {
      const mdit = mavonEditor.getMarkdownIt()
      this.notice_form_data.text = val
      this.notice_form_data.html = mdit.render(val)
    }
  },
  methods: {
    Get_noticeDataList () {
      getNotice().then(res => {
        this.notice_query_buttonTitle = '搜索'
        this.notice_data_list = res.data
        this.notice_save_title = '新增小区物业公告'
      })
    },
    Query_noticeDataList () {
      let text = this.notice_query_text
      let temp_list = []
      this.notice_data_list.forEach(item => {
        if (item[this.notice_query_type].indexOf(text) != -1) {
          temp_list.push(item)
        }
      })
      this.notice_query_buttonTitle = '返回'
      this.notice_data_list = temp_list
    },
    Edit_noticeData (form) {
      this.notice_save_title = '编辑小区物业公告'
      this.notice_form_data = JSON.parse(JSON.stringify(form))
      this.notice_save_modalVisible = true
    },
    Del_noticeData (id) {
      deleteNotice(id).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.Get_noticeDataList()
      })
    },
    Del_batchData () {
      this.table_selectedRowKeys.forEach(i => {
        this.Del_noticeData(this.notice_data_list[i].id)
      })
    },
    Save_noticeData () {
      saveNotice(this.notice_form_data).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.notice_save_modalVisible = false
        this.Get_noticeDataList()
      })


    },
    Table_selectChange (selectedRowKeys) {
      this.table_selectedRowKeys = selectedRowKeys;
    },
    Form_date_changeHandler (date) {
      this.notice_form_data.date = date._d
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