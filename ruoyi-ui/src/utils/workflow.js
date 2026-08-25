const BUSINESS_DETAIL_ROUTES = {
  drawing: businessId => ({ path: '/system/drawing', query: { id: businessId } }),
  tech_doc: businessId => ({ path: `/system/tech-detail/${businessId}` }),
  project: businessId => ({ path: '/system/overview', query: { id: businessId, view: true } })
}

/** 返回流程业务对应的详情路由；尚未接入详情页的业务返回 null。 */
export function getWorkflowBusinessRoute(businessType, businessId) {
  const routeFactory = BUSINESS_DETAIL_ROUTES[businessType]
  return routeFactory ? routeFactory(businessId) : null
}

/** 将工作流接口返回的秒数格式化为页面展示文案。 */
export function formatWorkflowDuration(seconds) {
  const duration = Number(seconds)
  if (!Number.isFinite(duration) || duration <= 0) return '-'

  const hours = Math.floor(duration / 3600)
  const minutes = Math.floor((duration % 3600) / 60)
  return hours > 0 ? `${hours}小时${minutes}分钟` : `${minutes}分钟`
}
