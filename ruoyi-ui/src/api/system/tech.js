import request from '@/utils/request'

// 查询技术文档列表
export function listTech(query) {
  return request({
    url: '/system/tech/list',
    method: 'get',
    params: query
  })
}

// 查询技术文档详细
export function getTech(id) {
  return request({
    url: '/system/tech/' + id,
    method: 'get'
  })
}

// 新增技术文档
export function addTech(data) {
  return request({
    url: '/system/tech',
    method: 'post',
    data: data
  })
}

// 修改技术文档
export function updateTech(data) {
  return request({
    url: '/system/tech',
    method: 'put',
    data: data
  })
}

// 删除技术文档
export function delTech(id) {
  return request({
    url: '/system/tech/' + id,
    method: 'delete'
  })
}
