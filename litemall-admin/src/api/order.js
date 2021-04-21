import request from '@/utils/request'
import Qs from 'qs'

export function listOrder(query) {
  return request({
    url: '/uorder/list',
    method: 'post',
    data: query
    // paramsSerializer: function (params) {
    //     return Qs.stringify(params, { arrayFormat: 'repeat' })
    // }
  })
}

export function deleteOrder(id) {
  return request({
    url: '/uorder/delete/' + id,
    method: 'post'
  })
}

export function updateOrder(data) {
  return request({
    url: '/uorder/update',
    method: 'post',
    data
  })
}

export function detailOrder(id) {
  return request({
    url: '/uorder/read/' + id,
    method: 'post'
  })
}

export function shipOrder(data) {
  return request({
    url: '/uorder/ship',
    method: 'post',
    data
  })
}

export function refundOrder(data) {
  return request({
    url: '/uorder/refund',
    method: 'post',
    data
  })
}

export function payOrder(data) {
  return request({
    url: '/uorder/pay',
    method: 'post',
    data
  })
}

export function replyComment(data) {
  return request({
    url: '/uorder/reply',
    method: 'post',
    data
  })
}

export function listChannel(id) {
  return request({
    url: '/uorder/channel',
    method: 'get'
  })
}
