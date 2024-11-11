export let download = (name, data) => {
  var export_blob = new Blob([data]);
  var urlObject = window.URL || window.webkitURL || window;
  var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a')
  save_link.href = urlObject.createObjectURL(export_blob);
  save_link.download = name;
  save_link.click();
}