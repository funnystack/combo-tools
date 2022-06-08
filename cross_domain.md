
打开chrome浏览的console,替换链接输入以下脚本
```javascript
var token= "123123";
var xhr = new XMLHttpRequest();
xhr.open('GET', 'http://127.0.0.1:6060/system/dept/list');
xhr.setRequestHeader("x-access-token",token);
xhr.send(null);
xhr.onload = function(e) {
    var xhr = e.target;
    console.log(xhr.responseText);
}
```
