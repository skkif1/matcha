var home = "http://localhost:8080/matcha";

var userPageContext;


$(document).ready(function(){
    buildPage();
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
                        console.log(acountContext);
                        user = json.data[0];
                    }else
                    {
                        user = json.data;
                    }

                    $('.chip').each(function () {
                        this.remove();
                    });

                    for (var i = user.information.interests.length - 1; i >= 0;  i--)
                    {
                        chipsHolder.prepend('<div class="chip"> <img src="'+ user.information.avatar +'">' +
                            user.information.interests[i] + '</div>');
                    }
                    for (var i = 0; i < user.information.photos.length;  i++)
                    {
                        $(gallery[i]).attr('src', user.information.photos[i]);
                    }

                    $('#user_name').text(user.firstName + " " + user.lastName);
                    $('.photo img')[0].src = (user.information.avatar === null) ? "http://localhost:8081/cdn/general/User.png" : user.information.avatar;
                    $('.age .info')[0].innerHTML = (user.information.age === '') ? '-' : user.information.age;
                    $('.pref .info')[0].innerHTML = (user.information.sexPref === '') ? '-' : user.information.sexPref;
                    $('.sex .info')[0].innerHTML = (user.information.sex === '') ? '-' : user.information.sex;
                    $('.country .info')[0].innerHTML = (user.information.country === '') ? '-' : user.information.country;
                    $('.state .info')[0].innerHTML = (user.information.state === '') ? '-' : user.information.state;
                    $('.about_me .info')[0].innerHTML = (user.information.aboutMe === '') ? '-' : user.information.aboutMe;
                    $('#rate').text(user.information.rate);
                    console.log($('#rate').val());

                    if(user.information.lastSean !== null)
                    {
                        date = new Date(user.information.lastSean);
                        $('#status').text(date.toDateString().substr(3, 7) + " " + date.toTimeString().substr(0, 8));
                    }else
                        $('#status').text("online");

                    buildAcountPage(acountContext);
                }
            }
        }
    );
}

function buildAcountPage(context)
{
    if(context !== null) {
        if (context.blocked === true)
        {
            $('#blocked').css('display', 'block');
            return ;
        }
        if (context.blackList === true)
            $('#blacklist').css('display', 'block');
        else
            if (context.matched === true)
            {
                $('#matched').css('display', 'block');
                $('#send').css('display', 'block');
                $('#dropdown').css('display', 'block');
            }
            else
            {
                if(context.liked === true)
                {
                    $('#liked').css('display', 'block');
                }else
                {
                    $('#like').css('display', 'block');
                }
            }

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
                        $('#like').css('display', 'none');
                    }, 250);

                }else
                {
                    Materialize.toast("You allready rate this user", 7000);
                }
             buildPage();
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
                    setTimeout(function(){
                        location.href = location.href;
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
                buildPage();

            }
        });
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