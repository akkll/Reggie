function loginApi(data) {
    return $axios({
      'url': '/user/login',
      'method': 'post',
      data
    })
  }

function getPhoneCode(data) {
    return $axios({
        'url': '/user/code',
        'method': 'post',
        data
        // params:{...data}
    })
}
function loginoutApi() {
  return $axios({
    'url': '/user/loginout',
    'method': 'post',
  })
}

  