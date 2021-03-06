var home = "http://localhost:8080/matcha";
var geoLocation = [];


$(document).ready(function () {
    findMe();
    if (location.href.indexOf('suggestions') > 0)
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
        '' + tag + '' +
        '<i class="close material-icons">close</i> ' +
        '</div>'
    );
    $('#tag').val('');
}


function addUserToList(list, user) {
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
        '<div class="message"> ' + text + '</div>' +
        '</div> ' +
        '</div>');
}

function searchRequest(url) {
    var minAge = $('#age_min').val().replace(/\ /g, '');
    var maxAge = $('#age_max').val().replace(/\ /g, '');
    var location = $('#location').val().replace(/\ /g, '');
    var rate = $('#rating').val().replace(/\ /g, '');
    var interests = [];


    $('.chip').each(function (index, data) {
        var interest = $(data).text().trim();
        toDell = interest.indexOf('close');
        interest = interest.substr(0, toDell);
        interests.push(interest);
    });

    $('#tip').text('We are searching for user between ' + minAge + ' and ' + maxAge +
        ' years old, with minimum rate of ' + rate + '. Who located in nearest ' + location + 'km. And interested in ' + interests);

    if (url)
        url = home + url;
    else
        url = home + '/search/suggestions';

    data =
        {
            minAge: minAge,
            maxAge: maxAge,
            locationRange: location,
            rate: rate,
            interests: interests,
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
            url: url,
            success: function (json) {
                if (json.status === "OK") {
                    $('.card').each(function () {
                        this.remove();
                    });

                    for (i = 0; i < json.data.length; i++) {
                        addUserToList($('#result_collection'), json.data[i]);
                    }

                    $('.card').click(function (event) {
                        window.location.href = event.currentTarget.id;
                    });
                }
            }
        }
    );
}


function suggest() {
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
                if (json.status === "OK") {
                    for (i = 0; i < json.data.length; i++) {
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

function sortUsersList(event, list) {

    url = home + '/search/sort/';

    if (list === 'searched')
        url = url + '/searched/' + event.target.name;
    else
        url = url + event.target.name;

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
                    $('.card').each(function () {
                        this.remove();
                    });

                    for (i = 0; i < json.data.length; i++)
                        addUserToList($('#result_collection'), json.data[i]);


                    $('.card').click(function (event) {
                        location.href = event.currentTarget.id;
                    })
                }
            }
        }
    );
}

function findMe() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            geoLocation['lat'] = position.coords.latitude;
            geoLocation['long'] = position.coords.longitude;
        });
    }
}
