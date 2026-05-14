import request from '@/utils/request'

// 查询物料分类（支持多级分类）列表
export function listClassify(query) {
  return request({
    url: '/system/classify/list',
    method: 'get',
    params: query
  })
}

// 查询物料分类（支持多级分类）详细
export function getClassify(id) {
  return request({
    url: '/system/classify/' + id,
    method: 'get'
  })
}

// 新增物料分类（支持多级分类）
export function addClassify(data) {
  return request({
    url: '/system/classify',
    method: 'post',
    data: data
  })
}

// 修改物料分类（支持多级分类）
export function updateClassify(data) {
  return request({
    url: '/system/classify',
    method: 'put',
    data: data
  })
}

// 删除物料分类（支持多级分类）
export function delClassify(id) {
  return request({
    url: '/system/classify/' + id,
    method: 'delete'
  })
}
