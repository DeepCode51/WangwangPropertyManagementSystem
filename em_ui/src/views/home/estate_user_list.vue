<template>
  <div>
    <a-list :grid="{ gutter: 30, column: 3 }" :data-source="estate_data_list">
      <a-list-item slot="renderItem" slot-scope="item, index">
        <a-card :title="`物业人员编号「${index}」`">
          <!-- <a-avatar shape="square" :size="100" icon="user" style="text-align: center;" /> -->
          <a-avatar
            shape="square"
            :size="100"
            src="https://www.hualigs.cn/image/6056ea2ee7807.jpg"
            style="text-align: center;"
          />
          <br />
          <br />
          <a-row :gutter="16">
            <a-col :span="7">
              <a-statistic title="姓名" :value="item.fullName" />
            </a-col>
            <a-col :span="12">
              <a-statistic title="联系电话" :value="` ${item.phone}`" />
            </a-col>
          </a-row>
        </a-card>
      </a-list-item>
    </a-list>
  </div>
</template>
<script>
import { getEstateUser, } from '@/api/requests/rq-manage.js'

export default {
  name: "estate_user_list",
  data () {
    return {
      estate_data_list: [],
    }
  },
  created () {
    this.get_estateUser()
  },
  methods: {
    get_estateUser () {
      getEstateUser().then(res => {
        this.estate_data_list = res.data
      })
    }
  },
}
</script>

<style lang="scss">
.ant-statistic-content {
    font-size: 18px;
}
</style>