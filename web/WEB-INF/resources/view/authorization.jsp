<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Welcome to matcha</title>
    <script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
    <script src="<c:url value="/resources/js/authorization.js" />"></script>
    <script src="<c:url value="/resources/js/validation.js" />"></script>
    <link href="<c:url value="/resources/css/authorization.css" />" rel="stylesheet">
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
</head>

<body>

<div id="content">
    <div class="form_wrapper scale-transition scale-out" id="auth">
        <form id="signup_form">
            <label>Email:</label><input onblur="validateEmail(this)" class="email" name="password" type="text">
            <label>First name:</label><input onblur="validateUserName(this)" class="first_name" name="firstName" type="text">
            <label>Last name:</label><input onblur="validateUserName(this)" class="last_name" name="lastName" type="text">
            <label>Password:</label> <input type="password" onblur="validatePassword(this)" class="password"  name="password">
            <label>Password:</label> <input type="password" onblur="validatePasswords(this)" class="password_repeat" name="password_repeat">
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
        <form id="login_form">
            <label>Email:</label> <input onblur="validateEmail(this)" class="email" name="email" type="text">
            <label>Password:</label> <input onblur="validatePassword(this)" class="password" name="email" type="text">
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
            <a href="http://localhost:8080/matcha" class="modal-action modal-close waves-effect waves-green btn-flat">Agree</a>
        </div>
    </div>

    <div class="form_wrapper scale-transition" id="reset">
        <label>Email:</label> <input onblur="validateEmail(this)" class="email" name="email" type="text">
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

<%--<button id="tets" class="lolll" onclick="test()">loll</button>--%>

</body>
</html>
