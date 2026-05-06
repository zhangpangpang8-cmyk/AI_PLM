import request from '@/utils/request'

// 查询流程定义列表
export function listProcessDefinition(query) {
  return request({
    url: '/system/process/definition/list',
    method: 'get',
    params: query
  })
}

// 查询流程定义详细
export function getProcessDefinition(id) {
  return request({
    url: '/system/process/definition/' + id,
    method: 'get'
  })
}

// 新增流程定义
export function addProcessDefinition(data) {
  return request({
    url: '/system/process/definition',
    method: 'post',
    data: data
  })
}

// 修改流程定义
export function updateProcessDefinition(data) {
  return request({
    url: '/system/process/definition',
    method: 'put',
    data: data
  })
}

// 删除流程定义
export function delProcessDefinition(id) {
  return request({
    url: '/system/process/definition/' + id,
    method: 'delete'
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
