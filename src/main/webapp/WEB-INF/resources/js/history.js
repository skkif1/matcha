var historyContext;
var home = "http://localhost:8080/matcha";

$(document).ready(function () {
    getHistoryPageContext();
});


function getHistoryPageContext() {
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
                historyContext = json;
                buildHistoryPage();
            }
        });
}

function buildHistoryPage() {

    $('.card').remove();
    if (historyContext.visitors !== null) {
        for (i = 0; i < historyContext.visitors.length; i++)
            renderCard($('#visitors'), historyContext.visitors[i]);
    }

    if (historyContext.likes !== null) {
        for (i = 0; i < historyContext.likes.length; i++)
            renderCard($('#likes'), historyContext.likes[i]);
    }

    if (historyContext.lastConnections !== null) {
        for (i = 0; i < historyContext.lastConnections.length; i++)
            renderCard($('#connections'), historyContext.lastConnections[i]);
    }

    if (historyContext.lastConnections !== null) {
        for (i = 0; i < historyContext.visited.length; i++)
            renderCard($('#visited'), historyContext.visited[i]);
    }

    $('.card').click(function (event) {
        location.href = event.currentTarget.id;
    });

    renderNewEventsNumber();
}

function renderNewEventsNumber() {
    if (historyContext.newVisitors !== null && historyContext.newVisitors !== 0)
        $('#new_visitors').show().text(historyContext.newVisitors);
    else
        $('#new_visitors').hide();

    if (historyContext.newConnections !== null && historyContext.newConnections !== 0)
        $('#new_connections').show().text(historyContext.newConnections);
    else
        $('#new_connections').hide();

    if (historyContext.newLikes !== null && historyContext.newLikes !== 0)
        $('#new_likes').show().text(historyContext.newLikes);
    else
        $('#new_likes').hide();
}

function renderCard(list, user) {

    var url = home + "/acount/" + user.id;
    var avatar = user.information.avatar;
    var name = user.firstName + " " + user.lastName;
    var interests = "";

    $.each(user.information.interests, function () {
        interests += "#" + this + " ";
    });

    var text = "<div>sex: " + user.information.sex + "<br/>age: " + user.information.age + "<br/>" + "rate: " + user.information.rate +
        "</div><div class='message_part'>location: " + user.information.country + ", " + user.information.state + "<br/>preferences: " + user.information.sexPref +
        "<br/>interests : " + interests + "</div>";

    $(list).prepend(
        '     <div class="card horizontal history_message" id="' + url + '"> ' +
        '<div class="message-image"><img src="' + avatar + '"></div>' +

        ' <div class="message_text"> <div class="author_name"><b>' + name + '</b></div>' +
        '<div class="message"> ' + text + '</div>' +
        '</div> ' +
        '</div>');


}

function read(elem, eventType) {

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            url: home + "/history/read/" + eventType,
            success: function () {
            }
        });
    $(elem).find('span')[0].style.display = 'none';
    buildUserPageContext();
}