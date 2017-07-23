<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
    <link href="<c:url value="/resources/css/search.css"/>" rel="stylesheet">
</head>
<body>

<%@include file="header.jsp" %>

<div class="content">

    <%@include file="side_bar.jsp" %>

    <div class="search">
        <div class="search_creeterea">
            <nav>
                <div class="nav-wrapper">
                    <form>
                        <div class="input-field">
                            <input id="search" type="search" required>
                            <label class="label-icon" for="search"><i class="material-icons">search</i></label>
                            <i class="material-icons">close</i>
                        </div>
                    </form>
                </div>
            </nav>
            <nav>
                <div class="nav-wrapper sort">
                    <ul class="left hide-on-med-and-down sort_creeterea">
                        <li><a href="">age</a></li>
                        <li><a href="">location</a></li>
                        <li><a href="">rating</a></li>
                        <li><a href="">interests</a></li>
                    </ul>
                </div>
            </nav>
            <div class="gap_creeterea">
                <div class="creeterea">
                    <label>age</label>
                    <div class="input-field col s6">
                        <input placeholder="min age" id="age_min" type="text" class="validate">
                    </div>
                    <div class="input-field col s6">
                        <input placeholder="min age" id="age_max" type="text" class="validate">
                    </div>
                </div>
                <div class="creeterea">
                    <label>location</label>
                    <div class="input-field col s6">
                        <input placeholder="location" id="location" type="text" class="validate">
                    </div>
                </div>
                <div class="creeterea">
                    <label>rating</label>
                    <div class="input-field col s6">
                        <input placeholder="min rating" id="rating" type="text" class="validate">
                    </div>
                </div>
            </div>
            <div class="button_wrapper">
            <a class="waves-effect waves-light btn"><i class="material-icons left">cloud</i>button</a>
            </div>
        </div>
    <div class="result">
        <ul class="collection">
            <li class="collection-item avatar">
                <img src="images/yuna.jpg" alt="" class="circle">
                <span class="title">Title</span>
                <p>First Line <br>
                    Second Line
                </p>
                <a href="#!" class="secondary-content"><i class="material-icons">grade</i></a>
            </li>
        </ul>
    </div>
    </div>

</div>
</body>
</html>
