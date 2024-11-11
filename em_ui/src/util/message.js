export let Success = (that, msg) => {
  that.$notification['success']({
    message: '消息',
    description:
      msg
  })
}
export let info = (that, msg) => {
  that.$notification['info']({
    message: '消息',
    description:
      msg
  })
}
export let Warning = (that, msg) => {
  that.$notification['warning']({
    message: '消息',
    description:
      msg
  })
}
export let Error = (that, msg) => {
  that.$notification['error']({
    message: '消息',
    description:
      msg
  })
}