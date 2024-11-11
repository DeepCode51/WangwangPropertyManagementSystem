<template>
  <div class="content">
    <div class="list">
      <a-list item-layout="horizontal" :data-source="notice_data_list">
        <a-list-item slot="renderItem" slot-scope="item, index" style="border: 1px solid #e8e8e8;">
          <a-list-item-meta :description="rTime(item.date)">
            <a slot="title" @click="open_notice_item_modal(item)">{{ item.title }}</a>
            <a-avatar slot="avatar" :size="45">{{item.author}}</a-avatar>
          </a-list-item-meta>
        </a-list-item>
      </a-list>
    </div>
    <a-pagination size="small" :total="50" />
    <a-modal
      v-model="notice_item_modal_visable"
      :title="notice_current_item.title"
      destroyOnClose
      :maskClosable="false"
      :footer="null"
      :width="1000"
      :height="100"
      style="height: 20vh;"
    >
      <a-alert
        :message="`「${notice_current_item.author}」发布于 ${rTime(notice_current_item.date)}`"
        type="info"
        style="margin-bottom: 10px;"
      />
      <div v-html="notice_current_item.html" class="text"></div>
    </a-modal>
  </div>
</template>
<script>
import { getNotice, } from '@/api/requests/rq-manage.js'
import { rTime } from '@/util/time.js'

export default {
  name: 'notice_list',
  data () {
    return {
      rTime,
      notice_data_list: [],
      notice_item_modal_visable: false,
      notice_current_item: {},
    }
  },
  created () {
    this.get_noticeDataList()
  },
  methods: {
    get_noticeDataList () {
      getNotice().then(res => {
        if (res.code == 200) {
          this.notice_data_list = res.data
        }
      })
    },
    open_notice_item_modal (val) {
      this.notice_item_modal_visable = true
      this.notice_current_item = val
    }
  },

}
</script>

<style lang="scss" scoped>
.list {
    height: 230px;
    padding: 10px;
    overflow: auto;
    .ant-list-item-meta {
        padding-left: 10px;
    }
}
.ant-pagination {
    margin-top: 10px;
}
.ant-modal-content {
    .text {
        height: 60vh;
        overflow: auto;
    }
}
.ant-card-body .content {
    padding: 0 10px;
}
</style>