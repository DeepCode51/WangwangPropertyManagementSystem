import api from '../index'
import down from '../down'

export let login = (from) => {
  return api.post('/login', from)
}
export let logout_ = () => {
  return api.get('/logout')
}
export let isAdmin = async () => {

  return await
    api.get('/system/user/isAdmin').then(res => {
      return res.data
    })
}

export let getRqInfo = () => {
  return api.get('/system/options/get/rq_info')
}
export let saveRqInfo = (from) => {
  let data = {
    'id': 'rq_info',
    'text': JSON.stringify(from)
  }
  return api.post('/system/options/save', data)
}
export let getUsers = () => {
  return api.get('/system/user/list')
}
export let getEstateUser = () => {
  return api.get('/system/user/admin/list')
}

export let registerUser = async (from) => {
  return api.post('/system/user/register', from)
}
export let saveUser = async (from) => {
  return api.post('/system/user/save', from)
}

export let getFacilities = () => {
  return api.get('/system/facilities/list')
}

export let saveFacilities = (from) => {
  return api.post('/system/facilities/save', from)
}
export let deleteFacilities = (id) => {
  return api.post('/system/facilities/delete', {
    "id": id
  })
}
export let getBuilding = () => {
  return api.get('/system/building/list')
}
export let getBuildingNames = () => {
  return api.get('/system/building/name/list')
}

export let saveBuilding = (from) => {
  return api.post('/system/building/save', from)
}
export let deleteBuilding = (id) => {
  return api.post('/system/building/delete', {
    "id": id
  })
}
export let getRoom = () => {
  return api.get('/system/room/list')
}
export let getUnitNameList = (buildingName) => {
  return api.get(`/system/room/unitName/list/${buildingName}`)
}

export let saveRoom = (from) => {
  return api.post('/system/room/save', from)
}
export let deleteRoom = (id) => {
  return api.post('/system/room/delete', {
    "id": id
  })
}
export let getNotice = () => {
  return api.get('/system/notice/list')
}

export let saveNotice = (from) => {
  return api.post('/system/notice/save', from)
}
export let deleteNotice = (id) => {
  return api.post('/system/notice/delete', {
    "id": id
  })
}

export let downloadRooms = () => {
  return down.get('/system/download/excel/rooms')
}
export let downloadHouseholds = () => {
  return down.get('/system/download/excel/households')
}

export let getRepair = () => {
  return api.get('/system/repair/list')
}
export let getRepairByUserId = () => {
  return api.get('/system/repair/list/user')
}
export let examineRepair = (from) => {
  return api.post('/system/repair/examine', from)
}
export let addRepair = (from) => {
  return api.post('/system/repair/add', from)
}
export let deleteRepair = (from) => {
  return api.post('/system/repair/delete', from)
}
export let addComplaint = (from) => {
  return api.post('/system/complaint/add', from)
}
export let deleteComplaint = (from) => {
  return api.post('/system/complaint/delete', from)
}
export let getComplaint = () => {
  return api.get('/system/complaint/list')
}
export let getComplaintByUserId = () => {
  return api.get('/system/complaint/list/user')
}

export let examineComplaint = (from) => {
  return api.post('/system/complaint/examine', from)
}
export let getTask = () => {
  return api.get('/system/task/list')
}
export let addTask = (from) => {
  return api.post('/system/task/add', from)
}
export let deleteTask = (from) => {
  return api.post('/system/task/delete', from)
}
export let updateTask = (from) => {
  return api.post('/system/task/update', from)
}
export let getChargeType = () => {
  return api.get('/system/chargeType/list')
}
export let saveChargeType = (from) => {
  return api.post('/system/chargeType/save', from)
}
export let deleteChargeType = (id) => {
  return api.post('/system/chargeType/delete', {
    "id": id
  })
}
export let getHousehold = () => {
  return api.get('/system/user/household/list')
}
export let saveHousehold = (from) => {
  return api.post('/system/user/household/save', from)
}
export let deleteHousehold = (from) => {
  return api.post('/system/user/household/delete', from)
}
export let getPayRecordOfMonth = () => {
  return api.get('/system/user/pay/record/month')
}
export let payFess = (chargeTypeId) => {
  return api.get(`/system/user/pay/fess/${chargeTypeId}`)
}
