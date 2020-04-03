<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String cssPath = request.getContextPath();
    String cssBasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+cssPath+"/";
%>
<!DOCTYPE html>
<html>
<body>
<h2>Hello World!</h2>
</body>
<script type="text/javascript" src="<%=cssPath%>/static/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script type="text/javascript">
    $(function () {
        var websocket ;
        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window){
            console.log("WebSocket");
            websocket = new WebSocket("ws://localhost:8080/websocket2");
        }else if ('MozWebSocket' in window){
            console.log("MozWebSocket");
            websocket = new MozWebSocket("ws://localhost:8080/websocket2");
        }else {
            console.log("SockJS");
            websocket = new SockJS("http://127.0.0.1:8080/websocket2");
        }

        websocket.onopen = function (evnt) {
            console.log(evnt);
            console.log("链接服务器成功! 目标地址: %s ", evnt.target.url);
            websocket.send('I am Client! ');
        };

        websocket.onmessage = function (evnt) {
            console.log('收到消息:', evnt.data);
        };
        websocket.onerror = function (evnt) {
        };

        websocket.onclose = function (evnt) {
            console.log("与服务器断开了链接!");
        };

    });

</script>
</html>
