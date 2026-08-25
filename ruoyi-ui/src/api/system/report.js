import { createCrudApi } from '@/api/crud'

const reportCrud = createCrudApi('/system/report')

export const listReport = reportCrud.list
export const getReport = reportCrud.get
export const addReport = reportCrud.add
export const updateReport = reportCrud.update
export const delReport = reportCrud.remove
