var home = "http://localhost:8080/matcha";
var UserInformationContext =
    {
        longitude:'',
        latitude: ''
    };

window.onload = function () {
    $('.chips').material_chip(
        {
            placeholder: 'Type your interest and press enter',
            secondaryPlaceholder: 'Type your interest and press enter'
        }
    );

    getUserInfo();
    $('.carousel').carousel();
    getPositionViaAPI();
};


function changeUserInfo() {
    var select_info = $(".input_info");
    var sexInput = $('.sex input');
    var sexPrefInput = $('.preferences input');
    var interests = $('.chip');

    var data = {
        sex: '',
        sexPref: '',
        interests: '',
        longitude:'',
        latitude: ''

};

    for (var i = 0; i < select_info.length; i++)
        data[select_info[i].name] = select_info.eq(i).val().trim();
    data.age = parseInt(data.age, 10);

    sexInput.each(function (index, node) {
        if (node.checked)
            data.sex = node.id;
    });
    sexPrefInput.each(function (index, node) {
        if (node.checked)
            data.sexPref = node.id;
    });
    var res = '';
    if (interests.length > 10) {
        var $toastContent = $('<section> you can declare only 10 interests </section>').mouseover(function () {
            $(this.parentNode).fadeOut(100);
        });
        Materialize.toast($toastContent, 7000);
        return;
    }

    for (var i = 0; i < interests.length; i++) {

        if ($(interests[i]).text().length > 32) {
            var $toastContent = $('<section>you interest max length is 32</section>').mouseover(function () {
                $(this.parentNode).fadeOut(100);
            });
            Materialize.toast($toastContent, 7000);
            continue;
        }
        if (!validateTags($(interests[i]).text()))
            continue;

        res += $(interests[i]).text();
    }


    data.interests = res.replace(new RegExp("close", 'g'), "#");

    if (!UserInformationContext.latitude || UserInformationContext.latitude === 0)
    {
        checkState($('#state'))
    }

    data.longitude =   UserInformationContext.longitude;
    data.latitude =   UserInformationContext.latitude;

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            data: JSON.stringify(data),
            dataType: "json",
            url: home + "/info/update/",
            success: function (json) {
                var $toastContent = $('<section>You data was successfuly change</section>').mouseover(function () {
                    $(this.parentNode).fadeOut(100);
                });
                Materialize.toast($toastContent, 7000);
            }
        }
    );
}

function uploadPhoto() {
    var files = $("#file").prop('files');
    var amount = $('.standart');
    var form = new FormData();


    if (amount.length === 0) {

        var $toastContent = $('<section>You can download 5 photo with max size of 10MB</section>').mouseover(function () {
            $(this.parentNode).fadeOut(100);
        });
        Materialize.toast($toastContent, 7000);
        return;
    }
    form.append('files', files[0]);
    files = "";
    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
            },
            url: home + "/info/updatePhoto",
            data: form,
            type: 'POST',
            processData: false,
            contentType: false,
            success: function (json) {
                if (json.status === 'OK') {
                    var images = $('.carousel-item');
                    $.each(images, function (index, item) {
                        item.children[0].className = 'standart';
                        item.children[0].src = 'http://localhost:8081/cdn/general/User.png';
                    });
                    getUserInfo();
                    $('.carousel').carousel();
                } else {
                    for (var i = 0; i < json.data.length; i++) {

                        var $toastContent = $('<section>' + json.data[i] + '</section>').mouseover(function () {
                            $(this.parentNode).fadeOut(100);
                        });
                        Materialize.toast($toastContent, 7000);
                    }
                }
            }
        });
}

function dellPhoto() {
    var toDell = $('.active img')[0];

    if (toDell.className === 'standart')
        return;
    var path = $(toDell).attr('src')

    data =
        {
            path: path
        };

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
            },
            data: data,
            type: "POST",
            url: home + "/info/dellPhoto/",
            success: function (json) {
                if (json.status === "OK") {
                    toDell.src = 'http://localhost:8081/cdn/general/User.png';
                    toDell.className = 'standart';
                    var $toastContent = $('<section>photo delleted</section>').mouseover(function () {
                        $(this.parentNode).fadeOut(100);
                    });
                    Materialize.toast($toastContent, 7000);
                }
            }
        }
    );
}

function changeCategory(item) {
    $('.active').removeClass('active');
    $(item).addClass("active");

    switch (item.innerHTML) {
        case"general":
            $('.user_photo').addClass('scale-out');
            $('.user').addClass('scale-out');
            $('.info').removeClass('scale-out');
            setTimeout(function () {
                $('.user_photo').css('display', 'none');
                $('.user').css('display', 'none');
                $('.info').css('display', 'block');
                $('.carousel').carousel();
            }, 300);
            break;
        case"photo":
            $('.info').addClass('scale-out');
            $('.user').addClass('scale-out');
            $('.user_photo').removeClass('scale-out');
            setTimeout(function () {
                $('.info').css('display', 'none');
                $('.user').css('display', 'none');
                $('.user_photo').css('display', 'block');
                $('.carousel').carousel();
            }, 300);
            break;
        case"user":
            $('.info').addClass('scale-out');
            $('.user_photo').addClass('scale-out');
            $('.user').removeClass('scale-out');
            setTimeout(function () {
                $('.info').css('display', 'none');
                $('.user_photo').css('display', 'none');
                $('.user').css('display', 'block');
                $('.carousel').carousel();
            }, 300);
            break;
    }
}

function validTag(event) {

    if (event.charCode === 32)
    {
        var $toastContent = $('<section>tag should contain one word</section>').mouseover(function () {
            $(this.parentNode).fadeOut(100);
        });
        Materialize.toast($toastContent, 7000);
    }
}

function getUserInfo() {
    var chipsHolder = $('.chips');
    var aboutHolder = $('#aboutMe');
    var gallery = $('.standart');

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            dataType: "json",
            url: home + "/info/getInfo/",
            success: function (json) {
                if (json.status === "OK") {


                    var information = json.data.information;

                    aboutHolder.val('');
                    aboutHolder.val(information.aboutMe);

                    if (parseInt(information.age) !== 0)
                        $('#age').val(information.age);
                    $('#country').val(information.country);
                    $('#state').val(information.state);
                    $('#' + information.sex).attr('checked', true);
                    $('#' + information.sexPref).attr('checked', true);


                    for (var i = information.interests.length - 1; i >= 0; i--) {
                        chipsHolder.prepend(
                            '<div class="chip">' + information.interests[i] +
                            '<i class="material-icons close">close</i>' + '</div>');
                    }

                    for (var i = 0; i < information.photos.length; i++) {
                        $(gallery[i]).attr('src', information.photos[i]);
                        $(gallery[i]).removeClass();
                    }
                }
            }
        }
    );
}

function changeUserData() {
    var userData = $(".user input");
    var data = {};

    for (var i = 0; i < userData.length; i++) {
        if ($(userData[i]).val().trim() === '')
            continue;
        data[userData[i].name] = $(userData[i]).val().trim();
    }

    $.ajax(
        {
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            data: JSON.stringify(data),
            type: "POST",
            url: home + "/info/user/",
            success: function (json) {
                if (json.status === "OK") {
                    var $toastContent = $('<section>You data was successfuly change</section>').mouseover(function () {
                        $(this.parentNode).fadeOut(100);
                    });
                    Materialize.toast($toastContent, 7000);
                }
            }
        }
    );
}

function setAvatar() {
    var avatar = $('.active img')[0];

    if (avatar.className === 'standart')
        return;
    var path = avatar.src;

    data =
        {
            path: path
        };

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
            },
            data: data,
            type: "POST",
            url: home + "/info/setAvatar/",
            success: function (json) {
                if (json.status === "OK") {
                    var $toastContent = $('<section>Your avatar has been set</section>').mouseover(function () {
                        $(this.parentNode).fadeOut(100);
                    });
                    Materialize.toast($toastContent, 7000);
                }

            }
        }
    );
}

function getPosition() {
    if (navigator.geolocation) {

        navigator.geolocation.getCurrentPosition(function (position) {
            $.ajax(
                {
                    headers: {
                        'Accept': 'application/json',
                    },
                    type: "POST",
                    url: "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + position.coords.latitude +
                    "," + position.coords.longitude + "&key=AIzaSyBX9jTJ5ltH5t_Tqtw_gXzVV-DYtHdenQQ",
                    success: function (json) {
                        if (json.status === "OK") {
                            $("#country").val(json.results[2].address_components[3].long_name);
                            $("#state").val(json.results[3].address_components[1].long_name);
                            UserInformationContext['longitude' +
                            ''] = json.results[0].geometry.location.lng;
                            UserInformationContext['latitude'] = json.results[0].geometry.location.lat;
                        }
                    }
                }
            );
        }, getPositionViaAPI);
    }
}


function getPositionViaAPI() {

    var $toastContent = $('<section>To enable auto geolocation you need allow it in your browser</section>').mouseover(function () {
        $(this.parentNode).fadeOut(100);
    });
    Materialize.toast($toastContent, 7000);
    geoip2.insights(function (json) {
        UserInformationContext.latitude = json.location.latitude;
        UserInformationContext.longitude = json.location.longitude;
    });
}


function checkState(input) {
    if (input.id === 'state')
        var state = $(input).val().trim();
    else
        var state = $(input).val().trim();
    if (state !== '') {
        $.ajax(
            {
                headers: {
                    'Accept': 'application/json',
                },
                type: "POST",
                url: "https://maps.googleapis.com/maps/api/geocode/json?address=" + state + "&key=AIzaSyBX9jTJ5ltH5t_Tqtw_gXzVV-DYtHdenQQ",
                success: function (json) {
                    if (json.status === "ZERO_RESULTS") {

                        var $toastContent = $('<section>Such state do not exist. Check input or use geolocation button</section>').mouseover(function () {
                            $(this.parentNode).fadeOut(100);
                        });
                        Materialize.toast($toastContent, 7000);
                        $(input).val('');
                        return false;
                    } else {
                        UserInformationContext.latitude = json.results[0].geometry.location.lat;
                        UserInformationContext.longitude = json.results[0].geometry.location.lng;
                        return true;
                    }
                }
            }
        );
    }
}
