<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<c:url value="/resources/css/sideBar.css" />" rel="stylesheet">
</head>
<body>
<div class="menu">
        <div class="collection">
            <a href="http://localhost:8080/matcha" class="collection-item">my page</a>
            <a href="http://localhost:8080/matcha/search" class="collection-item">search</a>
            <a href="#!" class="collection-item">messages</a>
            <a href="#!" class="collection-item">history</a>
            <a href="http://localhost:8080/matcha/info" class="collection-item">information</a>
            <button onclick="test()">test()</button>
        </div>
    </div>
</body>

<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="<c:url value="/resources/js/socket.js" />"></script>
<script>

    $(document).ready(function () {
        connect('ws://localhost:8080/matcha/user/')
    });
</script>
</html>
