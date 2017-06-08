<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
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
    Email: <input class="email" name="password" type="text">
</form>
<button onclick="signUpUser()">SignUp</button>
<div class="error"></div>

<form id="login_form">
    Login or Email: <input class="login" name="login" type="text">
    Password: <input class="password" name="email" type="text">
</form>
<button onclick="loginUser()">SignUp</button>
<div class="error"></div>

<form id="reset_form">
   Email: <input class="email" name="email" type="text">
</form>
<button onclick="resetPasswrd()">SignUp</button>
<div class="error"></div>

<div class="status">

</div>
</body>
</html>
