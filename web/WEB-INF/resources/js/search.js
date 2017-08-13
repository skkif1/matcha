var home = "http://localhost:8080/matcha";
var geoLocation = [];


$(document).ready(function () {

    findMe();

    var list = $('#result_collection');
    var user = {
        firstName: 'Oleksandr',
        lastName: 'Motyliuk',
        id:3,
        information: {
            avatar: 'http://localhost:8081/cdn/1/images1.jpeg',
            age:24
        }
    };
    addUserToList(list, user);
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
    $(list).prepend(
        '     <div class="card horizontal history_message" id="' + home + '/acount/' + user.id +'"> ' +
        '<div class="message-image"><img src="' + user.information.avatar + '"></div>' +
        ' <div class="message_text"> <div class="author_name"><b>' + user.firstName + " " + user.lastName + '</b></div>' +
        '<div class="message"> as;foaspodjf </div>' +
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
            longitude: geoLocation.long
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
            url: home + "/search/getUsers",
            success: function (json) {
                console.log(json);
            }
        }
    );
}

function findMe(){
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            console.log(position);
            geoLocation['lat'] = position.coords.latitude;
            geoLocation['long'] = position.coords.longitude;
        });
    }
}