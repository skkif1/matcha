<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>reset password</title>
</head>
<body>
<div id="content">
    <div class="form_wrapper scale-transition">
        <form id="change_form">
            <label>Password:</label> <input type="password" onblur="validatePassword(this)" class="password"  name="password">
            <label>Repeat password:</label> <input type="password" onblur="validatePasswords(this)" class="password_repeat" name="password_repeat">
        </form>
        <nav>
            <button class="waves-effect waves-light btn" onclick="validateAuthForm()">
                <i class="material-icons left">check</i>sign up</button>
        </nav>
    </div>
</div>
</body>

<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
<script src="<c:url value="/resources/js/validation.js" />"></script>
<link href="<c:url value="/resources/css/authorization.css" />" rel="stylesheet">

</html>
