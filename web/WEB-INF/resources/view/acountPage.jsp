<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
    <link href="<c:url value="/resources/css/userPage.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/acount.css"/>" rel="stylesheet">
</head>
<body>

<%@include file="header.jsp" %>

<div class="content">
    <div class="side_bar">
        <%@include file="side_bar.jsp" %>
    </div>
    <div class="photo_content">
        <div class="photo_wrapper">
            <div class="photo">
                <img src="http://localhost:8081/cdn/general/User.png">
            </div>
        </div>

        <div class="rate">
            <span>rate:</span><div class="info" id="rate"></div>
        </div>

        <div class="nav scale-transition" id="nav_bar">
            <button class="waves-effect waves-light btn scale-transition" onclick="likeUser()" id="like">like</button>
            <button class="waves-effect waves-light btn" onclick="openConversation()">send message</button>

            <a class="btn dropdown-button" href="#!" data-activates="dropdown2">more<i class="material-icons right">arrow_drop_down</i></a>

            <ul id="dropdown2" class="dropdown-content">
                <li><a onclick="dislikeUser()">dislike user</a></li>
                <li><a onclick="addToBlackList()">add to blacklist</a></li>
                <li><a href="#!">report as fake acount</a></li>
            </ul>

        </div>

    </div>

    <div class="user_info">
        <div class="user_name"><h5>Alex Cross</h5><div class="status">online</div></div>

        <div class="photo">
            <div class="carousel">

                <a class="carousel-item"  href="#one!"><img class="standart" src="http://localhost:8081/cdn/general/User.png"></a>
                <a class="carousel-item " href="#two!"><img class="standart" src="http://localhost:8081/cdn/general/User.png"></a>
                <a class="carousel-item " href="#three!"><img class="standart" src="http://localhost:8081/cdn/general/User.png"></a>
                <a class="carousel-item " href="#four!"><img class="standart" src="http://localhost:8081/cdn/general/User.png"></a>
                <a class="carousel-item " href="#five!"><img class="standart" src="http://localhost:8081/cdn/general/User.png"></a>
            </div>
        </div>

        <div class="location">
            <div class="country"><span>country:</span><div class="info">-</div> </div>
            <div class="state"><span>state:</span><div class="info">-</div></div>
        </div>

        <div class="age"><span>age:</span><div class="info">-</div></div>

        <div class="sex"><span>sex:</span><div class="info">-</div></div>

        <div class="pref"><span>pref:</span><div class="info">-</div></div>

        <div class="about_me">
            <span>about me:</span>
            <hr>
            <div class="info">

            </div>
        </div>

        <div class="interests">
            <span>interests:</span>
            <br/>
            <hr>
            <div class="info">

            </div>
        </div>

    </div>
</div>


</body>
<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/userPage.js" />"></script>
<script src="<c:url value="/resources/js/chat.js" />"></script>
</html>
