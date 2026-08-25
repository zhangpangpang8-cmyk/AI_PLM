import request from '@/utils/request'
import { createCrudApi } from '@/api/crud'

const overviewCrud = createCrudApi('/system/overview')

export const listOverview = overviewCrud.list
export const getOverview = overviewCrud.get
export const addOverview = overviewCrud.add
export const updateOverview = overviewCrud.update
export const delOverview = overviewCrud.remove

export function getStatistics() {
  return request({
    url: '/system/overview/statistics',
    method: 'get'
  })
}
