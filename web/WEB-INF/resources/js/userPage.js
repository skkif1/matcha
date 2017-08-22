var home = "http://localhost:8080/matcha";

var userPageContext;


$(document).ready(function(){
    buildPage();
    buildUserPageContext();
    $('.carousel').carousel();
});


function buildPage() {

    var gallery = $('.carousel img');
    var chipsHolder = $('.interests .info');
    var url = home + "/info/getInfo/";
    var acountContext = null;

    if (location.href.indexOf("/acount/") !== 0)
    {
        mass = location.href.split("/");
        url = url + mass[mass.length - 1];
    }

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            url: url,
            success: function (json) {
                if (json.status === "OK") {
                    if (location.href.indexOf('acount') > 0)
                    {
                        acountContext = json.data[1];
                        json.data = json.data[0];
                    }
                    for (var i = json.data.interests.length - 1; i >= 0;  i--)
                    {
                        chipsHolder.prepend('<div class="chip"> <img src="http://localhost:8081/cdn/general/User.png">' +
                            json.data.interests[i] + '</div>');
                    }
                    for (var i = 0; i < json.data.photos.length;  i++)
                    {
                        $(gallery[i]).attr('src', json.data.photos[i]);
                    }
                    console.log(json.data);
                    $('#user_name').text(json.data.firstName + " " + json.data.lastName);
                    $('.photo img')[0].src = (json.data.avatar === null) ? "http://localhost:8081/cdn/general/User.png" : json.data.avatar;
                    $('.age .info')[0].innerHTML = (json.data.age === '') ? '-' : json.data.age;
                    $('.pref .info')[0].innerHTML = (json.data.sexPref === '') ? '-' : json.data.sexPref;
                    $('.sex .info')[0].innerHTML = (json.data.sex === '') ? '-' : json.data.sex;
                    $('.country .info')[0].innerHTML = (json.data.country === '') ? '-' : json.data.country;
                    $('.state .info')[0].innerHTML = (json.data.state === '') ? '-' : json.data.state;
                    $('.about_me .info')[0].innerHTML = (json.data.aboutMe === '') ? '-' : json.data.aboutMe;
                    $('#rate')[0].innerHTML = (json.data.rate === '0') ? '-' : json.data.rate;

                    if(json.data.lastSean !== null)
                    {
                        date = new Date(json.data.lastSean);
                        $('#status').text(date.toDateString().substr(3, 7) + " " + date.toTimeString().substr(0, 8));
                    }else
                        $('#status').text("online");
                    buildAcountPage(acountContext);
                }
            }
        }
    );
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
            }
        })
}


function buildAcountPage(context)
{

    if(context !== null && context.liked === true)
    {
        $('#like').remove();
    }

}


function likeUser()
{
    mass = location.href.split("/");
    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            url: home + "/acount/like/" + mass[mass.length - 1],
            success: function (json) {
                if (json.status === 'OK')
                {
                    $('#like').addClass('scale-out');
                    setTimeout(function(){
                        $('#like').remove();
                    }, 250);

                }else
                {
                    Materialize.toast("You allready rate this user", 7000);
                }
            }
        });
}

function dislikeUser()
{
    mass = location.href.split("/");
    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            url: home + "/acount/dislike/" + mass[mass.length - 1],
            success: function (json) {
                if (json.status === 'OK')
                {
                    $('#like').addClass('scale-out');
                    setTimeout(function(){
                        $('#like').remove();
                    }, 250);
                    Materialize.toast("Connection with user interrupted!", 7000);
                }else
                {
                    Materialize.toast("You allready interrupt this connection", 7000);
                }
            }
        });
}


function addToBlackList()
{
    mass = location.href.split("/");
    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            url: home + "/acount/blackList/" + mass[mass.length - 1],
            success: function (json) {
                if (json.status === 'OK')
                {
                    $("#nav_bar").addClass('scale-out');
                    setTimeout(function(){
                        $('#nav_bar').remove();
                    }, 250);
                    Materialize.toast("This user will never disturb you!", 7000);
                }
            }
        });
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

function reportAsFake() {

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            url: home + "/acount/reportAsFake/" + mass[mass.length - 1],

            success: function (json) {
                if (json.status === 'OK')
                {
                    $("#nav_bar").addClass('scale-out');
                    setTimeout(function(){
                        $('#nav_bar').remove();
                    }, 250);
                    Materialize.toast("report as fake acount", 7000);
                }
            }
        });
}