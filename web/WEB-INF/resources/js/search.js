var home = "http://localhost:8080/matcha";
var geoLocation = [];


$(document).ready(function () {
    findMe();
    suggest();
});

function addTag(event) {
    if (event.charCode === 32)
        Materialize.toast("tag should contain one word!", 7000);
    if (event.charCode !== 13)
        return;
    var tag = $('#tag').val();
    first = tag.indexOf(' ');

    if (first !== -1)
        tag = tag.substr(0, first);

    $('#tag_holder').prepend(
        ' <div class="chip">' +
      ''+ tag +'' +
      '<i class="close material-icons">close</i> ' +
      '</div>'
    );
}


function addUserToList(list, user)
{
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

    $(list).append(
        '     <div class="card horizontal history_message" id="' + url + '"> ' +
        '<div class="message-image"><img src="' + avatar + '"></div>' +

        ' <div class="message_text"> <div class="author_name"><b>' + name + '</b></div>' +
        '<div class="message"> ' + text +'</div>' +
        '</div> ' +
        '</div>');
}

function searchRequest()
{
    var minAge = $('#age_min').val();
    var maxAge = $('#age_max').val();
    var location = $('#location').val();
    var rate = $('#rating').val();
    var interests = [];

    $('.chip').each(function (index, data) {
        var interest = $(data).text().trim();
        toDell = interest.indexOf('close');
        interest = interest.substr(0, toDell);
        interests.push(interest);
    });

    $('#tip').text('we are searching for user between ' + minAge + ' years old');

    data =
        {
            minAge: minAge,
            maxAge:maxAge,
            locationRange: location,
            rate: rate,
            interests:interests,
            latitude: geoLocation.lat,
            longitude: geoLocation.long,
            offset: $('.card').length - 1
        };

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            data: JSON.stringify(data),
            url: home + "/search/searchForUsers",
            success: function (json) {
               if (json.status === "OK")
               {
                   for (i = 0; i < json.data.length; i++)
                   {
                       addUserToList($('#result_collection'), json.data[i]);
                   }


                   $('.card').click(function (event) {
                       location.href = event.currentTarget.id;
                   })
               }
            }
        }
    );
}

function countUsers() {

}


function suggest()
{
    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            url: home + "/search/suggest",

            success: function (json) {
                if (json.status === "OK")
                {
                    for (i = 0; i < json.data.length; i++)
                    {
                        addUserToList($('#result_collection'), json.data[i]);
                    }


                    $('.card').click(function (event) {
                        location.href = event.currentTarget.id;
                    })
                }
            }
        }
    );
}

function sortUsersLsit(event) {

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            url: home + "/search/sort/" + event.target.name,

            success: function (json) {
                if (json.status === "OK")
                {
                    $('.card').each(function () {
                        this.remove();
                    });

                    for (i = 0; i < json.data.length; i++)
                    {
                        addUserToList($('#result_collection'), json.data[i]);
                        console.log('asfa');
                    }


                    $('.card').click(function (event) {
                        location.href = event.currentTarget.id;
                    })
                }
            }
        }
    );
}

function findMe(){
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            geoLocation['lat'] = position.coords.latitude;
            geoLocation['long'] = position.coords.longitude;
        });
    }
}