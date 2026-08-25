import { createCrudApi } from '@/api/crud'

const drawingCrud = createCrudApi('/system/drawing', {
  add: { headers: { 'Content-Type': 'multipart/form-data' } }
})

export const listDrawing = drawingCrud.list
export const getDrawing = drawingCrud.get
export const addDrawing = drawingCrud.add
export const updateDrawing = drawingCrud.update
export const delDrawing = drawingCrud.remove
