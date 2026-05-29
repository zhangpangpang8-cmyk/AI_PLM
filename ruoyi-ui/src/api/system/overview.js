import request from '@/utils/request'

// 查询项目概述列表
export function listOverview(query) {
  return request({
    url: '/system/overview/list',
    method: 'get',
    params: query
  })
}

// 查询项目概述详细
export function getOverview(id) {
  return request({
    url: '/system/overview/' + id,
    method: 'get'
  })
}

// 新增项目概述
export function addOverview(data) {
  return request({
    url: '/system/overview',
    method: 'post',
    data: data
  })
}

// 修改项目概述
export function updateOverview(data) {
  return request({
    url: '/system/overview',
    method: 'put',
    data: data
  })
}

// 删除项目概述
export function delOverview(id) {
  return request({
    url: '/system/overview/' + id,
    method: 'delete'
  })
}
export function getStatistics() {
  return request({
    url: '/system/overview/statistics',
    method: 'get'
  })
}
