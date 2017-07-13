var home = "http://localhost:8080/matcha";

window.onload = function () {
    $('.chips').material_chip(
        {
            placeholder: '+Interes'
        }
    );
    var chips = $(".chip");
    chips.each(function (index, chip) {
    $(chip).prependTo('.chips');
    });
    $('.carousel').carousel();
    $('#file').change(uploadFiles());
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
        data[select_info[i].name] = select_info.eq(i).val();

    sexInput.each(function (index, node) {
        if (node.checked)
            data.sex = node.id;
    });
    sexPrefInput.each(function (index, node) {
        if (node.checked)
            data.sexPref = node.id;
    });
    var res = '';
    for(var i = 0; i < interests.length; i++)
    {
        if(i == 10)
            break;
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
    var files = $(".loader")[0].files;
    var form = new FormData();

    $.each(files, function (i, file) {
        form.append("files", file);
        console.log(i);
    });

    $.ajax(
        {
            url: home + "/info/updatePhoto",
            data: form,
            type: 'POST',
            processData: false,
            contentType: false,
            success: function (json) {
                $.each(json.data, function (i, message) {
                    console.log(message);
                })
            }
        });
    console.log(form);
}


function editUser() {

    var data =
        {
            email: $(".input_email_info")[0].value.trim()
        };

    $.ajax({
        url: home + "/info/updateEmail",
        data: data,
        type: "POST",
        success: function (json) {
            json = JSON.parse(json);
            $.each(json.data, function (i, message) {
                console.log(message);
            })
        }
    });
}


function uploadFiles() {
        var files = $('#file').prop('files');
        var photoContainer = $('.carousel').prop('children');

        for (var i = 0; i < files.length; i++) {
            console.log(files[i]);
        }
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
