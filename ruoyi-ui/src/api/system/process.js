import request from '@/utils/request'
import { createCrudApi } from '@/api/crud'

const definitionCrud = createCrudApi('/system/process/definition')

export const listProcessDefinition = definitionCrud.list
export const getProcessDefinition = definitionCrud.get
export const addProcessDefinition = definitionCrud.add
export const updateProcessDefinition = definitionCrud.update
export const delProcessDefinition = definitionCrud.remove

// 保存流程设计（包含节点和连线）
export function saveProcessDesign(data) {
  return request({
    url: '/system/process/definition/saveDesign',
    method: 'post',
    data: data
  })
}

// 查询我的待办任务
export function listPendingTasks(query) {
  return request({
    url: '/system/process/task/pending',
    method: 'get',
    params: query
  })
}

// 查询我的已办任务
export function listCompletedTasks(query) {
  return request({
    url: '/system/process/task/completed',
    method: 'get',
    params: query
  })
}

// 审批任务
export function approveTask(taskId, approved, opinion) {
  return request({
    url: '/system/process/task/approve',
    method: 'post',
    params: {
      taskId: taskId,
      approved: approved,
      opinion: opinion
    }
  })
}

// 查询我发起的流程
export function listMyProcesses(query) {
  return request({
    url: '/system/process/instance/myList',
    method: 'get',
    params: query
  })
}

// 启动流程
export function startProcess(data) {
  return request({
    url: '/system/process/instance/start',
    method: 'post',
    params: data
  })
}

// 查询流程实例详情
export function getProcessInstance(id) {
  return request({
    url: '/system/process/instance/' + id,
    method: 'get'
  })
}
