var home = "http://localhost:8080/matcha";

window.onload = function () {
    $('.chips').material_chip(
        {
            placeholder: 'Type your interest and press enter',
            secondaryPlaceholder: 'Type your interest and press enter'
        }
    );
    getUserInfo();
    $('.carousel').carousel();
};

function changeUserInfo()
{
    var select_info = $(".input_info");
    var sexInput = $('.sex input');
    var sexPrefInput = $('.preferences input');
    var interests = $('.chip');

    var data = {
        sex: '',
        sexPref:'',
        interests:''
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
    if (interests.length > 10)
    {
        Materialize.toast('you can declare only 10 interests', 7000);
        return ;
    }

    for(var i = 0; i < interests.length; i++)
    {
        if ($(interests[i]).text().length > 32)
        {
            Materialize.toast('you interest max length is 32', 7000);
            return ;
        }
        res += $(interests[i]).text();
    }
    data.interests = res.replace(new RegExp("close",'g'),"#");
    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type:"POST",
            data: JSON.stringify(data),
            dataType: "json",
            url: home + "/info/update/",
            success: function (json) {
                if (json.action === "error") {
                    for (var i = 0; i < json.data.length; i++) {
                        console.log(json.data[i]);
                    }
                }
            }
        }
    );
}

function uploadPhoto()
{
    var files = $("#file").prop('files');
    var amount = $('.standart');
    var form = new FormData();

    if (amount.length === 0)
    {
        Materialize.toast('You can download 5 photo with max size of 10MB', 7000);
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
                if (json.status === 'OK')
                {
                    var images = $('.carousel-item');
                    $.each(images, function (index, item) {
                        item.children[0].className = 'standart';
                        item.children[0].src = 'http://localhost:8081/cdn/general/User.png';
                    });
                    getUserInfo();
                    $('.carousel').carousel();
                }else
                {
                    for (var i = 0; i < json.data.length; i++)
                    {
                        console.log(json.data[i]);
                        Materialize.toast(json.data[i], 7000);
                    }
                }
            }
        });
}

function changeCategory(item)
{
    $('.active').removeClass('active');
    $(item).addClass("active");

    switch (item.innerHTML)
    {
        case"general":
            $('.user_photo').addClass('scale-out');
            $('.user').addClass('scale-out');
            $('.info').removeClass('scale-out');
            setTimeout(function () {
                $('.user_photo').css('display', 'none');
                $('.user').css('display', 'none');
                $('.info').css('display','block');
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
                $('.user_photo').css('display','block');
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
                $('.user').css('display','block');
                $('.carousel').carousel();
            }, 300);
            break;
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
            type:"POST",
            dataType: "json",
            url: home + "/info/getInfo/",
            success: function (json) {
                if (json.status === "OK") {
                   for (var i = json.data.interests.length - 1; i >=0;  i--)
                   {
                       chipsHolder.prepend(
                           '<div class="chip">' + json.data.interests[i] +
                           '<i class="material-icons close">close</i>' + '</div>'
                       );
                       aboutHolder.val('');
                       aboutHolder.val(json.data.aboutMe);
                       $('#' + json.data.sex).attr('checked', true);
                       $('#' + json.data.sexPref).attr('checked', true);
                   }
                   for (var i = 0; i < json.data.photos.length;  i++)
                   {
                       $(gallery[i]).attr('src', json.data.photos[i]);
                        $(gallery[i]).removeClass();
                   }

                }
            }
        }
    );
}


function dellPhoto()
{
    var toDell = $('.active img')[0];

    if (toDell.className === 'standart')
        return  ;
    var path = toDell.src;

    data =
        {
            path : path
        };

    $.ajax(
        {
            headers: {
                'Accept': 'application/json',
            },
            data: data,
            type:"POST",
            url: home + "/info/dellPhoto/",
            success: function (json) {
                if (json.status === "OK") {
                    toDell.src = 'http://localhost:8081/cdn/general/User.png';
                    toDell.className = 'standart';
                }

            }
        }
    );
}

function changeUserData() {
    var userData = $(".user input");
    var data = {};

    for (var i = 0; i < userData.length; i++)
    {
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
            type:"POST",
            url: home + "/info/user/",
            success: function (json) {
                if (json.status === "OK") {
                    Materialize.toast("You data was successfuly change", 7000);
                }
            }
        }
    );
}

function displayButton()
{
    if ($('.active img').hasClass('standart'))
        $('.dell_button').addClass('scale-out');
    else
        $('.dell_button').removeClass('scale-out');
}