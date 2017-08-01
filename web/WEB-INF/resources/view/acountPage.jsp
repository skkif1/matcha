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
            <span>rate:</span><div class="info">95</div>
        </div>

        <nav>
            <button>like</button>
            <button>send message</button>
        </nav>

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
</html>
