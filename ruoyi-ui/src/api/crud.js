import request from '@/utils/request'

/**
 * 创建符合若依 REST 约定的 CRUD 请求集合。
 *
 * 约定：
 * - GET    {baseUrl}/list  查询列表
 * - GET    {baseUrl}/{id}  查询详情
 * - POST   {baseUrl}       新增
 * - PUT    {baseUrl}       修改
 * - DELETE {baseUrl}/{id}  删除（支持逗号分隔的批量 ID）
 *
 * 可通过 operationOptions 为单个操作补充 axios 配置，例如文件上传：
 * createCrudApi('/system/drawing', {
 *   add: { headers: { 'Content-Type': 'multipart/form-data' } }
 * })
 */
export function createCrudApi(baseUrl, operationOptions) {
  const options = operationOptions || {}

  function withOptions(operation, config) {
    return Object.assign({}, config, options[operation] || {})
  }

  return {
    list(query) {
      return request(withOptions('list', {
        url: baseUrl + '/list',
        method: 'get',
        params: query
      }))
    },

    get(id) {
      return request(withOptions('get', {
        url: baseUrl + '/' + id,
        method: 'get'
      }))
    },

    add(data) {
      return request(withOptions('add', {
        url: baseUrl,
        method: 'post',
        data: data
      }))
    },

    update(data) {
      return request(withOptions('update', {
        url: baseUrl,
        method: 'put',
        data: data
      }))
    },

    remove(id) {
      return request(withOptions('remove', {
        url: baseUrl + '/' + id,
        method: 'delete'
      }))
    }
  }
}
