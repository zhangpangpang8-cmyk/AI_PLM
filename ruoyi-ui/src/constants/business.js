/** PLM 业务页面共享枚举。后续新增状态时只需维护此处。 */

export const PROCESS_CATEGORY_OPTIONS = [
  { label: '图纸审批', value: 'drawing' },
  { label: '文档审批', value: 'document' },
  { label: '变更通知', value: 'ecn' },
  { label: '其他', value: 'other' }
]

export const PROCESS_TYPE_OPTIONS = [
  { label: '图纸', value: 'drawing' },
  { label: '文档', value: 'document' },
  { label: '变更通知', value: 'ecn' }
]

export const NORMAL_DISABLE_OPTIONS = [
  { label: '正常', value: '0' },
  { label: '停用', value: '1' }
]

export const ASSIGNEE_TYPE_OPTIONS = [
  { label: '指定用户', value: 'user' },
  { label: '角色', value: 'role' },
  { label: '部门', value: 'dept' },
  { label: '部门负责人', value: 'leader' }
]

/**
 * 业务状态的展示元数据。
 *
 * 页面只传入分组和值，状态文案和颜色统一在这里维护，避免各页面重复编写
 * v-if/v-else 映射。未配置的值由 BusinessStatusTag 原样展示。
 */
export const BUSINESS_STATUS_META = {
  workflowBusinessType: {
    drawing: { label: '图纸', type: 'primary' },
    document: { label: '文档', type: 'success' },
    tech_doc: { label: '技术文档', type: 'warning' },
    project: { label: '项目', type: 'danger' }
  },
  workflowTaskStatus: {
    approved: { label: '通过', type: 'success' },
    rejected: { label: '驳回', type: 'danger' }
  },
  workflowInstanceStatus: {
    running: { label: '进行中', type: 'warning' },
    approved: { label: '已通过', type: 'success' },
    rejected: { label: '已驳回', type: 'danger' }
  },
  processCategory: {
    drawing: { label: '图纸审批', type: 'primary' },
    document: { label: '文档审批', type: 'success' },
    ecn: { label: '变更通知', type: 'warning' },
    other: { label: '其他', type: 'info' }
  },
  processType: {
    drawing: { label: '图纸', type: 'primary' },
    document: { label: '文档', type: 'success' },
    ecn: { label: '变更通知', type: 'warning' }
  },
  normalDisable: {
    '0': { label: '正常', type: 'success' },
    '1': { label: '停用', type: 'danger' }
  },
  workflowNodeType: {
    startEvent: { label: '开始', type: 'success' },
    userTask: { label: '审批', type: 'primary' },
    exclusiveGateway: { label: '网关', type: 'warning' },
    endEvent: { label: '结束', type: 'danger' }
  },
  techPublishStatus: {
    draft: { label: '草稿', type: 'info' },
    pending: { label: '审批中', type: 'warning' },
    approved: { label: '已发布', type: 'success' },
    rejected: { label: '已驳回', type: 'danger' }
  },
  techStatus: {
    '0': { label: '草稿', type: 'info' },
    '1': { label: '审批中', type: 'warning' },
    '2': { label: '已完成', type: 'success' }
  }
}

export function getBusinessStatusMeta(group, value) {
  const groupMeta = BUSINESS_STATUS_META[group] || {}
  const meta = groupMeta[value]
  return meta
    ? { ...meta, known: true }
    : { label: value == null || value === '' ? '-' : String(value), type: 'info', known: false }
}
