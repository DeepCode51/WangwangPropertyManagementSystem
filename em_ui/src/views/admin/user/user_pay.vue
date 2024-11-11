<template>
  <page-main>
    <a-table :loading="loading" :data-source="payRecord_data_list">
      <a-table-column key="chargeName" title="费用类型" data-index="chargeName" />
      <a-table-column key="chargeMoney" title="所需费用" data-index="chargeMoney" />
      <a-table-column key="createTime" title="缴费类型创建时间" data-index="createTime" >
        <template slot-scope="text, record">
          {{rTime(text)}}
        </template>
      </a-table-column>
      </a-table-column>
      <a-table-column key="isPayment" title="本月是否缴纳" data-index="isPayment">
        <template slot-scope="text, record">
          <a-tag v-if="text" color="red">已缴纳</a-tag>
          <a-tag v-else color="blue" @click="pay_fess(record.chargeTypeId)">未缴纳</a-tag>
        </template>
      </a-table-column>
    </a-table> 
  </page-main>
</template>

<script>
import { getPayRecordOfMonth, payFess } from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'
import { rTime } from '@/util/time.js'


export default {
  name: 'user_pay',
  data () {
    return {
      rTime,
      loading: false,
      payRecord_data_list: [],
    }
  },
  created () {
    this.get_payRecordOfMonth()
  },
  methods: {
    get_payRecordOfMonth () {
      getPayRecordOfMonth().then(res => {
        this.payRecord_data_list = res.data
      })
    },
    pay_fess (chargeTypeId) {
      payFess(chargeTypeId).then(res => {
        this.$success({
          title: '支付物业费用回执',
          // JSX support
          content: (
            <div>
              <p>操作成功！</p>
            </div>
          ),
        });
        this.get_payRecordOfMonth()
      })
    }
  },
}
</script>