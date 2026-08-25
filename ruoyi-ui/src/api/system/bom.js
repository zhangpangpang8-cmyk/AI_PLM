import { createCrudApi } from '@/api/crud'

const bomCrud = createCrudApi('/system/bom')

export const listBom = bomCrud.list
export const getBom = bomCrud.get
export const addBom = bomCrud.add
export const updateBom = bomCrud.update
export const delBom = bomCrud.remove
