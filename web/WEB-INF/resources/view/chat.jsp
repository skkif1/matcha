<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Dialogs</title>

    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script src="https://cdn.jsdelivr.net/sockjs/1.1.4/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/css/materialize.css">
    <link href="<c:url value="/resources/css/chat.css"/>" rel="stylesheet">


</head>
<body>

<%@include file="header.jsp" %>

<div class="content">
    <%@include file="side_bar.jsp" %>

    <div class="conversation_list list">
        <ul class="collection" id="conversation_collection" >
            <li class="collection-item hiden" id="hiden_conversation">
                <img src="" alt="" class="circle">
                <span class="title"></span>
                <p></p>
            </li>
        </ul>
    </div>

    <div class="conversation_messages list">
        <div class="messages_list">
            <div class="user_message">
              <img src="">
                <div class="message">

                </div>
            </div>
            <div class="message">
              <img src="">
                <div class="message">

                </div>
            </div>
        </div>
        <div class="message_form">
            <div class="input-field col s6">
                <input id="last_name" type="text" class="validate">
                <label for="last_name">message</label>
            </div>
            <a class="waves-effect waves-light btn"><i class="material-icons left">cloud</i></a>
        </div>
    </div>


</div>


</body>
<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/chat.js" />"></script>

</html>