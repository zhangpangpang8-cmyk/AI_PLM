import request from '@/utils/request'

// 查询图纸管理列表
export function listDrawing(query) {
  return request({
    url: '/system/drawing/list',
    method: 'get',
    params: query
  })
}

// 查询图纸管理详细
export function getDrawing(id) {
  return request({
    url: '/system/drawing/' + id,
    method: 'get'
  })
}

// 新增图纸管理
export function addDrawing(file) {
  const formData = new FormData()
  formData.append('file', file)
  return request({
    url: '/system/drawing',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 修改图纸管理
export function updateDrawing(data) {
  return request({
    url: '/system/drawing',
    method: 'put',
    data: data
  })
}

// 删除图纸管理
export function delDrawing(id) {
  return request({
    url: '/system/drawing/' + id,
    method: 'delete'
  })
}
