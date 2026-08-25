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
