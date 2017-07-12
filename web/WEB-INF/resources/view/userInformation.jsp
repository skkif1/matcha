<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Your profile info</title>

    <title>Welcome to matcha</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="<c:url value="/resources/css/userInformation.css" />" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">

</head>
<body>
<div class="content">

    <div class="user_navigation">
    </div>

    <div class="info">
        <div id="user_info">
            <div class="input-field col s6">
                <label>age:</label>
                <input class="input_info" value="${user.information.age}" name="age" type="text"/>
            </div>

            <div class="input-field col s6">
                <label>country:</label>
                <input class="input_info" value="${user.information.age}" id="country" name="country" type="text"/>
            </div>
            <div class="input-field col s6">
                <label>state:</label>
                <input class="input_info" name="state" type="text" value="${user.information.age}"/>
            </div>
            <div class="input-field col s6">
                <label>about me:</label>
                <textarea class="input_info materialize-textarea" maxlength="1000" name="aboutMe">
                    ${user.information.aboutMe}
                </textarea>
            </div>

            <div class="input-field">
                <label>interests:</label>
                <div class="chips chips-placeholder input_info">
                </div>
            </div>

        <div class="input-field col s12">
            <label>sex:</label>
            <select class="input_info " name="sex">
                <option value="null" selected>${user.information.sex}</option>
                <option value="man">Man</option>
                <option value="woman">Women</option>
                <option value="trans">Trans</option>
            </select>
        </div>
        <div class="input-field col s12">
            <label>sex prefernces :</label>
            <select class="input_info sex_pref" name="sexPref">
                <option value="null" selected>${user.information.sexPref}</option>
                <option>Heterosexual</option>
                <option>Homosexual</option>
                <option>Bisexual</option>
                <option>Transgender</option>
            </select>
        </div>
        <button id="edit_info" class="waves-effect waves-light btn" onclick="changeUserInfo()">save</button>
    </div>
    </div>

    <div class="user_photo">
        <div class="carousel">
            <a class="carousel-item" href="#one!"><img src="https://lorempixel.com/250/250/nature/1"></a>
            <a class="carousel-item" href="#two!"><img src="https://lorempixel.com/250/250/nature/2"></a>
            <a class="carousel-item" href="#three!"><img src="https://lorempixel.com/250/250/nature/3"></a>
            <a class="carousel-item" href="#four!"><img src="https://lorempixel.com/250/250/nature/4"></a>
            <a class="carousel-item" href="#five!"><img src="https://lorempixel.com/250/250/nature/5"></a>
        </div>


        <div class="file-field input-field">
        <div class="btn">
            <span>Upload</span>
            <input type="file" id="file" accept="image/jpeg">
        </div>
        <div class="file-path-wrapper" >
            <input class="file-path validate" type="text" id="file-path">
        </div>
        </div>
        <button class="waves-effect waves-light btn" onclick="uploadPhoto()">submit changes</button>
    </div>

    <div class="user">

            <div class="input-field col s6" id="edit_email">
                <label>email :</label>
            <input class="input_email_info" name="lastName"/>
            </div>

            <button onclick="editUser()">save</button>
        </div>

    </div>

<div class="nav_bar">
    <div class="collection">
        <a  onclick="changeCategory(this)" class="collection-item  active">general</a>
        <a  onclick="changeCategory(this)" class="collection-item">photo</a>
        <a  onclick="changeCategory(this)" class="collection-item">user</a>
    </div>
</div>

</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/userInformation.js" />"></script>
<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
</html>
