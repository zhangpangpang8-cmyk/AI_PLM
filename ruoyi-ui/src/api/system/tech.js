import { createCrudApi } from '@/api/crud'

const techCrud = createCrudApi('/system/tech', {
  add: { headers: { 'Content-Type': 'multipart/form-data' } }
})

export const listTech = techCrud.list
export const getTech = techCrud.get
export const addTech = techCrud.add
export const updateTech = techCrud.update
export const delTech = techCrud.remove
