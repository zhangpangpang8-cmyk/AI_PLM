import request from '@/utils/request'

// 查询BOM版本列表
export function listBom(query) {
  return request({
    url: '/system/bom/list',
    method: 'get',
    params: query
  })
}

// 查询BOM版本详细
export function getBom(id) {
  return request({
    url: '/system/bom/' + id,
    method: 'get'
  })
}

// 新增BOM版本
export function addBom(data) {
  return request({
    url: '/system/bom',
    method: 'post',
    data: data
  })
}

// 修改BOM版本
export function updateBom(data) {
  return request({
    url: '/system/bom',
    method: 'put',
    data: data
  })
}

// 删除BOM版本
export function delBom(id) {
  return request({
    url: '/system/bom/' + id,
    method: 'delete'
  })
}
