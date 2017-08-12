var historyContext;
var home = "http://localhost:8080/matcha";

$(document).ready(function () {
    buildHistoryPage();
});


function buildHistoryPage() {
    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            url: home + "/history/historyContext/",
            success: function (json) {
                console.log(json);
                if (json.visitors !== null) {
                    for (i = 0; i < json.visitors.length; i++)
                        renderCard($('#visitors'), json.visitors[i]);
                }
                if (json.likes !== null) {
                    for (i = 0; i < json.likes.length; i++)
                        renderCard($('#likes'), json.likes[i]);
                }

                if (json.lastConnections !== null) {
                    for (i = 0; i < json.lastConnections.length; i++)
                        renderCard($('#likes'), json.lastConnections[i]);
                }
                $('.card').click(function (event) {
                    location.href = event.currentTarget.id;
                })
            }
        }
    );
}

function renderCard(list, user) {

    var url = home + "/acount/" + user.id;
    var avatar = user.information.avatar;
    var name = user.firstName + " " + user.lastName;
    var text =  "age: " + user.information.age + "<br/>" + "rate: " + user.information.rate;

        $(list).prepend(
        '     <div class="card horizontal history_message" id="' + url + '"> ' +
        '<div class="message-image"><img src="' + avatar + '"></div>' +

        ' <div class="message_text"> <div class="author_name"><b>' + name + '</b></div>' +
        '<div class="message"> ' + text +'</div>' +
        '</div> ' +
        '</div>');
}