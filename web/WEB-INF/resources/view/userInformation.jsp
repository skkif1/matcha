<%@ page import="com.matcha.entity.User" %>
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

    <div class="info scale-transition">
        <div id="user_info">
            <div class="input-field col s6">
                <i class="material-icons prefix">account_circle</i>

                <label>age:</label>
                <input maxlength="3" class="input_info" value="${user.information.age}" name="age" type="text"/>
            </div>

            <div class="input-field col s6">
                <i class="material-icons prefix">location_on</i>
                <label>country:</label>
                <input maxlength="32" class="input_info" value="${user.information.country}" id="country" name="country" type="text"/>
            </div>
            <div class="input-field col s6">
                <i class="material-icons prefix">location_on</i>
                <label>state:</label>
                <input maxlength="32"class="input_info" name="state" type="text" value="${user.information.state}"/>
            </div>
            <div class="input-field col s6">
                <i class="material-icons prefix">comment</i>
                <label>about me:</label>
                <textarea maxlength="1000"class="input_info materialize-textarea" maxlength="1000" name="aboutMe">
                    ${user.information.aboutMe}
                </textarea>
            </div>

            <div class="input-field col s6">
                <i class="material-icons prefix bad">local_play</i>
                <label>interests:</label>
                <div class="chips chips-placeholder input_info">


                </div>
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
            <input class="input_info" value="${user.firstName}" name="lastName" type="text"/>
        </div>

        <div class="input-field col s6" id="edit_lastName">
            <label>last name: </label>
            <input class="input_info" name="lastName" type="text"/>
        </div>

        <button class="waves-effect waves-light btn" onclick="editUser()">save</button>
    </div>

</div>

<div class="nav_bar">
    <div class="collection">
        <a onclick="changeCategory(this)" class="collection-item  active">general</a>
        <a onclick="changeCategory(this)" class="collection-item">photo</a>
        <a onclick="changeCategory(this)" class="collection-item">user</a>
    </div>
</div>

<div class="hiden">
   <%

       User user = (User) request.getAttribute("user");

       String interests = new String("");
       user.getInformation().getInterests().forEach(str ->
       {
         interests.concat(str).concat("#");
       });
   %>
</div>

</body>
<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/userInformation.js" />"></script>
</html>
