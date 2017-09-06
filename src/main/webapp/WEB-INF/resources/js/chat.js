var home = "http://localhost:8080/matcha";
var conversations;
var currentConversation;

$(document).ready(function () {
    getConversations();
});

function sendMessage() {
    var text = $("#message_input").val();
    var id = $(".message_list").attr("id");

    if (text.trim() === "")
        return;
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
            if (json.status === "OK") {
                var list = $(".message_list")[0];
                var hiden = $("#hiden_message");
                var message = hiden.clone();
                message.id = json.data.id;
                message.text(json.data.message);
                message.appendTo(list);
                $('#message_input').val('');
            }else
            {
                Materialize.toast("you cant chat with this user");
            }
        }
    });
}

function findConversationOnView(id) {
    for (i = 0; i < conversations.length; i++) {
        if (conversations[i].id == id)
            currentConversation = conversations[i];
    }
}

function getMessages(event) {
    var id = event.target.id;
    $(".messages_list")[0].id = id;
    $.ajax({
        type: "POST",
        data: {id: id},
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

            if (json.data.mess.length === 0)
                $(list).append('<section>You have no messages yet</section>');

            for (i = 0; i < json.data.mess.length; i++) {
                insertMessage(list, json.data.mess[i]);

            }

            connnectToConversation();
            $('#conversation_messages').removeClass("hiden");
            list.scrollTop = list.scrollHeight;

            readMessage();
        }
    });
}

function connnectToConversation() {
    connect('ws://localhost:8080/matcha/conversation/', function (response) {

        var message = JSON.parse(response.data);
        var list = $('.messages_list')[0];
        insertMessage(list, message, true);
        readMessage();

    })
}

function insertMessage(list, value, notification) {


    if (value.author == currentConversation.holder.id) {
        avatar = (currentConversation.holder.information.avatar === null) ? 'http://localhost:8081/cdn/general/User.png'
            :currentConversation.holder.information.avatar;
        name = currentConversation.holder.firstName + " " + currentConversation.holder.lastName;
    }
    else {
        avatar = (currentConversation.partner.information.avatar === null) ? 'http://localhost:8081/cdn/general/User.png'
            :currentConversation.partner.information.avatar;
        name = currentConversation.partner.firstName + " " + currentConversation.partner.lastName;
    }

    time = new Date(value.time);

    var messageText = ' <div class="card horizontal user_message"  id="' + value.id + '"> ' +
        '<div class="message-image"><img src="' + avatar + '"></div> ' +
        '<div class="message_text"> ' +
        '<div class="author_name"><b>' + name + '</b></div> ' +
        '<div class="time">' + time.toTimeString().substring(0, 8) + ' ' + time.toDateString() + '</div> ' +
        '<div class="message">' + value.message + '' + '</div> ' +
        '</div> ' +
        '</div>';
    if (notification === true)
        $(list).append(messageText);
    else
        $(list).prepend(messageText);

    $('.messages_list section').remove();

    list.scrollTop = list.scrollHeight;
}

function readMessage() {

    $.ajax(
        {
            type: "POST",
            url: home + "/chat/read",
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

function getConversations() {

    $.ajax(
        {
            type: "POST",
            url: home + "/chat/conversationList",
            success: function (json) {

                if (json.status === "OK") {
                    conversations = json.data;

                    if (conversations.length === 0)
                    {
                        $('.conversation_list').append('<span>you have no conversations yet <br> conversation will open after connection</span>');
                    }

                    for (i = 0; i < json.data.length; i++) {
                        var conversation = $("#hiden_conversation").clone();
                        json.data[i].partner.information.avatar = (json.data[i].partner.information.avatar === null) ?
                            'http://localhost:8081/cdn/general/User.png' : json.data[i].partner.information.avatar;
                        $(conversation).children('img').attr("src", json.data[i].partner.information.avatar);
                        $(conversation).children('div').text(json.data[i].partner.firstName + ' ' + json.data[i].partner.lastName);

                        if (json.data[i].notReadNumber > 0) {
                            $(conversation).children('span').text(json.data[i].notReadNumber);
                            $(conversation).children('span').css('display', 'block');
                        }

                        $(conversation).removeClass('hiden')
                            .attr('id', json.data[i].id)
                            .addClass('conversation')
                            .click(function () {
                                getMessages(event);
                            })
                            .appendTo('#conversation_collection');
                    }
                }
            }
        }
    );
}

function getPreviousMessages() {
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
                if (json.status === "OK") {
                    var list = $('#' + data.conversationId);
                    $.each(json.data.mess, function (index, value) {
                        insertMessage(list, value);
                    });
                }
            }
        }
    );
}

function checkScrollHeight(list) {
    if (list.scrollTop < 10)
        $('.previous_messages').css('display', 'block');
    if (list.scrollTop > 10)
        $('.previous_messages').css('display', 'none');
}
