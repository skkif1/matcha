<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>reset password</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="<c:url value="/resources/css/authorization.css" />" rel="stylesheet">

</head>
<body>
<div id="content">
    <div class="form_wrapper scale-transition">
        <form id="change_form">
            <label>Password:</label> <input type="password" onblur="validatePassword(this)" class="password"  name="password">
            <label>Repeat password:</label> <input type="password"  class="password_repeat" name="password_repeat">
        </form>
        <nav>
            <button class="waves-effect waves-light btn" onclick="changePassword()">
                <i class="material-icons left">check</i>change</button>
        </nav>
        <div class="progress" id="loader_reset">
            <div class="indeterminate"></div>
        </div>
    </div>
    <div id="onSuccess" class="modal">
        <div class="modal-content">
            <h4>Password changed</h4>
            <p>Your password was successfully changed! Now you can log in with new one!</p>
        </div>
        <div class="modal-footer">
            <a href="http://localhost:8080/matcha" class="modal-action modal-close waves-effect waves-green btn-flat">OK</a>
        </div>
    </div>
</div>
</body>

<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/validation.js" />"></script>
<script src="<c:url value="/resources/js/passwordReset.js" />"></script>

</html>
