import request from '@/utils/request'
import { createCrudApi } from '@/api/crud'

const classifyCrud = createCrudApi('/system/classify')

export const listClassify = classifyCrud.list
export const getClassify = classifyCrud.get
export const addClassify = classifyCrud.add
export const updateClassify = classifyCrud.update
export const delClassify = classifyCrud.remove

// 查询物料分类树
export function listClassifyTree() {
  return request({
    url: '/system/classify/tree',
    method: 'get'
  })
}
