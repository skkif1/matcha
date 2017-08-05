<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <script src="https://cdn.jsdelivr.net/sockjs/1.1.4/sockjs.js"></script>

    <script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
    <script src="<c:url value="/resources/js/test.js" />"></script>
</head>
<body>
<button onclick="sendName()">test</button>
</body>
</html>
