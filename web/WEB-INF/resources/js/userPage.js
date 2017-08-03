var home = "http://localhost:8080/matcha";

$(document).ready(function(){
    buildPage();
    $('.carousel').carousel();

});


function buildPage() {

    var gallery = $('.carousel img');
    var chipsHolder = $('.interests .info');
    var url = home + "/info/getInfo/";

    if (location.href.indexOf("/acount/") !== 0)
    {
        mass = location.href.split("/");
        url = url + mass[mass.length - 1];
        console.log(url);
    }


    console.log(url);
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
                    for (var i = json.data.interests.length - 1; i >=0;  i--)
                    {
                        chipsHolder.prepend('<div class="chip"> <img src="http://localhost:8081/cdn/general/User.png">' +
                            json.data.interests[i] + '</div>');
                    }
                    for (var i = 0; i < json.data.photos.length;  i++)
                    {
                        $(gallery[i]).attr('src', json.data.photos[i]);
                    }
                    $('.photo img')[0].src = (json.data.avatar === null) ? "http://localhost:8081/cdn/general/User.png" : json.data.avatar;
                    $('.age .info')[0].innerHTML = (json.data.age === '') ? '-' : json.data.age;
                    $('.pref .info')[0].innerHTML = (json.data.sexPref === '') ? '-' : json.data.sexPref;
                    $('.sex .info')[0].innerHTML = (json.data.sex === '') ? '-' : json.data.sex;
                    $('.country .info')[0].innerHTML = (json.data.country === '') ? '-' : json.data.country;
                    $('.state .info')[0].innerHTML = (json.data.state === '') ? '-' : json.data.state;
                    $('.about_me .info')[0].innerHTML = (json.data.aboutMe === '') ? '-' : json.data.aboutMe;
                    $('#rate_val')[0].innerHTML = (json.data.likeCount === '0') ? '-' : json.data.likeCount;
                }
            }
        }
    );
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
                    $('#rate_val').text(json.data[0]);
                    console.log(json.data[0]);
                }else
                {
                    Materialize.toast("You allready rate this user", 7000);
                }
            }
        });
}