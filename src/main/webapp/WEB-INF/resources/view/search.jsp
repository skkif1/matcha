<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>search</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Faustina|Saira+Semi+Condensed" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
    <link href="<c:url value="/resources/css/search.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/header.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/sideBar.css" />" rel="stylesheet">
    <link rel="shortcut icon" href="<c:url value="/resources/favicon.ico"/>" type="image/x-icon"/>

</head>
<body>

<%@include file="header.jsp" %>

<div class="content">

    <%@include file="side_bar.jsp" %>

    <div class="search">
        <div class="search_creeterea">
            <nav>
                <div class="nav-wrapper sort">
                    <ul class="sort_creeterea">
                        <li><a onclick="sortUsersList(event, 'searched')" name="age">age</a></li>
                        <li><a onclick="sortUsersList(event, 'searched')" name="location">location</a></li>
                        <li><a onclick="sortUsersList(event, 'searched')" name="rating">rating</a></li>
                        <li><a onclick="sortUsersList(event, 'searched')" name="interests">interests</a></li>
                    </ul>
                </div>
            </nav>
            <div class="gap_creeterea">
                <div class="creeterea">
                    <label>age</label>
                    <div class="input-field col s6">
                        <input placeholder="min age" id="age_min" type="text" class="validate" maxlength="2">
                    </div>
                    <div class="input-field col s6">
                        <input placeholder="max age" id="age_max" type="text" class="validate" maxlength="2">
                    </div>
                </div>
                <div class="creeterea">
                    <label>location range</label>
                    <div class="input-field col s6">
                        <input placeholder="location" id="location" type="text" class="validate" maxlength="4">
                    </div>
                </div>
                <div class="creeterea">
                    <label>rating</label>
                    <div class="input-field col s6">
                        <input placeholder="min rating" id="rating" type="text" class="validate" maxlength="4">
                    </div>
                </div>
                <div class="creeterea">
                    <label>tag</label>
                    <div class="input-field col s6">
                        <input placeholder="type and press enter" id="tag" type="text" class="validate" onkeypress="addTag(event);" maxlength="32">
                    </div>
                    <div id="tag_holder">
                    </div>
                </div>
            </div>
            <div class="button_wrapper">
                <a class="waves-effect waves-light btn" onclick="searchRequest('/search/searchForUsers')">find</a>
            </div>
            <div id="tip"></div>
        </div>
    <div class="result">
        <ul class="collection" id="result_collection">

        </ul>
    </div>
    </div>

</div>
</body>
<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/sideBar.js" />"></script>
<script src="<c:url value="/resources/js/search.js" />"></script>
</html>
