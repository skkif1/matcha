<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>history</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="shortcut icon" href="<c:url value="/resources/favicon.ico"/>" type="image/x-icon"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
    <link href="<c:url value="/resources/css/history.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/sideBar.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/header.css" />" rel="stylesheet">
</head>
<body>

<%@include file="header.jsp" %>

<div class="content">

    <%@include file="side_bar.jsp" %>

    <div class="history">
        <ul class="collapsible" data-collapsible="accordion">
            <li>
                <div onclick="read(this, 'visitors')" class="collapsible-header"><span class="new badge" id="new_visitors"></span><i class="material-icons">person pin</i>visitors
                </div>
                <div class="collapsible-body" id="visitors">
                </div>
            </li>
            <li>
                <div  class="collapsible-header"><span class="new badge">4</span><i class="material-icons">person pin</i>visited
                </div>
                <div class="collapsible-body" id="visited">
                </div>
            </li>
            <li>
                <div onclick="read(this, 'likes')" class="collapsible-header"><span class="new badge" id="new_likes"></span><i
                        class="material-icons">local_activity</i>likes
                </div>
                <div class="collapsible-body" id="likes"></div>
            </li>
            <li>
                <div onclick="read(this, 'connections')" class="collapsible-header" ><span class="new badge" id="new_connections"></span><i class="material-icons">done_all</i>last connections</div>
                <div class="collapsible-body" id="connections"></div>
            </li>
        </ul>
    </div>

</div>
</body>
<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/sideBar.js" />"></script>
<script src="<c:url value="/resources/js/history.js" />"></script>

</html>
