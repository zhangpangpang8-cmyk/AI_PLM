import request from '@/utils/request'

// 查询物料管理（仅存储激活版本）列表
export function listItem(query) {
  return request({
    url: '/system/item/list',
    method: 'get',
    params: query
  })
}

// 查询物料管理（仅存储激活版本）详细
export function getItem(id) {
  return request({
    url: '/system/item/' + id,
    method: 'get'
  })
}

// 新增物料管理（仅存储激活版本）
export function addItem(data) {
  return request({
    url: '/system/item',
    method: 'post',
    data: data
  })
}

// 修改物料管理（仅存储激活版本）
export function updateItem(data) {
  return request({
    url: '/system/item',
    method: 'put',
    data: data
  })
}

// 删除物料管理（仅存储激活版本）
export function delItem(id) {
  return request({
    url: '/system/item/' + id,
    method: 'delete'
  })
}

// 按一级分类统计物料数量
export function getStatistics() {
  return request({
    url: '/system/item/statistics',
    method: 'get'
  })
}

// 审核物料
export function auditItem(data) {
  return request({
    url: '/system/item/audit',
    method: 'put',
    data: data
  })
}

// 查询物料版本历史
export function getVersionHistory(itemId) {
  return request({
    url: '/system/item/versionHistory',
    method: 'get',
    params: { itemId: itemId }
  })
}
