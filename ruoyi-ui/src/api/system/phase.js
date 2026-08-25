import { createCrudApi } from '@/api/crud'

const phaseCrud = createCrudApi('/system/phase')

export const listPhase = phaseCrud.list
export const getPhase = phaseCrud.get
export const addPhase = phaseCrud.add
export const updatePhase = phaseCrud.update
export const delPhase = phaseCrud.remove
