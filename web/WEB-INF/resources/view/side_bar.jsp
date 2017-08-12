<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/sideBar.css" />" rel="stylesheet">
</head>
<body>
<div class="menu">
        <div class="collection">
            <a href="http://localhost:8080/matcha" class="collection-item"><span class="new badge"></span>my page</a>
            <a href="http://localhost:8080/matcha/search" class="collection-item"><span class="new badge"></span>search</a>
            <a href="#!" class="collection-item"><span class="new badge" id="mess_notif"></span>messages</a>
            <a href="http://localhost:8080/matcha/history" class="collection-item"><span class="new badge" id="hist_notif"></span>history</a>
            <a href="http://localhost:8080/matcha/info" class="collection-item"><span class="new badge"></span>information</a>
        </div>
    </div>
</body>

<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="<c:url value="/resources/js/socket.js" />"></script>

<script>
    $(document).ready(function () {
        connect('ws://localhost:8080/matcha/user/', function(data)
        {
            renderNotification(data);
        })
    });
</script>
</html>
