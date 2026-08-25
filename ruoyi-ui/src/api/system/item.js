import request from '@/utils/request'
import { createCrudApi } from '@/api/crud'

const itemCrud = createCrudApi('/system/item')

export const listItem = itemCrud.list
export const getItem = itemCrud.get
export const addItem = itemCrud.add
export const updateItem = itemCrud.update
export const delItem = itemCrud.remove

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
