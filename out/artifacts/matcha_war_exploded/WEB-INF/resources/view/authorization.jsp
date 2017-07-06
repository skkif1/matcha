<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Welcome to matcha</title>
    <script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
    <script src="<c:url value="/resources/js/authorization.js" />"></script>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
</head>


<body>
<form id="signup_form">
    Login: <input class="login" name="login" type="text">
    Password: <input class="password" name="email" type="text">
    First name :<input class="first_name" name="firstName" type="text">
    Last name :<input class="last_name" name="lastName" type="text">
    Email: <input class="email" name="password" type="text">
</form>
<button onclick="signUpUser()">sign up</button>


<form id="login_form">
    Login or Email: <input class="login" name="login" type="text">
    Password: <input class="password" name="email" type="text">
</form>
<button onclick="loginUser()">log in</button>

</body>
</html>
