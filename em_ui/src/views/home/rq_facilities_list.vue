<template>
  <div>
    <a-tabs default-active-key="1" tab-position="left" :style="{ height: '200px' }">
      <a-tab-pane v-for="(item,index) in facilities_data_list" :key="index" :tab="item.name">
        <a-descriptions bordered title="设施信息" size="middle">
          <a-descriptions-item label="设施名称">{{item.name}}</a-descriptions-item>
          <a-descriptions-item label="设施类型">{{item.type}}</a-descriptions-item>
          <a-descriptions-item label="负责人">{{item.chargePerson}}</a-descriptions-item>
          <a-descriptions-item label="联系人">{{item.contactPerson}}</a-descriptions-item>
          <a-descriptions-item label="手机号">{{item.phone}}</a-descriptions-item>
          <a-descriptions-item label></a-descriptions-item>
          <a-descriptions-item label="简介">{{item.descri}}</a-descriptions-item>
        </a-descriptions>
      </a-tab-pane>
    </a-tabs>
  </div>
</template>
<script>
import { getFacilities, } from '@/api/requests/rq-manage.js'

export default {
  name: "rq_facilities_list",
  data () {
    return {
      facilities_data_list: [],

    }
  },
  created () {
    this.get_facilities()
  },
  methods: {
    get_facilities () {
      getFacilities().then(res => {
        this.facilities_data_list = res.data
      })
    }

  },
}
</script>