var home = "http://localhost:8080/matcha";
$(document).load(function () {
    var wsocket = new SockJS(home + '/chat');
    var client = Stomp.over(wsocket);
    client.connect({}, function(frame) {
        client.subscribe('/topic/messages', function (message) {
            showMessage(console.log(message));
        });
    });

    client.send("/app/chat/" + 10, {}, JSON.stringify({
    }));
});



function sendMessage() {
    var text = $("#message_input").val();
    var id = $(".message_list").attr("id");
    data =
        {
            message: text,
            conv_id: id,
        };
    console.log(data);

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify(data),
        url: home + "/chat/send",
        dataType: "json",
        success: function (json) {
            if (json.status === "OK")
            {
                var list = $(".message_list")[0];
                var hiden = $("#hiden_message");
                var message = hiden.clone();
                message.id = json.data.id;
                message.text(json.data.message);
                message.appendTo(list);
            }
        }
    });
}

function getMessages(id) {
    $(".message_list")[0].id = id;
    $.ajax({
        type: "POST",
        data: {id:id},
        url: home + "/chat/conversation",
        success: function (json) {
            var conversations = $(".conversation");
            conversations.each(function (index) {
                if (this.id !== "hiden_conversation")
                    this.remove();
            });
            var list = $(".message_list")[0];
            var hiden = $("#hiden_message");
            $.each(json.data, function (index, value) {
             insertMessage(list, value, hiden);
            });
        }
    });
}


function insertMessage(list, value, hiden) {
    var message = hiden.clone();
    message.id = value.id;
    message.text(value.message);
    message.prependTo(list);
}

