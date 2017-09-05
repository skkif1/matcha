var home = "http://localhost:8080/matcha";

var userPageContext;

$(document).ready(function () {
    connect('ws://localhost:8080/matcha/user/', function (data) {
        renderNotification(data);
    })
});

buildUserPageContext();


function connect(addr, callback) {
    ws = new WebSocket(addr);
    ws.onmessage = callback;
}


function renderNotification(notification) {
    notification = JSON.parse(notification.data);

    if (notification.category === "message" && location.href.indexOf('chat') < 0) {
        $('#mess_notif').text(notification.history).show();
        var $toastContent = $('<section> '+ notification.body +' </section>').mouseover(function () {
            $(this.parentNode).fadeOut(100);
        });
        Materialize.toast($toastContent, 7000);
    }
    if (notification.category === 'history') {
        $('#hist_notif').text(notification.history).show();
        var $toastContent = $('<section> '+ notification.body +' </section>').mouseover(function () {
            $(this.parentNode).fadeOut(100);
        });
        Materialize.toast($toastContent, 7000)
        if (location.href.indexOf('history') > 0)
            getHistoryPageContext();
    }
    buildUserPageContext();
}

function buildUserPageContext() {

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
                if (context.permissionForSearch === true) {

                } else {
                    var $toastContent = $('<section> You need fill minimum requaired information about yourself.</br> ' +
                        'To have full access for application </section>').mouseover(function () {
                        $(this.parentNode).fadeOut(100);
                    });
                    Materialize.toast($toastContent, 7000)
                }
                buildSideBarNotification();
            }
        });

}

function buildSideBarNotification() {

    if (userPageContext.numberOfNewMessages !== null && userPageContext.numberOfNewMessages !== 0)
        $('#mess_notif').text(userPageContext.numberOfNewMessages).css('display', 'block');
    else
        $('#mess_notif').text(userPageContext.numberOfNewMessages).css('display', 'none');

    if (userPageContext.numberOfNewEvents !== null && userPageContext.numberOfNewEvents !== 0)
        $('#hist_notif').text(userPageContext.numberOfNewEvents).css('display', 'block');
    else
        $('#hist_notif').text(userPageContext.numberOfNewEvents).css('display', 'none');
}
