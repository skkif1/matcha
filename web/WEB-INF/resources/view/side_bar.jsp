<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<div class="menu" id="menu"  >
        <div class="collection">
            <a href="http://localhost:8080/matcha" class="collection-item"><span class="new badge"></span>my page</a>
            <a href="http://localhost:8080/matcha/search" class="collection-item with_rights"><span class="new badge"></span>search</a>
            <a href="http://localhost:8080/matcha/search/suggestions" class="collection-item with_rights"><span class="new badge"></span>suggestions</a>
            <a href="http://localhost:8080/matcha/chat/" class="collection-item with_rights disabled"><span class="new badge" id="mess_notif"></span>messages</a>
            <a href="http://localhost:8080/matcha/history" class="collection-item with_rights"><span class="new badge" id="hist_notif"></span>history</a>
            <a href="http://localhost:8080/matcha/info" class="collection-item pulse"><span class="new badge"></span>information</a>

        </div>
</div>


<script src="//code.jquery.com/jquery-2.1.0.min.js"></script>

<script>

    $(document).ready(function () {
        connect('ws://localhost:8080/matcha/user/', function(data)
        {
            renderNotification(data);
        })
    });


    function connect(addr, callback) {
        ws = new WebSocket(addr);
        ws.onmessage = callback;
    }


    function renderNotification(notification)
    {
        notification = JSON.parse(notification.data);
        console.log(notification);
        Materialize.toast(notification.body, 7000);
        if (notification.category === "message")
        {
            $('#mess_notif').show().text(notification.history);

        }else
        {
            $('#hist_notif').show().text(notification.history);
        }
    }

</script>
