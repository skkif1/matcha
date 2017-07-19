<%@ page import="com.matcha.entity.User" %>
<%@ page import="java.util.Iterator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Your profile info</title>

    <title>Welcome to matcha</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
    <link href="<c:url value="/resources/css/userInformation.css" />" rel="stylesheet">

</head>
<body>
<div class="content">

    <div class="user_navigation">
    </div>

    <div class="info scale-transition">
        <div id="user_info">
            <div class="input-field col s6">
                <i class="material-icons prefix">account_circle</i>

                <label>age:</label>
                <input maxlength="3" class="input_info" value="${information.age}" name="age" type="text"
                       placeholder=""/>
            </div>

            <div class="input-field col s6">
                <i class="material-icons prefix">location_on</i>
                <label>country:</label>
                <input maxlength="32" class="input_info" value="${information.country}" id="country" name="country"
                       type="text" placeholder=""/>
            </div>
            <div class="input-field col s6">
                <i class="material-icons prefix">location_on</i>
                <label>state:</label>
                <input maxlength="32" class="input_info" name="state" type="text" value="${information.state}"
                       placeholder=""/>
            </div>
            <div class="input-field col s6">
                <i class="material-icons prefix">comment</i>
                <textarea maxlength="1000" class="input_info materialize-textarea" name="aboutMe" id="aboutMe"
                          placeholder="">
                </textarea>
                <span>Type few words about yourself</span>

            </div>

            <div class="input-field col s6">
                <i class="material-icons prefix bad">local_play</i>
                <div class="chips chips-placeholder input_info">

                </div>
                <span>Type your interest and press Enter key</span>

            </div>


            <div class="sex_wrapper">
                <div class="sex">
                    <p>
                        <input class="with-gap" name="group1" type="radio" id="man"/>
                        <label for="man">man</label>
                    </p>
                    <p>
                        <input class="with-gap" name="group1" type="radio" id="woman"/>
                        <label for="woman">woman</label>
                    </p>
                    <p>
                        <input class="with-gap" name="group1" type="radio" id="trans"/>
                        <label for="trans">trans</label>
                    </p>
                </div>

                <div class="preferences">
                    <p>
                        <input class="with-gap" name="group2" type="radio" id="heterosexual"/>
                        <label for="heterosexual">Heterosexual</label>
                    </p>
                    <p>
                        <input class="with-gap" name="group2" type="radio" id="homosexual"/>
                        <label for="homosexual">Homosexual</label>
                    </p>
                    <p>
                        <input class="with-gap" name="group2" type="radio" id="bisexual"/>
                        <label for="bisexual">Bisexual</label>
                    </p>
                    <p>
                        <input class="with-gap" name="group2" type="radio" id="transgender"/>
                        <label for="transgender">Transgender</label>
                    </p>
                </div>
            </div>
            <button id="edit_info" class="waves-effect waves-light btn" onclick="changeUserInfo()">save</button>
        </div>
    </div>

    <div class="user_photo scale-transition">
        <div class="carousel">

            <a class="carousel-item" onmousedown="displayButton()" href="#one!"><img class="standart" src="http://localhost:8081/cdn/general/User.png"></a>
            <a class="carousel-item " href="#two!"><img class="standart" src="http://localhost:8081/cdn/general/User.png"></a>
                <a class="carousel-item " href="#three!"><img class="standart" src="http://localhost:8081/cdn/general/User.png"></a>
                <a class="carousel-item " href="#four!"><img class="standart" src="http://localhost:8081/cdn/general/User.png"></a>
                <a class="carousel-item " href="#five!"><img class="standart" src="http://localhost:8081/cdn/general/User.png"></a>
        </div>
        <button onclick="dellPhoto()" class="dell_button btn-floating btn-large waves-effect waves-light scale-transition scale-out"><i class="material-icons">clear</i></button>

        <div class="file-field input-field">
            <div class="btn">
                <span>Upload</span>
                <input type="file" id="file">
            </div>
            <div class="file-path-wrapper">
                <input class="file-path validate" type="text" id="file-path">
            </div>
        </div>
        <button class="waves-effect waves-light btn" onclick="uploadPhoto()">save</button>
    </div>

    <div class="user scale-transition">

        <div class="input-field col s6" id="edit_email">
            <label>email: </label>
            <input class="input_info" value="${user.email}" name="email" type="text"/>
        </div>

        <div class="input-field col s6" id="edit_name">
            <label>first name: </label>
            <input class="input_info" value="${user.firstName}" name="firstName" type="text"/>
        </div>

        <div class="input-field col s6" id="edit_lastName">
            <label>last name: </label>
            <input class="input_info" value="${user.lastName}" name="lastName" type="text"/>
        </div>

        <button class="waves-effect waves-light btn" onclick="changeUserData()">save</button>
    </div>

</div>

<div class="nav_bar">
    <div class="collection">
        <a onclick="changeCategory(this)" class="collection-item  active">general</a>
        <a onclick="changeCategory(this)" class="collection-item">photo</a>
        <a onclick="changeCategory(this)" class="collection-item">user</a>
    </div>
</div>

</body>
<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/userInformation.js" />"></script>
</html>
