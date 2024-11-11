import Layout from '@/layout'

export default [
  // {
  //   path: '/rq-manager',
  //   component: Layout,
  //   redirect: '/rq-manager/info',
  //   meta: {
  //     title: '小区管理'
  //   },
  //   children: [
  //     {
  //       path: 'info',
  //       name: 'rq_info',
  //       component: () => import('@/views/admin/rq/rq_info.vue'),
  //       meta: {
  //         title: '基本信息管理',
  //       }
  //     },
  //     {
  //       path: 'facilities',
  //       name: 'rq_facilities',
  //       component: () => import('@/views/admin/rq/rq_facilities.vue'),
  //       meta: {
  //         title: '周边设施管理',
  //       }
  //     },
  //     {
  //       path: 'notices',
  //       name: 'rq_notices',
  //       component: () => import('@/views/admin/rq/rq_notices.vue'),
  //       meta: {
  //         title: '物业公告管理',
  //       }
  //     },
  //   ]
  // },
  // {
  //   path: '/building-manager',
  //   component: Layout,
  //   redirect: '/building-manager/building',
  //   meta: {
  //     title: '楼盘管理'
  //   },
  //   children: [
  //     {
  //       path: 'building',
  //       name: 'rq_building',
  //       component: () => import('@/views/admin/rq/rq_building.vue'),
  //       meta: {
  //         title: '楼宇管理',
  //       }
  //     },
  //     {
  //       path: 'room',
  //       name: 'rq_room',
  //       component: () => import('@/views/admin/rq/rq_room.vue'),
  //       meta: {
  //         title: '房间管理',
  //       }
  //     }
  //   ]
  // },
  {
    path: '/user/base',
    component: Layout,
    redirect: '/user/base/info',
    meta: {
      title: '基本信息'
    },
    children: [
      {
        path: 'info',
        name: 'user_info',
        component: () => import('@/views/admin/user/user_info.vue'),
        meta: {
          title: '个人信息',
        }
      },
      {
        path: 'reset_pwd',
        name: 'user_reset_pwd',
        component: () => import('@/views/admin/user/user_reset_pwd.vue'),
        meta: {
          title: '密码修改',
        }
      },

    ]
  },
  {
    path: '/user/',
    component: Layout,
    redirect: '/user/pay',
    meta: {
      title: '物业收费'
    },
    children: [
      // {
      //   path: 'pay',
      //   name: 'user_pay',
      //   component: () => import('@/views/admin/user/user_pay.vue'),
      //   meta: {
      //     title: '缴纳费用',
      //   }
      // },
      {
        path: 'pay_record',
        name: 'user_pay',
        component: () => import('@/views/admin/user/user_pay.vue'),
        meta: {
          title: '缴纳费用',
        }
      },

    ]
  },
  {
    path: '/user/repair',
    component: Layout,
    redirect: '/user/repair/add',
    meta: {
      title: '报修管理'
    },
    children: [
      {
        path: 'add',
        name: 'rq_repair',
        component: () => import('@/views/admin/rq/guarantee/rq_repair_add.vue'),
        meta: {
          title: '申请报修',
        }
      },
      {
        path: 'manager',
        name: 'rq_repair',
        component: () => import('@/views/admin/rq/guarantee/rq_repair_manager.vue'),
        meta: {
          title: '报修管理',
        }
      },

    ]
  },
  {
    path: '/user/complaint',
    component: Layout,
    redirect: '/user/complaint/add',
    meta: {
      title: '投诉管理'
    },
    children: [
      {
        path: 'add',
        name: 'rq_complaint',
        component: () => import('@/views/admin/rq/guarantee/rq_complaint_add.vue'),
        meta: {
          title: '发起投诉',
        }
      },
      {
        path: 'manager',
        name: 'rq_complaint',
        component: () => import('@/views/admin/rq/guarantee/rq_complaint_manager.vue'),
        meta: {
          title: '投诉管理',
        }
      },

    ]
  },

  // {
  //   path: '/videos_example/1',
  //   component: Layout,
  //   redirect: '/videos_example/aaas',
  //   meta: {
  //     title: '收费管理'
  //   },
  //   children: [
  //     {
  //       path: 'aaas',
  //       name: 'videosExampleVideo1',
  //       component: () => import('@/views/videos_example/1'),
  //       meta: {
  //         title: '收费类型管理',
  //       }
  //     },
  //     {
  //       path: 'aaas',
  //       name: 'videosExampleVideo1',
  //       component: () => import('@/views/videos_example/1'),
  //       meta: {
  //         title: '住户费用管理',
  //       }
  //     },
  //   ]
  // },
  // {
  //   path: '/videos_example/1',
  //   component: Layout,
  //   redirect: '/videos_example/aaas',
  //   meta: {
  //     title: '用户管理'
  //   },
  //   children: [
  //     {
  //       path: 'aaas',
  //       name: 'videosExampleVideo1',
  //       component: () => import('@/views/videos_example/1'),
  //       meta: {
  //         title: '住户信息管理',
  //       }
  //     },
  //     {
  //       path: 'aaas',
  //       name: 'videosExampleVideo1',
  //       component: () => import('@/views/videos_example/1'),
  //       meta: {
  //         title: '物业管理人员管理',
  //       }
  //     },
  //   ]
  // },
  // {
  //   path: '/videos_example/1',
  //   component: Layout,
  //   redirect: '/videos_example/aaas',
  //   meta: {
  //     title: '系统管理'
  //   },
  //   children: [
  //     {
  //       path: 'aaas',
  //       name: 'videosExampleVideo1',
  //       component: () => import('@/views/videos_example/1'),
  //       meta: {
  //         title: '定时任务管理',
  //       }
  //     },
  //     {
  //       path: 'aaas',
  //       name: 'videosExampleVideo1',
  //       component: () => import('@/views/videos_example/1'),
  //       meta: {
  //         title: '编辑管理员',
  //       }
  //     },
  //   ]
  // },
]
