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
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:600i" rel="stylesheet">


</head>
<body>

<%@include file="header.jsp" %>

<div class="content">
    <%@include file="side_bar.jsp" %>

    <div class="conversation_list list">
        <ul class="collection" id="conversation_collection">
            <li class="collection-item hiden" id="hiden_conversation">
                <img src="" alt="" class="circle">
                <span class="title"></span>
                <p></p>
            </li>
        </ul>
    </div>

    <div class="list hiden" id="conversation_messages">
        <div class="messages_list">
            <%--<div class="card horizontal user_message" id="'+ message.id +'">--%>
                <%--<div class="message-image"><img src="http://localhost:8081/cdn/1/images1.jpeg"></div>--%>
                <%--<div class="message_text">--%>
                    <%--<div class="author_name"><b>John Dow</b></div>--%>
                    <%--<div class="time">12:11 10/31/2017</div>--%>
                    <%--<div class="message">dfsansg'knf'nsad--%>
                        <%--aoJSDOFIANOINGF[DOG--%>
                        <%--ASDOPFMSGOHINDOndfs[goin--%>
                        <%--asopfdgjdhopfj--%>
                        <%--aosgfj'ofhonodfhoindsfg;hoinfg[o--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
        <div class="message_form">
            <div class="input-field col s6">
                <input id="message_input" type="text">
                <label for="message_input">message</label>
            </div>
            <button class="waves-effect waves-light btn" onclick="sendMessage()"><i
                    class="material-icons left">cloud</i></button>
        </div>
    </div>


</div>


</body>
<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.99.0/js/materialize.min.js"></script>
<script src="<c:url value="/resources/js/socket.js" />"></script>
<script src="<c:url value="/resources/js/chat.js" />"></script>
</html>