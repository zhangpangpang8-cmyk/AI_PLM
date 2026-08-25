import { createCrudApi } from '@/api/crud'

const logCrud = createCrudApi('/system/log')

export const listLog = logCrud.list
export const getLog = logCrud.get
export const addLog = logCrud.add
export const updateLog = logCrud.update
export const delLog = logCrud.remove
