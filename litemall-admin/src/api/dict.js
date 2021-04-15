import request from '@/utils/request'

export function listDict(data) {
  return request({
    url: '/dict/list',
    method: 'post',
    data
  })
}

export function readDict(query) {
  return request({
    url: '/dict/read/' + query,
    method: 'get'
  })
}

export function createDict(data) {
  return request({
    url: '/dict/create',
    method: 'post',
    data
  })
}

export function updateDict(data) {
  return request({
    url: '/dict/update',
    method: 'post',
    data
  })
}

export function deleteDict(data) {
  return request({
    url: '/dict/delete/' + data,
    method: 'post'
  })
}
