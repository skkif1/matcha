var home = "http://localhost:8080/matcha";

$(document).ready(function () {
    getConversations();
});

function sendMessage() {
    var text = $("#message_input").val();
    var id = $(".message_list").attr("id");

    data =
        {
            message: text,
            conv_id: id
        };
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

function getMessages(event) {
    var id = event.target.id;
    $(".messages_list")[0].id = id;
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
            $('.conversation_list').addClass("hiden");
            var list = $(".messages_list")[0];
            var hiden = $("#hiden_message");
            $.each(json.data.mess, function (index, value) {
             insertMessage(list, value, hiden);});
           $('.conversation_messages').removeClass('hiden');
            connnectToConversation(json.data.conv, json.data.wsacode);
        }
    });
}

function connnectToConversation()
{
  connect('ws://localhost:8080/matcha/conversation/');
}

function insertMessage(list, value, hiden) {
    var message = hiden.clone();
    message.id = value.id;
    message.text(value.message).removeClass('hiden').prependTo(list);
}


function openConversation() {
    var url = home + "/chat/";
    mass = location.href.split("/");
    location.href = url + mass[mass.length - 1];
}


function getConversations() {

    $.ajax(
        {
            type: "POST",
            url: home + "/chat/conversationList",
            success: function (json) {
               if (json.status === "OK")
               {
                   console.log(json.data);
                   for (i = 0; i < json.data.length; i++)
                   {
                        var conversation = $("#hiden_conversation").clone();
                        $(conversation).children('img').attr("src", json.data[i].user2.information.avatar);
                        $(conversation).children('span').text(json.data[i].user2.firstName + ' ' + json.data[i].user2.lastName);

                        if (json.data[i].user2.information.interests !== null)
                        {
                            for (var j = 0; j < json.data[i].user2.information.interests.length; j++) {
                               $(conversation).children('p').text('#' + json.data[i].user2.information.interests[j] + ' ');
                            }
                        }

                        $(conversation).removeClass('hiden')
                                .attr('id', json.data[i].id)
                                .addClass('conversation')
                                .click(function() {getMessages(event);})
                                .appendTo('#conversation_collection');

                   }
               }
            }
        }
    );
}