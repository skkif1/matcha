<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change yor password</title>

    <script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
    <script src="<c:url value="/resources/js/changePassword.js" />"></script>
</head>
<body>


<form id="change_form">
    Password: <input class="password" name="email" type="text">
    Password: <input class="password_repeat" name="email" type="text" placeholder="repeat password">
</form>
<button onclick="changePassword()">submit</button>
</body>
</html>
