<template>
  <page-main>
    <a-card title="小区房间管理">
      <div class="head">
        <span>楼宇</span>
        <a-select size="large" v-model="building_query_name" style="width: 200px;">
          <a-select-option
            v-for="item in room_building_name_list"
            :key="item.name"
            :value="item.name"
          >{{item.name}}</a-select-option>
        </a-select>
        <span style="margin-left: 10px;">单元</span>
        <a-select size="large" v-model="unit_query_name" style="width: 200px;">
          <a-select-option
            v-for="item in room_unit_name_list"
            :key="item.unitName"
            :value="item.unitName"
          >{{item.unitName}}</a-select-option>
        </a-select>
        <a-button
          type="primary"
          style="height: 38px; margin-left: 10px;"
          @click="room_save_modalVisible = true"
        >添加楼宇</a-button>
        <a-button
          type="primary"
          style="height: 38px; margin-left: 10px;"
          @click="Download_roomExcel"
        >导出Excel</a-button>
        <a-button
          type="danger"
          v-if="table_selectedRowKeys.length > 0"
          style="height: 38px; margin-left: 10px;"
          @click="Del_batchData"
        >删除被选择的「楼宇」</a-button>
      </div>
      <a-table
        :loading="loading"
        :data-source="room_data_list"
        :row-selection="{ selectedRowKeys: table_selectedRowKeys, onChange: Table_selectChange }"
      >
        <a-table-column key="id" title="房间编号" data-index="id" />
        <a-table-column key="buildingName" title="楼宇名称" data-index="buildingName" />
        <a-table-column key="unitName" title="单元名称" data-index="unitName" />
        <a-table-column key="direction" title="朝向" data-index="direction" />
        <a-table-column key="purpose" title="用途" data-index="purpose" />
        <a-table-column key="specifications" title="规格" data-index="specifications" />
        <a-table-column key="grade" title="标准" data-index="grade" />
        <a-table-column key="builtupArea" title="建筑面积" data-index="builtupArea" />
        <a-table-column key="useArea" title="使用面积" data-index="useArea" />
        <a-table-column key="isSale" title="是否出售" data-index="isSale">
          <template slot-scope="text, record">
            <a-tag :color="record.isSale == '已有住户' ? 'red' : 'blue'">{{record.isSale}}</a-tag>
          </template>
        </a-table-column>
        <a-table-column key="action" title="操作">
          <template slot-scope="text, record">
            <a-button-group>
              <a-button type="primary" @click="Edit_roomData(record)">编辑</a-button>
              <a-button type="danger" @click="Del_roomData(record.id)">删除</a-button>
            </a-button-group>
          </template>
        </a-table-column>
      </a-table>
    </a-card>
    <!-- 新增或保存设施提示框 -->
    <a-modal
      v-model="room_save_modalVisible"
      :title="room_save_title"
      ok-text="确认"
      cancel-text="取消"
      :maskClosable="false"
      :destroyOnClose="false"
      @ok="Save_roomData"
    >
      <a-form-model :model="room_form_data" :label-col="labelCol" :wrapper-col="wrapperCol">
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="楼宇名称">
              <a-select v-model="room_form_data.buildingName" style="width: 131px;">
                <a-select-option
                  v-for="item in room_building_name_list"
                  :key="item.name"
                  :value="item.name"
                >{{item.name}}</a-select-option>
              </a-select>
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="单元名称">
              <a-input v-model="room_form_data.unitName" />
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="房间朝向">
              <a-select
                default-value="坐北朝南"
                v-model="room_form_data.direction"
                style="width: 131px;"
              >
                <a-select-option value="坐北朝南">坐北朝南</a-select-option>
                <a-select-option value="坐南朝北">坐南朝北</a-select-option>
                <a-select-option value="坐东朝西">坐东朝西</a-select-option>
                <a-select-option value="坐西朝东">坐西朝东</a-select-option>
              </a-select>
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="房间用途">
              <a-select default-value="住宅" v-model="room_form_data.purpose" style="width: 131px;">
                <a-select-option value="住宅">住宅</a-select-option>
                <a-select-option value="商铺">商铺</a-select-option>
              </a-select>
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="房间规格">
              <a-select
                default-value="普通"
                v-model="room_form_data.specifications"
                style="width: 131px;"
              >
                <a-select-option value="普通">普通</a-select-option>
                <a-select-option value="高级">高级</a-select-option>
                <a-select-option value="豪华">豪华</a-select-option>
              </a-select>
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="房间标准">
              <a-select default-value="二室一厅" v-model="room_form_data.grade" style="width: 131px;">
                <a-select-option value="二室一厅">二室一厅</a-select-option>
                <a-select-option value="三室一厅">三室一厅</a-select-option>
                <a-select-option value="三室二厅">三室二厅</a-select-option>
              </a-select>
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="建筑面积">
              <a-input v-model="room_form_data.builtupArea" />
            </a-form-model-item>
          </el-col>
          <el-col :span="12" :offset="0">
            <a-form-model-item label="使用面积">
              <a-input v-model="room_form_data.useArea" />
            </a-form-model-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12" :offset="0">
            <a-form-model-item label="是否出售">
              <a-switch @change="Form_isSale_changeHandler" />
            </a-form-model-item>
          </el-col>
        </el-row>
      </a-form-model>
    </a-modal>
  </page-main>
</template>

<script>
import { getBuildingNames, getUnitNameList, getRoom, saveRoom, deleteRoom, downloadRooms } from '@/api/requests/rq-manage.js'
import { Success, Warning } from '@/util/message.js'
import { download } from '@/util/download.js'
import { rTime } from '@/util/time.js'
export default {
  data () {
    return {
      rTime,
      loading: false,
      init: true,
      isEdit: false,
      labelCol: { span: 8 },
      wrapperCol: { span: 14 },
      table_selectedRowKeys: [],
      building_query_name: '',
      unit_query_name: '',
      room_query_buttonTitle: '搜索',
      room_query_text: '',
      room_save_title: '新增房间',
      room_save_modalVisible: false,
      room_form_data: {},
      room_data_list: [],
      room_building_name_list: [],
      room_unit_name_list: [],
    }
  },
  created () {
    this.Get_buildingNameList()
    this.Get_roomDataList()
  },
  watch: {
    room_save_modalVisible (val) {
      if (!val) {
        this.room_form_data = {}
      }
    },
    building_query_name (val) {
      this.Get_unitNameList(this.building_query_name)
      this.Get_roomDataList()
    },
    unit_query_name (val) {
      this.Get_roomDataList()
    },
    isEdit (val) {
      if (val) {
        this.room_save_title = '新增房间'
      } else {
        this.room_save_title = '编辑房间'

      }
    }

  },
  methods: {
    Get_buildingNameList () {
      getBuildingNames().then(res => {
        this.room_building_name_list = res.data
        this.building_query_name = res.data[0].name
        this.Get_unitNameList(res.data[0].name)
        this.init = false
      })

    },
    Get_unitNameList (buildingName) {
      getUnitNameList(buildingName).then(res => {
        this.room_unit_name_list = res.data
        this.unit_query_name = res.data[0].unitName
      })
    },
    Get_roomDataList () {
      this.loading = true
      if (this.init) {
        this.Get_buildingNameList()
      }
      getRoom().then(res => {
        this.room_query_buttonTitle = '搜索'
        let temp_list = []
        res.data.forEach(item => {
          if (item['buildingName'] == this.building_query_name && item['unitName'] == this.unit_query_name) {
            temp_list.push(item)
          }
        })
        this.room_data_list = temp_list
        this.loading = false
      })
    },
    Edit_roomData (form) {
      this.room_save_title = '编辑房间'
      this.room_form_data = JSON.parse(JSON.stringify(form))
      this.room_save_modalVisible = true
      this.isEdit = true
    },
    Del_roomData (id) {
      deleteRoom(id).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.Get_roomDataList()
      })
    },
    Del_batchData () {
      this.table_selectedRowKeys.forEach(i => {
        this.Del_roomData(this.room_data_list[i].id)
      })
      this.table_selectedRowKeys = []
    },
    Save_roomData () {
      if (this.room_form_data.isSale == undefined) {
        this.room_form_data.isSale = '空'
      }
      saveRoom(this.room_form_data).then(res => {
        if (res.code == 200) {
          Success(this, '操作成功')
        } else {
          Warning(this, '操作失败')
        }
        this.room_save_modalVisible = false
        this.isEdit = false
        this.Get_roomDataList()
      })


    },
    Table_selectChange (selectedRowKeys) {
      this.table_selectedRowKeys = selectedRowKeys;
    },
    Form_isSale_changeHandler (val) {
      this.room_form_data.isSale = val ? '已有住户' : '空'
    },
    Download_roomExcel () {
      downloadRooms().then(res => {
        download('room.xlsx', res.data)
      })
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

<style lang="scss">
.ant-table-body {
    height: 42vh;
    overflow: auto;
}
</style>