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
        }
    });
}

function connnectToConversation()
{
    connect('ws://localhost:8080/matcha/conversation/', function (response) {

        var message = JSON.parse(response.data);
        console.log(message);
        if (message.author == currentConversation.user1.id) {
            avatar = currentConversation.user1.information.avatar;
            name = currentConversation.user1.firstName + " " + currentConversation.user1.lastName;
        }
        else
        {
            avatar = currentConversation.user2.information.avatar;
            name = currentConversation.user1.firstName + " " + currentConversation.user1.lastName;
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
})
}

function insertMessage(list, value) {
    if (value.author == currentConversation.user1.id)
    {
        avatar = currentConversation.user1.information.avatar;
        name = currentConversation.user1.firstName + " " + currentConversation.user1.lastName;
    }
    else
    {
        avatar = currentConversation.user2.information.avatar;
        name = currentConversation.user2.firstName + " " + currentConversation.user2.lastName;
    }
        time = new Date(value.time);


    $(list).prepend(' <div class="card horizontal user_message" id=""> ' +
        '<div class="message-image"><img src="' + avatar + '"></div> ' +
        '<div class="message_text"> ' +
        '<div class="author_name"><b>'+ name +'</b></div> ' +
        '<div class="time">'+ time.toTimeString().substring(0, 8) + ' ' + time.toDateString() + '</div> ' +
        '<div class="message">'+ value.message +'' +
        '</div> ' +
        '</div> ' +
        '</div>');
}

function openConversation() {
    var url = home + "/chat/";
    mass = location.href.split("/");
    location.href = url + mass[mass.length - 1];
}


function getDate() {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1;
    var yyyy = today.getFullYear();
    var hh = today.getHours();
    var min = today.getMinutes();


    if(dd<10) {
        dd = '0'+dd
    }

    if(mm<10) {
        mm = '0'+mm
    }

    today = hh + ':' + min + "  " +mm + '/' + dd + '/' + yyyy;
    return today;
}

function getConversations() {

    $.ajax(
        {
            type: "POST",
            url: home + "/chat/conversationList",
            success: function (json) {
               if (json.status === "OK")
               {
                   conversations = json.data;
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