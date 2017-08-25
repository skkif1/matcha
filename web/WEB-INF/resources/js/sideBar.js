var home = "http://localhost:8080/matcha";

var userPageContext;

$(document).ready(function () {
    connect('ws://localhost:8080/matcha/user/', function(data)
    {
        renderNotification(data);
    })
});

buildUserPageContext();


function connect(addr, callback) {
    ws = new WebSocket(addr);
    ws.onmessage = callback;
}


function renderNotification(notification)
{
    notification = JSON.parse(notification.data);
    console.log(notification);
    if (notification.category === "message" && location.href.indexOf('chat') < 0)
    {
        $('#mess_notif').show().text(notification.history);
        Materialize.toast(notification.body, 7000);
    }
    if (notification.category === 'history' && location.href.indexOf('history') < 0)
    {
        $('#hist_notif').show().text(notification.history);
        Materialize.toast(notification.body, 7000);
    }
}



function buildUserPageContext()
{

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            url: home + '/context',
            success: function (context) {
                userPageContext = context;
                if (context.permissionForSearch === true)
                {

                }else {
                    Materialize.toast("You need fill minimum requaired information about yourself.\n" +
                        "To have full access for application", 7000)
                }
                buildSideBarNotification();
            }
        });

}


function buildSideBarNotification() {

    if(userPageContext.numberOfNewMessages !== null && userPageContext.numberOfNewMessages !== 0)
        $('#mess_notif').text(userPageContext.numberOfNewMessages).css('display', 'block');
    else
        $('#mess_notif').text(userPageContext.numberOfNewMessages).css('display', 'none');

    if(userPageContext.numberOfNewEvents !== null && userPageContext.numberOfNewEvents !== 0)
        $('#hist_notif').text(userPageContext.numberOfNewEvents).css('display', 'block');
    else
        $('#hist_notif').text(userPageContext.numberOfNewEvents).css('display', 'none');
}
