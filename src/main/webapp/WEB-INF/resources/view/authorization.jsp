<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Welcome to matcha</title>

    <link rel="shortcut icon" href="<c:url value="/resources/favicon.ico"/>" type="image/x-icon" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Faustina|Saira+Semi+Condensed" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
    <link href="<c:url value="/resources/css/authorization.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/header.css" />" rel="stylesheet">
    <script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />

</head>

<body>

<%@include file="header.jsp" %>

<script>
    $('#logout').remove();
       $('#res_menu').remove();

</script>


<div id="content">

    <div class="form_wrapper scale-transition scale-out" id="auth">
        <form id="signup_form">
            <label>Email:</label><input onblur="validateEmail(this)" class="email" name="password" type="text" maxlength="255">
            <label>First name:</label><input onblur="validateUserName(this)" class="first_name" name="firstName" type="text" maxlength="32">
            <label>Last name:</label><input onblur="validateUserName(this)" class="last_name" name="lastName" type="text" maxlength="32">
            <label>Password:</label> <input type="password" onblur="validatePassword(this)" class="password"  name="password" maxlength="255">
            <label>Password:</label> <input type="password" onblur="validatePasswords(this)" class="password_repeat" name="password_repeat" maxlength="255">
        </form>
        <nav>
            <button class="waves-effect waves-light btn" onclick="validateAuthForm()">
                <i class="material-icons left">check</i>sign up</button>
            <button class="waves-effect waves-light btn" name="back" onclick="toLogin()">back
                <i class="material-icons left">backspace</i></button>
        </nav>
        <div class="progress" id="loader_auth">
            <div class="indeterminate"></div>
        </div>
    </div>


    <div class="form_wrapper scale-transition" id="log">
        <form id="login_form" onkeypress="toAction(event, this.id);">
            <label>Email:</label> <input onblur="validateEmail(this)" class="email" name="email" type="text" autocomplete="off" maxlength="255">
            <label>Password:</label> <input onblur="validatePassword(this)" class="password" name="email" type="password" autocomplete="off" maxlength="255">
        </form>
        <nav class="nav">
            <button class="waves-effect waves-light btn" onclick="validateLoginForm()">log in</button>
            <button class="waves-effect waves-light btn" onclick="toSignUp()">create account</button>
        </nav>
        <div class="progress" id="loader_log">
            <div class="indeterminate"></div>
        </div>
        <div onclick="toReset()" id="forgot">forgot password?</div>
    </div>

    <div id="onSuccess" class="modal">
        <div class="modal-content">
            <h4>Successful registration!</h4>
            <p>We send you a confirmation letter on your email.</p>
            <p>You should confirm your email address befor login. Thank you an have a goo day!</p>
        </div>
        <div class="modal-footer">
            <a href="http://localhost:8080/matcha" class="modal-action modal-close waves-effect waves-green btn-flat">ok</a>
        </div>
    </div>

    <div class="form_wrapper scale-transition" id="reset">
        <label>Email:</label> <input onblur="validateEmail(this)" class="email" name="email" type="text" maxlength="255">
        <nav>
            <button class="waves-effect waves-light btn" onclick="resetPassword()">
                <i class="material-icons left">check</i>change</button>
            <button class="waves-effect waves-light btn" name="back" onclick="toLogin()">back
                <i class="material-icons left">backspace</i></button>
        </nav>
        <div class="progress" id="loader_reset">
            <div class="indeterminate"></div>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/authorization.js" />"></script>
<script src="<c:url value="/resources/js/validation.js" />"></script>

</body>
</html>
