/**
 * 物料域共享配置与纯函数。
 *
 * 这里只放物料、BOM 页面都可复用的无状态逻辑，避免页面组件之间相互依赖。
 */

export const MATERIAL_UNIT_DICT_TYPE = 'sys_item_unit'

export const ENABLE_STATUS_OPTIONS = [
  { label: '启用', value: '1' },
  { label: '禁用', value: '0' }
]

export const ITEM_AUDIT_STATUS_OPTIONS = [
  { label: '待审核', value: '0' },
  { label: '审核通过', value: '1' },
  { label: '审核驳回', value: '2' }
]

const STATISTIC_COLORS = [
  'green', 'yellow', 'purple', 'blue', 'teal',
  'orange', 'pink', 'light-blue', 'light-green'
]

export function createItemQueryParams() {
  return {
    pageNum: 1,
    pageSize: 20,
    searchText: null,
    materialClassifyIds: null,
    auditStatus: null,
    enable: null
  }
}

export function createItemForm(overrides = {}) {
  return {
    id: null,
    itemCode: null,
    itemName: null,
    specification: null,
    unitName: null,
    materialClassifyIds: null,
    vendorName: null,
    material: null,
    color: null,
    weight: null,
    Info: null,
    parametersValues: null,
    enable: '1',
    remake: null,
    itemVersion: 'V1.0',
    ...overrides
  }
}

export function createBomForm(overrides = {}) {
  return {
    id: null,
    bomCode: null,
    bomName: null,
    bomVersion: null,
    itemCode: null,
    itemName: null,
    productName: null,
    hierarchy: null,
    dosage: null,
    versionBriefly: null,
    remark: null,
    enable: '1',
    ...overrides
  }
}

export function createMaterialClassifyForm(overrides = {}) {
  return {
    id: null,
    materialClassifyCode: null,
    materialClassifyName: null,
    parentClassifyId: 0,
    ancestors: null,
    ancestorsName: null,
    orderNum: 0,
    enable: '1',
    remark: null,
    mesSyncId: null,
    ...overrides
  }
}

export function createEmptyMaterialStatistics() {
  return {
    totalCount: 0,
    compareLastMonth: 0,
    categories: []
  }
}

export function buildMaterialStatistics(data = []) {
  if (!data.length) {
    return createEmptyMaterialStatistics()
  }

  const totalCount = Number(data[0].count) || 0
  const categories = data.slice(1).map((item, index) => ({
    name: item.classifyName,
    count: Number(item.count) || 0,
    percent: totalCount > 0
      ? Number((((Number(item.count) || 0) / totalCount) * 100).toFixed(2))
      : 0,
    color: STATISTIC_COLORS[index % STATISTIC_COLORS.length],
    classifyId: item.classifyId
  }))

  return { totalCount, compareLastMonth: 0, categories }
}

export function normalizeMaterialClassifyNode(node) {
  return {
    id: node.id,
    label: node.materialClassifyName,
    children: node.children
  }
}

export function buildBomTree(data = []) {
  return buildTreeByParent(data, 'id', 'parentNode')
}

export function buildMaterialClassifyTree(data = []) {
  return buildTreeByParent(data, 'id', 'parentClassifyId')
}

function buildTreeByParent(data, idKey, parentKey, childrenKey = 'children') {
  const nodes = data.reduce((result, item) => {
    result[item[idKey]] = { ...item, [childrenKey]: [] }
    return result
  }, {})

  return data.reduce((tree, item) => {
    const node = nodes[item[idKey]]
    const parentId = item[parentKey]
    if (parentId && nodes[parentId]) {
      nodes[parentId][childrenKey].push(node)
    } else {
      tree.push(node)
    }
    return tree
  }, [])
}

export function createItemListParams(queryParams) {
  const params = { ...queryParams }
  if (params.searchText) {
    params.itemCode = params.searchText
    params.itemName = params.searchText
    params.specification = params.searchText
  }
  return params
}
