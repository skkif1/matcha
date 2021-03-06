<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>dialogs</title>

    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script src="https://cdn.jsdelivr.net/sockjs/1.1.4/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="shortcut icon" href="<c:url value="/resources/favicon.ico"/>" type="image/x-icon"/>
    <link href="https://fonts.googleapis.com/css?family=Faustina|Saira+Semi+Condensed" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:600i" rel="stylesheet">
    <link href="<c:url value="/resources/css/chat.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/header.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/sideBar.css" />" rel="stylesheet">



</head>
<body>

<%@include file="header.jsp" %>

<div class="content">

    <%@include file="side_bar.jsp" %>

<div class="list_wrapper">
    <div class="conversation_list list">
        <ul class="collection" id="conversation_collection">
            <li class="collection-item hiden" id="hiden_conversation">
                <img src="" alt="" class="circle">
                <div class="title"></div>
              <span class="new badge"></span>
            </li>
        </ul>
    </div>

    <div class="list hiden" id="conversation_messages">
        <button class="previous_messages" onclick="getPreviousMessages()">more</button>
        <div class="messages_list" onscroll="checkScrollHeight(this)">

        </div>

        <div class="message_form">
            <div class="input-field col s12">
                <textarea id="message_input" class="materialize-textarea" maxlength="250">  </textarea>
            </div>
            <button class="waves-effect waves-light btn" onclick="sendMessage()">send</button>
        </div>
    </div>
</div>
</div>


</body>
<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/sideBar.js" />"></script>
<script src="<c:url value="/resources/js/chat.js" />"></script>
</html>