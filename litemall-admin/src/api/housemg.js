import request from '@/utils/request'

export function listAunt(query) {
  return request({
    url: '/aunt/list',
    method: 'post',
    data: query
  })
}

export function detailAunt(query) {
  debugger
  return request({
    url: '/aunt/read/' + query,
    method: 'post'
  })
}

export function addAunt(data) {
  return request({
    url: '/aunt/create',
    method: 'post',
    data
  })
}

export function editAunt(data) {
  return request({
    url: '/aunt/update',
    method: 'post',
    data
  })
}

export function deleteAunt(data) {
  return request({
    url: '/aunt/delete/' + data,
    method: 'post'
  })
}
