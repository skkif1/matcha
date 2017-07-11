<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Dialogs</title>

    <script src="https://cdn.jsdelivr.net/sockjs/1.1.4/sockjs.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="//code.jquery.com/jquery-2.1.0.min.js"></script>
    <script src="<c:url value="/resources/js/chat.js" />"></script>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

</head>
<body>




<div class="conversation_list">

    <div class="conversation" id="hiden_conversation" onclick="getMessages(this.id)">

    </div>


    <c:forEach items="${sessionScope.get('conversationList')}" var="conv">
        <div class="conversation" onclick="getMessages(this.id)" id=${conv.id}>
            ${conv.user1.firstName}
        </div>
    </c:forEach>
</div>

<div class="message_list" id="13">

    <div class="message" id="hiden_message">
        <div class="message"></div>
    </div>
</div>
<div class="message_form">
    <input id="message_input" type="text"/>
    <button onclick="sendMessage()">send</button>
</div>

<button onclick="send()">Test</button>
</body>
</html>