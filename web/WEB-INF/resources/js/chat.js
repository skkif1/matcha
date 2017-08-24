var home = "http://localhost:8080/matcha";
var conversations;
var currentConversation;

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
                $('#message_input').val('');
            }
        }
    });
}

function findConversationOnView(id) {
    for (i = 0; i < conversations.length; i++)
    {
        if (conversations[i].id == id)
            currentConversation = conversations[i];
    }
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
            findConversationOnView(id);
            $.each(json.data.mess, function (index, value) {
             insertMessage(list, value);
            });
            connnectToConversation();
            $('#conversation_messages').removeClass("hiden");

            list.scrollTop = list.scrollHeight;

            $('.unread').mouseover(function () {
                readMessage(this);
            });
        }
    });
}

function connnectToConversation()
{
    connect('ws://localhost:8080/matcha/conversation/', function (response) {

        var message = JSON.parse(response.data);
        console.log(message);

        if (message.author === currentConversation.holder.id) {
            avatar = currentConversation.holder.information.avatar;
            name = currentConversation.holder.firstName + " " + currentConversation.holder.lastName;
        }
        else
        {
            avatar = currentConversation.partner.information.avatar;
            name = currentConversation.partner.firstName + " " + currentConversation.partner.lastName;
        }

        date = new Date();
        $($('.messages_list')[0]).append(' <div class="card horizontal user_message" id=""> ' +
            '<div class="message-image"><img src="' + avatar + '"></div> ' +
            '<div class="message_text"> ' +
            '<div class="author_name"><b>'+ name +'</b></div> ' +
            '<div class="time">' + date.toTimeString().substring(0, 8) + date.toDateString() + '</div>' +
            '<div class="message"> '+ message.message +' </div> ' +
            '</div> ' +
            '</div>');

        list = $('.messages_list')[0];
        list.scrollTop = list.scrollHeight;
})
}

function insertMessage(list, value) {
    if (value.author == currentConversation.holder.id)
    {
        avatar = currentConversation.holder.information.avatar;
        name = currentConversation.holder.firstName + " " + currentConversation.holder.lastName;
    }
    else
    {
        avatar = currentConversation.partner.information.avatar;
        name = currentConversation.partner.firstName + " " + currentConversation.partner.lastName;
    }

    time = new Date(value.time);
    readed = (value.read) ? "" : "unread";


    $(list).prepend(' <div class="card horizontal user_message '+ readed +'" id="'+ value.id +'"> ' +
        '<div class="message-image"><img src="' + avatar + '"></div> ' +
        '<div class="message_text"> ' +
        '<div class="author_name"><b>'+ name +'</b></div> ' +
        '<div class="time">'+ time.toTimeString().substring(0, 8) + ' ' + time.toDateString() + '</div> ' +
        '<div class="message">'+ value.message +'' + '</div> ' +
        '</div> ' +
        '</div>');

}

function readMessage(message)
{
    $(message).removeClass('unread');
    console.log(message);
    data = {
        messageId : message.id
    };

    $.ajax(
        {
            type: "POST",
            url: home + "/chat/read",
            data: data,
            success: function (json) {

            }
        }
    );

}

function openConversation() {
    var url = home + "/chat/";
    mass = location.href.split("/");
    location.href = url + mass[mass.length - 1];
}

function getConversations()
{

    $.ajax(
        {
            type: "POST",
            url: home + "/chat/conversationList",
            success: function (json) {
               console.log(json);
                if (json.status === "OK")
               {
                   conversations = json.data;
                   for (i = 0; i < json.data.length; i++)
                   {
                        var conversation = $("#hiden_conversation").clone();
                        $(conversation).children('img').attr("src", json.data[i].partner.information.avatar);
                        $(conversation).children('span').text(json.data[i].partner.firstName + ' ' + json.data[i].partner.lastName);
                        $(conversation).children('p').text(json.data[i].notReadNumber);

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

function getPreviousMessages()
{
    var data =
        {
            conversationId: $('.messages_list')[0].id
        };

    $.ajax(
        {
            type: "POST",
            url: home + "/chat/conversation/" + $('.card').length,
            data: data,
            success: function (json) {
                console.log(json);
                if (json.status === "OK")
                {
                    var list = $('#' + data.conversationId);
                    $.each(json.data.mess, function (index, value) {
                        insertMessage(list, value);
                    });
                }
            }
        }
    );
}

function checkScrollHeight(list)
{
    if (list.scrollTop < 10)
        $('.previous_messages').css('display', 'block');
    if (list.scrollTop > 10)
        $('.previous_messages').css('display', 'none');
}